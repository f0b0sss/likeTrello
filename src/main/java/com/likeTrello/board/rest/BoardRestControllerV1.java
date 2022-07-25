package com.likeTrello.board.rest;

import com.likeTrello.board.model.Board;
import com.likeTrello.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/board")
public class BoardRestControllerV1 {

    @Autowired
    private BoardService boardService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Board> getBoard(@PathVariable Long id){
        if (id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Board board = this.boardService.getById(id);

        if (board == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    @PostMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Board> saveBoard(@RequestBody Board board){
        HttpHeaders headers = new HttpHeaders();

        if (board == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.boardService.save(board);

        return new ResponseEntity<>(board, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Board> updateBoard(@RequestBody Board board){
        HttpHeaders headers = new HttpHeaders();

        if (board == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.boardService.save(board);
        return new ResponseEntity<>(board, headers, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Board> deleteBoard(@PathVariable Long id){
        Board board = this.boardService.getById(id);

        if (board == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.boardService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<List<Board>> getAllBoards(){
        List<Board> boards = this.boardService.getAll();

        if (boards.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

}
