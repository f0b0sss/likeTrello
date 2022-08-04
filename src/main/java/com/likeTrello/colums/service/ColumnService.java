package com.likeTrello.colums.service;

import com.likeTrello.colums.model.Columns;

import java.util.List;

public interface ColumnService {

    Columns getById(Long id);

    void save(Columns columns, Long boardId);

    void delete(Long id);

    List<Columns> getAll(Long boardId);

    void changeColumnOrder(Columns column, Integer FromIndex, Integer toIndex, List<Columns> columns);

    Columns getByOrder(Long boardId, Integer id);

    Integer getMaxOrderValue(Long boardId);
}
