package com.likeTrello.tasks.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.likeTrello.colums.model.Columns;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NotNull
    @NotBlank(message = "Task name can't be blank")
    @Size(min = 4, max = 255, message = "Task name must be min 4, max 255 symbols")
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
