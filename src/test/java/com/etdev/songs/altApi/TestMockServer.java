package com.etdev.songs.altApi;

import org.junit.jupiter.api.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.model.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.StringBody.exact;

@SpringBootTest
public class TestMockServer {

    @Autowired
    private OrcaService orcaService;

    @Test
    void getAnswer() {
        var mockServer = startClientAndServer(9999);
        createExpectationForInvalidAuth();
        var response = orcaService.getAnswer2(
                "Which was best synthy pop band of 2010-x?");
        System.out.println(response);
        assert response != null;

    }
    private static void createExpectationForInvalidAuth() {
        var req = request()
                .withMethod("POST")
                .withPath("/api/generate")
                .withHeader("\"Content-type\", \"application/json\"")
                .withBody(exact("{\"model\":\"orca-i:3b\",\"prompt\":\"Which was best synthy pop band of 2010-x?\",\"stream\":false,\"format\":\"null\"}"));

        new MockServerClient("127.0.0.1", 9999)
                .when(req, exactly(1))
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8"),
                                        new Header("Cache-Control", "public, max-age=86400"))
                                .withBody("{ \"response\": \"Bee Gees\" }")
                );
    }
}
