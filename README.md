# Hospital Queue Management System

A modern, fullstack hospital queue management system built with Java Spring Boot and a beautiful web interface. This system allows patients to join hospital department queues and provides real-time updates to both patients and hospital staff.

## Features

### Patient Features
- **Join Queue**: Patients can join the hospital queue by providing their name, phone number, and department
- **Real-time Updates**: Live queue position updates and notifications
- **Queue Status**: View current queue size and estimated wait time
- **Modern UI**: Beautiful, responsive interface with smooth animations

### Hospital Staff Features
- **Queue Management**: View and manage the entire patient queue
- **Call Next**: Call the next patient in line
- **Remove Patients**: Remove patients from the queue if needed
- **Real-time Statistics**: Live updates of queue statistics
- **Admin Dashboard**: Comprehensive admin panel with queue analytics

### Technical Features
- **Real-time Communication**: WebSocket integration for instant updates
- **RESTful API**: Clean API endpoints for all operations
- **Database Integration**: H2 in-memory database with JPA/Hibernate
- **Data Persistence**: Queue data persists during application session
- **Responsive Design**: Works on desktop, tablet, and mobile devices
- **Modern UI/UX**: Bootstrap 5 with custom styling and animations

## Technology Stack

- **Backend**: Java 17, Spring Boot 3.2.0
- **Database**: H2 In-Memory Database
- **ORM**: JPA/Hibernate
- **Frontend**: HTML5, CSS3, JavaScript (ES6+)
- **Real-time**: WebSocket with STOMP protocol
- **UI Framework**: Bootstrap 5
- **Icons**: Font Awesome 6
- **Build Tool**: Maven

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- A modern web browser

## Installation & Setup

1. **Clone or download the project**
   ```bash
   # If you have git
   git clone <repository-url>
   cd queue-management
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - Customer Interface: http://localhost:8080
   - Admin Panel: http://localhost:8080/admin
   - H2 Database Console: http://localhost:8080/h2-console
     - JDBC URL: `jdbc:h2:mem:queuedb`
     - Username: `sa`
     - Password: `password`

## Usage

### For Patients
1. Open http://localhost:8080 in your browser
2. Fill in your details (Name, Phone, Department)
3. Click "Join Hospital Queue" to get your position
4. Watch your position update in real-time
5. Wait for your turn to be called

### For Hospital Staff
1. Open http://localhost:8080/admin in your browser
2. View current queue statistics
3. Use "Call Next Patient" to serve the next patient
4. Remove patients from queue if necessary
5. Monitor real-time queue updates

## API Endpoints

### Queue Management
- `POST /api/queue/add` - Add person to queue
- `GET /api/queue/list` - Get current queue
- `GET /api/queue/next` - Call next person
- `DELETE /api/queue/remove/{id}` - Remove person from queue
- `GET /api/queue/status` - Get queue status

### WebSocket Endpoints
- `/ws` - WebSocket connection endpoint
- `/topic/queue` - Queue updates
- `/topic/notifications` - System notifications

## Database Schema

The system uses an H2 in-memory database with the following table:

### `queue_items` Table
| Column | Type | Description |
|--------|------|-------------|
| `id` | BIGINT | Primary key (auto-generated) |
| `name` | VARCHAR | Patient's full name |
| `phone_number` | VARCHAR | Patient's phone number |
| `service` | VARCHAR | Hospital department/service |
| `join_time` | TIMESTAMP | When patient joined the queue |
| `position` | INTEGER | Position in queue (1-based) |
| `status` | VARCHAR | Status: WAITING, IN_SERVICE, COMPLETED |

### Sample Data
The application initializes with 4 sample patients for testing purposes:
- Sarah Johnson (Cardiology)
- Michael Chen (Emergency) 
- Emily Rodriguez (Pediatrics)
- David Thompson (Orthopedics)

## Project Structure

```
src/
├── main/
│   ├── java/com/queue/
│   │   ├── QueueManagementApplication.java    # Main application class
│   │   ├── config/
│   │   │   └── WebSocketConfig.java          # WebSocket configuration
│   │   ├── controller/
│   │   │   ├── QueueController.java          # REST API controller
│   │   │   ├── WebController.java            # Web page controller
│   │   │   └── WebSocketController.java      # WebSocket controller
│   │   ├── model/
│   │   │   └── QueueItem.java                # Queue item model
│   │   └── service/
│   │       └── QueueService.java             # Business logic
│   └── resources/
│       └── templates/
│           ├── index.html                    # Customer interface
│           └── admin.html                    # Admin interface
└── pom.xml                                   # Maven configuration
```

## Customization

### Adding New Service Types
Edit the service dropdown in `src/main/resources/templates/index.html`:
```html
<option value="YourService">Your Service</option>
```

### Changing UI Colors
Modify the CSS variables in the `<style>` sections of the HTML files.

### Adding New Features
- Extend the `QueueService` class for new business logic
- Add new endpoints in `QueueController`
- Update the frontend JavaScript for new functionality

## Troubleshooting

### Common Issues

1. **Port 8080 already in use**
   - Change the port in `src/main/resources/application.properties`
   - Add: `server.port=8081`

2. **WebSocket connection fails**
   - Ensure your browser supports WebSockets
   - Check browser console for error messages

3. **Maven build fails**
   - Ensure Java 17+ is installed
   - Run `mvn clean install -U` to force update dependencies

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is open source and available under the MIT License.

## Support

For issues and questions, please create an issue in the project repository.
