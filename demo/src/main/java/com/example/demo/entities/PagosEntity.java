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
    private Integer totalKls;
    private Integer diasEnvioLeche;
    private Integer promedioDiarioKls;
    private Integer variacionLeche;
    private Integer variacionGrasa;
    private Integer variacionSolidos;
    private Integer pagoLeche;
    private Integer pagoGrasa;
    private Integer pagoSolidos;
    private Integer bonificacionFrecuencia;
    private Integer descVarLeche;
    private Integer descVarGrasa;
    private Integer descVarSolidos;
    private Integer pagoTotal;
    private Integer montoRetencion;
    private Integer montoFinal;

}
