package com.likeTrello.board.model.tasks.service;

import com.likeTrello.board.model.tasks.model.Task;
import com.likeTrello.exceptions.InvalidParameterException;
import com.likeTrello.exceptions.TaskNotFoundException;

import java.util.List;

public interface TaskService {

    Task getById(Long id) throws InvalidParameterException;

    void save(Task task, Long columnId);

    void delete(Long id) throws InvalidParameterException;

    List<Task> getAll(Long columnId) throws TaskNotFoundException;

    void changeTaskOrder(Task task, Integer FromIndex, Integer toIndex, List<Task> tasks);

    Task getByOrder(Long columnId, Integer id);

    Integer getMaxOrderValue(Long columnId);
}
