package edu.uw.nemo.io;

import edu.uw.nemo.model.Mapping;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ParserTest {

    @Test
    public void assertValidParse() throws IOException {
        Parser target = new Parser();
        Mapping actual = target.parser("/Users/joglekaa/pers/learning/uw/mscsse/6 network motif/resources/temp.out");
        assertNotNull(actual);
        assertEquals(5119, actual.getNodeCount());
        assertEquals(22637, actual.getLinkCount());
    }

}