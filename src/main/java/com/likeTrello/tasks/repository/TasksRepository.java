package com.likeTrello.tasks.repository;

import com.likeTrello.tasks.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TasksRepository extends JpaRepository<Task, Long> {

    @Query("from Task where columns.id = ?1 order by taskOrder")
    List<Task> findAll(Long columnId);

    @Query("from Task where columns.id = ?1 and taskOrder = ?2")
    Task getByOrder(Long columnId, Integer id);

    @Query("select max(taskOrder) from Task where columns.id = ?1")
    Integer getMaxOrderValue(Long columnId);

}
