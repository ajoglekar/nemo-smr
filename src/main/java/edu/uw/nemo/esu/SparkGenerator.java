package edu.uw.nemo.esu;

import edu.uw.nemo.io.Parser;
import edu.uw.nemo.model.Mapping;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.broadcast.Broadcast;

import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.*;

/**
 * ESU on Spark
 */
public class SparkGenerator implements Serializable {

    private final String fileName;
    private final int k;
    private final String appName;
    private final String master;
    private final int n;

    // full_scere_20140427.csv 3 sparky local[4] 12
    public static void main(String[] args) throws IOException, URISyntaxException {
        int length = args.length;
        String fileName = null;
        int k = 0;
        String appName = null;
        String master = null;
        int n = 0;
        if (length > 0) {
            fileName = args[0];
        }
        if (length > 1) {
            k = Integer.parseInt(args[1]);
        }
        if (length > 2) {
            appName = args[2];
        }
        if (length > 3) {
            master = args[3];
        }
        if (length > 4) {
            n = Integer.parseInt(args[4]);
        }
        SparkGenerator generator = new SparkGenerator(fileName, k, appName, master, n);
        generator.generate();
    }

    SparkGenerator(String fileName, int k, String appName, String master, int n) {
        this.fileName = fileName;
        this.k = k;
        this.appName = appName;
        this.master = master;
        this.n = n;
    }

    public long generate() throws IOException, URISyntaxException {
        long start = System.currentTimeMillis();

        // instantiate spark context
        SparkConf conf = new SparkConf().setAppName(appName).setMaster(master);
        JavaSparkContext sc = new JavaSparkContext(conf);
        long scStart = System.currentTimeMillis();
        System.out.println("time to start spark context = " + (scStart - start) +" milliseconds.");

        // parse input file and broadcast variable for mapping
        Parser parser = new Parser();
        final Broadcast<Mapping> mapping = sc.broadcast(parser.parse(fileName));
        long broadcast = System.currentTimeMillis();
        System.out.println("time to parse and broadcast mapping = " + (broadcast - scStart) +" milliseconds.");

        // partition vertices
        System.out.println(3 * k * n);
        JavaRDD<Integer> vertices = sc.parallelize(mapping.getValue().getIds(), 3 * k * n);
        long parallelize = System.currentTimeMillis();
        System.out.println("time to parallelize = " + (parallelize - broadcast) +" milliseconds.");

        // map call on vertices...
        final ESUCounter counter = new ESUCounter();

        System.out.println("map partitions.");
        JavaRDD<Integer> counts = vertices.mapPartitions(new FlatMapFunction<Iterator<Integer>, Integer>() {

            public Iterable<Integer> call(Iterator<Integer> integerIterator) throws Exception {
                ArrayList<Integer> integers = new ArrayList<Integer>();
                while (integerIterator.hasNext()) {
                    Integer vertex = integerIterator.next();
                    integers.add(counter.enumerateSubgraphs(vertex, mapping.getValue(), k).size());
                }
                return integers;
            }

        });
        long extract = System.currentTimeMillis();
        System.out.println("time to extract = " + (extract - parallelize) +" milliseconds.");

        System.out.println("summing up.");
        long result = counts.reduce(new Function2<Integer, Integer, Integer>() {
            public Integer call(Integer i, Integer j) throws Exception {
                return i + j;
            }
        });
        System.out.println("done!");
        sc.close();
        return result;
    }

}
