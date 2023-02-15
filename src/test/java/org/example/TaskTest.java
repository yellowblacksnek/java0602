package org.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TaskTest {
    private static Stream<Arguments> filesToGroups() {
        return Stream.of(
                arguments("test1.txt", 1),
                arguments("test2.txt", 2),
                arguments("test3.txt", 3)
        );
    }
    @ParameterizedTest
    @MethodSource("filesToGroups")
    public void testProcessLines(String filePath, int groupsNum) throws IOException {
        Path path = Path.of("src","test","resources", filePath);
        Task task;
        try(BufferedReader reader = Files.newBufferedReader(path)) {
            Set<String> lines = reader.lines().collect(Collectors.toSet());
            task = new Task(lines);
        }
        task.processLines();
        assertEquals(groupsNum, task.getEndGroups().size());
    }
}
