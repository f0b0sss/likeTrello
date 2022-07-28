package com.likeTrello.board.model.colums.service;

import com.likeTrello.board.model.colums.model.Columns;
import com.likeTrello.board.model.colums.repository.ColumnsRepository;
import com.likeTrello.exceptions.ColumnNotFoundException;
import com.likeTrello.exceptions.IncorrectParameterException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class ColumnServiceImplTest {

    @Autowired
    ColumnService columnService;

    @Autowired
    ColumnsRepository columnsRepository;

    @Test
    void getById_throwInvalidParameterException_whenIncorrectId() {
        assertThrows(IncorrectParameterException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                columnService.getById(10l);
            }
        });
    }

    @Test
    void getById() {
        assertEquals("Backlog", columnService.getById(1l).getColumnName());
    }

    @Test
    void save_ThrowConstraintViolationException_whenNameNull() {
        Columns column = new Columns();

        assertThrows(ConstraintViolationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                columnService.save(column, 1l);
            }
        });
    }

    @Test
    void save_ThrowConstraintViolationException_whenNameBlank() {
        Columns column = new Columns();
        column.setColumnName("    ");

        assertThrows(ConstraintViolationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                columnService.save(column, 1l);
            }
        });
    }

    @Test
    void save_ThrowConstraintViolationException_whenNameLessThen4Symbols() {
        Columns column = new Columns();
        column.setColumnName("New");

        assertThrows(ConstraintViolationException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                columnService.save(column, 1l);
            }
        });
    }

    @Test
    void saveColumn() {
        Columns column = new Columns();
        column.setColumnName("New column name");

        columnService.save(column, 1l);

        assertEquals("New column name", columnService.getById(4l).getColumnName());
    }

    @Test
    void save_generateCorrectId_whenAddColumn() {
        Columns column = new Columns();
        column.setColumnName("New column name");

        columnService.save(column, 1l);

        assertEquals(4l, columnService.getById(4l).getId());
    }

    @Test
    void save_setCorrectColumnOrderId_whenAddColumn() {
        Columns column = new Columns();
        column.setColumnName("New column name");

        columnService.save(column, 1l);

        assertEquals(4, columnService.getById(4l).getColumnOrder());
    }

    @Test
    void delete_throwInvalidParameterException_whenIncorrectId() {
        assertThrows(IncorrectParameterException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                columnService.delete(10l);
            }
        });
    }

    @Test
    void deleteTask() {
        int size = columnService.getAll(1l).size();

        columnService.delete(1l);

        assertEquals(size - 1, columnService.getAll(1l).size());
    }

    @Test
    void deleteTask_withDeleteAllTasks() {
        int size = columnService.getAll(1l).get(0).getTasks().size();

        assertEquals(4, size);

        columnService.delete(1l);

        int sizeNew = columnService.getAll(1l).get(0).getTasks().size();

        assertEquals(0, sizeNew);
    }

    @Test
    void getAll_throwColumnNotFoundException_noOneColumnCreated() {
        assertThrows(ColumnNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                columnService.getAll(2l);
            }
        });
    }

    @Test
    void getAllColumns() {
        assertEquals(3, columnService.getAll(1l).size());
    }

    @Test
    void changeColumnOrder_left() {
        Columns columns = columnService.getById(1l);

        assertEquals(1, columns.getColumnOrder());

        columnService.changeColumnOrder(columns, 1, 3, columnService.getAll(1l));

        assertEquals(3, columns.getColumnOrder());
    }

    @Test
    void changeTaskOrder_right() {
        Columns column = columnService.getById(3l);

        assertEquals(3, column.getColumnOrder());

        columnService.changeColumnOrder(column, 3, 1, columnService.getAll(1l));

        assertEquals(1, column.getColumnOrder());
    }

    @Test
    void getMaxOrderValue() {
        assertEquals(3, columnService.getMaxOrderValue(1l));
    }
}