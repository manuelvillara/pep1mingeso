package com.example.demo.services;

import com.example.demo.entities.DatosImportadosEntity;
import com.example.demo.entities.DatosLabEntity;
import com.example.demo.entities.PagosEntity;
import com.example.demo.entities.ProveedorEntity;
import com.example.demo.repositories.DatosImportadosRepository;
import com.example.demo.repositories.PagosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

@Service
public class PagosService {

    @Autowired
    private PagosRepository pagosRepository;

    public ArrayList<DatosImportadosEntity> filtrarProveedorEnAcopio(Integer proveedor,
                                                                     ArrayList<DatosImportadosEntity> datosImportados){
        ArrayList<DatosImportadosEntity> datosProveedor = new ArrayList<>();
        for ( int i = 0 ; i < datosImportados.size(); i++ ){
            DatosImportadosEntity filaActual = datosImportados.get(i);
            Integer proveedorActual = filaActual.getProveedor();
            if( proveedor == proveedorActual){
                datosProveedor.add(filaActual);
            }
        }
        return datosProveedor;
    }

    public ArrayList<DatosImportadosEntity> entregasMismoMes(ArrayList<DatosImportadosEntity> proveedorFiltrado,
                                                             String mes){

        ArrayList<DatosImportadosEntity> entregasMes = new ArrayList<>();
        for ( int i = 0 ; i < proveedorFiltrado.size(); i++){
            if ( mes == proveedorFiltrado.get(i).getFecha().substring(5,7) ) {
                entregasMes.add(proveedorFiltrado.get(i));
            }
        }
        return entregasMes;

    }

    public Integer pagoPorFrecuencia(ArrayList<DatosImportadosEntity> datosProveedorMismoMes){

        Integer manana = 0;
        Integer tarde = 0;

        if(datosProveedorMismoMes.size() > 10){
            for ( int i = 0 ; i < datosProveedorMismoMes.size(); i++) {
                if ( datosProveedorMismoMes.get(i).getTurno().equals("M") ){
                    manana = 1;
                }else if ( datosProveedorMismoMes.get(i).getTurno().equals("T") ){
                    tarde = 1;
                }
            }
            if ( manana == 1 && tarde == 1 ){
                return 20;
            } else if ( manana == 1 && tarde == 0){
                return 12;
            } else {
                return 8;
            }
        }

        return 0;

    }


    public Integer pagoPorKiloCategoria(ProveedorEntity proveedor){
        Character categoria = proveedor.getCategoria();
        Integer pagoPorKilo = 0;

        if (categoria.equals("A")) {
            pagoPorKilo = 700;
            return pagoPorKilo;
        } else if (categoria.equals("B")) {
            pagoPorKilo = 550;
            return pagoPorKilo;
        } else if (categoria.equals("C")) {
            pagoPorKilo = 400;
            return pagoPorKilo;
        } else if(categoria.equals("D")) {
            pagoPorKilo = 250;
        }
        return pagoPorKilo;
    }

    public Integer pagoPorKiloGrasa(ProveedorEntity proveedor, DatosLabEntity datosLab){
        Integer codigoProveedor = proveedor.getCodigo();
        Integer codigoProveedorLab = datosLab.getProveedor();
        Integer grasa = datosLab.getGrasa();

        Integer pagoPorKilo = 0;

        if ( codigoProveedor == codigoProveedorLab && grasa >= 0 && grasa <= 20 ) {
            pagoPorKilo = 30;
            return pagoPorKilo;
        } else if ( codigoProveedor == codigoProveedorLab && grasa >= 21 && grasa <= 45 ) {
            pagoPorKilo = 80;
            return pagoPorKilo;
        } else if ( codigoProveedor == codigoProveedorLab && grasa >= 46 ) {
            pagoPorKilo = 120;
            return pagoPorKilo;
        }
        return pagoPorKilo;
    }

    public Integer pagoPorKiloSolidosTotales(ProveedorEntity proveedor, DatosLabEntity datosLab){
        Integer codigoProveedor = proveedor.getCodigo();
        Integer codigoProveedorLab = datosLab.getProveedor();
        Integer solido = datosLab.getSolido();

        Integer pagoPorKilo = 0;

        if ( codigoProveedor == codigoProveedorLab && solido >= 0 && solido <= 7 ) {
            pagoPorKilo = -130;
            return pagoPorKilo;
        } else if ( codigoProveedor == codigoProveedorLab && solido >= 8 && solido <= 18 ) {
            pagoPorKilo = -90;
            return pagoPorKilo;
        } else if ( codigoProveedor == codigoProveedorLab && solido >= 19 && solido <= 35 ) {
            pagoPorKilo = 95;
            return pagoPorKilo;
        } else if ( codigoProveedor == codigoProveedorLab && solido >= 36 ) {
            pagoPorKilo = 150;
            return pagoPorKilo;
        }
        return pagoPorKilo;
    }

    public String encontrarMesAnterior(String mes){

        Integer mesActual = 0;
        String mesFinal = "";

        mesActual = parseInt( mes ) - 1;
        if (mesActual == 0){
            mesActual = 12;
        }
        mesFinal = mesActual.toString();
        if( mesFinal.length() == 1 ){
            mesFinal = "0" + mesFinal;

        }
        return mesFinal;

    }

    public Integer descKlsRespectoMesAnterior(ProveedorEntity proveedor,
                                              ArrayList<DatosImportadosEntity> todosLosDatos,
                                              String mesActual
    ){
        String mesAnterior = "";

        ArrayList<DatosImportadosEntity> entregasProveedor = new ArrayList<>();
        entregasProveedor = filtrarProveedorEnAcopio( proveedor.getCodigo() , todosLosDatos );

        ArrayList<DatosImportadosEntity> entregasMesActual = new ArrayList<>();
        entregasMesActual = entregasMismoMes( entregasProveedor , mesActual );

        mesAnterior = encontrarMesAnterior( mesActual );
        ArrayList<DatosImportadosEntity> entregasMesAnterior = new ArrayList<>();
        entregasMesAnterior = entregasMismoMes( entregasProveedor , mesAnterior );

        Integer cantidadMesActual, cantidadMesAnterior, variacion;

        cantidadMesActual = entregasMesActual.size();
        cantidadMesAnterior = entregasMesAnterior.size();

        variacion = -1 * ( (cantidadMesActual - cantidadMesAnterior)/cantidadMesAnterior );

        if( cantidadMesActual > cantidadMesAnterior ){
            return 0;
        } else if ( variacion >= 0 && variacion <= 8 ){
            return 0;
        } else if ( variacion >= 9 && variacion <= 25 ){
            return 7;
        } else if ( variacion >= 26 && variacion <= 45 ){
            return 15;
        } else if ( variacion >= 46 ){
            return 30;
        }

        return -1;

    }

    public Integer descGrasaRespectoMesAnterior(ProveedorEntity proveedor,
                                                ArrayList<DatosImportadosEntity> todosLosDatos,
                                                String mesActual,
                                                ArrayList<PagosEntity> historialPagos){

        ArrayList<DatosImportadosEntity> entregasProveedor = new ArrayList<>();
        entregasProveedor = filtrarProveedorEnAcopio( proveedor.getCodigo() , todosLosDatos );

        ArrayList<DatosImportadosEntity> entregasMesActual = new ArrayList<>();
        entregasMesActual = entregasMismoMes( entregasProveedor , mesActual );

        // Se asume como que la última entrada en la entidad de Pagos es el mes pasado
        ArrayList<PagosEntity> datosMesPasado = new ArrayList<>();
        Integer idMayor = 0;
        for( int i = 0; i < historialPagos.size(); i++ ){
            if( proveedor.getCodigo() == historialPagos.get(i).getCodigoProveedor() ){
                if( historialPagos.get(i).getID() >= idMayor ){
                    //datosMesPasado = historialPagos.get(i);
                    idMayor = historialPagos.get(i).getID();
                }
            }
        }
        return -1;

    }



}
