import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class QueueManager {
    private List<Patient> waitingQueue;
    private List<Patient> completedPatients;
    private Patient currentlyServing;
    private int nextQueueNumber;
    private int totalServedToday;
    private static final String DATA_FILE = "queue_data.txt";
    
    public QueueManager() {
        this.waitingQueue = new ArrayList<>();
        this.completedPatients = new ArrayList<>();
        this.currentlyServing = null;
        // Always start fresh numbering on each launch
        this.nextQueueNumber = 1;
        this.totalServedToday = 0;
        // Comment out loading to reset state every launch
        // loadData();
    }
    
    public void addPatient(Patient patient) {
        waitingQueue.add(patient);
        saveData();
    }
    
    public Patient callNextPatient() {
        if (waitingQueue.isEmpty()) {
            return null;
        }
        
        Patient nextPatient = waitingQueue.remove(0);
        nextPatient.setStatus("Serving");
        currentlyServing = nextPatient;
        saveData();
        return nextPatient;
    }
    
    public void completeCurrentPatient() {
        if (currentlyServing != null) {
            currentlyServing.setStatus("Completed");
            completedPatients.add(currentlyServing);
            totalServedToday++;
            currentlyServing = null;
            saveData();
        }
    }
    
    public Patient getCurrentlyServing() {
        return currentlyServing;
    }
    
    public List<Patient> getWaitingQueue() {
        return new ArrayList<>(waitingQueue);
    }
    
    public List<Patient> getCompletedPatients() {
        return new ArrayList<>(completedPatients);
    }
    
    public int getNextQueueNumber() {
        return nextQueueNumber++;
    }
    
    public int getWaitingCount() {
        return waitingQueue.size();
    }
    
    public int getTotalServedToday() {
        return totalServedToday;
    }
    
    public int getAverageWaitTime() {
        if (waitingQueue.isEmpty()) return 0;
        return waitingQueue.size() * 5; // Simple calculation: 5 minutes per person
    }
    
    public void removePatient(int queueNumber) {
        waitingQueue.removeIf(patient -> patient.getQueueNumber() == queueNumber);
        saveData();
    }
    
    public void clearQueue() {
        waitingQueue.clear();
        saveData();
    }
    
    private void saveData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {
            writer.println("QUEUE_DATA");
            writer.println("NEXT_QUEUE_NUMBER:" + nextQueueNumber);
            writer.println("TOTAL_SERVED_TODAY:" + totalServedToday);
            writer.println("CURRENTLY_SERVING:" + (currentlyServing != null ? currentlyServing.getQueueNumber() : "null"));
            
            writer.println("WAITING_QUEUE:");
            for (Patient patient : waitingQueue) {
                writer.println(patient.getName() + "|" + 
                             patient.getPhoneNumber() + "|" + 
                             patient.getDepartment() + "|" +
                             patient.getReason() + "|" + 
                             patient.getQueueNumber() + "|" + 
                             patient.getRegistrationTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "|" + 
                             patient.getStatus());
            }
            
            writer.println("COMPLETED_PATIENTS:");
            for (Patient patient : completedPatients) {
                writer.println(patient.getName() + "|" + 
                             patient.getPhoneNumber() + "|" + 
                             patient.getDepartment() + "|" +
                             patient.getReason() + "|" + 
                             patient.getQueueNumber() + "|" + 
                             patient.getRegistrationTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "|" + 
                             patient.getStatus());
            }
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }
    
    private void loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean inWaitingQueue = false;
            boolean inCompletedPatients = false;
            
            while ((line = reader.readLine()) != null) {
                if (line.equals("WAITING_QUEUE:")) {
                    inWaitingQueue = true;
                    inCompletedPatients = false;
                    continue;
                } else if (line.equals("COMPLETED_PATIENTS:")) {
                    inWaitingQueue = false;
                    inCompletedPatients = true;
                    continue;
                } else if (line.startsWith("NEXT_QUEUE_NUMBER:")) {
                    nextQueueNumber = Integer.parseInt(line.substring(18));
                    continue;
                } else if (line.startsWith("TOTAL_SERVED_TODAY:")) {
                    totalServedToday = Integer.parseInt(line.substring(19));
                    continue;
                } else if (line.startsWith("CURRENTLY_SERVING:")) {
                    String servingStr = line.substring(18);
                    if (!servingStr.equals("null")) {
                        int servingQueueNum = Integer.parseInt(servingStr);
                        // We'll set this after loading all patients
                    }
                    continue;
                }
                
                if (inWaitingQueue || inCompletedPatients) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 7) {
                        String name = parts[0];
                        String phone = parts[1];
                        String department = parts[2];
                        String reason = parts[3];
                        int queueNum = Integer.parseInt(parts[4]);
                        LocalDateTime regTime = LocalDateTime.parse(parts[5], DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                        String status = parts[6];
                        
                        Patient patient = new Patient(name, phone, department, reason, queueNum);
                        patient.setStatus(status);
                        
                        if (inWaitingQueue) {
                            waitingQueue.add(patient);
                        } else {
                            completedPatients.add(patient);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }
}
