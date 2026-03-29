package com.app.quantity.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuantityRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double input1;
    private String unit1;

    private double input2;
    private String unit2;

    private String operation;
    private double result;

    private String type;
}
