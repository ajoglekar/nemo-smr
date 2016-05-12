package edu.uw.nemo.esu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anand on 5/12/16.
 */
public class Extractor implements Collector {

    private List<int[]> subgraphs = new ArrayList<int[]>();

    public void add(int[] subgraph) {
        subgraphs.add(subgraph);
    }

    public List<int[]> getSubgraphs() {
        return new ArrayList<int[]>(subgraphs);
    }

}
