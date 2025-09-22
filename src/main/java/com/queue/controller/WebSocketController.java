package com.queue.controller;

import com.queue.model.QueueItem;
import com.queue.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private QueueService queueService;

    public void broadcastQueueUpdate() {
        Map<String, Object> queueStatus = new HashMap<>();
        queueStatus.put("size", queueService.getQueueSize());
        queueStatus.put("currentServing", queueService.getCurrentServing());
        queueStatus.put("queue", queueService.getQueue());
        
        messagingTemplate.convertAndSend("/topic/queue", queueStatus);
    }

    public void broadcastNewPerson(QueueItem item) {
        Map<String, Object> message = new HashMap<>();
        message.put("type", "new_person");
        message.put("item", item);
        message.put("message", item.getName() + " joined the queue at position #" + item.getPosition());
        
        messagingTemplate.convertAndSend("/topic/notifications", message);
    }

    public void broadcastNextCalled(QueueItem item) {
        Map<String, Object> message = new HashMap<>();
        message.put("type", "next_called");
        message.put("item", item);
        message.put("message", "Now serving: " + item.getName());
        
        messagingTemplate.convertAndSend("/topic/notifications", message);
    }
}
