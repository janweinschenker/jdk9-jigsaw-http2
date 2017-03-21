#!/bin/bash
$JAVA_9_HOME/bin/keytool -import -v -trustcacerts -alias localhost-alias2 -file ~/Downloads/localhost.crt -keystore ../lib/security/cacerts -keypass changeit -storepass changeit
