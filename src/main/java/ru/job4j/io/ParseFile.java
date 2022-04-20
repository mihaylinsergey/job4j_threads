package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String content(Predicate<Character> filter) {
        String output = "";
        try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = i.read()) > 0) {
                if (filter.test((char) data)) {
                    output += (char) data;
                }
            }
         } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    public String getContent() throws IOException {
        return content(x -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return content(x -> x < 0x80);
    }
}
