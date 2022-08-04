package com.likeTrello.board.service;

import com.likeTrello.board.model.Board;
import com.likeTrello.board.repository.BoardRepository;
import com.likeTrello.exceptions.BoardNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Override
    public Board getById(Long id) {
        return boardRepository.findById(id).orElseThrow(
                () -> new BoardNotFoundException("Board with id " + id + " not found"));
    }

    @Override
    public void save(Board board) {
        boardRepository.save(board);
    }

    @Override
    public void delete(Long id) {
        if (!boardRepository.existsById(id)) {
            throw new BoardNotFoundException("Board with id " + id + " not found");
        }
        boardRepository.deleteById(id);
    }

    @Override
    public List<Board> getAll() {
        return boardRepository.findAll();
    }
}
