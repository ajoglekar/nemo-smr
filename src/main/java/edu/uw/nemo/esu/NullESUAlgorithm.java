package edu.uw.nemo.esu;

import edu.uw.nemo.model.Mapping;

/**
 * do nothing version at vertex level
 * used for estimating setup / teardown cost of the algorithm
 */
public class NullESUAlgorithm extends ESUAlgorithm {

    @Override
    public void enumerateSubgraphs(Integer vertex, Mapping mapping, int size, Collector collector) {
        collector.add(new int[size]);
    }

}
