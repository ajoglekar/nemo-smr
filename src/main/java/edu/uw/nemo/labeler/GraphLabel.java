/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.uw.nemo.labeler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.*;

/**
 *
 * @author vartikav
 */
// Creates the canonical labelling for a given graph using nauty library's tool labelg.exe
public class GraphLabel {
    public GraphLabel() {
        this.subGraphs = new LinkedHashMap<GraphFormat, Integer>();
    }
    
    public void addSubGraph(List<int[]> edges, int vertexCount) {
        GraphFormat graphFormat = new GraphFormat(FormatType.Graph6, edges, vertexCount);
        graphFormat.formatGraph();
        Integer count = this.subGraphs.get(graphFormat);
        if (count == null) {
            this.subGraphs.put(graphFormat, 1);
        }
        else {
            this.subGraphs.put(graphFormat, count+1);
        }
    }
    
    // Computes the canonanical labelling for all the sub-graphs that it cureently holds.
    // The count for each labelling, after aggregation, is the value of the map entry.
    // Thus it aggregate all canonical sub-graphs together.
    public Map<String, Integer> getCanonicalLabels() {
        Map<String, Integer> canonicalLabels = new HashMap<String, Integer>();
        Map<GraphFormat, String> graphWithCanonicalLabels = generateCanonicalLabels(this.subGraphs.keySet());
        for (Map.Entry<GraphFormat, Integer> subGraph : this.subGraphs.entrySet()) {
            String canonicalLabel = graphWithCanonicalLabels.get(subGraph.getKey());
            Integer canonicalLabelCount = canonicalLabels.get(canonicalLabel);
            if (canonicalLabelCount == null) {
                canonicalLabels.put(canonicalLabel, subGraph.getValue());
            }
            else {
                canonicalLabels.put(canonicalLabel, canonicalLabelCount + subGraph.getValue());
            }
        }
        
        return canonicalLabels;
    }
    
    private static Map<GraphFormat, String> generateCanonicalLabels(Set<GraphFormat> graphFormats) {
        // Create a file with all the entries from graphFormats, one at each line.
        BufferedWriter graphWriter = null;
        try {
            graphWriter = new BufferedWriter(new FileWriter(LabelGInputFile));
            for (GraphFormat graph : graphFormats) {
                graphWriter.write(graph.toString());
                graphWriter.write('\n');
            }
        }
        catch (IOException ioe) {
            System.err.println("Error while writing graphs for labelg. Error msg: " + ioe.getMessage());
            return null;
        }
        finally {
            if (graphWriter != null) {
                try {
                    graphWriter.close();
                }
                catch (IOException ioe) {
                    System.err.println("Error while writing graphs for labelg. Error msg: " + ioe.getMessage());
                    return null;
                }
            }
        }
        
        // Call labelg.exe with the file formed and a output file.
        try {
            String[] args = {"labelg.exe", "-i3", "-I1:100", LabelGInputFile, LabelGOutputFile};
            Process labelg = Runtime.getRuntime().exec(args);
            
            OutputStream out = labelg.getOutputStream();
            out.close();
            
            BufferedReader inStr = new BufferedReader(new java.io.InputStreamReader(labelg.getInputStream()));
            String line = inStr.readLine();
            while (line != null) {
                line = inStr.readLine();
            }
            
            inStr.close();
            
            BufferedReader errStr = new BufferedReader(new java.io.InputStreamReader(labelg.getErrorStream()));
            line = errStr.readLine();
            while (line != null) {
                line = errStr.readLine();
            }
            
            errStr.close();
            
            int returnCode = -1;
            try {
                returnCode = labelg.waitFor();
                System.out.println(returnCode);
            }
            catch (InterruptedException ie) {
                System.err.println("Error while generating canonical labels. Error msg: " + ie.getMessage());
            }
        }
        catch (IOException ioe) {
            System.err.println("Error while generating canonical labels. Error msg: " + ioe.getMessage());
        }
        
        // Read the outputfile to get the canonical label for each graphFormat
        Map<GraphFormat, String> canonicalLabels = new HashMap<GraphFormat, String>();
        BufferedReader canonicalLabelReader = null;
        try {
            canonicalLabelReader = new BufferedReader(new FileReader(LabelGOutputFile));
            Iterator<GraphFormat> itr = graphFormats.iterator();
            String label = canonicalLabelReader.readLine();
            while (label != null) {
                canonicalLabels.put(itr.next(), label);
                label = canonicalLabelReader.readLine();
            }
        }
        catch (IOException ioe) {
            System.err.println("Error while reading canonical labels produced by labelg. Error msg: " + ioe.getMessage());
            return null;
        }
        finally {
            if (canonicalLabelReader != null) {
                try {
                    canonicalLabelReader.close();
                }
                catch (IOException ioe) {
                    System.err.println("Error while reading canonical labels produced by labelg. Error msg: " + ioe.getMessage());
                    return null;
                }
            }
        }
        
        return canonicalLabels;
    }
    
    private Map<GraphFormat, Integer> subGraphs;
    private final static String LabelGInputFile = "InputGraphs.g6";
    private final static String LabelGOutputFile = "OutputGraphs.g6";
}
