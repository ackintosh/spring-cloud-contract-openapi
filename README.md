# Spring Cloud Contract OpenAPI

:information_source: This project is under heavy development :information_source:

Spring Cloud Contract OpenAPI converts your OpenAPI definitions to [Spring Cloud Contract](https://github.com/spring-cloud/spring-cloud-contract) definitions. No need to add more definitions to your OpenAPI document for conversion. :relieved:

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
