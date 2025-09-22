package com.queue.service;

import com.queue.model.QueueItem;
import com.queue.repository.QueueItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class QueueService {
    
    @Autowired
    private QueueItemRepository queueItemRepository;

    public QueueItem addToQueue(String name, String phoneNumber, String service) {
        QueueItem item = new QueueItem(name, phoneNumber, service);
        item.setJoinTime(LocalDateTime.now());
        item.setStatus("WAITING");
        
        // Get the next position
        long queueSize = queueItemRepository.countByStatus("WAITING");
        item.setPosition((int) queueSize + 1);
        
        return queueItemRepository.save(item);
    }

    public QueueItem getNext() {
        QueueItem next = queueItemRepository.findNextInQueue();
        if (next != null) {
            next.setStatus("IN_SERVICE");
            queueItemRepository.save(next);
            updatePositions();
        }
        return next;
    }

    public boolean removeFromQueue(Long id) {
        if (queueItemRepository.existsById(id)) {
            queueItemRepository.deleteById(id);
            updatePositions();
            return true;
        }
        return false;
    }

    public List<QueueItem> getQueue() {
        return queueItemRepository.findByStatusOrderByPositionAsc("WAITING");
    }

    public QueueItem getQueueItem(Long id) {
        return queueItemRepository.findById(id).orElse(null);
    }

    public int getQueueSize() {
        return (int) queueItemRepository.countByStatus("WAITING");
    }

    public QueueItem getCurrentServing() {
        return queueItemRepository.findByStatusOrderByPositionAsc("IN_SERVICE")
                .stream()
                .findFirst()
                .orElse(null);
    }

    public void clearQueue() {
        queueItemRepository.deleteAll();
    }

    private void updatePositions() {
        List<QueueItem> waitingItems = queueItemRepository.findByStatusOrderByPositionAsc("WAITING");
        for (int i = 0; i < waitingItems.size(); i++) {
            QueueItem item = waitingItems.get(i);
            item.setPosition(i + 1);
            queueItemRepository.save(item);
        }
    }
}
