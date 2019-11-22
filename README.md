# Spring Cloud Contract OpenAPI

:information_source: This project is under heavy development :information_source:

OpenAPI support for [Spring Cloud Contract](https://github.com/spring-cloud/spring-cloud-contract), which converts from OpenAPI spec to Contract internally and generate test codes based on the spec. No need to add more definitions to your OpenAPI spec for the conversion. :relieved:

## Usage

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
                        <version>1.0.0</version>
                    </dependency>
                </dependencies>
            </plugin>

            <!-- Install a jar file built in your machine locally for now -->
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
                            <version>1.0.0</version>
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

### Quick Example

#### OpenAPI document

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
```

#### Generate tests

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
