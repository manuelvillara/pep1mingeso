package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "datos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatosImportadosEntity {

    @Column(unique = true, nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;
    private String fecha;
    private Character turno;
    private Integer proveedor;
    private Integer kls;

}
