package com.likeTrello.board.model.colums.repository;

import com.likeTrello.board.model.colums.model.Columns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ColumnsRepository extends JpaRepository<Columns, Long> {

    @Query("from Columns where board.id = ?1 order by columnOrder")
    List<Columns> findAll(Long boardId);

    @Query("from Columns where board.id = ?1 and columnOrder = ?2")
    Columns getByOrder(Long boardId, Integer id);

    @Query("select max(columnOrder) from Columns where board.id = ?1")
    Integer getMaxOrderValue(Long boardId);

}
