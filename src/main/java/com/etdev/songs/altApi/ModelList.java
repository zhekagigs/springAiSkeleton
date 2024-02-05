package com.etdev.songs.altApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

public record ModelList(List<Model> data) {
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Model(String id, long created, String ownedBy) { }
}
