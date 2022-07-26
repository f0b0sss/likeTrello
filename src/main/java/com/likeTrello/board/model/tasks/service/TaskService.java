package com.likeTrello.board.model.tasks.service;

import com.likeTrello.board.model.tasks.model.Task;

import java.util.List;

public interface TaskService {

    Task getById(Long id);

    void save(Task task);

    void delete(Long id);

    List<Task> getAll(Long columnId);

    void changeTaskOrder(Task task, Integer index, List<Task> tasks);

    Task getByOrder(Long columnId, Long id);

    Long getMaxOrderValue(Long columnId);
}
