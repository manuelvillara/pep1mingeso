package com.example.demo.controllers;

import com.example.demo.entities.DatosImportadosEntity;
import com.example.demo.entities.DatosLabEntity;
import com.example.demo.entities.PagosEntity;
import com.example.demo.entities.ProveedorEntity;
import com.example.demo.services.DatosImportadosService;
import com.example.demo.services.DatosLabService;
import com.example.demo.services.PagosService;
import com.example.demo.services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping
public class PagosController {

    @Autowired
    private PagosService pagosService;

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private DatosLabService datosLabService;

    @Autowired
    private DatosImportadosService datosImportadosService;

    @GetMapping("/listar-pagos")
    public String listarPagos(Model model){
        ArrayList<PagosEntity> pagos = pagosService.obtenerTodosPagos();
        model.addAttribute("pagos", pagos);
        return "pagos";
    }


    @GetMapping("/pago-proveedores")
    public String pagoProveedores(){
        pagosService.eliminarPagos();
        ArrayList<ProveedorEntity> listaProveedores = proveedorService.obtenerProveedores();
        ArrayList<DatosImportadosEntity> listaDatosImportados = datosImportadosService.obtenerDatos();
        ArrayList<DatosLabEntity> listaDatosLab = datosLabService.obtenerDatosLab();
        ArrayList<PagosEntity> historialPagos = pagosService.obtenerTodosPagos();


        // A cada proveedor se le hace su respectiva ficha de pago
        for(ProveedorEntity proveedor:listaProveedores){
            Integer codigoProveedor = proveedor.getCodigo();

            ArrayList<DatosImportadosEntity> listaAcopioProveedor = pagosService.filtrarProveedorEnAcopio(codigoProveedor,listaDatosImportados);

            ArrayList<DatosLabEntity> listaLabProveedor = pagosService.filtrarProveedorEnLab(codigoProveedor, listaDatosLab);

            if( !listaAcopioProveedor.isEmpty() & !listaLabProveedor.isEmpty() ){

                // Si el proveedor tiene datos en acopio y en el lab, entonces se puede calcular su pago.
                PagosEntity pagoProveedor = new PagosEntity(null,"", codigoProveedor, proveedor.getNombre(),
                        0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0);

                String mes = listaAcopioProveedor.get(0).getFecha();
                String mesActual = "" + mes.charAt(5) + mes.charAt(6);

                String quincena = listaAcopioProveedor.get(0).getFecha().substring(0, 4) + '/' + mesActual;

                String nombreProveedor = proveedor.getNombre();

                double totalKls = pagosService.totalKlsProveedor(codigoProveedor, listaDatosImportados);

                double numeroDiasEnvios = pagosService.nroDiasEnvios(listaAcopioProveedor);


                double promedioDiario = pagosService.promedioDiarioKls(listaAcopioProveedor);

                double variacionKls = pagosService.variacionKls(listaAcopioProveedor,mesActual);


                double grasa = listaLabProveedor.get(0).getGrasa();

                double variacionGrasa = pagosService.variacionGrasa(listaLabProveedor,historialPagos);


                double solidos = listaLabProveedor.get(0).getSolido();

                double variacionSolido = pagosService.variacionSolido(listaLabProveedor,historialPagos);


                double pagoPorKilo = pagosService.pagoPorLeche(proveedor, totalKls);

                double pagoPorGrasa = pagosService.pagoPorGrasa(proveedor, listaDatosLab, totalKls);

                double pagoPorSolidos = pagosService.pagoPorSolidos(proveedor, listaDatosLab, totalKls);

                double pagoPorFrecuencia = pagosService.sumaPagosLecheGrasaSolidoMasFrecuencia(pagoPorKilo,pagoPorGrasa,pagoPorSolidos,listaAcopioProveedor);

                double descPorKilo = pagosService.descKiloLeche(pagoPorFrecuencia,proveedor,listaDatosImportados,mesActual);
                double descPorGrasa = pagosService.descPorGrasa(pagoPorFrecuencia,proveedor,listaDatosLab,mesActual,historialPagos);
                double descPorSolidos = pagosService.descPorSolidos(pagoPorFrecuencia,proveedor,listaDatosLab,mesActual,historialPagos);

                double pagoTotal = pagosService.pagoTotalApagar(descPorKilo,descPorGrasa,descPorSolidos,pagoPorFrecuencia);
                double pagoImpuestos = pagosService.retencionFinal(listaProveedores, proveedor, pagoTotal);
                double montoFinal = pagoTotal - pagoImpuestos;

                pagoProveedor.setQuincena(quincena);
                pagoProveedor.setCodigoProveedor(codigoProveedor);
                pagoProveedor.setNombreProveedor(nombreProveedor);
                pagoProveedor.setTotalKls(totalKls);
                pagoProveedor.setDiasEnvioLeche(numeroDiasEnvios);
                pagoProveedor.setPromedioDiarioKls(promedioDiario);
                pagoProveedor.setVariacionLeche(variacionKls);
                pagoProveedor.setPorcentajeGrasa(grasa);
                pagoProveedor.setVariacionGrasa(variacionGrasa);
                pagoProveedor.setPorcentajeSolidos(solidos);
                pagoProveedor.setVariacionSolidos(variacionSolido);
                pagoProveedor.setPagoLeche(pagoPorKilo);
                pagoProveedor.setPagoGrasa(pagoPorGrasa);
                pagoProveedor.setPagoSolidos(pagoPorSolidos);
                pagoProveedor.setBonificacionFrecuencia(pagoPorFrecuencia);
                pagoProveedor.setDescVarLeche(descPorKilo);
                pagoProveedor.setDescVarGrasa(descPorGrasa);
                pagoProveedor.setDescVarSolidos(descPorSolidos);
                pagoProveedor.setPagoTotal(pagoTotal);
                pagoProveedor.setMontoRetencion(pagoImpuestos);
                pagoProveedor.setMontoFinal(montoFinal);


                pagosService.guardarPago(pagoProveedor);
            }


        }
        return "redirect:/listar-pagos";
    }


}
