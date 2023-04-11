package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lab")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatosLabEntity {

    @Column(unique = true, nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;
    private Integer proveedor;
    private Integer grasa;
    private Integer solido;
}
