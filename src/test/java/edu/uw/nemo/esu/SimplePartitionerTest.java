package edu.uw.nemo.esu;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SimplePartitionerTest {

    @Test
    public void assertInputOrderIsRetainedPrepPartitions() throws Exception {
        Partitioner target = new SimplePartitioner();
        List<Integer> input = new ArrayList<Integer>();
        input.add(7);
        input.add(1);
        input.add(31);
        input.add(-5);

        assertEquals(input, target.prepPartitions(input, 3));
    }

}