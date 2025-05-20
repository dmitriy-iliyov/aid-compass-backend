package com.aidcompass.work_interval.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "work_intervals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkIntervalEntity {

    @Id
    @SequenceGenerator(name = "w_i_seq", sequenceName = "w_i_seq", initialValue = 1, allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "w_i_seq")
    private Long id;

    @Column(name = "owner_id", nullable = false, updatable = false)
    private UUID ownerId;

    @Column(name = "start", nullable = false)
    private LocalTime start;

    @Column(name = "end", nullable = false )
    private LocalTime end;

    @Column(name = "date", nullable = false, updatable = false)
    private LocalDate date;
}
