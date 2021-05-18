package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) throws IOException {

        String[] arg = new String[] {"http://www.mtuci.ru/","1"};
        int depth = 0;
        try {
            depth = Integer.parseInt(arg[1]);// делает из строки число
        }
        catch (NumberFormatException nfe) {
            System.out.println("usage: java Crawler <URL> <depth>");
            System.exit(1);
        }
        LinkedList<com.company.URLDepthPair> pendingURLs = new LinkedList<com.company.URLDepthPair>();
        LinkedList<com.company.URLDepthPair> processedURLs = new LinkedList<com.company.URLDepthPair>();
        com.company.URLDepthPair currentDepthPair = new com.company.URLDepthPair(arg[0], 0);
        pendingURLs.add(currentDepthPair);
        ArrayList<String> seenURLs = new ArrayList<String>();
        seenURLs.add(currentDepthPair.getURL());

        while (pendingURLs.size() != 0) {
            com.company.URLDepthPair depthPair = pendingURLs.pop();
            processedURLs.add(depthPair);
            int myDepth = depthPair.getDepth();
            LinkedList<String> linksList;
            linksList = com.company.Crawler.getAllLinks(depthPair);
            if (myDepth < depth) {
                for (int i=0;i<linksList.size();i++) {
                    String newURL = linksList.get(i);
                    if (seenURLs.contains(newURL)) {
                        continue;
                    }
                    else {
                        com.company.URLDepthPair newDepthPair = new com.company.URLDepthPair(newURL, myDepth + 1);
                        pendingURLs.add(newDepthPair);
                        seenURLs.add(newURL);
                    }
                }
            }
        }
        Iterator<com.company.URLDepthPair> iter = processedURLs.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }

}