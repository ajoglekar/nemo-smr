package edu.uw.nemo.esu;

import edu.uw.nemo.model.Mapping;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.broadcast.Broadcast;

import java.io.Serializable;
import java.util.*;

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

    public void enumerateSubgraphsS(Integer root, Broadcast<Mapping> mapping, int size, Collector collector, JavaSparkContext sc) {
        List<Integer> subGraph = new ArrayList<Integer>();
        subGraph.add(root);
        List<Integer> extension = filterGreater(mapping.getValue().getNeighbours(root), root);
        if (extension.size() > 5) {
            JavaRDD<Integer> extRDD = sc.parallelize(extension, (extension.size() / 5));
            JavaRDD<Integer[]> subgraphRDD = extRDD.mapPartitions(new FlatMapFunction<Iterator<Integer>, Integer[]>() {
                public Iterable<Integer[]> call(Iterator<Integer> integerIterator) throws Exception {
                    ArrayList<Integer[]> counts = new ArrayList<Integer[]>();

                    return counts;
                }
            });
            Long count = subgraphRDD.count();
        } else {
            extendSubgraph(subGraph, extension, root, size, mapping.getValue(), collector);
        }
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
            Map<Integer, List<Integer>> extMap = listToMap(extension);
            for (Map.Entry<Integer, List<Integer>> extEntry : extMap.entrySet()) {
                    int current = extEntry.getKey();
                    Set<Integer> nExt = new HashSet<Integer>(extEntry.getValue());
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

    Map<Integer, List<Integer>> listToMap(List<Integer> ext) {
        Map<Integer, List<Integer>> result = new HashMap<Integer, List<Integer>>();
        for (int i = 0; i < ext.size(); i++) {
            result.put(ext.get(i), sublist(ext, i));
        }
        return result;
    }

    private List<Integer> sublist(List<Integer> ext, int i) {
        if (i+1 > ext.size()) {
            return new ArrayList<Integer>();
        } else {
            return ext.subList(i + 1, ext.size());
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
