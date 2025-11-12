#!/bin/bash
echo "Starting Morphium Playground..."
mvn compile exec:java -Dexec.mainClass="com.morphium.playground.PlaygroundServer"
