package com.etdev.songs.altApi;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/v1")
public interface OpenAiInterface {
    @GetExchange(value = "/models", accept = "application/json")
    ModelList listModels();
}
