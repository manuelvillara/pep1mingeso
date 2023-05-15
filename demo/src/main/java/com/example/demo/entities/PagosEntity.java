package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagosEntity {

    @Column(unique = true, nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;
    private String quincena;
    private Integer codigoProveedor;
    private String nombreProveedor;
    private double totalKls;
    private double diasEnvioLeche;
    private double promedioDiarioKls;
    private double variacionLeche;
    private double porcentajeGrasa;
    private double variacionGrasa;
    private double porcentajeSolidos;
    private double variacionSolidos;
    private double pagoLeche;
    private double pagoGrasa;
    private double pagoSolidos;
    private double bonificacionFrecuencia;
    private double descVarLeche;
    private double descVarGrasa;
    private double descVarSolidos;
    private double pagoTotal;
    private double montoRetencion;
    private double montoFinal;


}
