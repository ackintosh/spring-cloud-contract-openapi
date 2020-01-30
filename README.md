# Spring Cloud Contract OpenAPI

:information_source: This project is under heavy development :information_source:

OpenAPI support for [Spring Cloud Contract](https://github.com/spring-cloud/spring-cloud-contract), which converts from OpenAPI spec to Contract internally and generate test codes based on the spec. No need to add more definitions to your OpenAPI spec for the conversion. :relieved:

## Usage

### Gradle

A sample project using Gradle is available in the repo:
https://github.com/ackintosh/spring-cloud-contract-openapi-sample

### Maven

```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-contract-maven-plugin</artifactId>
                <version>2.0.2.RELEASE</version>
                <extensions>true</extensions>
                <configuration>
                    <packageWithBaseClasses>{YOUR PACKAGE NAME}</packageWithBaseClasses>
                </configuration>
                <dependencies>
                    <dependency>
                        <groupId>com.github.ackintosh</groupId>
                        <artifactId>spring-cloud-contract-openapi</artifactId>
                        <version>0.0.1-SNAPSHOT</version>
                    </dependency>
                </dependencies>
            </plugin>

            <!-- Spring Cloud Contract OpenAPI is published on Github Packages but GitHub Packages does not support SNAPSHOT versions of Apache Maven. -->
            <!-- https://help.github.com/en/github/managing-packages-with-github-packages/configuring-apache-maven-for-use-with-github-packages -->
            <!-- For the above reason we need to install a jar file built by local machine for now. -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-install-plugin</artifactId>
                <executions>
                    <execution>
                        <id>install-external</id>
                        <phase>clean</phase>
                        <configuration>
                            <file>{YOUR PATH TO JAR FILE}</file>
                            <repositoryLayout>default</repositoryLayout>
                            <groupId>com.github.ackintosh</groupId>
                            <artifactId>spring-cloud-contract-openapi</artifactId>
                            <version>0.0.1-SNAPSHOT</version>
                            <packaging>jar</packaging>
                            <generatePom>true</generatePom>
                        </configuration>
                        <goals>
                            <goal>install-file</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```

## Quick Example

### OpenAPI document

Place your OpenAPI document under the `src/test/resources/contracts` folder.

```yaml
openapi: 3.0.2
info:
  version: 1.0.0
  title: Example
  license:
    name: MIT
paths:
  /example:
    get:
      responses:
        '200':
          description: OK
          content:
            application/json:
              schema:
                type: string
```

### Generate tests

Run `mvn spring-cloud-contract:generateTests` and then you can see the auto-generated test codes below:

```java
public class ContractVerifierTest extends ContractVerifierBase {

	@Test
	public void validate_openapi() throws Exception {
		// given:
			MockMvcRequestSpecification request = given();

		// when:
			ResponseOptions response = given().spec(request)
					.get("/example");

		// then:
			assertThat(response.statusCode()).isEqualTo(200);
	}

}
```

## Authors

* **Akihito Nakano** - *founding author* - [@ackintosh](https://github.com/ackintosh)

See also the list of [contributors](https://github.com/ackintosh/spring-cloud-contract-openapi/graphs/contributors) who participated in this project.
