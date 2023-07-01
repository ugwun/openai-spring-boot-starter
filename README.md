# Work in progress
The main goal is to create a simple and easy to use library for OpenAI API.
Short term goals:
* [ ] OpenAI integration
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