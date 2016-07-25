# Showcasing the HTTP/2 Client from JDK9


Code-examples showcasing the http2-implementation of JDK 9.

## Contents
1 [How to run this Code](#howtorun)

1\.1 [Prerequisites](#Prerequisites)

1\.2 [Setup instructions](#Setup)


## <a name="howtorun"></a>1 How to run this Code

### <a name="Prerequisites"></a> 1.1 Prerequisites
1. Apache Maven (the maven executable itself may run with a JVM < version 9)
1. A working maven-toolchains-plugin configuration file ([~/.m2/toolchains.xml](http://maven.apache.org/plugins/maven-toolchains-plugin/toolchains/jdk.html))
1. JDK 9 with Project Jigsaw


### <a name="Setup"></a> 1.2 Setup instructions
1. Clone this project to your local drive.
1. [Download and install JDK 9 with Project Jigsaw](https://jdk9.java.net/jigsaw/)
1. Add the home directory of the Jigsaw-JDK to your toolchains.xml. See the example file at [toolchain/toolchains.xml](./toolchain/toolchains.xml)
1. In the project folder call `$> mvn clean:clean package`
  1. Maven should use the JDK 9 `javac` compiler to compile the sources.
1. The setup is complete, when `$> mvn clean:clean package` has build successfully.

## Running the examples

The examples can be executed with the exec-maven-plugin.

### Fetching a list of target URIs asynchronously

This example is implemented in [ResponseAsync.java](./src/main/java/com/holisticon/jdk9/http2/strategy/ResponseAsync.java).

1. If not done already, compile the sources 
  1. `$> mvn clean:clean package`
1. Execute the example code with maven by using the correct profile.
  1. `mvn exec:exec -PResponseAsyncExample`
1. The example will contact the configured http servers ([see UriProvider.java](./src/main/java/com/holisticon/jdk9/http2/util/UriProvider.java)) and will try to download one html-file from every server.
  1. This might take several minutes.
1. Downloaded files can be found in [./downloads/ResponseAsync](./downloads/ResponseAsync).


