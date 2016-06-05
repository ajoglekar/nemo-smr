package edu.uw.nemo.esu;

import edu.uw.nemo.io.Parser;
import edu.uw.nemo.model.Mapping;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import org.apache.spark.broadcast.Broadcast;
import scala.Tuple2;

import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ESU on Spark
 */
public class SparkGenerator implements Serializable {

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
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
        String estimate = null;

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
        if (length > 5) {
            estimate = args[5];
        }
        SparkGenerator generator = new SparkGenerator(fileName, k, appName, master, n);
        if ("test".equals(estimate)) {
            generator.generate(new NullESUAlgorithm());
        } else {
            generator.generate(new ESUAlgorithm());
        }
    }

    SparkGenerator(String fileName, int k, String appName, String master, int n) {
        this.fileName = fileName;
        this.k = k;
        this.appName = appName;
        this.master = master;
        this.n = n;
    }

    public long generate(ESUAlgorithm esu) throws IOException, URISyntaxException {
        long start = System.currentTimeMillis();
        logAndPrint("starting to process [" + fileName + "].");

        // instantiate spark context
        SparkConf conf = new SparkConf().setAppName(appName).setMaster(master);
        JavaSparkContext sc = new JavaSparkContext(conf);
        long scStart = System.currentTimeMillis();
        logAndPrint("time to start spark context = " + (scStart - start) +" milliseconds.");

        // parse input file and broadcast variable for mapping
        JavaRDD<String> textFile = sc.textFile(fileName);
        List<String> strings = textFile.toArray();
        Parser parser = new Parser();
        final Broadcast<Mapping> mapping = sc.broadcast(parser.parseList(strings));
        long broadcast = System.currentTimeMillis();
        logAndPrint("Input file [" + fileName + "] had [" + mapping.getValue().getNodeCount() + "] vertices and [" + mapping.getValue().getLinkCount() + "] edges.");
        logAndPrint("Time to parse and broadcast mapping = " + (broadcast - scStart) +" milliseconds.");

        // partition vertices
        int numPartitions = 3 * k * n;
        logAndPrint("Parallelizing with [" + numPartitions + "] partitions.");
        List<Integer> pVertices = new HashPartitioner().prepPartitions(mapping.getValue().getIds(), numPartitions);
        JavaRDD<Integer> vertices = sc.parallelize(pVertices, numPartitions);
        long parallelize = System.currentTimeMillis();
        logAndPrint("time to parallelize = " + (parallelize - broadcast) +" milliseconds.");

        long result = getCount(esu, mapping, vertices);

        sc.close();
        return result;
    }

    // map call on vertices...
    private long getCount(final ESUAlgorithm esu, final Broadcast<Mapping> mapping, JavaRDD<Integer> vertices) {
        logAndPrint("Starting parallel run: map partitions.");
        long startExtract = System.currentTimeMillis();
        JavaPairRDD<Integer, Long> counts = vertices.mapPartitionsToPair(new PairFlatMapFunction<Iterator<Integer>, Integer, Long>() {

            public Iterable<Tuple2<Integer, Long>> call(Iterator<Integer> integerIterator) throws Exception {
                ArrayList<Tuple2<Integer, Long>> countsPerVertex = new ArrayList<Tuple2<Integer, Long>>();
                while (integerIterator.hasNext()) {
                    Integer vertex = integerIterator.next();
                    Counter c = new Counter();
                    esu.enumerateSubgraphs(vertex, mapping.getValue(), k, c);
                    countsPerVertex.add(new Tuple2<Integer, Long>(vertex, c.getCount()));
                }
                return countsPerVertex;
            }

        });
        long endExtract = System.currentTimeMillis();
        logAndPrint("Time to extract = " + (endExtract - startExtract) +" milliseconds.");

        counts.cache();

        long result = counts.values().reduce(new Function2<Long, Long, Long>() {
            public Long call(Long i, Long j) throws Exception {
                return i + j;
            }
        });
        logAndPrint("Done, found [" + result + "] distinct subgraphs.");

        // write the file out to bucket
       // counts.saveAsTextFile("/generated/temp.txt");

        return result;
    }

    private void logAndPrint(String t) {
        Date now = new Date();
        System.out.println(format.format(now) + " ::tracker print:: " + t);
    }

}
