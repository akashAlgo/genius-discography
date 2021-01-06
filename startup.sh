#!/bin/bash


### Run the java application
echo "Starting java process"
java -jar /opt/service.jar


### We should not reach this place during correct operation
echo "FATAL: java process exited"
