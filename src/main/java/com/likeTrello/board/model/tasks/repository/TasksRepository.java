package com.likeTrello.board.model.tasks.repository;

import com.likeTrello.board.model.tasks.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TasksRepository extends JpaRepository<Task, Long> {

}
