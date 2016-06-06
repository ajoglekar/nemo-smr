package edu.uw.nemo.esu;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ESUAlgorithmTest {

    @Test
    public void assertConversionToMap() {
        ESUAlgorithm target = new ESUAlgorithm();
        List<Integer> input = new ArrayList<Integer>();
        input.add(1);
        input.add(2);
        input.add(3);
        input.add(4);
        input.add(5);
        input.add(6);
        Map<Integer, List<Integer>> actual = target.listToMap(input);
        Map<Integer, List<Integer>> expected = buildExpected();
        assertEquals(expected, actual);
    }

    @Test
    public void assertEmptyListConversionToMap() {
        ESUAlgorithm target = new ESUAlgorithm();
        List<Integer> input = new ArrayList<Integer>();
        Map<Integer, List<Integer>> actual = target.listToMap(input);
        Map<Integer, List<Integer>> expected = new HashMap<Integer, List<Integer>>();
        assertEquals(expected, actual);
    }

    private Map<Integer, List<Integer>> buildExpected() {
        Map<Integer, List<Integer>> expected = new HashMap<Integer, List<Integer>>();
        ArrayList<Integer> value1 = new ArrayList<Integer>();
        value1.add(2);
        value1.add(3);
        value1.add(4);
        value1.add(5);
        value1.add(6);
        expected.put(1, value1);

        ArrayList<Integer> value2 = new ArrayList<Integer>();
        value2.add(3);
        value2.add(4);
        value2.add(5);
        value2.add(6);
        expected.put(2, value2);

        ArrayList<Integer> value3 = new ArrayList<Integer>();
        value3.add(4);
        value3.add(5);
        value3.add(6);
        expected.put(3, value3);

        ArrayList<Integer> value4 = new ArrayList<Integer>();
        value4.add(5);
        value4.add(6);
        expected.put(4, value4);

        ArrayList<Integer> value5 = new ArrayList<Integer>();
        value5.add(6);
        expected.put(5, value5);

        ArrayList<Integer> value6 = new ArrayList<Integer>();
        expected.put(6, value6);

        return expected;
    }

}