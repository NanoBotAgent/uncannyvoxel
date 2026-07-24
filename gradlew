#!/bin/sh
# Gradle wrapper script
# This is a simple wrapper that delegates to the gradle wrapper jar

GRADLE_WRAPPER_JAR="gradle/wrapper/gradle-wrapper.jar"
GRADLE_PROPERTIES="gradle/wrapper/gradle-wrapper.properties"

if [ ! -f "$GRADLE_WRAPPER_JAR" ]; then
    echo "Error: $GRADLE_WRAPPER_JAR not found"
    echo "Run 'gradle wrapper' to generate wrapper files"
    exit 1
fi

if [ ! -f "$GRADLE_PROPERTIES" ]; then
    echo "Error: $GRADLE_PROPERTIES not found"
    exit 1
fi

exec java -jar "$GRADLE_WRAPPER_JAR" "$@"