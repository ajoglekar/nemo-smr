package edu.uw.nemo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Mapping {

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

}
