package com.etdev.songs.altApi;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OrcaReq(String model, String prompt,Boolean stream ) {
}
