package org.example;

import java.util.ArrayList;
import java.util.Map;

public class Group {
    private ArrayList<String[]> lines;
    private Group successor;
    private final ArrayList<Map<String, Group>> columnMapList;
    public Group(ArrayList<Map<String, Group>> columnMapList) {
        this.columnMapList = columnMapList;
        lines = new ArrayList<>();
    }

    public Group getEndGroup() {
        Group group = this;
        while (group.successor != null) {
            group = group.successor;
        }
        return group;
    }

    public void add(String[] line) {
        Group endGroup = getEndGroup();
        if(endGroup != this) {
            endGroup.add(line);
            return;
        }


        boolean hasAtLeastOneElement = false;

        for(int i = 0; i < line.length; i++) {
            if(line[i].isEmpty() || line[i].equals("\"\"")) continue;

            hasAtLeastOneElement = true;
            Group prev = columnMapList.get(i).put(line[i], this);
            if(prev == null || prev == this) continue;
            prev = prev.getEndGroup();
            if(prev == this) continue;

            lines.addAll(prev.lines);
            prev.lines = null;

            prev.successor = this;
        }

        if (hasAtLeastOneElement)
            lines.add(line);
    }

    public ArrayList<String[]> getLines() {
        return lines;
    }
}
