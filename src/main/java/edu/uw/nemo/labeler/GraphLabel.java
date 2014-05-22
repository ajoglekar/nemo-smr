/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.uw.nemo.labeler;

import java.util.*;

/**
 *
 * @author vartikav
 */
// Creates the canonical labelling for a given graph using nauty library's tool labelg.exe
public class GraphLabel {
    public GraphLabel() {
        this.subGraphs = new HashMap<GraphFormat, Integer>();
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
        // TODO: Create a file with all the entries from graphFormats, one at each line.
        // TODO: Call labelg.exe via Runtime.getRuntime ().exec with the file formed and a output file.
        // TODO: Read the outputfile to get the canonical label for each graphFormat
        return new HashMap<GraphFormat, String>();
    }
    
    private Map<GraphFormat, Integer> subGraphs;
}
