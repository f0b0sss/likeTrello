package com.likeTrello.board.model.colums.rest;

import com.likeTrello.board.model.Board;
import com.likeTrello.board.model.colums.model.Columns;
import com.likeTrello.board.model.colums.service.ColumnService;
import com.likeTrello.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/board/{boardId}/column")
public class ColumnsRestControllerV1 {

    @Autowired
    private ColumnService columnService;

    @Autowired
    private BoardService boardService;

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<List<Columns>> getAllColumns(@PathVariable Long boardId){
        List<Columns> columns = this.columnService.getAll(boardId);

        if (columns.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(columns, HttpStatus.OK);
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Columns> getColumn(@PathVariable Long id){
        if (id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Columns column = this.columnService.getById(id);

        if (column == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(column, HttpStatus.OK);
    }

    @PostMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Columns> saveColumn(@RequestBody Columns column, @PathVariable Long boardId){
        HttpHeaders headers = new HttpHeaders();

        if (column == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Board board = boardService.getById(boardId);

        if (board == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        column.setBoard(board);

        Integer columnOrderId = this.columnService.getMaxOrderValue(boardId);

        if (columnOrderId == null){
            column.setColumnOrder(1);
        }else {
            column.setColumnOrder(columnOrderId + 1);
        }

        this.columnService.save(column);

        return new ResponseEntity<>(column, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Columns> updateColumn(@RequestBody Columns column, @PathVariable Long boardId){
        HttpHeaders headers = new HttpHeaders();

        if (column == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Board board = this.boardService.getById(boardId);

        if (board == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        column.setBoard(board);

        this.columnService.save(column);

        return new ResponseEntity<>(column, headers, HttpStatus.OK);
    }

    @PutMapping(value = "{fromIndex}/order/{toIndex}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Columns>> changeColumnOrder(@PathVariable("fromIndex") Integer fromIndex,
                                                      @PathVariable("toIndex") Integer toIndex,
                                                      @PathVariable("boardId") Long boardId) {
        HttpHeaders headers = new HttpHeaders();

        if (fromIndex != toIndex){
            Columns column = this.columnService.getByOrder(boardId, fromIndex);

            if (column == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            this.columnService.changeColumnOrder(column, fromIndex, toIndex, columnService.getAll(boardId));
        }

        List<Columns> columns = columnService.getAll(boardId);

        return new ResponseEntity<>(columns, headers, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Columns> deleteColumn(@PathVariable("id") Long id){
        Columns column = this.columnService.getById(id);

        if (column == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.columnService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
