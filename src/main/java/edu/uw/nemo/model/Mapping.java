package edu.uw.nemo.model;

import java.io.Serializable;
import java.util.*;

public class Mapping implements Serializable {

    private final List<String[]> adjList;
    private final Map<String, Integer> nodeToId;
    private final Map<Integer, String> idToNode;
    private final Map<Integer, Set<Integer>> adjMapping;

    public Mapping(List<String[]> adjList, Map<String, Integer> nodeToId, Map<Integer, String> idToNode, Map<Integer, Set<Integer>> adjMapping) {
        this.adjList = adjList;
        this.nodeToId = nodeToId;
        this.idToNode = idToNode;
        this.adjMapping = adjMapping;
    }

    public int getNodeCount() {
        return (adjMapping != null) ? adjMapping.size() : 0;
    }

    public int getLinkCount() {
        return adjList != null ? adjList.size() : 0;
    }

    public List<Integer> getIds() {
        return new ArrayList<Integer>(adjMapping.keySet());
    }

    public List<Integer> getNeighbours(Integer node) {
        return new ArrayList<Integer>(this.adjMapping.get(node));
    }

    public List<int[]> getEdges(int[] subGraph) {
        ArrayList<int[]> links = new ArrayList<int[]>();
        for (int from = 0; from < subGraph.length; from++) {
            Set<Integer> neighbours = new HashSet<Integer>(getNeighbours(from));
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
