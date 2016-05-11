package edu.uw.nemo.proto.converter;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;

import static junit.framework.Assert.assertEquals;

/**
 * Created by joglekaa on 4/15/14.
 */
public class DIPConverterTest {

    @Test
    public void assertConverterHandlesMultipleLinksCorrectly() throws IOException, URISyntaxException {
        DIPConverter target = new DIPConverter();

        assertEquals(558, target.convert("Rnorv20150429.txt", "Rnorv20150429.csv"));
        assertEquals(4138, target.convert("Celeg20150429.txt", "Celeg20150429.csv"));
        assertEquals(22874, target.convert("Scere20150429.txt", "Scere20150429.csv"));
        assertEquals(23210, target.convert("Dmela20150429.txt", "Dmela20150429.csv"));
        assertEquals(22735, target.convert("Scere20140427.txt", "Scere20140427.out")); // old file, for ref
        assertEquals(12246, target.convert("Ecoli20150429.txt", "Ecoli20150429.out"));
        assertEquals(1432, target.convert("Hpylo20150429.txt", "Hpylo20150429.out"));
        assertEquals(6707, target.convert("Hsapi20150429.txt", "Hsapi20150429.out"));
        assertEquals(2265, target.convert("Mmusc20150429.txt", "Mmusc20150429.out"));
    }

}
