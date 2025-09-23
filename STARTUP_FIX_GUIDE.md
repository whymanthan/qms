# Hospital Queue Management - Startup Fix Guide

## Issue: SilentExitException Startup Error

The "SilentExitException: Startup error" occurs when the Spring Boot desktop application fails to start properly. This guide provides multiple solutions.

## Root Causes

1. **Missing Dependencies**: Spring Boot dependencies not downloaded
2. **Configuration Issues**: Web application type conflicts
3. **Maven Not Available**: Maven not installed or not in PATH
4. **Classpath Issues**: Missing JAR files in classpath

## Solutions (Try in Order)

### Solution 1: Install Maven (Recommended)

1. **Download Maven**:
   - Go to https://maven.apache.org/download.cgi
   - Download "Binary zip archive"
   - Extract to `C:\Program Files\Apache\maven` (or any folder)

2. **Add Maven to PATH**:
   - Open System Properties → Environment Variables
   - Add `C:\Program Files\Apache\maven\bin` to PATH
   - Restart command prompt

3. **Run the application**:
   ```cmd
   mvn clean compile
   mvn spring-boot:run -Dspring-boot.run.main-class=com.queue.desktop.DesktopApp
   ```

### Solution 2: Use IDE (Easiest)

1. **Open in IDE** (IntelliJ IDEA, Eclipse, VS Code):
   - Import as Maven project
   - Right-click on `DesktopApp.java`
   - Select "Run DesktopApp.main()"

2. **IDE will handle**:
   - Dependency resolution
   - Classpath setup
   - Compilation

### Solution 3: Manual Compilation (Advanced)

If Maven is not available, you can try manual compilation:

1. **Download dependencies manually**:
   - Spring Boot 3.2.0
   - Spring Framework 6.1.0
   - H2 Database
   - All transitive dependencies

2. **Set classpath**:
   ```cmd
   set CLASSPATH=target\classes;path\to\all\jars
   javac -cp "%CLASSPATH%" src\main\java\com\queue\desktop\DesktopApp.java
   java -cp "%CLASSPATH%" com.queue.desktop.DesktopApp
   ```

### Solution 4: Use the Updated run.bat

The `run.bat` file has been updated to attempt running the desktop application directly if compiled classes are available.

## What Was Fixed

### 1. DesktopApp.java Improvements:
- Better error handling and reporting
- More detailed startup logging
- Proper system property configuration
- Graceful error messages with solutions

### 2. DataInitializer.java Improvements:
- Exception handling to prevent SilentExitException
- Non-blocking initialization

### 3. application.properties Improvements:
- Explicit headless mode configuration
- Better logging configuration
- Lazy initialization disabled

## Testing the Fix

1. **Run the application** using any of the solutions above
2. **Look for these success messages**:
   - "Starting Spring Boot application context..."
   - "Spring Boot context started successfully!"
   - "QueueService bean retrieved successfully"
   - "Initializing Swing UI..."
   - "Swing UI initialized successfully!"

3. **If errors occur**, the improved error messages will show:
   - Specific error details
   - Suggested solutions
   - Clear troubleshooting steps

## Alternative: Simple Desktop App

If Spring Boot continues to cause issues, consider creating a simpler desktop application without Spring Boot dependencies, using just Swing and a simple file-based storage system.

## Need Help?

If none of these solutions work:
1. Check Java version (requires Java 17+)
2. Verify all files are present
3. Try running from different directory
4. Check Windows permissions
5. Consider using a different IDE

## Files Modified

- `src/main/java/com/queue/desktop/DesktopApp.java` - Enhanced error handling
- `src/main/java/com/queue/config/DataInitializer.java` - Exception handling
- `src/main/resources/application.properties` - Better configuration
- `run.bat` - Updated to run desktop app
- `run_desktop.bat` - New script for desktop-only execution

