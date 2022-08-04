package com.likeTrello.board.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.likeTrello.colums.model.Columns;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "boards")
@Data
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "board_name")
    @NotNull
    @NotBlank(message = "Board name can't be blank")
    @Size(min = 4, max = 255, message = "Board name must be min 4, max 255 symbols")
    private String boardName;

    @JsonManagedReference
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "board_id")
    private List<Columns> columns;


}
