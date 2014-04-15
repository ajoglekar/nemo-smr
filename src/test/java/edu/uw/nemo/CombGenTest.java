package edu.uw.nemo;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by joglekaa on 4/14/14.
 */
public class CombGenTest {

    @Test public void assertGenerationOfNonDupCombinations() {

        List<int[]> input = new ArrayList<int[]>();
        input.add(new int[]{1, 3});
        input.add(new int[]{1, 2});
        input.add(new int[]{2, 3});
        input.add(new int[]{1, 4});
        input.add(new int[]{1, 5});
        input.add(new int[]{2, 6});
        input.add(new int[]{2, 7});
        input.add(new int[]{3, 8});
        input.add(new int[]{3, 9});

        CombGen target = new CombGen();
        List<int[]> actual = target.generate(input, 2);
        printCombinations(actual);
        assertEquals(9, actual.size());

        List<int[]> actual2 = target.generate(input, 3);
        printCombinations(actual2);

    }

    private void printCombinations(List<int[]> actual) {
        for (int i = 0; i < actual.size(); i++) {
            int[] cur = actual.get(i);
            int length = cur.length;
            for (int j = 0; j < length; j++) {
                System.out.print(cur[j]);
                if ( j < length - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println("");
        }
        System.out.println("\ntotal [" + actual.size() + "] elements.");
    }

}
