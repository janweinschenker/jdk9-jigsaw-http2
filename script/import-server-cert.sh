#!/bin/bash
filename=$1

$JAVA_9_HOME/bin/keytool -import -v -trustcacerts -alias localhost-alias2 -file $filename -keystore ../lib/security/cacerts -keypass changeit -storepass changeit
