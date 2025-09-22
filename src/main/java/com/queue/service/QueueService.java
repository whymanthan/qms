package com.queue.service;

import com.queue.model.QueueItem;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class QueueService {
    private final List<QueueItem> queue = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public QueueItem addToQueue(String name, String phoneNumber, String service) {
        QueueItem item = new QueueItem(name, phoneNumber, service);
        item.setId(idGenerator.getAndIncrement());
        item.setPosition(queue.size() + 1);
        queue.add(item);
        return item;
    }

    public QueueItem getNext() {
        if (queue.isEmpty()) {
            return null;
        }
        QueueItem next = queue.remove(0);
        updatePositions();
        return next;
    }

    public boolean removeFromQueue(Long id) {
        return queue.removeIf(item -> item.getId().equals(id));
    }

    public List<QueueItem> getQueue() {
        return new ArrayList<>(queue);
    }

    public QueueItem getQueueItem(Long id) {
        return queue.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public int getQueueSize() {
        return queue.size();
    }

    public QueueItem getCurrentServing() {
        return queue.isEmpty() ? null : queue.get(0);
    }

    private void updatePositions() {
        for (int i = 0; i < queue.size(); i++) {
            queue.get(i).setPosition(i + 1);
        }
    }
}
