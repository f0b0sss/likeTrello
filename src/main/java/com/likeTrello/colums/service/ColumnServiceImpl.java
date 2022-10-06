package com.likeTrello.colums.service;

import com.likeTrello.board.model.Board;
import com.likeTrello.board.service.BoardService;
import com.likeTrello.colums.model.Columns;
import com.likeTrello.colums.repository.ColumnsRepository;
import com.likeTrello.exceptions.ColumnNotFoundException;
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
        return columnsRepository.findById(id).orElseThrow(
                () -> new ColumnNotFoundException("Column with id " + id + " not found"));
    }

    @Override
    public void save(Columns column, Long boardId) {
        Board board = boardService.getById(boardId);

        column.setBoard(board);

        Integer columnOrderId = getMaxOrderValue(boardId);

        if (columnOrderId == null) {
            column.setColumnOrder(1);
        } else {
            column.setColumnOrder(columnOrderId + 1);
        }

        columnsRepository.save(column);
    }

    @Override
    public void delete(Long id) {
        if (!columnsRepository.existsById(id)) {
            throw new ColumnNotFoundException("Column with id " + id + " not found");
        }
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

interface EqualNumbers {
    int getHighNumber(int a, int b);
}

class EqualOperation {
    public static void main(String[] args) {

        EqualNumbers equalNumbers;

        equalNumbers = (a, b) -> {
            int result = 1;

            for (int i = 1; i <= b; i++) {
                result = result * a;
            }

            return result;

        };

        System.out.println(equalNumbers.getHighNumber(2, 9));
    }
}