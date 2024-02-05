package com.etdev.songs.altApi;

import java.util.List;

public record ModelsResponse(List<Model> models) {

    public record Model(
            String name,
            String modified_at,
            long size,
            String digest,
            Details details
    ) {}

    public record Details(
            String format,
            String family,
            List<String> families,
            String parameter_size,
            String quantization_level
    ) {}
}




