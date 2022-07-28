package com.likeTrello.board.service;

import com.likeTrello.board.model.Board;
import com.likeTrello.board.repository.BoardRepository;
import com.likeTrello.exceptions.BoardNotFoundException;
import com.likeTrello.exceptions.IncorrectParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Override
    public Board getById(Long id) {
        if (boardRepository.findById(id).isEmpty()) {
            throw new IncorrectParameterException("Incorrect board id");
        }

        return boardRepository.findById(id).get();
    }

    @Override
    public void save(Board board) {
        boardRepository.save(board);
    }

    @Override
    public void delete(Long id) {
        if (boardRepository.findById(id).isEmpty()) {
            throw new IncorrectParameterException("Incorrect board id");
        } else {
            boardRepository.deleteById(id);
        }
    }

    @Override
    public List<Board> getAll() {
        if (boardRepository.findAll().isEmpty()) {
            throw new BoardNotFoundException("No boards exist");
        }

        return boardRepository.findAll();
    }
}
