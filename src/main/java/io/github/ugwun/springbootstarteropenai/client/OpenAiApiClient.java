package io.github.ugwun.springbootstarteropenai.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OpenAiApiClient implements AiApiClient {

    private final String apiKey;
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public OpenAiApiClient(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

//    Map<String, String>[] messages = new Map[]{
//            Map.of("role", "system", "content", "You are a helpful assistant."),
//            Map.of("role", "user", "content", "Who won the world series in 2020?")
//    };
//
//    String response = client.generateChatMessage("gpt-3.5-turbo", messages);

    public String generateChatMessage(String model, Map<String, String>[] messages) throws IOException {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("messages", messages);

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"),
                objectMapper.writeValueAsString(requestBody)
        );

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            return Objects.requireNonNull(response.body()).string();
        }
    }
}