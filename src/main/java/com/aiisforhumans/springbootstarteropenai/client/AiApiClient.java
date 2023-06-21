package com.aiisforhumans.springbootstarteropenai.client;

import java.io.IOException;
import java.util.Map;

public interface AiApiClient {

    String generateChatMessage(String model, Map<String, String>[] messages) throws IOException;

}