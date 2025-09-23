#!/bin/bash

echo "🏥 Hospital Queue Management System"
echo "==================================="
echo ""
echo "Compiling Java files..."
javac *.java
if [ $? -ne 0 ]; then
    echo "❌ Compilation failed!"
    exit 1
fi
echo "✅ Compilation successful!"
echo ""
echo "Starting application..."
java HospitalQueueApp
