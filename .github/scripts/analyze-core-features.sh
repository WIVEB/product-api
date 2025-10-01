#!/bin/bash

# Analyze Core Features (Business Logic Classes)
echo "# Core Features Analysis"
echo ""
echo "Analyzing business logic components..."
echo ""

# Initialize analysis file
cat > core-features-analysis.txt << 'EOF'
PROJECT CONTEXT:
This is a Spring Boot reactive web application using Java.

ANALYZE THE FOLLOWING CORE BUSINESS LOGIC COMPONENTS:

EOF

# Find and analyze all Java files in domain, usecase, and infrastructure directories
# Look for domain models, use cases, repositories, etc.

if [ -d "src/main/java" ]; then
    # Find all Java files in domain directory
    if [ -d "src/main/java/com/example/product/api/produc/domain" ]; then
        find src/main/java/com/example/product/api/produc/domain -name "*.java" -type f | while read -r file; do
            filename=$(basename "$file")
            echo "" >> core-features-analysis.txt
            echo "=== Domain: $filename ===" >> core-features-analysis.txt
            cat "$file" >> core-features-analysis.txt
            echo "" >> core-features-analysis.txt
        done
    fi

    # Find all Java files in usecase directory
    if [ -d "src/main/java/com/example/product/api/produc/usecase" ]; then
        find src/main/java/com/example/product/api/produc/usecase -name "*.java" -type f | while read -r file; do
            filename=$(basename "$file")
            echo "" >> core-features-analysis.txt
            echo "=== Use Case: $filename ===" >> core-features-analysis.txt
            cat "$file" >> core-features-analysis.txt
            echo "" >> core-features-analysis.txt
        done
    fi

    # Find all Java files in infrastructure directory (excluding controllers)
    if [ -d "src/main/java/com/example/product/api/produc/infrastructure" ]; then
        find src/main/java/com/example/product/api/produc/infrastructure -name "*.java" -type f | while read -r file; do
            filename=$(basename "$file")
            echo "" >> core-features-analysis.txt
            echo "=== Infrastructure: $filename ===" >> core-features-analysis.txt
            cat "$file" >> core-features-analysis.txt
            echo "" >> core-features-analysis.txt
        done
    fi

    # Find configuration files
    if [ -d "src/main/java/com/example/product/api/produc/config" ]; then
        find src/main/java/com/example/product/api/produc/config -name "*.java" -type f | while read -r file; do
            filename=$(basename "$file")
            echo "" >> core-features-analysis.txt
            echo "=== Configuration: $filename ===" >> core-features-analysis.txt
            cat "$file" >> core-features-analysis.txt
            echo "" >> core-features-analysis.txt
        done
    fi
fi

echo "Core features analysis prepared in core-features-analysis.txt"
