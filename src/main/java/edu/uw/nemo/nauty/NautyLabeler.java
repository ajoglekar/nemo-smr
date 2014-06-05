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
                    label.addSubGraph(getEdges(mapping, subGraph), subGraph.length);
                }
            }
        }

        return label.getCanonicalLabels();
    }

    private List<int[]> getEdges(Mapping mapping, int[] subGraph) {
        ArrayList<int[]> links = new ArrayList<int[]>();
        for (int from = 0; from < subGraph.length; from++) {
            Set<Integer> neighbours = new HashSet<Integer>(mapping.getNeighbours(from));
            for (int to = from + 1; to < subGraph.length; to++) {
                if (neighbours.contains(to)) {
                    links.add(makeLink(from, to));
                }
            }
        }

        return links;
    }

    private int[] makeLink(int from, int to) {
        int[] link = new int[2];
        link[0] = from;
        link[1] = to;
        return link;
    }

}
