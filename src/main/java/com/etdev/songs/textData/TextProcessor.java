package com.etdev.songs.textData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TextProcessor {

    public void cleanText(Path dirtyFilePath, Path cleanedFilePath) {
        try {
            var pattern = Pattern.compile("^(1[0-2]|0?[1-9]):[0-5][0-9] ?(AM|PM)?$");
            var cleanedText = Files
                    .readString(dirtyFilePath)
                    .lines()
                    .map(String::stripLeading)
                    .filter(line -> !line.isBlank())
                    .filter(line -> {
                        var result = pattern.matcher(line).matches();
                        return !result;
                    } )
                    .collect(Collectors.joining("\n"));
            Files.writeString(cleanedFilePath, cleanedText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
