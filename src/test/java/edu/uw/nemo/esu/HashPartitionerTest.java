package edu.uw.nemo.esu;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class HashPartitionerTest {

    @Test
    public void assertHashOrderingInPrepPartitions() throws Exception {
        Partitioner target = new HashPartitioner();
        List<Integer> input = new ArrayList<Integer>();
        input.add(0);
        input.add(1);
        input.add(2);
        input.add(3);
        input.add(4);
        input.add(5);
        input.add(6);
        input.add(7);

        List<Integer> expected = new ArrayList<Integer>();
        expected.add(0);
        expected.add(3);
        expected.add(6);
        expected.add(1);
        expected.add(4);
        expected.add(7);
        expected.add(2);
        expected.add(5);

        assertEquals(expected, target.prepPartitions(input, 3));
    }

}
