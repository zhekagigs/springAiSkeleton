package com.etdev.songs.altApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OpenAiService {
    public static final String GPT35 = "gpt-3.5-turbo";
    public static final String GPT4 = "gpt-4-1106-preview";

    private final OpenAiInterface openAiInterface;

    @Autowired
    public OpenAiService(OpenAiInterface openAiInterface) {
        this.openAiInterface = openAiInterface;
    }

    public List<String> getModelNames() {
        var modelNames = new ArrayList<String>();
        openAiInterface.listModels().data().stream().map(ModelList.Model::id).sorted().forEach(modelNames::add);
        return modelNames;
    }
}
