# java-metric-agent

[![Build Status](https://travis-ci.com/Arlabunakty/java-metric-agent.svg?branch=master)](https://travis-ci.com/Arlabunakty/java-metric-agent)
![License](http://img.shields.io/:license-mit-blue.svg?style=flat-square)

**The java metric agent** is a metric-gathering extension for a web application. It gathers metrics about requests and responses served by the application.

-   Uniquely identify every Http Request/Response pair by Response Header 'X-Metric-Trace-Id'.
-   Embedded http server to display gathered metrics on the fly and to look up historical request time/response size by the unique identifier.
-   Support Request Time Metric to measure time spent between when the application starts to process the request, and the time when the application sends the response to the client.
-   Support Response Sizes to measure the size of the HTTP response body in bytes.

## How to use

1. Build the agent jar, see section **Building**.
2. Copy jar file on the path:
```/agent/build/libs/agent-all.jar```
3. To install the Java agent you need to configure your JVM to load the agent by passing the `-javaagent:/full/path/to/agent-all.jar` command-line argument.

## Building

The Java agent requires JDK 1.8.0_265 to build; your `JAVA_HOME` must be set to this JDK version.

To build the agent jar, run the following command from the project root directory:  
```./gradlew clean agent:shadowJar```

To build and run all checks:  
```./gradlew clean build```

After building, Java agent artifacts folder:  
- Agent: `agent/build/shadowJar/agent.jar`

## Testing

To run unit tests:
```./gradlew test```

To run integration tests:
```./gradlew integrationTest```

#### Local Sample application

To run the application:
```./gradlew bootRun```

Open in Chrome with dev tools:
```http:\\localhost:3030\```

[![Screenshot-2020-09-26-at-18-44-01.png](https://i.postimg.cc/RZ99vFQj/Screenshot-2020-09-26-at-18-44-01.png)](https://postimg.cc/fJH1KMPK)

Each response has header 'X-Metric-Trace-Id' with UUID to fetch request/response metrics on page:
```http:\\localhost:8081\```

[![Screenshot-2020-09-26-at-18-55-28.png](https://i.postimg.cc/BQzDzrTL/Screenshot-2020-09-26-at-18-55-28.png)](https://postimg.cc/6ydy211t)

- `requestOperationTime` is Request Time Metric to measure time spent between when the application starts to process the request, and the time when the application sends the response to the client;
- `responseBodyLength` is Response Size Metric to measure the size of the HTTP response body in bytes.

Future features:
- support Java 8+ runtimes;
- make configurable agent initialization. I.e. embedded server port, etc;
- provide the agent api jar to record custom metrics in user application;
- add more build-in metrics. I.e. JVM GC, servlet containers stats.

## License
[MIT](https://choosealicense.com/licenses/mit/)
