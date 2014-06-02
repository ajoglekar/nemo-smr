package edu.uw.nemo;

import edu.uw.nemo.esu.DirectCalculator;
import edu.uw.nemo.esu.ESUGen;
import edu.uw.nemo.io.Parser;
import edu.uw.nemo.nauty.NautyLabeler;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class NemoControllerTest {

    @Test
    public void assertValidMotifCount() throws IOException {
        NemoController target = new NemoController(new Parser(), new ESUGen(), new NautyLabeler(), new DirectCalculator());
//        Map<String, Set<int[]>> actual = target.extract("/Users/joglekaa/pers/learning/uw/mscsse/6 network motif/resources/temp.out", 3);
        Map<String, Set<int[]>> actual = target.extract("C:\\Users\\anand\\dev\\network-motif\\src\\main\\resources\\temp.out", 3);
        assertEquals(23, actual.size());
    }

}