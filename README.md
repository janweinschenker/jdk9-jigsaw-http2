# Showcasing the HTTP/2 Client from JDK9


Code-examples showcasing the http2-implementation of JDK 9.




## How to run this Code

### Prerequisites
1. Apache Maven (the maven executable itself may run with a JVM < version 9)
1. A working maven-toolchains-plugin configuration file ([~/.m2/toolchains.xml](http://maven.apache.org/plugins/maven-toolchains-plugin/toolchains/jdk.html))
1. JDK 9 with Project Jigsaw


### Setup instructions
1. Clone this project to your local drive.
1. [Download and install JDK 9 with Project Jigsaw](https://jdk9.java.net/jigsaw/)
1. Add the home directory of the Jigsaw-JDK to your toolchains.xml. See the example file at [toolchain/toolchains.xml](./toolchain/toolchains.xml)
1. In the project folder call `$> mvn clean:clean package`
  1. Maven should use the JDK 9 `javac` compiler to compile the sources.