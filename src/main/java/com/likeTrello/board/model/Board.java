package com.likeTrello.board.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.likeTrello.board.model.colums.model.Columns;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "boards")
@Data
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "board_name")
    private String boardName;

    @JsonManagedReference
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
    @Column(name = "board_id")
    private List<Columns> columns;


}
