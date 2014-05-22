/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.uw.nemo.labeler;

import java.nio.charset.Charset;
import java.util.*;

/**
 *
 * @author vartikav
 */
// Formats the graph into format used by nauty library for canonical labelling.
// Supports two formats used in nauty - graph6 and sparse6.
// For more information about graph formats, refer src/main/nauty/nauty25r9/formats.txt
public class GraphFormat {

    public GraphFormat(FormatType type, List<int[]> edges, int vertexCount) {
        this.formatType = type;
        this.edges = edges;
        this.vertexCount = vertexCount;
        
        this.formattedGraph = null;
        this.formattedGraphAsASCIIString = null;
    }
    
    public void formatGraph() {
        boolean[][] adjMatrix = new boolean[this.vertexCount][this.vertexCount];
        for (int[] edge : this.edges) {
            adjMatrix[edge[0]-1][edge[1]-1] = true;
            adjMatrix[edge[1]-1][edge[0]-1] = true;
        }
        
        byte[] Nn = getBytesRepresentationOfN(this.vertexCount);
        int formattedGraphSize = Nn.length + this.getByteCountForFormattedGraph();
        this.formattedGraph = new byte[formattedGraphSize];
        System.arraycopy(Nn, 0, this.formattedGraph, 0, Nn.length);
        
        if (this.formatType == FormatType.Graph6) {
            int bitPos = 0;
            int formattedGraphNextIndex = Nn.length;
            byte formattedGraphNextByte = 0;
            for (int col = 1; col < this.vertexCount; ++col) {
                for (int row = 0; row < col; ++row) {
                    if (adjMatrix[row][col]) {
                        formattedGraphNextByte = (byte) (formattedGraphNextByte | (1 << (5 - bitPos)));
                    }

                    bitPos = (bitPos + 1) % 6;
                    if (bitPos == 0) {
                        this.formattedGraph[formattedGraphNextIndex++] = (byte) (formattedGraphNextByte + 63);
                        formattedGraphNextByte = 0;
                    }
                }
            }

            this.formattedGraph[formattedGraphNextIndex] = (byte) (formattedGraphNextByte + 63);
        }
        else {
            // TODO: Implement for FormatType.Sparse6
        }
    }
    
    public byte[] getBytes() {
        if (this.formattedGraph == null) {
            this.formatGraph();
        }
        
        return this.formattedGraph;
    }
    
    @Override
    public String toString() {
        if (this.formattedGraphAsASCIIString == null) {
            byte[] formattedGraphBytes = this.getBytes();
            this.formattedGraphAsASCIIString = new String(formattedGraphBytes, Charset.forName("US-ASCII"));
        }
        
        return this.formattedGraphAsASCIIString;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Arrays.hashCode(this.getBytes());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GraphFormat other = (GraphFormat) obj;
        if (!Arrays.equals(this.getBytes(), other.getBytes())) {
            return false;
        }
        return true;
    }
    
    private int getByteCountForFormattedGraph() {
        if (this.formatType == FormatType.Graph6) {
            int temp = this.vertexCount * (this.vertexCount - 1) / 2;
            return temp/6 + ((temp % 6) > 0 ? 1 : 0);
        }
        else {
            // TODO: Implement of FormatType.Sparse6
            return 0;
        }
    }
    
    private static byte[] getBytesRepresentationOfN(int n) {
        byte[] byteRepresentation;
        if (n <= 62) {
            byteRepresentation = new byte[1];
            byteRepresentation[0] = (byte) (n + 63);
        }
        else if (n <= 258047) {
            byteRepresentation = new byte[4];
            byteRepresentation[0] = 126;
            byteRepresentation[1] = (byte) ((n >>> 12) & 63);
            byteRepresentation[2] = (byte) ((n >>> 6) & 63);
            byteRepresentation[3] = (byte) (n & 63);
        }
        else {
            byteRepresentation = new byte[8];
            byteRepresentation[0] = 126;
            byteRepresentation[1] = 126;
            byteRepresentation[2] = (byte) ((n >>> 30) & 63);
            byteRepresentation[3] = (byte) ((n >>> 24) & 63);
            byteRepresentation[4] = (byte) ((n >>> 18) & 63);
            byteRepresentation[5] = (byte) ((n >>> 12) & 63);
            byteRepresentation[6] = (byte) ((n >>> 6) & 63);
            byteRepresentation[7] = (byte) (n & 63);
        }
        
        return byteRepresentation;
    }
    
    private FormatType formatType;
    private List<int[]> edges;
    private int vertexCount;
    
    private byte[] formattedGraph;
    private String formattedGraphAsASCIIString;
}
