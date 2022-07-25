package com.likeTrello.board.model.tasks.service;

import com.likeTrello.board.model.tasks.model.Task;
import com.likeTrello.board.model.tasks.repository.TasksRepository;
import com.likeTrello.board.model.tasks.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TasksRepository tasksRepository;

    @Override
    public Task getById(Long id) {
        return tasksRepository.findById(id).get();
    }

    @Override
    public void save(Task task) {
        tasksRepository.save(task);
    }

    @Override
    public void delete(Long id) {
        tasksRepository.deleteById(id);
    }

    @Override
    public List<Task> getAll() {
        return tasksRepository.findAll();
    }
}
