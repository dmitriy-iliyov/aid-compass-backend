package com.aidcompass.models.entity;

import com.aidcompass.models.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "continue_flags")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContinueFlagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @Column(name = "task_status", nullable = false)
    private TaskTypeEntity typeEntity;

    @Column(name = "should_continue", nullable = false)
    private boolean shouldContinue;
}
