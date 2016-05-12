package edu.uw.nemo.io;

import edu.uw.nemo.model.Mapping;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ParserTest {

    @Test
    public void assertValidParse() throws IOException, URISyntaxException {
        Parser target = new Parser();
        Mapping actual = target.parse("full_scere_20140427.csv");
        assertNotNull(actual);
        assertEquals(5132, actual.getNodeCount());
        assertEquals(22735, actual.getLinkCount());
    }

    @Test
    public void assertStats() throws IOException, URISyntaxException {
        Parser target = new Parser();
        printStats(target, "Rnorv20150429.csv");
        printStats(target, "Celeg20150429.csv");
        printStats(target, "Scere20150429.csv");
        printStats(target, "Dmela20150429.csv");
        printStats(target, "Ecoli20150429.out");
        printStats(target, "Hpylo20150429.out");
        printStats(target, "Hsapi20150429.out");
        printStats(target, "Mmusc20150429.out");
        printStats(target, "Scere20140427.out");
    }

    private void printStats(Parser parser, String inputFileName) throws IOException, URISyntaxException {
        Mapping mapping = parser.parse(inputFileName);
        System.out.println("File " + inputFileName + " contains [" + mapping.getNodeCount() + "] nodes and [" + mapping.getLinkCount() + "] links.");
    }

}