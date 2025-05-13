package com.aidcompass.day;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "days")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DayEntity {

    @Id
    @SequenceGenerator(name = "day_seq", sequenceName = "day_seq", allocationSize = 28)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "day_seq")
    private Long id;


}
