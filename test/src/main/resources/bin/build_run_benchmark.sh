#!/bin/bash

# Change to your project root where pom.xml exists
PROJECT_ROOT="/home/pptutun/code/vwap-calculator"

echo "Changing directory to $PROJECT_ROOT"
cd "$PROJECT_ROOT" || { echo "Failed to cd to project root"; exit 1; }

echo "Running Maven clean install..."
mvn clean install

if [ $? -ne 0 ]; then
  echo "Maven build failed. Aborting."
  exit 1
fi

mkdir -p "$DIR/logs"

echo "Running JMH benchmark with JVM args..."
java -jar  $PROJECT_ROOT/test/target/benchmarks.jar -jvmArgs "-Xms4g -Xmx8g"
