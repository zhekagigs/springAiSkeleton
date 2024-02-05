package com.etdev.songs.altApi;

import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockserver.client.server.MockServerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import static org.mockserver.model.HttpRequest.request;

@SpringBootTest
class OrcaServiceMockTest {
    @Autowired
    private OrcaService orcaService;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private WebClient webClient;

    @Test
    void getAnswer() {

    }
}