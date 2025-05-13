package com.aidcompass.day_type.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "day_types")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DayTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "day_type", nullable = false, unique = true, updatable = false)
    @Enumerated(EnumType.STRING)
    private DayType dayType;
}
