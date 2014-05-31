package edu.uw.nemo.esu;

import edu.uw.nemo.model.Mapping;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ESUGenTest {

    private final GenUtil genUtil = new GenUtil();

    @Test
    public void assertDefaultGen() {
        Mapping input = genUtil.buildMapping(genUtil.setupAL());

        ESUGen target = new ESUGen();
        List<int[]> actual = target.enumerateSubgraphs(input, 2);
//        genUtil.printCombinations(actual);
        assertEquals(9, actual.size());

        List<int[]> actual2 = target.enumerateSubgraphs(input, 3);
        assertEquals(16, actual2.size());
//        genUtil.printCombinations(actual2);

        List<int[]> actual3 = target.enumerateSubgraphs(input, 4);
        assertEquals(24, actual3.size());
        genUtil.printCombinations(actual3);
    }

}