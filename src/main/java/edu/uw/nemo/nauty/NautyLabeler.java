package edu.uw.nemo.nauty;

import edu.uw.nemo.labeler.GraphFormat;
import edu.uw.nemo.labeler.GraphLabel;
import edu.uw.nemo.model.Mapping;

import java.util.*;

/**
 * converts a list of subgraphs to map of canonical labels to equivalent subgraphs
 * Created by anand joglekar on 5/30/14.
 */
public class NautyLabeler {
    public Map<String, List<GraphFormat>> mapCanonical(Mapping mapping, List<int[]> subgraphs) {
        GraphLabel label = new GraphLabel();
        if (subgraphs != null) {
            for (int[] subGraph : subgraphs) {
                if (subGraph != null && subGraph.length > 0) {
                    label.addSubGraph(mapping.getEdges(subGraph), subGraph.length);
                }
            }
        }

        return label.getCanonicalLabels();
    }

}
