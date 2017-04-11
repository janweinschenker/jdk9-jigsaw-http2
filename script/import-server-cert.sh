#!/bin/bash

$JAVA_9_HOME/bin/keytool -import -v -trustcacerts -alias localhost-alias2 -file localhost.crt -keystore $JAVA_9_HOME/lib/security/cacerts -keypass changeit -storepass changeit
