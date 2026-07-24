#!/bin/bash
# CI verification script - runs all validation checks

set -e

echo "=== Uncanny Voxel CI Verification ==="

# 1. Build
echo "Building project..."
./gradlew clean build --no-daemon

# 2. Unit tests
echo "Running unit tests..."
./gradlew test --no-daemon

# 3. GameTests
echo "Running GameTests..."
./gradlew runGametest --no-daemon

# 4. Resource validation
echo "Validating resources..."
python3 ci/validate_resources.py

# 5. Log check (if logs exist)
if [ -d "logs" ]; then
    echo "Checking logs..."
    python3 ci/check_logs.py
fi

# 6. Check JAR exists
JAR_COUNT=$(find build/libs -name "*.jar" -not -name "*-sources.jar" -not -name "*-dev.jar" | wc -l)
if [ "$JAR_COUNT" -eq 0 ]; then
    echo "ERROR: No JAR artifact found"
    exit 1
fi

echo "Found $JAR_COUNT JAR artifact(s)"
echo "=== CI Verification PASSED ==="