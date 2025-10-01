#!/bin/bash

# Analyze Technology Stack from build files
echo "# Technology Stack Analysis"
echo ""
echo "Analyzing build configuration..."
echo ""

# Initialize analysis file
cat > tech-stack-analysis.txt << 'EOF'
PROJECT CONTEXT:
This is a Spring Boot reactive web application using Java.

ANALYZE THE FOLLOWING BUILD CONFIGURATION:

EOF

# Detect and analyze build files (Maven or Gradle)
if [ -f "pom.xml" ]; then
    echo "=== pom.xml ===" >> tech-stack-analysis.txt
    cat pom.xml >> tech-stack-analysis.txt
    echo "" >> tech-stack-analysis.txt
elif [ -f "build.gradle.kts" ]; then
    echo "=== build.gradle.kts ===" >> tech-stack-analysis.txt
    cat build.gradle.kts >> tech-stack-analysis.txt
    echo "" >> tech-stack-analysis.txt
elif [ -f "build.gradle" ]; then
    echo "=== build.gradle ===" >> tech-stack-analysis.txt
    cat build.gradle >> tech-stack-analysis.txt
    echo "" >> tech-stack-analysis.txt
fi

# Add gradle properties if exists
if [ -f "gradle.properties" ]; then
    echo "=== gradle.properties ===" >> tech-stack-analysis.txt
    cat gradle.properties >> tech-stack-analysis.txt
    echo "" >> tech-stack-analysis.txt
fi

# Add application properties if exists
if [ -f "src/main/resources/application.properties" ]; then
    echo "=== application.properties ===" >> tech-stack-analysis.txt
    cat src/main/resources/application.properties >> tech-stack-analysis.txt
    echo "" >> tech-stack-analysis.txt
fi

if [ -f "src/main/resources/application.yml" ] || [ -f "src/main/resources/application.yaml" ]; then
    echo "=== application.yml ===" >> tech-stack-analysis.txt
    cat src/main/resources/application.y* 2>/dev/null >> tech-stack-analysis.txt
    echo "" >> tech-stack-analysis.txt
fi

# Add Dockerfile if exists
if [ -f "Dockerfile" ]; then
    echo "=== Dockerfile ===" >> tech-stack-analysis.txt
    cat Dockerfile >> tech-stack-analysis.txt
    echo "" >> tech-stack-analysis.txt
fi

# Add docker-compose if exists
if [ -f "docker-compose.yml" ] || [ -f "docker-compose.yaml" ]; then
    echo "=== docker-compose.yml ===" >> tech-stack-analysis.txt
    cat docker-compose.y* 2>/dev/null >> tech-stack-analysis.txt
    echo "" >> tech-stack-analysis.txt
fi

echo "Tech stack analysis prepared in tech-stack-analysis.txt"
