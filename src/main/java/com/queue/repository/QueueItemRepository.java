package com.queue.repository;

import com.queue.model.QueueItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QueueItemRepository extends JpaRepository<QueueItem, Long> {
    List<QueueItem> findAllByOrderByPositionAsc();
    List<QueueItem> findByStatusOrderByPositionAsc(String status);
    long countByStatus(String status);
    QueueItem findByPosition(int position);
    Optional<QueueItem> findFirstByStatusOrderByPositionAsc(String status);
}
