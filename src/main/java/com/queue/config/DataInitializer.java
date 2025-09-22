package com.queue.config;

import com.queue.repository.QueueItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private QueueItemRepository queueItemRepository;

    @Override
    public void run(String... args) throws Exception {
        // Clear any existing data and start with empty queue
        queueItemRepository.deleteAll();
        System.out.println("Hospital queue cleared - starting with empty queue!");
    }
}
