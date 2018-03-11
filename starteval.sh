#!/bin/bash

clear
cd frontend-console/
echo "Bootstrapping the application..."
echo "...may take some time on an slower system..."
echo "============================================"
mvn exec:java
#java -jar ./frontend-console/target/frontend-console-0.0.1.jar

