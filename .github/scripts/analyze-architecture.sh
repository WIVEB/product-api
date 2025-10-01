#!/bin/bash

# Analyze Application Architecture
echo "# Architecture Analysis"
echo ""
echo "Analyzing application architecture..."
echo ""

# Initialize analysis file
cat > architecture-analysis.txt << 'EOF'
PROJECT CONTEXT:
This is a Spring Boot reactive web application using Java.

ANALYZE THE FOLLOWING COMPONENTS TO UNDERSTAND THE APPLICATION ARCHITECTURE:

EOF

# Include main application file
if [ -d "src/main/java" ]; then
    find src/main/java -name "*Application.java" | head -1 | while read -r file; do
        echo "=== Application Entry Point ===" >> architecture-analysis.txt
        cat "$file" >> architecture-analysis.txt
        echo "" >> architecture-analysis.txt
    done
fi

# Include all controller files
if [ -d "src/main/java/com/example/product/api/produc/presentation" ]; then
    find src/main/java/com/example/product/api/produc/presentation -name "*.java" -type f | while read -r file; do
        filename=$(basename "$file")
        echo "" >> architecture-analysis.txt
        echo "=== Presentation Layer: $filename ===" >> architecture-analysis.txt
        cat "$file" >> architecture-analysis.txt
        echo "" >> architecture-analysis.txt
    done
fi

# Include domain models
if [ -d "src/main/java/com/example/product/api/produc/domain/model" ]; then
    find src/main/java/com/example/product/api/produc/domain/model -name "*.java" -type f | while read -r file; do
        filename=$(basename "$file")
        echo "" >> architecture-analysis.txt
        echo "=== Domain Model: $filename ===" >> architecture-analysis.txt
        cat "$file" >> architecture-analysis.txt
        echo "" >> architecture-analysis.txt
    done
fi

# Include use cases (sample)
if [ -d "src/main/java/com/example/product/api/produc/usecase" ]; then
    find src/main/java/com/example/product/api/produc/usecase -name "*.java" -type f | head -3 | while read -r file; do
        filename=$(basename "$file")
        echo "" >> architecture-analysis.txt
        echo "=== Use Case: $filename ===" >> architecture-analysis.txt
        cat "$file" >> architecture-analysis.txt
        echo "" >> architecture-analysis.txt
    done
fi

# Include repository interfaces and implementations
if [ -d "src/main/java/com/example/product/api/produc/domain/repository" ]; then
    find src/main/java/com/example/product/api/produc/domain/repository -name "*.java" -type f | while read -r file; do
        filename=$(basename "$file")
        echo "" >> architecture-analysis.txt
        echo "=== Repository Interface: $filename ===" >> architecture-analysis.txt
        cat "$file" >> architecture-analysis.txt
        echo "" >> architecture-analysis.txt
    done
fi

if [ -d "src/main/java/com/example/product/api/produc/infrastructure/repository" ]; then
    find src/main/java/com/example/product/api/produc/infrastructure/repository -name "*.java" -type f | while read -r file; do
        filename=$(basename "$file")
        echo "" >> architecture-analysis.txt
        echo "=== Repository Implementation: $filename ===" >> architecture-analysis.txt
        cat "$file" >> architecture-analysis.txt
        echo "" >> architecture-analysis.txt
    done
fi

echo "Architecture analysis prepared in architecture-analysis.txt"
