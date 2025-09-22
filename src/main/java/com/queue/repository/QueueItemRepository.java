package com.queue.repository;

import com.queue.model.QueueItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueueItemRepository extends JpaRepository<QueueItem, Long> {
    
    // Find all queue items ordered by position
    List<QueueItem> findAllByOrderByPositionAsc();
    
    // Find all waiting queue items ordered by position
    List<QueueItem> findByStatusOrderByPositionAsc(String status);
    
    // Get the next person in queue (lowest position)
    @Query("SELECT q FROM QueueItem q WHERE q.status = 'WAITING' ORDER BY q.position ASC LIMIT 1")
    QueueItem findNextInQueue();
    
    // Count waiting queue items
    long countByStatus(String status);
    
    // Find queue item by position
    QueueItem findByPosition(int position);
}
