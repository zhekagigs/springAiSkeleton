package com.etdev.songs;

import com.etdev.songs.altApi.OpenAiInterface;
import com.etdev.songs.altApi.OrcaInterface;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class AppConfig {

    @Bean
    public RestClient restClient(@Value("${openai.baseurl}")String baseUrl,
                                 @Value("${openai.apikey}")String apiKey) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Authorization", "Bearer %s".formatted(apiKey))
                .build();
    }

    @Bean
    public OpenAiInterface openAiInterface(@Qualifier("restClient") RestClient client) {
        var adapter = RestClientAdapter.create(client);
        var factory= HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(OpenAiInterface.class);
    }

    @Bean
    public RestClient restClientOrcaLocal(@Value("${local.orca.baseurl}")String baseUrl) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/json")
                .build();
    }

    @Bean
    public OrcaInterface orcaInterface(@Qualifier("restClientOrcaLocal") RestClient client) {
        var adapter = RestClientAdapter.create(client);
        var factory= HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(OrcaInterface.class);
    }
}
