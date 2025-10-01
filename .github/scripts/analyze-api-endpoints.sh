#!/bin/bash

# Analyze API Endpoints (Routes and Controllers)
echo "# API Endpoints Analysis"
echo ""
echo "Analyzing API endpoints and routing..."
echo ""

# Initialize analysis file
cat > api-endpoints-analysis.txt << 'EOF'
PROJECT CONTEXT:
This is a Spring Boot reactive web application with REST API endpoints.

ANALYZE THE FOLLOWING API CONTROLLERS AND ENDPOINT IMPLEMENTATIONS:

EOF

# Find all controller files in presentation layer
if [ -d "src/main/java/com/example/product/api/produc/presentation" ]; then
    find src/main/java/com/example/product/api/produc/presentation -name "*.java" -type f | while read -r file; do
        filename=$(basename "$file")
        echo "" >> api-endpoints-analysis.txt
        echo "=== $filename ===" >> api-endpoints-analysis.txt
        cat "$file" >> api-endpoints-analysis.txt
        echo "" >> api-endpoints-analysis.txt
    done
fi

# Find any other controller or endpoint files
if [ -d "src/main/java" ]; then
    find src/main/java -name "*Controller.java" -o -name "*Endpoint.java" -o -name "*Route.java" | while read -r file; do
        # Skip if already processed
        if [[ ! "$file" =~ presentation ]]; then
            filename=$(basename "$file")
            echo "" >> api-endpoints-analysis.txt
            echo "=== $filename ===" >> api-endpoints-analysis.txt
            cat "$file" >> api-endpoints-analysis.txt
            echo "" >> api-endpoints-analysis.txt
        fi
    done
fi

echo "API endpoints analysis prepared in api-endpoints-analysis.txt"
