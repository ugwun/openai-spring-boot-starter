package com.aiisforhumans.springbootstarteropenai;

import com.aiisforhumans.springbootstarteropenai.client.AiApiClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootstarteropenaiApplicationTests {

	@Autowired
	AiApiClient aiApiClientBean;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(aiApiClientBean);
	}

}