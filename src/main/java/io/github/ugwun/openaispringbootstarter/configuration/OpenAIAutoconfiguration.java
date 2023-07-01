package io.github.ugwun.openaispringbootstarter.configuration;

import io.github.ugwun.openaispringbootstarter.client.AiApiClient;
import io.github.ugwun.openaispringbootstarter.client.OpenAiApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAIAutoconfiguration {

    @Value("${openai.api.token}")
    private String apiKey;

    @Bean
    @ConditionalOnMissingBean
    public AiApiClient openAIApiClient() {
        return new OpenAiApiClient(apiKey);
    }

}