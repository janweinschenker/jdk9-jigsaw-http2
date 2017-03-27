#!/bin/bash

mvn clean:clean package exec:exec -PResponseAsyncCompare -DhttpVersion=$1 -DnumberOfRequests=$2 -q


