package com.etdev.songs.altApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class OrcaService {

    public static final String MINI_3_B = "orca-mini:3b";

    private final OrcaInterface orcaInterface;

    @Autowired
    public OrcaService(OrcaInterface orcaInterface) {
        this.orcaInterface = orcaInterface;
    }

    public String getAnswer(String msg) {
        var req = new OrcaReq(MINI_3_B, msg, false);
        var answer = orcaInterface.prompt(req);
        System.out.println(answer);
        return answer.response();
    }

    public OrcaResponse getAnswer2(String msg) {
        var headers  = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var req = new HttpEntity<>(new OrcaReq(MINI_3_B, msg, false), headers);

        RestTemplate rest = new RestTemplate();

        var response = rest.postForObject("http://127.0.0.1:11434/api/generate", req, OrcaResponse.class);

        return response;
    }

    public ModelsResponse getLocalModelsList() {
        RestTemplate rest = new RestTemplate();
        var payload = rest.getForObject("http://127.0.0.1:11434/api/tags", ModelsResponse.class);
        System.out.println(payload);
        return payload;
    }
}
