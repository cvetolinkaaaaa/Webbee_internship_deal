package com.webbee.deal.repository;

import com.webbee.deal.entity.InboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Репозиторий для работы с Inbox событиями.
 */
@Repository
public interface InboxEventRepository extends JpaRepository<InboxEvent, String> {

    /**
     * Проверка на идемпотентность - существует ли событие с данным messageId.
     */
    boolean existsByMessageId(String messageId);

    /**
     * Находит последнее обработанное событие для конкретного агрегата.
     */
    @Query("SELECT i FROM InboxEvent i " +
           "WHERE i.aggregateId = :aggregateId " +
           "AND i.aggregateType = :aggregateType " +
           "AND i.status = :status " +
           "ORDER BY i.version DESC, i.receivedAt DESC")
    List<InboxEvent> findLastProcessedEventByAggregate(@Param("aggregateId") String aggregateId,
                                                       @Param("aggregateType") String aggregateType,
                                                       @Param("status") String status);

    /**
     * Возвращает все события для агрегата, отсортированные по версии (убывание).
     */
    @Query("SELECT i FROM InboxEvent i " +
           "WHERE i.aggregateId = :aggregateId " +
           "AND i.aggregateType = :aggregateType " +
           "ORDER BY i.version DESC, i.receivedAt DESC")
    List<InboxEvent> findAllByAggregateOrderByVersionDesc(@Param("aggregateId") String aggregateId,
                                                          @Param("aggregateType") String aggregateType);

    /**
     * Подсчитывает количество обработанных событий с определенного времени.
     */
    @Query("SELECT COUNT(i) FROM InboxEvent i " +
           "WHERE i.status = :status " +
           "AND i.processedAt >= :since")
    Long countProcessedEventsSince(@Param("status") String status,
                                   @Param("since") LocalDateTime since);

    /**
     * Подсчитывает количество событий по статусу.
     */
    long countByStatus(String status);

    /**
     * Находит события по списку статусов.
     */
    List<InboxEvent> findByStatusIn(List<String> statuses);

    /**
     * Находит события по статусу.
     */
    List<InboxEvent> findByStatus(String status);

    /**
     * Находит события по messageId.
     */
    InboxEvent findByMessageId(String messageId);

}
