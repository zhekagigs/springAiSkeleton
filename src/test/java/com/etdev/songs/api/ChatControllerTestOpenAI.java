package com.etdev.songs.api;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.RestTemplate;


import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "36000")
public class ChatControllerTestOpenAI {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Disabled
    void generateByGivenPrompt() {
        webTestClient
                .get()
                .uri("/ai/generate?message=Tell+me+a+joke+about+macbook.+Must+include+word+macbook.")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.completion").value(Matchers.containsStringIgnoringCase("macbook"))
                .jsonPath("$.completion").
                isNotEmpty().consumeWith(System.out::println);
    }

    @Test
    public void smokeTestLocalOrca() {
        // Create a RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Create a map for the request body
        Map<String, String> map = new HashMap<>();
        map.put("model", "orca-mini:3b");
        map.put("prompt", "Why is the sky blue?");

        // Build the request
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(map, headers);

        // Send the POST request
        ResponseEntity<String> response = restTemplate.exchange("http://127.0.0.1:11434/api/chat", HttpMethod.POST, entity, String.class);

        // Verify the response status code
        assertEquals(200, response.getStatusCodeValue());
        System.out.println(response.getBody());
    }

    @Test
    @Disabled
    void getOutputAsJsonByQueryRequest() {
        webTestClient
                .get()
                .uri("/ai/output?actor=Keanu+Reeves&limit=5")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.actor").isEqualTo("Keanu Reeves")
                .jsonPath("$.movies").isArray()
                .jsonPath("$.movies").isNotEmpty()
                .jsonPath("$.movies.length()").isEqualTo(5)
                .consumeWith(System.out::println);
    }

    @Test
    @Disabled
    void stuffing() {
        webTestClient
                .get()
                .uri("/ai/stuff?stuffit=true")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.completion").value(Matchers.containsStringIgnoringCase("Stefania Constantini"))
                .jsonPath("$.completion").value(Matchers.containsStringIgnoringCase("Amos Mosaner"))
                .consumeWith(System.out::println);
    }

}
