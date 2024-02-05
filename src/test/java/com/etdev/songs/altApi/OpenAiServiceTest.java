package com.etdev.songs.altApi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OpenAiServiceTest {
    @Autowired
    private OpenAiService openAiService;

    @Test
    void smokeTestOpenAIService() {

        System.out.println(openAiService.getModelNames());
    }


}