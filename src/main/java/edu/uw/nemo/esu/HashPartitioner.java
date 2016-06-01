package edu.uw.nemo.esu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HashPartitioner implements Partitioner {

    public List<Integer> prepPartitions(List<Integer> input, final int numPartitions) {
        ArrayList<Integer> result = new ArrayList<Integer>(input);
        Collections.sort(result, new Comparator<Integer>() {

            public int compare(Integer o1, Integer o2) {
                return (o1 % numPartitions) - (o2 % numPartitions);
            }

        });

        return result;
    }

}
