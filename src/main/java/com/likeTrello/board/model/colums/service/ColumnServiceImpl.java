package com.likeTrello.board.model.colums.service;

import com.likeTrello.board.model.colums.model.Columns;
import com.likeTrello.board.model.colums.repository.ColumnsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColumnServiceImpl implements ColumnService {

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
    public List<Columns> getAll(Long boardId) {
        return columnsRepository.findAll(boardId);
    }

    @Override
    public void changeColumnOrder(Columns column, Integer FromIndex, Integer toIndex, List<Columns> columns) {
        if (FromIndex < toIndex) {
            moveColumnRight(column, toIndex, columns);
        } else {
            moveColumnLeft(column, toIndex, columns);
        }

        for (Columns columnElement : columns) {
            columnsRepository.save(columnElement);
        }
    }

    private void moveColumnLeft(Columns column, Integer toIndex, List<Columns> columns) {
        int prevOrder = 0;
        int nextOrder = 0;

        for (int i = columns.size() - 1; i >= 0; i--) {
            if (columns.get(i).getColumnOrder() == column.getColumnOrder()) {

                while (i != toIndex - 1) {
                    prevOrder = nextOrder;

                    if (columns.get(i).getColumnOrder() == column.getColumnOrder()) {
                        nextOrder = columns.get(i).getColumnOrder();
                    } else {
                        nextOrder = columns.get(i).getColumnOrder();

                        columns.get(i).setColumnOrder(prevOrder);
                    }
                    i--;

                    if (i == toIndex - 1) {
                        prevOrder = nextOrder;
                        nextOrder = columns.get(i).getColumnOrder();
                    }
                }
                columns.get(i).setColumnOrder(prevOrder);

                column.setColumnOrder(nextOrder);
                break;
            }
        }
    }

    private void moveColumnRight(Columns column, Integer toIndex, List<Columns> columns) {
        int prevOrder = 0;
        int nextOrder = 0;

        for (int i = 0; i < columns.size(); i++) {
            if (columns.get(i).getColumnOrder() == column.getColumnOrder()) {

                while (i + 1 != toIndex) {
                    prevOrder = nextOrder;

                    if (columns.get(i).getColumnOrder() == column.getColumnOrder()) {
                        nextOrder = columns.get(i).getColumnOrder();
                    } else {
                        nextOrder = columns.get(i).getColumnOrder();

                        columns.get(i).setColumnOrder(prevOrder);
                    }
                    i++;

                    if (i + 1 == toIndex) {
                        prevOrder = nextOrder;
                        nextOrder = columns.get(i).getColumnOrder();
                    }
                }
                columns.get(i).setColumnOrder(prevOrder);

                column.setColumnOrder(nextOrder);
                break;
            }
        }
    }

    @Override
    public Columns getByOrder(Long boardId, Integer id) {
        int orderId = this.columnsRepository.findAll(boardId).get(id - 1).getColumnOrder();
        return this.columnsRepository.getByOrder(boardId, orderId);
    }

    @Override
    public Integer getMaxOrderValue(Long boardId) {
        return columnsRepository.getMaxOrderValue(boardId);
    }
}
