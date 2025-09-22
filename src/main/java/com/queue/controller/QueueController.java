package com.queue.controller;

import com.queue.model.QueueItem;
import com.queue.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/queue")
@CrossOrigin(origins = "*")
public class QueueController {

    @Autowired
    private QueueService queueService;

    @Autowired
    private WebSocketController webSocketController;

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addToQueue(
            @RequestParam String name,
            @RequestParam String phoneNumber,
            @RequestParam String service) {
        
        QueueItem item = queueService.addToQueue(name, phoneNumber, service);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("item", item);
        response.put("message", "Added to queue successfully");
        
        // Broadcast updates
        webSocketController.broadcastQueueUpdate();
        webSocketController.broadcastNewPerson(item);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<List<QueueItem>> getQueue() {
        return ResponseEntity.ok(queueService.getQueue());
    }

    @GetMapping("/next")
    public ResponseEntity<Map<String, Object>> getNext() {
        QueueItem next = queueService.getNext();
        Map<String, Object> response = new HashMap<>();
        
        if (next != null) {
            response.put("success", true);
            response.put("item", next);
            response.put("message", "Next person called");
            
            // Broadcast updates
            webSocketController.broadcastQueueUpdate();
            webSocketController.broadcastNextCalled(next);
        } else {
            response.put("success", false);
            response.put("message", "Queue is empty");
        }
        
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Map<String, Object>> removeFromQueue(@PathVariable Long id) {
        boolean removed = queueService.removeFromQueue(id);
        Map<String, Object> response = new HashMap<>();
        
        if (removed) {
            response.put("success", true);
            response.put("message", "Removed from queue successfully");
            
            // Broadcast updates
            webSocketController.broadcastQueueUpdate();
        } else {
            response.put("success", false);
            response.put("message", "Item not found in queue");
        }
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getQueueStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("size", queueService.getQueueSize());
        status.put("currentServing", queueService.getCurrentServing());
        status.put("queue", queueService.getQueue());
        
        return ResponseEntity.ok(status);
    }
}
