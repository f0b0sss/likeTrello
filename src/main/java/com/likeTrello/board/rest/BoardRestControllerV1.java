package com.likeTrello.board.rest;

import com.likeTrello.board.model.Board;
import com.likeTrello.board.service.BoardService;
import com.likeTrello.exceptions.BoardNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/board")
public class BoardRestControllerV1 {

    @Autowired
    private BoardService boardService;

    @GetMapping()
    public ResponseEntity<List<Board>> getAllBoards() throws BoardNotFoundException {
        List<Board> boards = this.boardService.getAll();

        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Board> getBoard(@PathVariable Long id) throws BoardNotFoundException {
        Board board = this.boardService.getById(id);

        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Board> saveBoard(@RequestBody @Valid Board board) {
        this.boardService.save(board);

        return new ResponseEntity<>(board, HttpStatus.CREATED);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Board> updateBoard(@RequestBody @Valid Board board) {
        this.boardService.save(board);

        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Board> deleteBoard(@PathVariable Long id) throws BoardNotFoundException {
        this.boardService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
