# IDE Setup Instructions

Since Maven might not be available in your command line, here are alternative ways to run the Queue Management System:

## Option 1: Using IntelliJ IDEA

1. **Open the project**
   - Open IntelliJ IDEA
   - Select "Open" and navigate to the project folder
   - IntelliJ will automatically detect it as a Maven project

2. **Run the application**
   - Navigate to `src/main/java/com/queue/QueueManagementApplication.java`
   - Right-click on the class and select "Run 'QueueManagementApplication'"
   - Or click the green play button next to the main method

3. **Access the application**
   - Customer Interface: http://localhost:8080
   - Admin Panel: http://localhost:8080/admin

## Option 2: Using Eclipse

1. **Import the project**
   - Open Eclipse
   - Go to File → Import
   - Select "Existing Maven Projects"
   - Browse to the project folder and import

2. **Run the application**
   - Right-click on the project
   - Select "Run As" → "Java Application"
   - Choose `QueueManagementApplication`

## Option 3: Using VS Code

1. **Install extensions**
   - Install "Extension Pack for Java"
   - Install "Spring Boot Extension Pack"

2. **Open the project**
   - Open VS Code
   - Open the project folder
   - VS Code will detect it as a Java project

3. **Run the application**
   - Press F5 or go to Run → Start Debugging
   - Select "Java" as the environment
   - Choose `QueueManagementApplication`

## Option 4: Command Line (if Maven is installed)

1. **Install Maven** (if not already installed)
   - Download from https://maven.apache.org/download.cgi
   - Extract and add to system PATH

2. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

## Troubleshooting

### Java Version Issues
- Ensure you have Java 17 or higher installed
- Check with: `java -version`

### Port Already in Use
- If port 8080 is busy, the application will fail to start
- Change the port in `src/main/resources/application.properties`:
  ```
  server.port=8081
  ```

### Dependencies Not Found
- In your IDE, refresh/reimport the Maven project
- In IntelliJ: Right-click project → Maven → Reload project
- In Eclipse: Right-click project → Maven → Reload projects

## Testing the Application

Once running, you can test the system by:

1. **Opening the customer interface** at http://localhost:8080
2. **Adding yourself to the queue** with test data
3. **Opening the admin panel** at http://localhost:8080/admin
4. **Calling the next person** and watching real-time updates

The system includes real-time WebSocket updates, so changes in one browser window will immediately reflect in other windows.
