package com.likeTrello.tasks.service;

import com.likeTrello.exceptions.TaskNotFoundException;
import com.likeTrello.tasks.model.Task;
import com.likeTrello.tasks.repository.TasksRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class TaskServiceImplTest {

    @Autowired
    TaskService taskService;

    @Autowired
    TasksRepository tasksRepository;

    @Test
    void getById_throwTaskNotFoundException_whenIncorrectId() {
        assertThrows(TaskNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                taskService.getById(10l);
            }
        });
    }

    @Test
    void getById() {
        assertEquals("Create entities", taskService.getById(1l).getTaskName());
    }

    @Test
    void save_ThrowConstraintViolationException_whenNameNull() {
        Task task = new Task();

        assertThrows(ConstraintViolationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                taskService.save(task, 1l);
            }
        });
    }

    @Test
    void save_ThrowConstraintViolationException_whenNameBlank() {
        Task task = new Task();
        task.setTaskName("    ");

        assertThrows(ConstraintViolationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                taskService.save(task, 1l);
            }
        });
    }

    @Test
    void save_ThrowConstraintViolationException_whenNameLessThen4Symbols() {
        Task task = new Task();
        task.setTaskName("New");

        assertThrows(ConstraintViolationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                taskService.save(task, 1l);
            }
        });
    }

    @Test
    void saveTask() {
        Task task = new Task();
        task.setTaskName("New Task name");

        taskService.save(task, 1l);

        assertEquals("New Task name", taskService.getById(5l).getTaskName());
    }

    @Test
    void save_generateCorrectId_whenAddTask() {
        Task task = new Task();
        task.setTaskName("New Task name");

        taskService.save(task, 1l);

        assertEquals(5l, taskService.getById(5l).getId());
    }

    @Test
    void save_setCorrectTaskOrderId_whenAddTask() {
        Task task = new Task();
        task.setTaskName("New Task name");

        taskService.save(task, 1l);

        assertEquals(5, taskService.getById(5l).getTaskOrder());
    }

    @Test
    void delete_throwInvalidParameterException_whenIncorrectId() {
        assertThrows(TaskNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                taskService.delete(10l);
            }
        });
    }

    @Test
    void deleteTask() {
        int size = taskService.getAll(1l).size();

        taskService.delete(1l);

        assertEquals(size - 1, taskService.getAll(1l).size());
    }

    @Test
    void getAllTasks() {
        assertEquals(4, taskService.getAll(1l).size());
    }

    @Test
    void changeTaskOrder_down() {
        Task task = taskService.getById(1l);

        assertEquals(1, task.getTaskOrder());

        taskService.changeTaskOrder(task, 1, 3, taskService.getAll(1l));

        assertEquals(3, task.getTaskOrder());
    }

    @Test
    void changeTaskOrder_up() {
        Task task = taskService.getById(4l);

        assertEquals(4, task.getTaskOrder());

        taskService.changeTaskOrder(task, 4, 1, taskService.getAll(1l));

        assertEquals(1, task.getTaskOrder());
    }

    @Test
    void getMaxOrderValue() {
        assertEquals(4, taskService.getMaxOrderValue(1l));
    }

}