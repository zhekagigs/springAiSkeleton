package com.etdev.songs.altApi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpenAiInterfaceTest {
    @Autowired
    private OpenAiInterface openAiInterface;

    @Test
    void listModels() {
        var models = openAiInterface.listModels();
        var modelNames = new ArrayList<String>();
        models.data().stream().map(ModelList.Model::id).sorted().forEach(modelNames::add);
    }
}