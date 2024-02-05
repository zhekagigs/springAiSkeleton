package com.etdev.songs.api;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled("dependency fighting")
class ClientsTest {

    @Autowired @Qualifier("ollamaChatClient")
    private ChatClient ollamaChatClient;

    @Autowired @Qualifier("openAiChatClient")
    private ChatClient openAichatClient;

    @Test
    void testChatClient() {
        String response = openAichatClient.call("Write a function in Java to calculate average temperature from file of 1 billion temperature values");
        System.out.println(response);
        String test = ollamaChatClient.call("Give me a short answer if this code is correct and free of bugs and esy to read and maintain: " + response);
        System.out.println(test);
    }

}