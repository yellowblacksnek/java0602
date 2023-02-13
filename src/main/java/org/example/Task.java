package org.example;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

public class Task {
    private final ArrayList<Map<String, Group>> columnMapList;
    private final Set<String> lines;
    private final ArrayList<Group> groups;

    public Task(Set<String> lines) {
        columnMapList = new ArrayList<>();
        this.lines = lines;
        groups = new ArrayList<>();
    }

    public void processLines() {
        for(String lineStr : lines) {
            handleLine(lineStr);
        }
    }

    public void handleLine(String lineStr) {
        String[] line = lineStr.split(";");
        for (String element : line) {
            if (!element.startsWith("\"")
                    || !element.endsWith("\"")
                    || element.indexOf('\"', 1) != element.length() - 1) {
                return;
            }
        }
        if(line.length == 0) return;

        while(columnMapList.size() < line.length) {
            columnMapList.add(new HashMap<>());
        }

        Group assignedGroup = null;

        for (int i = 0; i < line.length; i++) {
            assignedGroup = columnMapList.get(i).get(line[i]);
            if(assignedGroup != null) {
                break;
            }
        }

        if (assignedGroup == null) {
            Group newGroup = new Group(this.columnMapList);
            newGroup.add(line);
            groups.add(newGroup);
        } else {
            assignedGroup.add(line);
        }
    }

    public Set<Group> getEndGroups() {
        Set<Group> groupsSet = new HashSet<>();
        groups.forEach(i -> {
            if(i != null) {
                groupsSet.add(i.getEndGroup());
            }
        });
        return groupsSet;
    }

    public void printGroups(String filepath) {
        Set<Group> groupsSet = getEndGroups();
        List<Group> finalGroups = groupsSet
                .stream()
                .sorted((o1, o2) -> o2.getLines().size() - o1.getLines().size())
                .collect(Collectors.toList());
        System.out.printf("Total number of groups: %d\n", finalGroups.size());
        long numOfMatching = finalGroups.stream().filter(i -> i.getLines().size() > 1).count();
        System.out.printf("Number of groups with size > 1: %d\n", numOfMatching);

        Path path = Paths.get(filepath);
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE)) {
            writer.write("" + numOfMatching);
            writer.newLine();

            for(int i = 0; i < finalGroups.size(); i++) {
                writer.write("Группа " + (i+1));
                writer.newLine();

                for(var line : finalGroups.get(i).getLines()) {
                    StringBuilder builder = new StringBuilder();
                    for(var elem : line) {
                        builder.append(elem);
                        builder.append(";");
                    }
                    builder.deleteCharAt(builder.length()-1);
                    writer.write(builder.toString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error while printing result:");
            e.printStackTrace();
        }
    }
}
