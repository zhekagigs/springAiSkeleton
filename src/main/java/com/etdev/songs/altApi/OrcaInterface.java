package com.etdev.songs.altApi;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface OrcaInterface {
    @PostExchange(value = "/api/generate", accept = "application/json", contentType = "application/json")
    OrcaResponse prompt(@RequestBody OrcaReq req);
}
