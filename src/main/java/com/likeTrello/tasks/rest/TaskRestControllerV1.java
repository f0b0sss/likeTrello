package com.likeTrello.tasks.rest;

import com.likeTrello.colums.model.Columns;
import com.likeTrello.colums.service.ColumnService;
import com.likeTrello.tasks.model.Task;
import com.likeTrello.tasks.service.TaskService;
import com.likeTrello.exceptions.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/board/{boardId}/column/{columnId}/task")
public class TaskRestControllerV1 {

    @Autowired
    private TaskService taskService;
    @Autowired
    private ColumnService columnService;

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(@PathVariable("columnId") Long columnId) throws TaskNotFoundException {
        List<Task> tasks = this.taskService.getAll(columnId);

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Task> getTask(@PathVariable("id") Long id) throws TaskNotFoundException {
        Task task = this.taskService.getById(id);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Task> saveTask(@RequestBody @Valid Task task,
                                         @PathVariable("columnId") Long columnId) {
        this.taskService.save(task, columnId);

        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Task> updateTask(@RequestBody @Valid Task task, @PathVariable Long columnId) {
        this.taskService.save(task, columnId);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PutMapping(value = "{id}/move/{newColumnId}")
    public ResponseEntity<Task> moveTaskToAnotherColumn(@RequestBody Task task, @PathVariable Long columnId,
                                                        @PathVariable Long newColumnId) {
        Columns columns = this.columnService.getById(newColumnId);

        task.setColumns(columns);

        this.taskService.save(task, columnId);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PutMapping(value = "{fromIndex}/order/{toIndex}")
    public ResponseEntity<List<Task>> changeTaskOrder(@PathVariable("fromIndex") Integer fromIndex,
                                                      @PathVariable("toIndex") Integer toIndex,
                                                      @PathVariable("columnId") Long columnId) {
        if (fromIndex != toIndex) {
            Task task = this.taskService.getByOrder(columnId, fromIndex);

            this.taskService.changeTaskOrder(task, fromIndex, toIndex, taskService.getAll(columnId));
        }

        List<Task> tasks = taskService.getAll(columnId);

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable("id") Long id) throws TaskNotFoundException {
        this.taskService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
