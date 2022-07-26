package com.likeTrello.board.model.colums.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.likeTrello.board.model.Board;
import com.likeTrello.board.model.tasks.model.Task;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "columns")
@Data
public class Columns {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "column_name")
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
