/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.uw.nemo.labeler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 *
 * @author vartikav
 */
// Creates the canonical labelling for a given graph using nauty library's tool labelg.exe
public class GraphLabel {
    public GraphLabel() {
        this.subGraphs = new LinkedHashMap<String, List<GraphFormat>>();
    }
    
    public void addSubGraph(List<int[]> edges, int vertexCount) {
        GraphFormat graphFormat = new GraphFormat(FormatType.Graph6, edges, vertexCount);
        graphFormat.formatGraph();
        List<GraphFormat> graphs = this.subGraphs.get(graphFormat.toString());
        if (graphs == null) {
            graphs = new ArrayList<GraphFormat>();
            this.subGraphs.put(graphFormat.toString(), graphs);
        }
        
        graphs.add(graphFormat);
    }
    
    // Computes the canonanical labelling for all the sub-graphs that it currently holds.
    // The list of original graphs, after aggregation, is the value of the map entry.
    // Thus it aggregate all canonical sub-graphs together.
    public Map<String, List<GraphFormat>> getCanonicalLabels() {
        Map<String, List<GraphFormat>> canonicalLabels = new HashMap<String, List<GraphFormat>>();
        Map<String, String> graphWithCanonicalLabels = generateCanonicalLabels(this.subGraphs.keySet());
        for (Map.Entry<String, List<GraphFormat>> subGraph : this.subGraphs.entrySet()) {
            String canonicalLabel = graphWithCanonicalLabels.get(subGraph.getKey());
            List<GraphFormat> canonicalGraphs = canonicalLabels.get(canonicalLabel);
            if (canonicalGraphs == null) {
                canonicalGraphs = new ArrayList<GraphFormat>();
                canonicalLabels.put(canonicalLabel, canonicalGraphs);
            }
            
            canonicalGraphs.addAll(subGraph.getValue());
        }
        
        return canonicalLabels;
    }
    
    private static Map<String, String> generateCanonicalLabels(Set<String> graphLabels) {
        // Create a file with all the entries from graphLabels, one at each line.
        BufferedWriter graphWriter = null;
        try {
            graphWriter = new BufferedWriter(new FileWriter(LabelGInputFile));
            for (String graphLabel : graphLabels) {
                graphWriter.write(graphLabel);
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
        Map<String, String> canonicalLabels = new HashMap<String, String>();
        BufferedReader canonicalLabelReader = null;
        try {
            canonicalLabelReader = new BufferedReader(new FileReader(LabelGOutputFile));
            Iterator<String> itr = graphLabels.iterator();
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
    
    private Map<String, List<GraphFormat>> subGraphs;
    private final static String LabelGInputFile = "InputGraphs.g6";
    private final static String LabelGOutputFile = "OutputGraphs.g6";
}
