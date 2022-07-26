package com.likeTrello.board.model.colums.service;

import com.likeTrello.board.model.colums.model.Columns;

import java.util.List;

public interface ColumnService {

    Columns getById(Long id);

    void save(Columns columns);

    void delete(Long id);

    List<Columns> getAll(Long boardId);
}
