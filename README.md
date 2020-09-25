# java-metric-agent

[![Build Status](https://travis-ci.com/Arlabunakty/java-metric-agent.svg?branch=master)](https://travis-ci.com/Arlabunakty/java-metric-agent)

**The java metric agent** is a metric-gathering extension for a web application. It gathers metrics about requests and responses served by the application

## Features

-   Uniquely identify every Http Request/Response pair by Response Header 'X-Metric-Trace-Id'.
-   Embedded http server to display gathered metrics on the fly and to look up historical request time/response size by the unique identifier.
-   Support Request Time Metric to measure time spent between when the application starts to process the request, and the time when the application sends the response to the client.
-   Support Response Sizes to measure the size of the HTTP response body in bytes.

## Installation

To install the Java agent you need to configure your JVM to load the agent by passing the `-javaagent:/full/path/to/agent.jar` command-line argument.

## Building
#### Gradle build

The Java agent requires JDK 1.8.0_265 to build; your `JAVA_HOME` must be set to this JDK version.

To build the agent jar, run the following command from the project root directory:  
`./gradlew clean agent:shadowJar`

To build and run all checks:  
`./gradlew clean build --parallel`

After building, Java agent artifacts are located here:  
- Agent: `agent/build/shadowJar/agent.jar`

## License
[MIT](https://choosealicense.com/licenses/mit/)
