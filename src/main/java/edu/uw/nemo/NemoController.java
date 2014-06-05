package edu.uw.nemo;

import edu.uw.nemo.esu.DirectCalculator;
import edu.uw.nemo.esu.ESUGen;
import edu.uw.nemo.io.Parser;
import edu.uw.nemo.labeler.GraphFormat;
import edu.uw.nemo.model.Mapping;
import edu.uw.nemo.nauty.NautyLabeler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * extracts significant subgraphs using ESU algorithm and DIRECT calculation of concentration
 * Created by joglekaa on 5/30/14.
 */
public class NemoController {

    private Parser parser;
    private ESUGen generator;
    private NautyLabeler labeler;
    private DirectCalculator calculator;

    public NemoController(Parser parser, ESUGen generator, NautyLabeler labeler, DirectCalculator calculator) {
        this.parser = parser;
        this.generator = generator;
        this.labeler = labeler;
        this.calculator = calculator;
    }

    Map<String, Set<int[]>> extract(String fileName, int size) throws IOException, URISyntaxException {
        // build Mapping with parser
        Mapping mapping = parser.parser(fileName);
        // generate motifs with ESUGen
        List<int[]> subgraphs = generator.enumerateSubgraphs(mapping, size);
        System.out.println(subgraphs.size());
        // get canonical labels with GraphLabel
        //todo: fix mapping integration
//        Map<String, List<GraphFormat>> canonicalSubgraphs = labeler.mapCanonical(mapping, subgraphs);
        // for each label, get standard concentration with DirectCalc
        //todo: fix standard conc integration
//        Map<String, List<Integer>> conc = calculator.standardConcentrations(canonicalSubgraphs);

        return new HashMap<String, Set<int[]>>();
    }


}
