package com.likeTrello.board.model.tasks.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.likeTrello.board.model.colums.model.Columns;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_order")
    private Integer taskOrder;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "description")
    private String description;

    @Column(name = "creation_date")
    @CreationTimestamp
    private LocalDate creationDate;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Columns columns;

}
