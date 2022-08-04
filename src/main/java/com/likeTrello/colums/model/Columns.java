package com.likeTrello.colums.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.likeTrello.board.model.Board;
import com.likeTrello.tasks.model.Task;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "columns")
@Data
public class Columns {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "column_name")
    @NotNull
    @NotBlank(message = "Column name can't be blank")
    @Size(min = 4, max = 255, message = "Column name must be min 4, max 255 symbols")
    private String columnName;

    @Column(name = "column_order")
    private Integer columnOrder;

    @JsonManagedReference
    @OneToMany(mappedBy = "columns", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name =  "column_id" )
    private List<Task> tasks;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;


}
