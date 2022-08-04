package com.likeTrello.colums.rest;

import com.likeTrello.board.service.BoardService;
import com.likeTrello.colums.model.Columns;
import com.likeTrello.colums.service.ColumnService;
import com.likeTrello.exceptions.ColumnNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/board/{boardId}/column")
public class ColumnsRestControllerV1 {

    @Autowired
    private ColumnService columnService;

    @Autowired
    private BoardService boardService;

    @GetMapping
    public ResponseEntity<List<Columns>> getAllColumns(@PathVariable Long boardId) throws ColumnNotFoundException {
        List<Columns> columns = this.columnService.getAll(boardId);

        return new ResponseEntity<>(columns, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Columns> getColumn(@PathVariable Long id) throws ColumnNotFoundException {
        Columns column = this.columnService.getById(id);

        return new ResponseEntity<>(column, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Columns> saveColumn(@RequestBody @Valid Columns column, @PathVariable Long boardId){
        this.columnService.save(column, boardId);

        return new ResponseEntity<>(column, HttpStatus.CREATED);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Columns> updateColumn(@RequestBody @Valid Columns column, @PathVariable Long boardId){
        this.columnService.save(column, boardId);

        return new ResponseEntity<>(column, HttpStatus.OK);
    }

    @PutMapping(value = "{fromIndex}/order/{toIndex}")
    public ResponseEntity<List<Columns>> changeColumnOrder(@PathVariable("fromIndex") Integer fromIndex,
                                                      @PathVariable("toIndex") Integer toIndex,
                                                      @PathVariable("boardId") Long boardId) {
        if (fromIndex != toIndex){
            Columns column = this.columnService.getByOrder(boardId, fromIndex);

            this.columnService.changeColumnOrder(column, fromIndex, toIndex, columnService.getAll(boardId));
        }

        List<Columns> columns = columnService.getAll(boardId);

        return new ResponseEntity<>(columns, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Columns> deleteColumn(@PathVariable("id") Long id) throws ColumnNotFoundException {
        this.columnService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
