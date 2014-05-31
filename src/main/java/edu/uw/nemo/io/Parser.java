package edu.uw.nemo.io;

import edu.uw.nemo.model.Mapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Parser {

    public Mapping parser(String fileName) throws IOException {
        return map(readFile(fileName));
    }

    // read file
    // split record
    List<String[]> readFile(String srcFile) throws IOException {
        long start = System.currentTimeMillis();
        List<String[]> result = new ArrayList<String[]>();
        BufferedReader input = openInputFile(srcFile);
        String line = null;
        while ((line = input.readLine()) != null) {
            String[] split = line.trim().split("\t");
            String node1 = split[0];
            String node2 = split[1];
            if (node1 != null && node2 != null) {
                split[0] = node1.trim();
                split[1] = node2.trim();
                result.add(split);
            }
        }
        System.out.println("loading for file too " + (System.currentTimeMillis() - start) + " milliseconds.");
        return result;
    }

    // map string -> string
    // map string -> int
    // map int -> string
    Mapping map(List<String[]> aL) {
        long start = System.currentTimeMillis();
        Map<String, Integer> nodeToId = new HashMap<String, Integer>();
        Map<Integer, String> idToNode = new HashMap<Integer, String>();
        Map<Integer, Set<Integer>> adjMapping = new HashMap<Integer, Set<Integer>>();
        int count = 0;
        for (String[] link : aL) {
            int id1 = mapNode(nodeToId, idToNode, count, link[0]);
            if (id1 == count) {
                count++;
            }
            int id2 = mapNode(nodeToId, idToNode, count, link[1]);
            if (id2 == count) {
                count++;
            }
            setupAdjMappings(adjMapping, id1, id2);
            setupAdjMappings(adjMapping, id2, id1);
        }
        System.out.println("mapping to ids took " + (System.currentTimeMillis() - start) + " milliseconds.");

        return new Mapping(aL, nodeToId, idToNode, adjMapping);
    }

    private void setupAdjMappings(Map<Integer, Set<Integer>> adjMapping, int id1, int id2) {
        Set<Integer> aSet = adjMapping.get(id1);
        if (aSet == null) {
            aSet = new HashSet<Integer>();
            adjMapping.put(id1, aSet);
        }
        aSet.add(id2);
    }

    private int mapNode(Map<String, Integer> nodeToId, Map<Integer, String> idToNode, int count, String node) {
        Integer id = nodeToId.get(node);
        if (id == null) {
            id = count;
            nodeToId.put(node, id);
            idToNode.put(id, node);
        }
        return id;
    }

    private BufferedReader openInputFile(String srcFile) throws IOException {
        Path path = FileSystems.getDefault().getPath(srcFile);
        Charset charset = Charset.forName("US-ASCII");
        return Files.newBufferedReader(path, charset);
    }

}
