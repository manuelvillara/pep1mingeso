package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "proveedor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorEntity {

    @Column(unique = true, nullable = false)
    @Id
    private Integer codigo;
    private String nombre;
    private Character categoria;
    private String retencion;
}
