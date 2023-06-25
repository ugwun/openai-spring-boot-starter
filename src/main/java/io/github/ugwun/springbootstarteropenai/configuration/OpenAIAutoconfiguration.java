package io.github.ugwun.springbootstarteropenai.configuration;

import io.github.ugwun.springbootstarteropenai.client.AiApiClient;
import io.github.ugwun.springbootstarteropenai.client.OpenAiApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAIAutoconfiguration {

    @Value("${openai.api.key}")
    private String apiKey;

    @Bean
    @ConditionalOnMissingBean
    public AiApiClient openAIApiClient() {
        return new OpenAiApiClient(apiKey);
    }

}