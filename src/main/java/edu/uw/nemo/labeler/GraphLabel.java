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
    public GraphLabel()
    {
        this.subGraphs = new HashMap<String, Integer>();
        this.graphFormat = new GraphFormat(FormatType.Graph6);
    }
    
    public void AddSubGraph(List<int[]> graphAsAdjacencyList)
    {
        String formattedGraph = this.graphFormat.FormatGraph(graphAsAdjacencyList);
        Integer count = this.subGraphs.get(formattedGraph);
        if (count == null)
        {
            this.subGraphs.put(formattedGraph, 1);
        }
        else
        {
            this.subGraphs.put(formattedGraph, count+1);
        }
    }
    
    // Computes the canonanical labelling for all the sub-graphs that it cureently holds.
    // The count for each labelling, after aggregation, is the value of the map entry.
    // Thus it aggregate all canonical sub-graphs together.
    public Map<String, Integer> GetCanonicalLabels()
    {
        // TODO: Implement
        return this.subGraphs;
    }
    
    public String GetCanonicalLabel(List<int[]> adjacencyList)
    {
        GraphFormat graphFormatG6 = new GraphFormat(FormatType.Graph6);
        String formattedGraph = graphFormatG6.FormatGraph(adjacencyList);
        
        // TODO: Call labelg.exe via Runtime.getRuntime ().exec
        return "";
    }
    
    private Map<String, Integer> subGraphs;
    private GraphFormat graphFormat;
}
