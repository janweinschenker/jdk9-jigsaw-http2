#!/bin/bash
mvn clean:clean package exec:exec -PCancelClientExample -DhttpVersion=$1 -DnumberOfRequests=$2 -q


