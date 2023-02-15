package org.example;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.example.Utils.readUniqueLinesFromFile;

public class Main {


    public static void main(String[] args) throws IOException {
        if(args.length < 1) {
            System.out.println("No filepath was provided");
        } else {
            long startTime = System.nanoTime();
            String filePath = args[0];
            Set<String> lines = readUniqueLinesFromFile(filePath);
            Task task = new Task(lines);
            task.processLines();
            task.printGroupsToFile("out.txt");

            long endTime   = System.nanoTime();
            long totalTime = endTime - startTime;
            System.out.println(TimeUnit.NANOSECONDS.toSeconds(totalTime) + " seconds");
        }
    }
}