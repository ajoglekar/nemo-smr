package edu.uw.nemo.esu;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by anand on 5/11/16.
 */
public class SparkGeneratorTest {

    @Test
    public void testGenerate() throws Exception {
        SparkGenerator target = new SparkGenerator("full_scere_20140427.csv", 3, "sparky", "local[4]", 4);
        long start = System.currentTimeMillis();
        assertEquals(763322, target.generate());
        System.out.println("total time taken = " + (System.currentTimeMillis() - start) +" milliseconds.");
    }

    @Test
    public void testGeneratek2() throws Exception {
        SparkGenerator target = new SparkGenerator("full_scere_20140427.csv", 2, "sparky", "local[4]", 4);
        long start = System.currentTimeMillis();
        assertEquals(22402, target.generate());
        System.out.println("total time taken = " + (System.currentTimeMillis() - start) +" milliseconds.");
    }

}