# Update
This small library will be used only for demonstration/tutorial purposes. If you are looking for production Java library, please have look at LangChain4J: https://github.com/langchain4j/langchain4j

You can read tutorials on developing this library with GPT-4 and Github Copilot here: 
* https://medium.com/@cyrilsadovsky/creating-openai-springboot-library-1st-part-e8c6ecbb6b56
* https://medium.com/aimonks/creating-openai-springboot-library-using-gpt-4-and-github-copilot-2st-part-58c1cbdf16d2
---
# Work in progress
The main goal is to create a simple and easy to use library for OpenAI API.
Short term goals:
* [ ] OpenAI integration
  * Prototype is ready
  * set OpenAPI token in application.properties: openai.api.token={your API token} (https://platform.openai.com/account/api-keys)
  * inject AiApiClient bean in your class
  ```java
    @Autowired
    private AiApiClient aiApiClient;
    ```
  * usage:
  ```java
    Map<String, String>[] messages = new Map[]{
            Map.of("role", "system", "content", "You are a helpful assistant."),
            Map.of("role", "user", "content", "Who won the world series in 2020?")
    };

    try {
        return client.generateChatMessage("gpt-3.5-turbo", messages);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
  ```
* [ ] Azure OpenAI integration
* [x] Maven SNAPSHOT release
  * SNAPSHOT version can be found at https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/ugwun/openaispringbootstarter
  ```xml
    <dependency>
        <groupId>io.github.ugwun</groupId>
        <artifactId>openaispringbootstarter</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
    ```
  * Use snapshot repository
  ```xml
    <repositories>
        <repository>
            <id>ossrh</id>
            <url>https://s01.oss.sonatype.org/content/repositories/snapshots/</url>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
        </repository>
    </repositories>
    ```

If you want to contribute, please fork the project and submit a pull request. Unit and SpringBoot integration tests are would be appreciated.

The repo is unstable and under heavy development, so please be patient.
