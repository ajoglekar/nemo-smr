package edu.uw.nemo.esu;

import edu.uw.nemo.model.Mapping;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ESUAlgorithm implements Serializable {

    public List<int[]> enumerateSubgraphs(Mapping mapping, int size) {
        long start = System.currentTimeMillis();
        Extractor extractor = new Extractor();
        List<Integer> nodes = mapping.getIds();
        for (Integer node : nodes) {
            List<Integer> subGraph = new ArrayList<Integer>();
            subGraph.add(node);
            List<Integer> extension = filterGreater(mapping.getNeighbours(node), node);
            extendSubgraph(mapping, subGraph, extension, size, extractor);
        }
        System.out.println("enumerate subgraph took " + (System.currentTimeMillis() - start) + " milliseconds.");

        return extractor.getSubgraphs();
    }

    public void enumerateSubgraphs(Integer vertex, Mapping mapping, int size, Collector collector) {
        List<Integer> subGraph = new ArrayList<Integer>();
        subGraph.add(vertex);
        List<Integer> extension = filterGreater(mapping.getNeighbours(vertex), vertex);
        extendSubgraph(mapping, subGraph, extension, size, collector);
    }

    private List<Integer> filterGreater(List<Integer> neighbours, Integer node) {
        ArrayList<Integer> filetered = new ArrayList<Integer>();
        for (int i : neighbours) {
            if (i > node) {
                filetered.add(i);
            }
        }
        return filetered;
    }

    private void extendSubgraph(Mapping mapping, List<Integer> subGraph
            , List<Integer> extension, int size, Collector collector) {
        int currentSize = subGraph.size();
        if (currentSize >= size) {
            int[] x = new int[size];
            for (int i = 0; i < size; i++) {
                x[i] = subGraph.get(i);
            }
            collector.add(x);
        } else {
            Integer root = subGraph.get(0);
            while (extension.size() > 0) {
                int vertex = extension.get(0);
                extension.remove(0);
                Set<Integer> nExt = new HashSet<Integer>(extension);
                nExt.addAll(filterGreater(getExclusiveNeighbours(mapping, subGraph, vertex), root));
                nExt.remove(vertex);
                subGraph.add(vertex);
                extendSubgraph(mapping, subGraph, new ArrayList<Integer>(nExt), size, collector);
                subGraph.remove(subGraph.size() - 1);
            }
        }
    }

    private List<Integer> getExclusiveNeighbours(Mapping mapping, List<Integer> subGraph, int node) {
        Set<Integer> old = new HashSet<Integer>();
        for (Integer x : subGraph) {
            old.addAll(mapping.getNeighbours(x));
        }
        Set<Integer> n = new HashSet<Integer>(mapping.getNeighbours(node));
        n.removeAll(old);
        return new ArrayList<Integer>(n);
    }

}
