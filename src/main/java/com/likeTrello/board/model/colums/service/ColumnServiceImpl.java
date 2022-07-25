package com.likeTrello.board.model.colums.service;

import com.likeTrello.board.model.colums.model.Columns;
import com.likeTrello.board.model.colums.repository.ColumnsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColumnServiceImpl implements ColumnService{

    @Autowired
    private ColumnsRepository columnsRepository;

    @Override
    public Columns getById(Long id) {
        return columnsRepository.findById(id).get();
    }

    @Override
    public void save(Columns columns) {
        columnsRepository.save(columns);
    }

    @Override
    public void delete(Long id) {
        columnsRepository.deleteById(id);
    }

    @Override
    public List<Columns> getAll() {
        return columnsRepository.findAll();
    }
}
