package org.example;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GroupTest {

    @Test
    public void testEndGroup() {
        ArrayList<Map<String, Group>> columnMapList = new ArrayList<>();
        Group groupA = new Group(columnMapList);
        Group groupB = new Group(columnMapList);

        groupA.setSuccessor(groupB);
        assertEquals(groupB, groupA.getEndGroup());
    }

    @Test
    public void testEndGroupMultipleSuccessors() {
        ArrayList<Map<String, Group>> columnMapList = new ArrayList<>();
        ArrayList<Group> groups = new ArrayList<>();
        int length = 10;
        for(int i = 0; i < length; i++) {
            groups.add(new Group(columnMapList));
        }
        for(int i = 0; i < length - 1; i++) {
            groups.get(i).setSuccessor(groups.get(i+1));
        }
        assertEquals(groups.get(length-1), groups.get(0).getEndGroup());
    }

    @Test
    public void testAdd() {
        ArrayList<Map<String, Group>> columnMapList = new ArrayList<>();
        Group group = new Group(columnMapList);
        String[] line = new String[]{"111", "222", "333"};

        for(int i = 0; i < line.length; i++) {
            columnMapList.add(new HashMap<>());
        }
        group.add(line);

        for(int i = 0; i < line.length; i++) {
            assertTrue(columnMapList.get(i).containsKey(line[i]));
            assertEquals(group, columnMapList.get(i).get(line[i]));
        }
    }

    @Test
    public void testAddSuccession() {
        ArrayList<Map<String, Group>> columnMapList = new ArrayList<>();
        Group group = new Group(columnMapList);
        String[] line = new String[]{"111", "222", "333"};

        for(int i = 0; i < line.length; i++) {
            columnMapList.add(new HashMap<>());
        }
        group.add(line);

        Group newGroup = new Group(columnMapList);
        String[] newLine = new String[]{"0", "222", "5"};

        newGroup.add(newLine);

        for(int i = 0; i < line.length; i++) {
            assertTrue(columnMapList.get(i).containsKey(line[i]));
        }
        for(int i = 0; i < newLine.length; i++) {
            assertTrue(columnMapList.get(i).containsKey(newLine[i]));
        }

        columnMapList.forEach(map -> {
            map.forEach((elemKey, groupValue) -> {
                assertEquals(newGroup, groupValue.getEndGroup());
            });
        });
    }
}
