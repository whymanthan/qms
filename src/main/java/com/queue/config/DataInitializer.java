package com.queue.config;

import com.queue.model.QueueItem;
import com.queue.repository.QueueItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private QueueItemRepository queueItemRepository;

    @Override
    public void run(String... args) throws Exception {
        // Add some sample data for testing
        if (queueItemRepository.count() == 0) {
            QueueItem item1 = new QueueItem("John Doe", "555-0101", "Consultation");
            item1.setJoinTime(LocalDateTime.now().minusMinutes(10));
            item1.setPosition(1);
            item1.setStatus("WAITING");
            queueItemRepository.save(item1);

            QueueItem item2 = new QueueItem("Jane Smith", "555-0102", "Support");
            item2.setJoinTime(LocalDateTime.now().minusMinutes(5));
            item2.setPosition(2);
            item2.setStatus("WAITING");
            queueItemRepository.save(item2);

            QueueItem item3 = new QueueItem("Bob Johnson", "555-0103", "Sales");
            item3.setJoinTime(LocalDateTime.now().minusMinutes(2));
            item3.setPosition(3);
            item3.setStatus("WAITING");
            queueItemRepository.save(item3);

            System.out.println("Sample queue data initialized!");
        }
    }
}
