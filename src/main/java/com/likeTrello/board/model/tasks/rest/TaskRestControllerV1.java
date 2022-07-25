package com.likeTrello.board.model.tasks.rest;

import com.likeTrello.board.model.colums.model.Columns;
import com.likeTrello.board.model.colums.service.ColumnService;
import com.likeTrello.board.model.tasks.model.Task;
import com.likeTrello.board.model.tasks.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/board/{boardId}/column/{columnId}/task")
public class TaskRestControllerV1 {

    @Autowired
    private TaskService taskService;
    @Autowired
    private ColumnService columnService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> getTask(@PathVariable("id") Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Task task = this.taskService.getById(id);

        if (task == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PostMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> saveTask(@RequestBody Task task, @PathVariable("columnId") Long columnId) {

        HttpHeaders headers = new HttpHeaders();

        if (task == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Columns columns = this.columnService.getById(columnId);

        if (columns == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        task.setColumns(columns);

        this.taskService.save(task);

        return new ResponseEntity<>(task, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> updateTask(@RequestBody Task task, @PathVariable("columnId") Long columnId) {
        HttpHeaders headers = new HttpHeaders();

        if (task == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Columns columns = this.columnService.getById(columnId);

        if (columns == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        task.setColumns(columns);

        this.taskService.save(task);

        return new ResponseEntity<>(task, headers, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> deleteTask(@PathVariable("id") Long id) {
        Task task = this.taskService.getById(id);

        if (task == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.taskService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = this.taskService.getAll();

        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

}
