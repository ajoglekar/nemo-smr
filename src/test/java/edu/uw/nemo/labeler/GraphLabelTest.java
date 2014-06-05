/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.uw.nemo.labeler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 *
 * @author vartikav
 */
public class GraphLabelTest {
    //@Test
    public void testGraphLabel() {
        FormatType formatType = FormatType.Graph6;
        int vertexCount = 5;
        GraphLabel graphLabel = new GraphLabel();
        
        // graph 1
        List<int[]> edges = new ArrayList<int[]>();
        edges.add( new int[] {1, 4} );
        edges.add( new int[] {1, 5} );
        edges.add( new int[] {2, 3} );
        edges.add( new int[] {3, 5} );
        graphLabel.addSubGraph(edges, vertexCount);
        
        // graph 2
        edges = new ArrayList<int[]>();
        edges.add( new int[] {1, 2} );
        edges.add( new int[] {1, 4} );
        edges.add( new int[] {2, 5} );
        edges.add( new int[] {3, 5} );
        graphLabel.addSubGraph(edges, vertexCount);
        
        // graph 3
        edges = new ArrayList<int[]>();
        edges.add( new int[] {1, 2} );
        edges.add( new int[] {2, 3} );
        edges.add( new int[] {3, 4} );
        edges.add( new int[] {4, 5} );
        graphLabel.addSubGraph(edges, vertexCount);
        
        // graph 4
        edges = new ArrayList<int[]>();
        edges.add( new int[] {1, 2} );
        edges.add( new int[] {1, 3} );
        edges.add( new int[] {2, 5} );
        edges.add( new int[] {4, 5} );
        graphLabel.addSubGraph(edges, vertexCount);
        
        // graph 5
        edges = new ArrayList<int[]>();
        edges.add( new int[] {1, 3} );
        edges.add( new int[] {1, 5} );
        edges.add( new int[] {2, 3} );
        edges.add( new int[] {4, 5} );
        graphLabel.addSubGraph(edges, vertexCount);
        
        Map<String, List<GraphFormat>> canonicalLabels = graphLabel.getCanonicalLabels();
        
        assertEquals(canonicalLabels.size(), 1);
        String expectedCanonicalLabel = "DDW";
        List<GraphFormat> actualGraphs = canonicalLabels.get(expectedCanonicalLabel);
        assertEquals(actualGraphs.size(), 5);
    }
}
