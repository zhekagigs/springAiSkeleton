package com.etdev.songs.textData;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class TextProcessorTest {
    public TextProcessor textProcessor = new TextProcessor();

    @Test
    void testProcessText() {
        var dirtyPath = Path.of( "src/main/resources/data/chat.txt");
        var cleanPath = Path.of("src/main/resources/data/clean");
        textProcessor.cleanText(dirtyPath, cleanPath);
    }

}