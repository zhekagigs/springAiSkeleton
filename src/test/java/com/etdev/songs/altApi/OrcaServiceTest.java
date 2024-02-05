package com.etdev.songs.altApi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrcaServiceTest {
    @Autowired
    private OrcaService orcaService;

    @Test
    void getAnswer() {
        var response = orcaService.getAnswer2(
                "Why the sky blue?");
        System.out.println(response);
        assert response != null;
    }

    @Test
    void testGetAnswer() {
    }

    @Test
    void getLocalModelsList() {
        orcaService.getLocalModelsList();
    }
}