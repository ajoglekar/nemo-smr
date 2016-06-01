package edu.uw.nemo.esu;

import java.util.List;

public interface Partitioner {

    List<Integer> prepPartitions(List<Integer> input, int numPartitions);

}
