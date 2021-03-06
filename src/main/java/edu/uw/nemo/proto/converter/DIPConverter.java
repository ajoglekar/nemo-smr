package edu.uw.nemo.proto.converter;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by joglekaa on 4/15/14.
 */
public class DIPConverter {

    public int convert(String srcFile, String destFile) throws IOException, URISyntaxException {
        int count = 0;
        BufferedReader input = null;
        BufferedWriter output = null;
        try {
            input = openInputFile(srcFile);

            output = openOutputFile(destFile);
            String header = input.readLine();
            if (header != null && !header.isEmpty()) {
//            prnt("header", header.split("\t"));
                String line = null;
                while ((line = input.readLine()) != null) {
                    // extract node id 1, node id 2
                    String[] split = line.trim().split("\t");
//                prnt("record", split);
                    // validate
                    output.write(split[0]);
                    output.write("\t");
                    output.write(split[1]);
                    output.newLine();
                    count++;
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (output != null) {
                output.flush();
                output.close();
            }
            if (input != null) {
                input.close();
            }
        }
        return count;
    }

    private void prnt(String type, String[] split) {
        System.out.println(type + ":");
        for (int i = 0; i < split.length; i++) {
            System.out.println(i + ": [" + split[i] + "]");
        }
        System.out.println("finished");
    }

    private BufferedReader openInputFile(String srcFile) throws IOException, URISyntaxException {
        URL url =
                Thread.currentThread().getContextClassLoader().getResource(srcFile);
        Path path = Paths.get(url.toURI());
        Charset charset = Charset.forName("US-ASCII");
        return Files.newBufferedReader(path, charset);
    }

    private BufferedWriter openOutputFile(String destFile) throws IOException, URISyntaxException {
        Path path = Paths.get(destFile);
        Charset charset = Charset.forName("US-ASCII");
        return Files.newBufferedWriter(path, charset);
    }

}
