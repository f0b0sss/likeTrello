package com.likeTrello.board.model.colums.service;

import com.likeTrello.board.model.Board;
import com.likeTrello.board.model.colums.model.Columns;
import com.likeTrello.board.model.colums.repository.ColumnsRepository;
import com.likeTrello.board.service.BoardService;
import com.likeTrello.exceptions.ColumnNotFoundException;
import com.likeTrello.exceptions.IncorrectParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColumnServiceImpl implements ColumnService {

    @Autowired
    private ColumnsRepository columnsRepository;

    @Autowired
    private BoardService boardService;

    @Override
    public Columns getById(Long id) {
        if (columnsRepository.findById(id).isEmpty()) {
            throw new IncorrectParameterException("Incorrect column id");
        }

        return columnsRepository.findById(id).get();
    }

    @Override
    public void save(Columns column, Long boardId) {
        Board board = boardService.getById(boardId);

        column.setBoard(board);

        Integer columnOrderId = getMaxOrderValue(boardId);

        if (columnOrderId == null){
            column.setColumnOrder(1);
        }else {
            column.setColumnOrder(columnOrderId + 1);
        }

        columnsRepository.save(column);
    }

    @Override
    public void delete(Long id) {
        if (columnsRepository.findById(id).isEmpty()) {
            throw new IncorrectParameterException("Incorrect column id");
        } else {
            columnsRepository.deleteById(id);
        }
    }

    @Override
    public List<Columns> getAll(Long boardId) {
        if (columnsRepository.findAll(boardId).isEmpty()) {
            throw new ColumnNotFoundException("No columns exist");
        }

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
