package edu.uw.nemo.esu;

/**
 * strategy for supporting counting or extraction.
 */
public interface Collector {

    void add(int[] subgraph);

}
