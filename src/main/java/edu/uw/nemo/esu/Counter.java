package edu.uw.nemo.esu;

/**
 * subgraph counting strategy for ESU algorithm.
 */
public class Counter implements Collector {

    private long count;

    public void add(int[] subgraph) {
        this.count++;
    }

    public long getCount() {
        return count;
    }

}
