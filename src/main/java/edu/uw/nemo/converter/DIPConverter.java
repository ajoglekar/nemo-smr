package edu.uw.nemo.converter;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by joglekaa on 4/15/14.
 */
public class DIPConverter {

    public int convert(String srcFile, String destFile) throws IOException {
        int count = 0;
        BufferedReader input = openInputFile(srcFile);

        BufferedWriter ouput = openOutputFile(destFile);
        String header = input.readLine();
        if (header != null && !header.isEmpty()) {
//            prnt("header", header.split("\t"));
            String line = null;
            while ((line = input.readLine()) != null) {
                // extract node id 1, node id 2
                String[] split = line.trim().split("\t");
//                prnt("record", split);
                // validate
                ouput.write(split[0]);
                ouput.write("\t");
                ouput.write(split[1]);
                ouput.newLine();
                count++;
            }
        }
        ouput.flush();
        ouput.close();
        input.close();
        return count;
    }

    private void prnt(String type, String[] split) {
        System.out.println(type + ":");
        for (int i = 0; i < split.length; i++) {
            System.out.println(i + ": [" + split[i] + "]");
        }
        System.out.println("finished");
    }

    private BufferedReader openInputFile(String srcFile) throws IOException {
        Path path = FileSystems.getDefault().getPath(srcFile);
        Charset charset = Charset.forName("US-ASCII");
        return Files.newBufferedReader(path, charset);
    }

    private BufferedWriter openOutputFile(String destFile) throws IOException {
        Path path = FileSystems.getDefault().getPath(destFile);
        Charset charset = Charset.forName("US-ASCII");
        return Files.newBufferedWriter(path, charset);
    }

}