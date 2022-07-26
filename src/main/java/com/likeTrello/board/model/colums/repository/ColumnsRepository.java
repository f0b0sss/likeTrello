package com.likeTrello.board.model.colums.repository;

import com.likeTrello.board.model.colums.model.Columns;
import com.likeTrello.board.model.tasks.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ColumnsRepository extends JpaRepository<Columns, Long> {

    @Query("from Columns where board.id = ?1")
    List<Task> findAll(Long boardId);

}
