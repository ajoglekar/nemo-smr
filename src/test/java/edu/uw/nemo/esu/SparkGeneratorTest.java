package edu.uw.nemo.esu;

import org.junit.Test;

import static org.junit.Assert.*;

public class SparkGeneratorTest {

    @Test
    public void testGenerate() throws Exception {
        SparkGenerator target = new SparkGenerator("src/test/resources/full_scere_20140427.csv", 3, "sparky", "local[4]", 4);
        long start = System.currentTimeMillis();
        assertEquals(763322, target.generate(new ESUAlgorithm()));
        System.out.println("total time taken = " + (System.currentTimeMillis() - start) +" milliseconds.");
    }

    @Test
    public void testGeneratekDirPathK2OldData() throws Exception {
        SparkGenerator target = new SparkGenerator("src/test/resources/full_scere_20140427.csv", 2, "sparky", "local[4]", 4);
        long start = System.currentTimeMillis();
        assertEquals(22402, target.generate(new ESUAlgorithm()));
        System.out.println("total time taken = " + (System.currentTimeMillis() - start) +" milliseconds.");
    }

    @Test
    public void testGeneratekDirPathK2() throws Exception {
        SparkGenerator target = new SparkGenerator("src/test/resources/Scere20150429.txt", 2, "sparky", "local[4]", 4);
        long start = System.currentTimeMillis();
        assertEquals(22531, target.generate(new ESUAlgorithm()));
        System.out.println("total time taken = " + (System.currentTimeMillis() - start) +" milliseconds.");
    }

    @Test
    public void testGeneratekCountMain() throws Exception {
        SparkGenerator target = new SparkGenerator("src/test/resources/Scere20150429.txt", 2, "sparky", "local[4]", 4);
        long start = System.currentTimeMillis();
        assertEquals(5154, target.generate(new NullESUAlgorithm()));
        System.out.println("total time taken = " + (System.currentTimeMillis() - start) +" milliseconds.");
    }

}