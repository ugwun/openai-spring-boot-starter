package io.github.ugwun.openaispringbootstarter.client.openai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(properties = {
        "openai.api.token=openai_token"
})
class OpenAiApiClientTest {

    public static final String ASSISTANT_NAME = "Assistant name";
    public static final String MODEL = "gpt-4";
    public static final String DESCRIPTION = "This is a test assistant";
    public static final String INSTRUCTIONS = "You are a testing assistant. Always reply that you don't know the answer.";
    private static final String ASSISTANT_ID = "asst_abc123";
    private WireMockServer wireMockServer;

    @Autowired
    private OkHttpClient httpClient;

    @Autowired
    private ObjectMapper objectMapper;

    private OpenAiApiClient client;

    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());
        client = new OpenAiApiClient("testApiKey",
                objectMapper,
                httpClient,
                wireMockServer.baseUrl() + "/v1/assistants");
    }

    @AfterEach
    void teardown() {
        wireMockServer.stop();
    }

    @Test
    void createAssistant() throws IOException {
        String expectedResponse = "{\n" +
                "  \"id\": \"" + ASSISTANT_ID + "\",\n" +
                "  \"object\": \"assistant\",\n" +
                "  \"created_at\": 1698984975,\n" +
                "  \"name\": \"" + ASSISTANT_NAME + "\",\n" +
                "  \"description\": " + DESCRIPTION + ",\n" +
                "  \"model\": \"" + MODEL + "\",\n" +
                "  \"instructions\": \"" + INSTRUCTIONS + "\",\n" +
                "  \"tools\": [\n" +
                "    {\n" +
                "      \"type\": \"code_interpreter\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"file_ids\": [],\n" +
                "  \"metadata\": {}\n" +
                "}";

        // Stubbing WireMock to respond to a POST request
        stubFor(post(urlEqualTo("/v1/assistants"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(expectedResponse)
                        .withHeader("Content-Type", "application/json")));

        AssistantBuilder assistantBuilder = new AssistantBuilder();
        assistantBuilder.name(ASSISTANT_NAME)
                .model(MODEL)
                .description(DESCRIPTION)
                .instructions(INSTRUCTIONS);

        // Call createAssistant
        String actualResponse = client.createAssistant(assistantBuilder);

        // Assert the response
        assertNotNull(actualResponse);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    void retrieveAssistant() throws IOException {
        String expectedResponse = "{\n" +
                "  \"id\": \"" + ASSISTANT_ID + "\",\n" +
                "  \"object\": \"assistant\",\n" +
                "  \"created_at\": 1699009709,\n" +
                "  \"name\": \"" + ASSISTANT_NAME + "\",\n" +
                "  \"description\": " + DESCRIPTION + ",\n" +
                "  \"model\": \"" + MODEL + "\",\n" +
                "  \"instructions\": \"" + INSTRUCTIONS + "\",\n" +
                "  \"tools\": [\n" +
                "    {\n" +
                "      \"type\": \"retrieval\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"file_ids\": [\n" +
                "    \"file-abc123\"\n" +
                "  ],\n" +
                "  \"metadata\": {}\n" +
                "}";

        // Stubbing WireMock to respond to a GET request
        stubFor(get(urlEqualTo("/v1/assistants/" + ASSISTANT_ID))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(expectedResponse)
                        .withHeader("Content-Type", "application/json")));

        // Call retrieveAssistant
        String actualResponse = client.retrieveAssistant(ASSISTANT_ID);

        // Assert the response
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
    }


    @Test
    void modifyAssistant() throws IOException {
        String expectedResponse = "{\n" +
                "  \"id\": \"" + ASSISTANT_ID + "\",\n" +
                "  \"object\": \"assistant\",\n" +
                "  \"created_at\": 1699009709,\n" +
                "  \"name\": \"" + ASSISTANT_NAME + "\",\n" +
                "  \"description\": " + DESCRIPTION + ",\n" +
                "  \"model\": \"" + MODEL + "\",\n" +
                "  \"instructions\": \"" + INSTRUCTIONS + ". Always response with info from either of the files.\",\n" +
                "  \"tools\": [\n" +
                "    {\n" +
                "      \"type\": \"retrieval\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"file_ids\": [\n" +
                "    \"file-abc123\",\n" +
                "    \"file-abc456\"\n" +
                "  ],\n" +
                "  \"metadata\": {}\n" +
                "}";

        // Stubbing WireMock to respond to a POST request for modifying the assistant
        stubFor(post(urlEqualTo("/v1/assistants/" + ASSISTANT_ID))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(expectedResponse)
                        .withHeader("Content-Type", "application/json")));

        AssistantBuilder assistantBuilder = new AssistantBuilder();
        assistantBuilder.name(ASSISTANT_NAME)
                .model(MODEL)
                .description(DESCRIPTION)
                .instructions(INSTRUCTIONS + ". Always response with info from either of the files.");

        // Call modifyAssistant
        String actualResponse = client.modifyAssistant(ASSISTANT_ID, assistantBuilder);

        // Assert the response
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
    }


    @Test
    void deleteAssistant() throws IOException {
        String expectedResponse = "{\n" +
                "  \"id\": \"" + ASSISTANT_ID + "\",\n" +
                "  \"object\": \"assistant.deleted\",\n" +
                "  \"deleted\": true\n" +
                "}";

        // Stubbing WireMock to respond to a DELETE request
        stubFor(delete(urlEqualTo("/v1/assistants/" + ASSISTANT_ID))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(expectedResponse)
                        .withHeader("Content-Type", "application/json")));

        // Call deleteAssistant
        String actualResponse = client.deleteAssistant(ASSISTANT_ID);

        // Assert the response
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
    }


    @Test
    void listAssistants() throws IOException {
        String expectedResponse = "{\n" +
                "  \"object\": \"list\",\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"id\": \"asst_abc123\",\n" +
                "      \"object\": \"assistant\",\n" +
                "      \"created_at\": 1698982736,\n" +
                "      \"name\": \"Coding Tutor\",\n" +
                "      \"description\": null,\n" +
                "      \"model\": \"gpt-4\",\n" +
                "      \"instructions\": \"You are a helpful assistant designed to make me better at coding!\",\n" +
                "      \"tools\": [],\n" +
                "      \"file_ids\": [],\n" +
                "      \"metadata\": {}\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"asst_abc456\",\n" +
                "      \"object\": \"assistant\",\n" +
                "      \"created_at\": 1698982718,\n" +
                "      \"name\": \"My Assistant\",\n" +
                "      \"description\": null,\n" +
                "      \"model\": \"gpt-4\",\n" +
                "      \"instructions\": \"You are a helpful assistant designed to make me better at coding!\",\n" +
                "      \"tools\": [],\n" +
                "      \"file_ids\": [],\n" +
                "      \"metadata\": {}\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"asst_abc789\",\n" +
                "      \"object\": \"assistant\",\n" +
                "      \"created_at\": 1698982643,\n" +
                "      \"name\": null,\n" +
                "      \"description\": null,\n" +
                "      \"model\": \"gpt-4\",\n" +
                "      \"instructions\": null,\n" +
                "      \"tools\": [],\n" +
                "      \"file_ids\": [],\n" +
                "      \"metadata\": {}\n" +
                "    }\n" +
                "  ],\n" +
                "  \"first_id\": \"asst_abc123\",\n" +
                "  \"last_id\": \"asst_abc789\",\n" +
                "  \"has_more\": false\n" +
                "}";

        // Stubbing WireMock to respond to a GET request
        stubFor(get(urlEqualTo("/v1/assistants"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(expectedResponse)
                        .withHeader("Content-Type", "application/json")));

        AssistantListQueryBuilder queryBuilder = new AssistantListQueryBuilder();

        // Call listAssistants
        String actualResponse = client.listAssistants(queryBuilder);

        // Assert the response
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
    }

}