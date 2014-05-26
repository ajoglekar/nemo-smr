package edu.uw.nemo.proto.converter;

import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;

/**
 * Created by joglekaa on 4/15/14.
 */
public class DIPConverterTest {

    @Test public void assertConverterHandlesMultipleLinksCorrectly() throws IOException {
        DIPConverter target = new DIPConverter();

        int count = target.convert("/Users/joglekaa/pers/learning/uw/mscsse/6 network motif/resources/Scere20140117.txt"
                , "/Users/joglekaa/pers/learning/uw/mscsse/6 network motif/resources/temp.out");

        assertEquals(22637, count);

        /*
        (- 22638 22465) == 173
         */
    }

}
