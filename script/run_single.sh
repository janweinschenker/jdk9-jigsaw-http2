#!/bin/bash
clear
mvn clean:clean package exec:exec -PResponseAsyncExample -DhttpVersion=$1 -DnumberOfRequests=$2 -q


