package com.likeTrello.board.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.likeTrello.board.model.colums.model.Columns;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

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
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "board_id")
    private List<Columns> columns;


}
