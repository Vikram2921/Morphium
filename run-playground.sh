#!/bin/bash
# Morphium Playground Server
# Professional JSON Transformation DSL Development Environment

echo "================================================================================"
echo " Morphium Playground Server"
echo " Starting professional development environment..."
echo "================================================================================"
echo ""

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "ERROR: Maven is not installed or not in PATH"
    echo "Please install Maven from https://maven.apache.org/"
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "ERROR: Java is not installed or not in PATH"
    echo "Please install Java JDK 11 or higher"
    exit 1
fi

echo "Compiling and starting server..."
echo ""

mvn compile exec:java -Dexec.mainClass="com.morphium.playground.PlaygroundServer" -Dexec.args="$*"

if [ $? -ne 0 ]; then
    echo ""
    echo "ERROR: Server failed to start"
    exit 1
fi
