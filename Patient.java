import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Patient {
    private String name;
    private String phoneNumber;
    private String department;
    private String reason;
    private int queueNumber;
    private LocalDateTime registrationTime;
    private String status; // "Waiting", "Called", "Completed", "Serving"
    
    public Patient(String name, String phoneNumber, String department, String reason, int queueNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.department = department;
        this.reason = reason;
        this.queueNumber = queueNumber;
        this.registrationTime = LocalDateTime.now();
        this.status = "Waiting";
    }
    
    // Getters
    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getDepartment() { return department; }
    public String getReason() { return reason; }
    public int getQueueNumber() { return queueNumber; }
    public LocalDateTime getRegistrationTime() { return registrationTime; }
    public String getStatus() { return status; }
    
    // Setters
    public void setStatus(String status) { this.status = status; }
    
    public String getFormattedTime() {
        return registrationTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
    
    @Override
    public String toString() {
        return String.format("Queue #%d - %s (%s) - %s - %s", 
                           queueNumber, name, phoneNumber, department, reason);
    }
}
