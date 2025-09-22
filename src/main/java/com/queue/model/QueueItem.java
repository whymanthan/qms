package com.queue.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "queue_items")
public class QueueItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    
    @Column(nullable = false)
    private String service;
    
    @Column(name = "join_time", nullable = false)
    private LocalDateTime joinTime;
    
    @Column(nullable = false)
    private int position;
    
    @Column(nullable = false)
    private String status; // WAITING, IN_SERVICE, COMPLETED

    public QueueItem() {}

    public QueueItem(String name, String phoneNumber, String service) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.service = service;
        this.joinTime = LocalDateTime.now();
        this.status = "WAITING";
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getService() { return service; }
    public void setService(String service) { this.service = service; }

    public LocalDateTime getJoinTime() { return joinTime; }
    public void setJoinTime(LocalDateTime joinTime) { this.joinTime = joinTime; }

    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
