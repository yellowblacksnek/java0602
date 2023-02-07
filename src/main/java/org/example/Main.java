package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {


    public static void main(String[] args){
        if(args.length < 1) {
            System.out.println("No filename");
            System.exit(1);
        }
        long startTime = System.nanoTime();
        String filePath = args[0];
        Path path = Paths.get(filePath);
        Task task = null;
        try(BufferedReader reader = Files.newBufferedReader(path)) {
            Set<String> lines = reader.lines().collect(Collectors.toSet());
            task = new Task(lines);
        } catch (IOException e) {
            System.err.println("File read error: " + filePath);
            System.exit(1);
        }

        task.processLines();
        task.printGroups();

        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println(TimeUnit.NANOSECONDS.toSeconds(totalTime) + " seconds");
    }
}