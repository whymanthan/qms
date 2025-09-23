# 🏥 Hospital Queue Management System

A modern, professional standalone Java application for managing patient queues in a hospital or clinic setting. Built with Java Swing featuring a beautiful gradient UI design and comprehensive admin functionality.

## ✨ Features

### 🎨 **Modern UI Design**
- **Gradient Background**: Beautiful purple-to-blue gradient design
- **Card-based Layout**: Clean white cards with rounded corners
- **Professional Color Scheme**: Hospital-themed colors and styling
- **Responsive Design**: Adaptive layout for different screen sizes

### 👥 **Patient Management**
- **Patient Registration**: Register with name, phone, department, and reason
- **Department Selection**: 18+ medical departments to choose from
- **Queue Number Assignment**: Automatic sequential numbering
- **Real-time Status**: Live updates of patient status

### 📊 **Queue Management**
- **Live Queue Display**: Real-time patient list with all details
- **Queue Statistics**: People waiting, average wait time, served today
- **Currently Serving**: Track who is being served right now
- **Status Tracking**: Waiting → Serving → Completed workflow

### ⚙️ **Admin Panel**
- **Dual Mode Interface**: Switch between patient and admin views
- **Advanced Controls**: Call next, complete current, clear queue
- **Statistics Dashboard**: Comprehensive metrics and analytics
- **Queue Management**: Full control over patient queue

### 💾 **Data Persistence**
- **Automatic Saving**: All data saved to file automatically
- **Session Recovery**: Restore queue state on application restart
- **Backup Support**: Simple text-based data storage

## 🚀 Quick Start

### Prerequisites
- Java 8 or higher
- No additional dependencies required

### Running the Application

1. **Compile the application:**
   ```bash
   javac *.java
   ```

2. **Run the application:**
   ```bash
   java HospitalQueueApp
   ```

## 📋 How to Use

### 🏥 **Patient View (Default)**
1. **Patient Registration**
   - Fill in patient's full name and phone number
   - Select department/service from dropdown (18+ options)
   - Enter reason for visit
   - Click "Join Hospital Queue" to register

2. **Queue Status**
   - View real-time queue statistics (people waiting, wait time)
   - See current queue list with all patient details
   - Track your position in the queue

### ⚙️ **Admin Panel**
1. **Access Admin Mode**
   - Click "Admin Panel" button in top-right corner
   - Switch between patient and admin views

2. **Queue Management**
   - **Call Next Patient**: Move next patient to "Currently Serving"
   - **Complete Current**: Mark current patient as completed
   - **Clear Queue**: Remove all patients from queue

3. **Statistics Dashboard**
   - **Total in Queue**: Current number of waiting patients
   - **Average Wait Time**: Estimated wait time in minutes
   - **Served Today**: Total patients completed today

4. **Currently Serving**
   - See who is currently being served
   - Manage patient flow through the system

## 🏗️ Project Structure

```
Hospital Queue Management System/
├── Patient.java                    # Patient model with department support
├── QueueManager.java              # Enhanced queue management logic
├── ModernHospitalQueueGUI.java    # Modern GUI with gradient design
├── HospitalQueueGUI.java          # Legacy GUI (backward compatibility)
├── HospitalQueueApp.java          # Application launcher
├── queue_data.txt                # Data persistence file (auto-generated)
├── run.bat                       # Windows batch file
├── run.sh                        # Unix/Linux shell script
└── README.md                     # This file
```

## 🔧 Technical Details

### Classes Overview

- **Patient**: Enhanced patient model with department, name, phone, reason, queue number, and status
- **QueueManager**: Advanced queue management with currently serving, statistics, and persistence
- **ModernHospitalQueueGUI**: Modern GUI with gradient design, dual-mode interface, and admin panel
- **HospitalQueueGUI**: Legacy GUI for backward compatibility
- **HospitalQueueApp**: Application launcher

### Data Persistence
- Uses simple text file format for data storage
- Automatically saves after each operation
- Loads data on application startup
- Enhanced format: `name|phone|department|reason|queueNumber|timestamp|status`
- Supports currently serving patient and daily statistics

### GUI Features
- **Gradient Background**: Beautiful purple-to-blue gradient design
- **Card-based Layout**: Clean white cards with rounded corners and shadows
- **Dual Mode Interface**: Patient view and Admin panel
- **Real-time Statistics**: Live queue metrics and status updates
- **Professional Color Scheme**: Hospital-themed colors and styling
- **Responsive Design**: Adaptive layout for different screen sizes
- **Interactive Elements**: Hover effects and smooth transitions

## 🎨 Interface Features

### 🏥 **Patient View**
- **Gradient Header**: Hospital branding with admin panel access
- **Registration Card**: Clean form with department selection
- **Queue Status Cards**: Real-time metrics (people waiting, wait time)
- **Current Queue List**: Live patient display with all details

### ⚙️ **Admin Panel**
- **Statistics Dashboard**: Three metric cards (queue, wait time, served)
- **Currently Serving Panel**: Track active patient with control buttons
- **Queue Management Table**: Full patient list with admin controls
- **Advanced Controls**: Call next, complete, clear queue functions

## 🔄 Workflow

### 🏥 **Patient Journey**
1. **Registration**: Patient fills form with name, phone, department, reason
2. **Queue Assignment**: System assigns queue number automatically
3. **Waiting**: Patient waits while seeing real-time queue status
4. **Called**: Admin calls next patient (moves to "Currently Serving")
5. **Completion**: Admin marks patient as completed

### ⚙️ **Admin Operations**
1. **Monitor Queue**: View statistics and patient list
2. **Call Next**: Move next patient to "Currently Serving" status
3. **Complete Service**: Mark current patient as completed
4. **Manage Queue**: Remove patients or clear entire queue
5. **Track Statistics**: Monitor daily performance metrics

## 🛠️ Customization

The application is designed to be easily customizable:

- **Colors & Themes**: Modify gradient colors and styling in ModernHospitalQueueGUI
- **Departments**: Add/remove departments in the DEPARTMENTS array
- **Fields**: Extend Patient class for additional patient information
- **Features**: Add new functionality by extending QueueManager
- **Layout**: Modify card layouts and responsive design
- **Statistics**: Customize metrics and calculations in QueueManager

## 📝 License

This project is open source and available under the MIT License.

## 🤝 Contributing

Feel free to contribute to this project by:
- Adding new features
- Improving the UI/UX
- Fixing bugs
- Adding documentation

## 📞 Support

For questions or issues, please create an issue in the project repository.

---

**Built with ❤️ using Java Swing**
