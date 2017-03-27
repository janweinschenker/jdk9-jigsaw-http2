#!/bin/bash
clear
mvn clean:clean package exec:exec -PResponseAsyncMultiExample -q
