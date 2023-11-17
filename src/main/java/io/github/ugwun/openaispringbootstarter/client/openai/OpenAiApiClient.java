package io.github.ugwun.openaispringbootstarter.client.openai;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ugwun.openaispringbootstarter.client.AiApiClient;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * Client for interacting with OpenAI's Assistants API.
 * Provides methods to create, retrieve, modify, delete, and list assistants.
 */
public class OpenAiApiClient implements AiApiClient {

    private final String apiKey;
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private static final String ASSISTANTS_BASE_URL = "https://api.openai.com/v1/assistants";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * Constructs an OpenAiApiClient with the provided API key.
     *
     * @param apiKey The API key for authenticating with OpenAI's API.
     */
    public OpenAiApiClient(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Creates a new assistant in the OpenAI system.
     *
     * OpenAI doc: https://platform.openai.com/docs/api-reference/assistants/createAssistant
     *
     * @param assistantBuilder The builder containing the assistant configuration.
     *                         It should include the model, and optionally the name,
     *                         description, instructions, tools, file IDs, and metadata.
     * @return The JSON response from the OpenAI API representing the created assistant.
     * @throws IOException If an I/O error occurs during the API request.
     */
    @Override
    public String createAssistant(AssistantBuilder assistantBuilder) throws IOException {
        Map<String, Object> data = assistantBuilder.build();
        RequestBody body = RequestBody.Companion.create(objectMapper.writeValueAsString(data).getBytes(), JSON);

        Request request = new Request.Builder()
                .url(ASSISTANTS_BASE_URL)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("OpenAI-Beta", "assistants=v1")
                .post(body)
                .build();

        return executeRequest(request);
    }

    /**
     * Retrieves an existing assistant from the OpenAI system.
     *
     * OpenAI doc: https://platform.openai.com/docs/api-reference/assistants/getAssistant
     *
     * @param assistantId The unique identifier of the assistant to retrieve.
     * @return The JSON response from the OpenAI API representing the assistant.
     * @throws IOException If an I/O error occurs during the API request.
     */
    @Override
    public String retrieveAssistant(String assistantId) throws IOException {
        Request request = new Request.Builder()
                .url(ASSISTANTS_BASE_URL + "/" + assistantId)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("OpenAI-Beta", "assistants=v1")
                .get()
                .build();

        return executeRequest(request);
    }

    /**
     * Modifies an existing assistant in the OpenAI system.
     *
     * OpenAI doc: https://platform.openai.com/docs/api-reference/assistants/modifyAssistant
     *
     * @param assistantId      The unique identifier of the assistant to modify.
     * @param assistantBuilder The builder containing the updated assistant configuration.
     *                         Can include changes to the model, name, description,
     *                         instructions, tools, file IDs, and metadata.
     * @return The JSON response from the OpenAI API representing the modified assistant.
     * @throws IOException If an I/O error occurs during the API request.
     */
    @Override
    public String modifyAssistant(String assistantId, AssistantBuilder assistantBuilder) throws IOException {
        Map<String, Object> data = assistantBuilder.build();
        RequestBody body = RequestBody.Companion.create(objectMapper.writeValueAsString(data).getBytes(), JSON);

        Request request = new Request.Builder()
                .url(ASSISTANTS_BASE_URL + "/" + assistantId)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("OpenAI-Beta", "assistants=v1")
                .post(body)
                .build();

        return executeRequest(request);
    }

    /**
     * Deletes an existing assistant from the OpenAI system.
     *
     * OpenAI doc: https://platform.openai.com/docs/api-reference/assistants/deleteAssistant
     *
     * @param assistantId The unique identifier of the assistant to delete.
     * @return The JSON response from the OpenAI API confirming the deletion.
     * @throws IOException If an I/O error occurs during the API request.
     */
    @Override
    public String deleteAssistant(String assistantId) throws IOException {
        Request request = new Request.Builder()
                .url(ASSISTANTS_BASE_URL + "/" + assistantId)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("OpenAI-Beta", "assistants=v1")
                .delete()
                .build();

        return executeRequest(request);
    }

    /**
     * Lists assistants available in the OpenAI system.
     *
     * OpenAI doc: https://platform.openai.com/docs/api-reference/assistants/listAssistants
     *
     * @param queryBuilder The builder containing query parameters for listing assistants.
     *                     Can include parameters like limit, order, after, and before
     *                     for pagination and sorting.
     * @return The JSON response from the OpenAI API representing a list of assistants.
     * @throws IOException If an I/O error occurs during the API request.
     */
    @Override
    public String listAssistants(AssistantListQueryBuilder queryBuilder) throws IOException {
        Map<String, String> queryParams = queryBuilder.build();
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(ASSISTANTS_BASE_URL)).newBuilder();
        queryParams.forEach(urlBuilder::addQueryParameter);

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("OpenAI-Beta", "assistants=v1")
                .build();

        return executeRequest(request);
    }

    private String executeRequest(Request request) throws IOException {
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return Objects.requireNonNull(response.body()).string();
        }
    }
}