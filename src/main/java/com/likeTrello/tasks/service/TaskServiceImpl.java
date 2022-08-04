package com.likeTrello.tasks.service;

import com.likeTrello.colums.model.Columns;
import com.likeTrello.colums.service.ColumnService;
import com.likeTrello.exceptions.TaskNotFoundException;
import com.likeTrello.tasks.model.Task;
import com.likeTrello.tasks.repository.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {


    @Autowired
    private TasksRepository tasksRepository;

    @Autowired
    private ColumnService columnService;

    @Override
    public Task getById(Long id) {
        return tasksRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException("Task with id " + id + " not found"));
    }

    @Override
    public void save(Task task, Long columnId) {
        Columns columns = this.columnService.getById(columnId);

        task.setColumns(columns);

        Integer taskOrderId = getMaxOrderValue(columnId);

        if (taskOrderId == null) {
            task.setTaskOrder(1);
        } else {
            task.setTaskOrder(taskOrderId + 1);
        }

        tasksRepository.save(task);
    }

    @Override
    public void delete(Long id) {
        if (!tasksRepository.existsById(id)) {
            throw new TaskNotFoundException("Column with id " + id + " not found");
        }
        tasksRepository.deleteById(id);
    }

    @Override
    public List<Task> getAll(Long columnId) {
        return tasksRepository.findAll(columnId);
    }

    @Override
    public void changeTaskOrder(Task task, Integer FromIndex, Integer toIndex, List<Task> tasks) {
        if (FromIndex < toIndex) {
            moveTaskDown(task, toIndex, tasks);
        } else {
            moveTaskUp(task, toIndex, tasks);
        }

        for (Task taskElement : tasks) {
            tasksRepository.save(taskElement);
        }

    }

    private void moveTaskUp(Task task, Integer toIndex, List<Task> tasks) {
        int prevOrder = 0;
        int nextOrder = 0;

        for (int i = tasks.size() - 1; i >= 0; i--) {
            if (tasks.get(i).getTaskOrder() == task.getTaskOrder()) {

                while (i != toIndex - 1) {
                    prevOrder = nextOrder;

                    if (tasks.get(i).getTaskOrder() == task.getTaskOrder()) {
                        nextOrder = tasks.get(i).getTaskOrder();
                    } else {
                        nextOrder = tasks.get(i).getTaskOrder();

                        tasks.get(i).setTaskOrder(prevOrder);
                    }
                    i--;

                    if (i == toIndex - 1) {
                        prevOrder = nextOrder;
                        nextOrder = tasks.get(i).getTaskOrder();
                    }
                }
                tasks.get(i).setTaskOrder(prevOrder);

                task.setTaskOrder(nextOrder);
                break;
            }
        }
    }

    private void moveTaskDown(Task task, Integer toIndex, List<Task> tasks) {
        int prevOrder = 0;
        int nextOrder = 0;

        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTaskOrder() == task.getTaskOrder()) {

                while (i + 1 != toIndex) {
                    prevOrder = nextOrder;

                    if (tasks.get(i).getTaskOrder() == task.getTaskOrder()) {
                        nextOrder = tasks.get(i).getTaskOrder();
                    } else {
                        nextOrder = tasks.get(i).getTaskOrder();

                        tasks.get(i).setTaskOrder(prevOrder);
                    }
                    i++;

                    if (i + 1 == toIndex) {
                        prevOrder = nextOrder;
                        nextOrder = tasks.get(i).getTaskOrder();
                    }
                }
                tasks.get(i).setTaskOrder(prevOrder);

                task.setTaskOrder(nextOrder);
                break;
            }
        }
    }

    @Override
    public Task getByOrder(Long columnId, Integer id) {
        int orderId = this.tasksRepository.findAll(columnId).get(id - 1).getTaskOrder();
        return this.tasksRepository.getByOrder(columnId, orderId);
    }

    @Override
    public Integer getMaxOrderValue(Long columnId) {
        return tasksRepository.getMaxOrderValue(columnId);
    }
}
