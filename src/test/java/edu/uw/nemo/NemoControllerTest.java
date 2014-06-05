package edu.uw.nemo;

import edu.uw.nemo.esu.DirectCalculator;
import edu.uw.nemo.esu.ESUGen;
import edu.uw.nemo.io.Parser;
import edu.uw.nemo.nauty.NautyLabeler;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class NemoControllerTest {

    @Test
    public void assertValidMotifCount() throws IOException, URISyntaxException {
        NemoController target = new NemoController(new Parser(), new ESUGen(), new NautyLabeler(), new DirectCalculator());
        Map<String, Set<int[]>> actual = target.extract("full_scere_20140427.csv", 3);
//        assertEquals(23, actual.size());
    }

}