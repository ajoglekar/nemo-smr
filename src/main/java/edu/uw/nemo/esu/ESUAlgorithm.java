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
        List<Integer> vertices = mapping.getIds();
        for (Integer vertex : vertices) {
            enumerateSubgraphs(vertex, mapping, size, extractor);
        }
        System.out.println("enumerate subgraph took " + (System.currentTimeMillis() - start) + " milliseconds.");

        return extractor.getSubgraphs();
    }

    public void enumerateSubgraphs(Integer root, Mapping mapping, int size, Collector collector) {
        List<Integer> subGraph = new ArrayList<Integer>();
        subGraph.add(root);
        List<Integer> extension = filterGreater(mapping.getNeighbours(root), root);
        extendSubgraph(subGraph, extension, root, size, mapping, collector);
    }

    private void extendSubgraph(List<Integer> subGraph, List<Integer> extension, Integer root, int size, Mapping mapping,
                                Collector collector) {
        int currentSize = subGraph.size();
        if (currentSize >= size) {
            int[] x = new int[size];
            for (int i = 0; i < size; i++) {
                x[i] = subGraph.get(i);
            }
            collector.add(x);
        } else {
            while (extension.size() > 0) {
                int current = extension.get(0);
                extension.remove(0);
                Set<Integer> nExt = new HashSet<Integer>(extension);
                nExt.addAll(filterGreater(getExclusiveNeighbours(mapping, subGraph, current), root));
                nExt.remove(current);
                List<Integer> newSubgraph = new ArrayList<Integer>(subGraph);
                newSubgraph.add(current);
                extendSubgraph(newSubgraph, new ArrayList<Integer>(nExt), root, size, mapping, collector);
            }
        }
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
