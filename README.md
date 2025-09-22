# Hospital Queue Management System

## Installation Guide

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher (optional - can use IDE instead)
- A modern web browser

### Installation Steps

#### Option 1: Using IDE (Recommended)

1. **Open the project in your IDE**
   - **IntelliJ IDEA**: Open project folder → Run `QueueManagementApplication.java`
   - **Eclipse**: Import as Maven project → Run as Java Application
   - **VS Code**: Install Java extensions → Press F5 to run

2. **Access the application**
   - Patient Interface: http://localhost:8080
   - Admin Panel: http://localhost:8080/admin
   - Database Console: http://localhost:8080/h2-console

#### Option 2: Using Command Line (if Maven is installed)

1. **Build the project**
   ```bash
   mvn clean install
   ```

2. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

3. **Access the application**
   - Patient Interface: http://localhost:8080
   - Admin Panel: http://localhost:8080/admin

### Database Access
- **H2 Console**: http://localhost:8080/h2-console
- **JDBC URL**: `jdbc:h2:mem:queuedb`
- **Username**: `sa`
- **Password**: `password`

### Quick Start
1. Run the application using your preferred method
2. Open http://localhost:8080 to join the hospital queue
3. Open http://localhost:8080/admin to manage the queue
4. The system starts with an empty queue ready for use

### Troubleshooting
- **Port 8080 in use**: Change port in `application.properties`
- **Maven not found**: Use IDE to run the application instead
- **Java version**: Ensure Java 17+ is installed
