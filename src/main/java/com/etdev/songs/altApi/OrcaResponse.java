package com.etdev.songs.altApi;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

/*
{
  "model": "llama2",
  "created_at": "2023-08-04T19:22:45.499127Z",
  "response": "",
  "done": true,
  "context": [1, 2, 3],
  "total_duration": 10706818083,
  "load_duration": 6338219291,
  "prompt_eval_count": 26,
  "prompt_eval_duration": 130079000,
  "eval_count": 259,
  "eval_duration": 4232710000
}
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record OrcaResponse(String model, String createdAt, String response, Boolean done, List<Integer> context,
                           long totalDuration, long loadDuration, int promptEvalCount, long promptEvalDuration,
                           int evalCount, long evalDuration) {
}
