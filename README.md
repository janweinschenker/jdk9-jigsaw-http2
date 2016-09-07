# Showcasing the HTTP/2 Client from JDK9


Code-examples showcasing the http2-implementation of JDK 9.

**Information concerning support of HTTPS**: *As of the jigsaw jdk9, build 133, I could not figure out how to
retrieve contents from https-addresses. As soon as I find out how to run these examples with https, the code will be changed accordingly.*

*As of now, this seems to be a bug in the JDK, see [the OpenJDK bugtracker](https://bugs.openjdk.java.net/browse/JDK-8157482).*

## Contents
1 [How to run this Code](#howtorun) <br/>
1.1 [Prerequisites](#Prerequisites) <br/>
1.2 [Setup instructions](#Setup) <br/>

2 [Running the examples](#Running)<br/>
2.1 [Fetching a list of target URIs asynchronously](#ResponseAsync)<br/>
2.2 [Fetching a list of target URIs asynchronously with multi response](#ResponseAsyncMulti)<br/>
2.3 [Cancel an HTTP request](#CancelClient)<br/>
2.4 [Clean the target and downloads folder](#Clean)



## <a name="howtorun"></a>1 How to run this Code

### <a name="Prerequisites"></a> 1.1 Prerequisites
1. Apache Maven (the maven executable itself may run with a JVM < version 9)
1. A working maven-toolchains-plugin configuration file ([~/.m2/toolchains.xml](http://maven.apache.org/plugins/maven-toolchains-plugin/toolchains/jdk.html))
1. JDK 9 with Project Jigsaw


### <a name="Setup"></a> 1.2 Setup and compile and compile instructions
1. Clone this project to your local drive.
1. [Download and install JDK 9 with Project Jigsaw](https://jdk9.java.net/jigsaw/)
1. Add the home directory of the Jigsaw-JDK to your toolchains.xml. See the example file at [toolchain/toolchains.xml](./toolchain/toolchains.xml)
1. In the project folder call `$> mvn clean:clean package`
  1. Maven should use the JDK 9 `javac` compiler to compile the sources.
1. The setup is complete, when `$> mvn clean:clean package` has build successfully.

## <a name="Running"></a> 2 Running the examples

The examples can be executed with the exec-maven-plugin.

### <a name="ResponseAsync"></a> 2.1 Fetching a list of target URIs asynchronously

This example is implemented in [ResponseAsync.java](./src/main/java/com/holisticon/jdk9/http2/strategy/ResponseAsync.java).

1. If not done already, compile the sources 
  1. `$> mvn clean:clean package`
1. Execute the example code with maven by using the correct profile.
  1. `$> mvn exec:exec -PResponseAsyncExample`
1. The example will contact the configured http servers ([see UriProvider.java](./src/main/java/com/holisticon/jdk9/http2/util/UriProvider.java)) and will try to download one html-file from every server.
  1. This might take several minutes.
1. Downloaded files can be found in [./downloads/ResponseAsync](./downloads/ResponseAsync).

### <a name="ResponseAsyncMulti"></a> 2.2 Fetching a list of target URIs asynchronously with multi response

This example is implemented in [ResponseAsyncMulti.java](./src/main/java/com/holisticon/jdk9/http2/strategy/ResponseAsyncMulti.java).

The client will download an HTML file from the server. If the server pushes any other resources

1. If not done already, compile the sources 
  1. `$> mvn clean:clean package`
1. Execute the example code with maven by using the correct profile.
  1. `$> mvn exec:exec -PResponseAsyncMultiExample`
1. The example will contact the configured http servers ([see UriProvider.java](./src/main/java/com/holisticon/jdk9/http2/util/UriProvider.java)) and will try to download one html-file from every server.
  1. This might take several minutes.
1. Downloaded files can be found in [./downloads/ResponseAsyncMulti](./downloads/ResponseAsyncMulti).

### <a name="CancelClient"></a> 2.3 Cancel an HTTP request

This example is implemented in [CancelClientExample.java](./src/main/java/com/holisticon/jdk9/http2/CancelClientExample.java).

The client will initiate an HTTP GET request and will cancel it after 10 milliseconds. 

1. If not done already, compile the sources 
  1. `$> mvn clean:clean package`
1. Execute the example code with maven by using the correct profile.
  1. `$> mvn exec:exec -PCancelClientExample`
1. The request will be cancelled after 10 miiliseconds and a ``CancellationException`` will be thrown.


### <a name="Clean"></a> 2.4 Clean the target and downloads folder

Call `$> mvn clean:clean`.
