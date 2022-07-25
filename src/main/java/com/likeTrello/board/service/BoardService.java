package com.likeTrello.board.service;

import com.likeTrello.board.model.Board;

import java.util.List;

public interface BoardService {

    Board getById(Long id);

    void save(Board board);

    void delete(Long id);

    List<Board> getAll();

}
