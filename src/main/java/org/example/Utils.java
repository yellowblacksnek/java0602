package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.util.Set;
import java.util.stream.Collectors;

public class Utils {
    public static IOException getPrettyIOException(IOException e) {
        String message;
        try {
            throw e;
        } catch (NoSuchFileException | FileNotFoundException ex) {
            message = String.format("File not found: %s", e.getMessage());
        } catch (AccessDeniedException ex) {
            message = String.format("Not enough permissions for file: %s", e.getMessage());
        } catch (IOException ex) {
            message = String.format("File operation error occurred (%s) with file: %s", e.getClass().getSimpleName(), e.getMessage());
        }
        IOException exception = new IOException(message);
        exception.setStackTrace(e.getStackTrace());
        return exception;
    }

    public static Set<String> readUniqueLinesFromFile(String filepath) throws IOException {
        Path path = Paths.get(filepath);
        try(BufferedReader reader = Files.newBufferedReader(path)) {
            return reader.lines().collect(Collectors.toSet());
        }
        catch (IOException e) {
            throw getPrettyIOException(e);
        }
    }
}
