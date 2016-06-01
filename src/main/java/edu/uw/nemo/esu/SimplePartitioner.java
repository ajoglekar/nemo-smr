package edu.uw.nemo.esu;

import java.util.ArrayList;
import java.util.List;

/**
 * Do nothing partitioner
 */
public class SimplePartitioner implements Partitioner {

    public List<Integer> prepPartitions(List<Integer> input, int numPartitions) {
        return new ArrayList<Integer>(input);
    }

}
