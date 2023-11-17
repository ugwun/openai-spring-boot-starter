package io.github.ugwun.openaispringbootstarter.client;

import io.github.ugwun.openaispringbootstarter.client.openai.AssistantBuilder;
import io.github.ugwun.openaispringbootstarter.client.openai.AssistantListQueryBuilder;

import java.io.IOException;

public interface AiApiClient {

        String createAssistant(AssistantBuilder assistantBuilder) throws IOException;

        String retrieveAssistant(String assistantId) throws IOException;

        String modifyAssistant(String assistantId, AssistantBuilder assistantBuilder) throws IOException;

        String deleteAssistant(String assistantId) throws IOException;

        String listAssistants(AssistantListQueryBuilder queryBuilder) throws IOException;
}