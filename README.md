# Showcasing the HTTP/2 Client from JDK9


Code-examples showcasing the http2-implementation of JDK 9.

**Information concerning the incubator status of the http client**: *As of the jigsaw jdk9, build 149. the http client has been moved to incubator status. It will not be part of the final API of jdk9.* 

There is currently an [Issue in IntelliJ 2016.3](https://youtrack.jetbrains.com/issue/IDEA-162653) that prevents projects based on the Jigsaw-JDK to run properly. Use IntelliJ 2017.1 instead.

## Contents
1 [How to run this Code](#howtorun) <br/>
1.1 [Prerequisites](#Prerequisites) <br/>
1.2 [Setup instructions](#Setup) <br/>

2 [Running the examples](#Running)<br/>
2.1 [Fetching a list of target URIs asynchronously](#ResponseAsync)<br/>
2.2 [Fetching a list of target URIs asynchronously with multi response](#ResponseAsyncMulti)<br/>
2.3 [Cancel an HTTP request](#CancelClient)<br/>
2.4 [Run multiple requests and display milliseconds](#ResponseAsyncCompare)<br/>
2.5 [Clean the target and downloads folder](#Clean)<br/>

3 [Contribute](#Contribute)<br/>



## <a name="howtorun"></a>1 How to run this Code

### <a name="Prerequisites"></a> 1.1 Prerequisites
1. Apache Maven (the maven executable itself may run with a JVM < version 9)
1. A working maven-toolchains-plugin configuration file ([~/.m2/toolchains.xml](http://maven.apache.org/plugins/maven-toolchains-plugin/toolchains/jdk.html))
1. JDK 9 with Project Jigsaw
1. Optional: An IDE with JDK9 support. 
   1. [Eclipse Neon](http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/neonr) with its [Java 9 Support Plugin](https://marketplace.eclipse.org/content/java-9-support-beta-neon).
   1. IntelliJ IDEA 2017.1 or newer


### <a name="Setup"></a> 1.2 Setup and compile and compile instructions
1. Clone this project to your local drive.
1. [Download and install JDK 9 with Project Jigsaw](https://jdk9.java.net/jigsaw/)
   - Set the environment variable JAVA_9_HOME
   - JAVA_9_HOME is used in the script [import-server-cert.sh](./script/import-server-cert.sh)
1. Add the home directory of the Jigsaw-JDK to your toolchains.xml. See the example file at [toolchain/toolchains.xml](./toolchain/toolchains.xml)
1. In the project folder call `$> mvn clean:clean package`
   - Maven should use the JDK 9 `javac` compiler to compile the sources.
1. The setup is complete, when `$> mvn clean:clean package` has build successfully.

## <a name="Running"></a> 2 Running the examples

The examples can be executed with the exec-maven-plugin.

### <a name="ResponseAsync"></a> 2.1 Fetching a list of target URIs asynchronously

This example is implemented in [ResponseAsyncExample.java](
./src/main/java/de/holisticon/jdk9showcase/http2client/ResponseAsyncExample.java)
1. If not done already, compile the sources 
   - `$> mvn clean:clean package`
1. Execute the example code with maven by using the correct profile.
   - `$> mvn exec:exec -PResponseAsyncExample`
1. There is also a bash script that starts this maven target
   - `./script/run_single.sh`


### <a name="ResponseAsyncMulti"></a> 2.2 Fetching a list of target URIs asynchronously with multi response

This example is implemented in [ResponseAsyncMultiExample.java](
./src/main/java/de/holisticon/jdk9showcase/http2client/ResponseAsyncMultiExample.java).

The client will download an HTML file from the server. If the server pushes any other resources, they will be processed as well.

1. If not done already, compile the sources 
  1. `$> mvn clean:clean package`
1. Execute the example code with maven by using the correct profile.
  1. `$> mvn exec:exec -PResponseAsyncMultiExample`
1. There is also a bash script that starts this maven target
  1. `./script/run_multi.sh`

### <a name="CancelClient"></a> 2.3 Cancel an HTTP request

This example is implemented in [CancelClientExample.java](
./src/main/java/de/holisticon/jdk9showcase/http2client/CancelClientExample.java).

The client will initiate an HTTP GET request and will cancel it after 10 milliseconds. 

1. If not done already, compile the sources 
  1. `$> mvn clean:clean package`
1. Execute the example code with maven by using the correct profile.
  1. `$> mvn exec:exec -PCancelClientExample`
1. There is also a bash script that starts this maven target
  1. `./script/run_cancel.sh`

### <a name="ResponseAsyncCompare"></a> 2.4 Run multiple requests with HTTP/1.1 or HTTP/2.0 

This example is implemented in [ResponseAsyncCompare.java](
./src/main/java/de/holisticon/jdk9showcase/http2client/ResponseAsyncExample.java).

The client will run a number of requests against a server. At the end, it 
will display, how many milliseconds were needed to complete this task. 

1. If not done already, compile the sources 
  1. `$> mvn clean:clean package`
1. Execute the example code with maven by using the correct profile.
  1. `$> mvn exec:exec -PResponseAsyncCompare`
1. There is also a bash script that starts this maven target
  1. run 2000 HTTP1.1 requests: `./script/run_compare.sh HTTP1_1 2000`
  1. run 2000 HTTP2 requests: `./script/run_compare.sh HTTP2 2000`
  
### <a name="Clean"></a> 2.5 Clean the target and downloads folder

Call `$> mvn clean:clean`.

## <a name="Contribute"></a> 3 Contribute

Please feel free to propose bugfixes and changes to this showcase in a pull request.
