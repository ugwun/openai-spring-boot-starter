package io.github.ugwun.openaispringbootstarter;

import io.github.ugwun.openaispringbootstarter.client.AiApiClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"openai.api.token=openai_token"
})
class SpringbootstarteropenaiApplicationTests {

	@Autowired
	AiApiClient aiApiClientBean;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(aiApiClientBean);
	}

}