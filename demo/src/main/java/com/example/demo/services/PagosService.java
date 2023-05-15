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
    double impuesto = 0.13;

    public ArrayList<PagosEntity> obtenerTodosPagos(){
        return (ArrayList<PagosEntity>) pagosRepository.findAll();
    }

    public void guardarPago(PagosEntity pago){ pagosRepository.save(pago);}

    public void eliminarPagos(){pagosRepository.deleteAll();}

    public ArrayList<DatosImportadosEntity> filtrarProveedorEnAcopio(Integer proveedor,
                                                                     ArrayList<DatosImportadosEntity> datosImportados){
        ArrayList<DatosImportadosEntity> datosProveedor = new ArrayList<>();
        for ( int i = 0 ; i < datosImportados.size(); i++ ){
            DatosImportadosEntity filaActual = datosImportados.get(i);

            Integer proveedorActual = filaActual.getProveedor();

            if( proveedorActual.equals(proveedor)){

                datosProveedor.add(filaActual);
            }
        }
        return datosProveedor;
    }

    public ArrayList<DatosLabEntity> filtrarProveedorEnLab(Integer proveedor,
                                                           ArrayList<DatosLabEntity> datosLab){

        ArrayList<DatosLabEntity> datosProveedor = new ArrayList<>();
        for ( int i = 0 ; i < datosLab.size(); i++ ){
            DatosLabEntity filaActual = datosLab.get(i);
            Integer proveedorActual = filaActual.getProveedor();
            if( proveedorActual.equals(proveedor)){
                datosProveedor.add(filaActual);
            }
        }
        return datosProveedor;

        /*
        double grasa = datosProveedor.get(0).getGrasa();
        for(int i = 0; i < datosLab.size(); i++)
        {
            if(datosLab.get(i).getProveedor() == codigoProveedor)
            {
                codigoProveedorLab = datosLab.get(i).getProveedor();
                grasa = datosLab.get(i).getGrasa();
            }
        }
         */

    }

    public double totalKlsProveedor(Integer proveedor, ArrayList<DatosImportadosEntity> datosImportados){
        ArrayList<DatosImportadosEntity> datosProveedor = new ArrayList<>();
        double totalKls = 0;
        datosProveedor = filtrarProveedorEnAcopio(proveedor,datosImportados);
        // Una vez que tengo todas las apariciones del proveedor en el excel, obtengo cuantos Kls en total tiene
        for(int i = 0; i<datosProveedor.size();i++){
            totalKls = totalKls + datosProveedor.get(i).getKls();
        }

        return totalKls;
    }


    public ArrayList<DatosImportadosEntity> entregasMismoMes(ArrayList<DatosImportadosEntity> proveedorFiltrado,
                                                             String mes){

        ArrayList<DatosImportadosEntity> entregasMes = new ArrayList<>();
        for ( int i = 0 ; i < proveedorFiltrado.size(); i++){
            if ( ( proveedorFiltrado.get(i).getFecha().substring(5,7) ).equals(mes) ) {
                entregasMes.add(proveedorFiltrado.get(i));
            }
        }
        return entregasMes;

    }

    public String obtenerRetencion(ArrayList<ProveedorEntity> listaProveedores,
                                   Integer codigoProveedor){

        String entrega = null;
        for(int i = 0; i<listaProveedores.size(); i++){
            if( (listaProveedores.get(i).getCodigo()).equals(codigoProveedor) ){
                entrega = listaProveedores.get(i).getRetencion();
            }
        }
        return entrega;
    }

    public double pagoPorFrecuencia(ArrayList<DatosImportadosEntity> datosProveedorMismoMes){

        Integer manana = 0;
        Integer tarde = 0;

        if(datosProveedorMismoMes.size() > 10){
            for ( int i = 0 ; i < datosProveedorMismoMes.size(); i++) {
                if ( ( datosProveedorMismoMes.get(i).getTurno() ).equals('M') ){
                    manana = 1;
                }else if ( ( datosProveedorMismoMes.get(i).getTurno() ).equals('T') ){
                    tarde = 1;
                }
            }
            if ( manana == 1 && tarde == 1 ){
                return 0.20;
            } else if ( manana == 1 && tarde == 0){
                return 0.12;
            } else {
                return 0.08;
            }
        }

        return 0;

    }

    /*
    ME QUEDÉ AQUI EDITANDO LOS EQUALS
     */

    public Integer pagoPorKiloCategoria(ProveedorEntity proveedor){
        Character categoria = proveedor.getCategoria();
        Integer pagoPorKilo = 0;

        if (categoria.equals('A')) {
            pagoPorKilo = 700;
            return pagoPorKilo;
        } else if (categoria.equals('B')) {
            pagoPorKilo = 550;
            return pagoPorKilo;
        } else if (categoria.equals('C')) {
            pagoPorKilo = 400;
            return pagoPorKilo;
        } else if(categoria.equals('D')) {
            pagoPorKilo = 250;
        }
        return pagoPorKilo;
    }

    public Integer pagoPorKiloGrasa(ProveedorEntity proveedor, ArrayList<DatosLabEntity> datosLab){
        // deberia ser: ArrayList<DatosLabEntity>
        // antes: DatosLabEntity datosLab segun yo está malo
        Integer codigoProveedor = proveedor.getCodigo();
        Integer codigoProveedorLab = 0;
        Integer grasa = 0;
        for(int i = 0; i < datosLab.size(); i++)
        {
            if( ( datosLab.get(i).getProveedor() ).equals(codigoProveedor))
            {
                codigoProveedorLab = datosLab.get(i).getProveedor();
                grasa = datosLab.get(i).getGrasa();
            }
        }

        Integer pagoPorKilo = 0;

        if ( codigoProveedor.equals(codigoProveedorLab) && grasa >= 0 && grasa <= 20 ) {
            pagoPorKilo = 30;
            return pagoPorKilo;
        } else if ( codigoProveedor.equals(codigoProveedorLab) && grasa >= 21 && grasa <= 45 ) {
            pagoPorKilo = 80;
            return pagoPorKilo;
        } else if ( codigoProveedor.equals(codigoProveedorLab) && grasa >= 46 ) {
            pagoPorKilo = 120;
            return pagoPorKilo;
        }
        return pagoPorKilo;
    }

    public Integer pagoPorKiloSolidosTotales(ProveedorEntity proveedor, ArrayList<DatosLabEntity> datosLab){
        // deberia ser: ArrayList<DatosLabEntity>
        // antes: DatosLabEntity datosLab segun yo está malo
        Integer codigoProveedor = proveedor.getCodigo();
        Integer codigoProveedorLab = 0;
        Integer solido = 0;

        for(int i = 0; i < datosLab.size(); i++)
        {
            if( ( datosLab.get(i).getProveedor() ).equals(codigoProveedor))
            {
                codigoProveedorLab = datosLab.get(i).getProveedor();
                solido = datosLab.get(i).getSolido();
            }
        }

        Integer pagoPorKilo = 0;

        if ( codigoProveedor.equals(codigoProveedorLab) && solido >= 0 && solido <= 7 ) {
            pagoPorKilo = -130;
            return pagoPorKilo;
        } else if ( codigoProveedor.equals(codigoProveedorLab) && solido >= 8 && solido <= 18 ) {
            pagoPorKilo = -90;
            return pagoPorKilo;
        } else if ( codigoProveedor.equals(codigoProveedorLab) && solido >= 19 && solido <= 35 ) {
            pagoPorKilo = 95;
            return pagoPorKilo;
        } else if ( codigoProveedor.equals(codigoProveedorLab) && solido >= 36 ) {
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

    public double variacionKls(ArrayList<DatosImportadosEntity> datosImportadosProveedor,
                               String mesActual){


        // Se filtran las entregas hechas por ese proveedor en el mes actual
        // (el mes que se quiere, ingresado como parametro)
        ArrayList<DatosImportadosEntity> entregasMesActual = new ArrayList<>();
        entregasMesActual = entregasMismoMes( datosImportadosProveedor , mesActual );

        // Teniendo todas las entregas del mes actual, se obtienen las del mes anterior para
        // poder comparar y hacer el descuento en caso de requerirlo
        String mesAnterior = encontrarMesAnterior( mesActual );

        ArrayList<DatosImportadosEntity> entregasMesAnterior = new ArrayList<>();
        entregasMesAnterior = entregasMismoMes( datosImportadosProveedor , mesAnterior );

        double cantidadMesActual, cantidadMesAnterior, variacion;
        cantidadMesActual = 0; cantidadMesAnterior = 0; variacion = 0.0;

        for (int i=0; i<entregasMesActual.size();i++){
            cantidadMesActual = cantidadMesActual + entregasMesActual.get(i).getKls();
        }

        for (int i=0; i<entregasMesAnterior.size();i++){
            cantidadMesAnterior = cantidadMesAnterior + entregasMesActual.get(i).getKls();
        }

        if(cantidadMesAnterior == 0){
            return 0;
        }
        else{
            variacion = -1 * ( (cantidadMesActual - cantidadMesAnterior)/cantidadMesAnterior );
            return variacion;
        }

    }

    public double descKlsRespectoMesAnterior(ProveedorEntity proveedor,
                                              ArrayList<DatosImportadosEntity> todosLosDatos,
                                              String mesActual){
        String mesAnterior = "";

        // Se buscan todas las entregas hechas por los proveedores y se filtra por el
        // proveedor que se quiere (proveedor como parametro)
        ArrayList<DatosImportadosEntity> entregasProveedor = new ArrayList<>();
        entregasProveedor = filtrarProveedorEnAcopio( proveedor.getCodigo() , todosLosDatos );

        // Se filtran las entregas hechas por ese proveedor en el mes actual
        // (el mes que se quiere, ingresado como parametro)
        ArrayList<DatosImportadosEntity> entregasMesActual = new ArrayList<>();
        entregasMesActual = entregasMismoMes( entregasProveedor , mesActual );

        // Teniendo todas las entregas del mes actual, se obtienen las del mes anterior para
        // poder comparar y hacer el descuento en caso de requerirlo
        mesAnterior = encontrarMesAnterior( mesActual );
        ArrayList<DatosImportadosEntity> entregasMesAnterior = new ArrayList<>();
        entregasMesAnterior = entregasMismoMes( entregasProveedor , mesAnterior );

        double cantidadMesActual, cantidadMesAnterior, variacion;
        cantidadMesActual = 0; cantidadMesAnterior = 0;

        //cantidadMesActual = entregasMesActual.size();
        //cantidadMesAnterior = entregasMesAnterior.size();

        for (int i=0; i<entregasMesActual.size();i++){
            cantidadMesActual = cantidadMesActual + entregasMesActual.get(i).getKls();
        }

        for (int i=0; i<entregasMesAnterior.size();i++){
            cantidadMesAnterior = cantidadMesAnterior + entregasMesActual.get(i).getKls();
        }

        if( cantidadMesActual > cantidadMesAnterior ){
            return 0.0;
        } else{
            variacion = -1 * ( (cantidadMesActual - cantidadMesAnterior)/cantidadMesAnterior );

            if ( variacion >= 0 && variacion <= 8 ){
                return 0.0;
            } else if ( variacion >= 9 && variacion <= 25 ){
                return 0.07;
            } else if ( variacion >= 26 && variacion <= 45 ){
                return 0.15;
            } else if ( variacion >= 46 ){
                return 0.30;
            }
        }

        return -1;

    }

    public double variacionGrasa(ArrayList<DatosLabEntity> datosLabProveedor,
                                 ArrayList<PagosEntity> historialPagos){

        double grasaMesActual = datosLabProveedor.get(0).getGrasa();
        Integer codigoProveedor = datosLabProveedor.get(0).getProveedor();
        // Se asume como que la ultima entrada en la entidad de Pagos es el mes pasado, es decir, el
        // ID mas grande
        double grasaMesPasado;
        Integer idMayor = -1;
        for( int i = 0; i < historialPagos.size(); i++ ){
            if( codigoProveedor.equals(historialPagos.get(i).getCodigoProveedor()) ){
                if( historialPagos.get(i).getID() >= idMayor ){
                    idMayor = i;
                }
            }
        }

        // si nunca encontró al proveedor en el historial de pagos, entonces no habrá variacion
        if(idMayor == -1){
            return 0;
        }
        else{
            grasaMesPasado = historialPagos.get(idMayor).getPorcentajeGrasa();
            double variacion;

            if(grasaMesActual >= grasaMesPasado){
                return 0;
            }
            else{
                variacion = -1 * ( (grasaMesActual - grasaMesPasado)/grasaMesPasado );
                return variacion;
            }
        }

    }

    public double descGrasaRespectoMesAnterior(ProveedorEntity proveedor,
                                                ArrayList<DatosLabEntity> todosLosDatos,
                                                String mesActual,
                                                ArrayList<PagosEntity> historialPagos){

        ArrayList<DatosLabEntity> entregasProveedor = new ArrayList<>();
        Integer codigoProveedor = proveedor.getCodigo();
        // Se asume que la lista del Lab solo tiene los datos de este mes, por lo tanto
        // el filtro "solo saca una fila de datos"
        entregasProveedor = filtrarProveedorEnLab(codigoProveedor, todosLosDatos);
        Integer grasaMesActual = entregasProveedor.get(0).getGrasa();

        // Se asume como que la ultima entrada en la entidad de Pagos es el mes pasado, es decir, el
        // ID mas grande
        double grasaMesPasado;
        Integer idMayor = -1;
        for( int i = 0; i < historialPagos.size(); i++ ){
            if( proveedor.getCodigo().equals( historialPagos.get(i).getCodigoProveedor() ) ){
                if( historialPagos.get(i).getID() >= idMayor ){
                    idMayor = i;
                }
            }
        }

        // Si no encontró un historial del proveedor, entonces no hay variacion negativa
        if(idMayor == -1){
            return 0.0;
        }
        else{
            grasaMesPasado = historialPagos.get(idMayor).getPorcentajeGrasa();

            // Si no hay variacion negativa
            if( grasaMesActual >= grasaMesPasado ){
                return 0.0;
            }else {
                double variacion;
                variacion = -1 * ( (grasaMesActual - grasaMesPasado)/grasaMesPasado );

                if ( variacion >= 0 && variacion <= 15 ){
                    return 0.0;
                } else if ( variacion >= 16 && variacion <= 25 ){
                    return 0.12;
                } else if ( variacion >= 26 && variacion <= 40 ){
                    return 0.2;
                } else if ( variacion >= 41 ){
                    return 0.3;
                }
            }

        }
        return -1;

    }

    public double variacionSolido(ArrayList<DatosLabEntity> datosLabProveedor,
                                  ArrayList<PagosEntity> historialPagos){

        double solidosMesActual = datosLabProveedor.get(0).getSolido();
        Integer codigoProveedorr = datosLabProveedor.get(0).getProveedor();

        // Se asume como que la ultima entrada en la entidad de Pagos es el mes pasado, es decir, el
        // ID mas grande
        double solidoMesPasado;
        Integer idMayor = -1;
        //if( historialPagos.size() == 0 ){

        //}
        for( int i = 0; i < historialPagos.size(); i++ ) {
            if (codigoProveedorr.equals(historialPagos.get(i).getCodigoProveedor())) {
                if (historialPagos.get(i).getID() >= idMayor) {
                    idMayor = i;
                }
            }
        }

        if(idMayor == -1){
            return 0;
        }
        else{
            solidoMesPasado = historialPagos.get(idMayor).getPorcentajeSolidos();
            if(solidoMesPasado <= solidosMesActual)
            {
                return 0;
            }
            else{
                double variacion;
                variacion = -1 * ( (solidosMesActual - solidoMesPasado)/solidoMesPasado );
                return variacion;
            }
        }

    }

    public double descSolidosRespectoMesAnterior(ProveedorEntity proveedor,
                                                ArrayList<DatosLabEntity> todosLosDatos,
                                                String mesActual,
                                                ArrayList<PagosEntity> historialPagos){

        ArrayList<DatosLabEntity> entregasProveedor = new ArrayList<>();
        Integer codigoProveedor = proveedor.getCodigo();
        // Se asume que la lista del Lab solo tiene los datos de este mes, por lo tanto
        // el filtro "solo saca una fila de datos"
        entregasProveedor = filtrarProveedorEnLab(codigoProveedor, todosLosDatos);
        double solidosMesActual = entregasProveedor.get(0).getSolido();

        // Se asume como que la ultima entrada en la entidad de Pagos es el mes pasado, es decir, el
        // ID mas grande
        double solidosMesPasado;
        Integer idMayor = -1;
        for( int i = 0; i < historialPagos.size(); i++ ){
            if( proveedor.getCodigo().equals( historialPagos.get(i).getCodigoProveedor() ) ){
                if( historialPagos.get(i).getID() >= idMayor ){
                    idMayor = i;
                }
            }
        }

        // Si no encontró un historial del proveedor, entonces no hay variacion negativa
        if(idMayor == -1){
            return 0.0;
        }
        else{
            solidosMesPasado = historialPagos.get(idMayor).getPorcentajeSolidos();

            // Si no hay variacion negativa
            if( solidosMesActual >= solidosMesPasado ){
                return 0.0;
            }else {
                double variacion;
                variacion = -1 * ( (solidosMesActual - solidosMesPasado)/solidosMesPasado );

                if ( variacion >= 0 && variacion <= 6 ){
                    return 0.0;
                } else if ( variacion >= 7 && variacion <= 12 ){
                    return 0.18;
                } else if ( variacion >= 13 && variacion <= 35 ){
                    return 0.27;
                } else if ( variacion >= 36 ){
                    return 0.45;
                }
            }

        }
        return -1;

    }
    // -----------------------------------------------------------------------


    // Funcion creada para la HU6 y hacer mas simple la funcion pagoPorleche
    public double pagoPorLeche(ProveedorEntity proveedor, double acopio){

        double pagoPorKilo = acopio * pagoPorKiloCategoria(proveedor);
        return pagoPorKilo;
    }
    public double pagoPorGrasa(ProveedorEntity proveedor,
                               ArrayList<DatosLabEntity> todosDatosLab,
                               double acopio){
        double pagoPorGrasa = acopio * pagoPorKiloGrasa(proveedor,todosDatosLab);
        return pagoPorGrasa;
    }
    public double pagoPorSolidos(ProveedorEntity proveedor,
                                 ArrayList<DatosLabEntity> todosDatosLab,
                                 double acopio){
        double pagoPorSolid = acopio * pagoPorKiloSolidosTotales(proveedor, todosDatosLab);
        return pagoPorSolid;
    }
    public double sumaPagosLecheGrasaSolidoMasFrecuencia(double pagoPorKilo,
                                                         double pagoPorGrasa,
                                                         double pagoPorSolidos,
                                                         ArrayList<DatosImportadosEntity> acopioDelProveedor){


        double montoAcumulado = 0, pagoAcopioLeche = 0;
        montoAcumulado = pagoPorKilo + pagoPorGrasa + pagoPorSolidos;

        // El enunciado dice % del total pago por KLS Leche, yo entiendo que es del monto acumulado
        double pagoPorLaFrecuencia = montoAcumulado*pagoPorFrecuencia(acopioDelProveedor);

        pagoAcopioLeche = montoAcumulado + pagoPorLaFrecuencia;

        return pagoAcopioLeche;

    }

    public double descKiloLeche(double pagoAcopioLeche,
                                ProveedorEntity proveedor,
                                ArrayList<DatosImportadosEntity> todosDatosImportados,
                                String mesActual){
        double descPorKilo = 0;
        descPorKilo = pagoAcopioLeche * descKlsRespectoMesAnterior(proveedor, todosDatosImportados, mesActual);
        return descPorKilo;
    }

    public double descPorGrasa(double pagoAcopioLeche,
                               ProveedorEntity proveedor,
                               ArrayList<DatosLabEntity> todosDatosLab,
                               String mesActual,
                               ArrayList<PagosEntity> historialPagos){
        double descPorGrasaa = 0;
        descPorGrasaa = pagoAcopioLeche * descGrasaRespectoMesAnterior(proveedor, todosDatosLab, mesActual, historialPagos);
        return descPorGrasaa;
    }

    public double descPorSolidos(double pagoAcopioLeche,
                                  ProveedorEntity proveedor,
                                  ArrayList<DatosLabEntity> todosDatosLab,
                                  String mesActual,
                                  ArrayList<PagosEntity> historialPagos){
        double descPorSolido = 0;
        descPorSolido = pagoAcopioLeche * descSolidosRespectoMesAnterior(proveedor, todosDatosLab, mesActual, historialPagos);
        return descPorSolido;
    }

    public double pagoTotalApagar(double descPorKilo,
                            double descPorGrasa,
                            double descPorSolidos,
                            double pagoAcopioLeche){

        double descuentos = descPorKilo + descPorGrasa + descPorSolidos;
        double pagoTotalApagar = pagoAcopioLeche - descuentos;
        return pagoTotalApagar;
    }

    public double retencionFinal(ArrayList<ProveedorEntity> proveedores,
                                 ProveedorEntity proveedor,
                                 double pagoTotalApagar){
        double pagoImpuestos = 0;
        // Se obtiene la retencion del proveedor en la base de datos
        String retencion = obtenerRetencion(proveedores,proveedor.getCodigo());
        if ( retencion == "Si" ){
            if( pagoTotalApagar >= 950000 ){
                pagoImpuestos = pagoTotalApagar * impuesto;
                return pagoImpuestos;
            }
        }
        else{
            // En caso de no aplicar el impuesto, el pago final nunca se modificó por lo que el monto
            // quedó almacenado en pagoTotalApagar.
            return pagoImpuestos;
        }
        return pagoImpuestos;
    }

    public double nroDiasEnvios(ArrayList<DatosImportadosEntity> acopioDelProveedor){
        double contadorDias = 0;
        ArrayList<String> dias = new ArrayList<>();
        dias.add("2000/01/28");
        Boolean encontrado = false;
        for (int i = 0; i<acopioDelProveedor.size();i++) {
            encontrado = false;
            for (int j = 0;j<dias.size();j++) {
                if ( (dias.get(j)).equals(acopioDelProveedor.get(i).getFecha()) ) {
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                dias.add(acopioDelProveedor.get(i).getFecha());
                contadorDias = contadorDias + 1;
            }
        }
        return contadorDias;

    }

    public double promedioDiarioKls(ArrayList<DatosImportadosEntity> acopioDelProveedor){

        ArrayList<Double> listaNumeroDias = new ArrayList<>();
        ArrayList<String> diasString = new ArrayList<>();
        diasString.add("2000/01/28");
        listaNumeroDias.add(0.0);

        Boolean encontrado = false;
        for (int i = 0; i<acopioDelProveedor.size();i++) {
            encontrado = false;
            for (int j = 0;j<diasString.size();j++) {
                if ( (diasString.get(j)).equals(acopioDelProveedor.get(i).getFecha()) ) {
                    encontrado = true;
                    listaNumeroDias.set(j,listaNumeroDias.get(j)+(double)acopioDelProveedor.get(i).getKls());
                    break;
                }
            }
            if (!encontrado) {
                diasString.add(acopioDelProveedor.get(i).getFecha());
                listaNumeroDias.add( (double)acopioDelProveedor.get(i).getKls() );
            }
        }
        double cantidadDiasEntregados = listaNumeroDias.size() - 1;
        double acumulador = 0;
        for (int i = 0; i< listaNumeroDias.size();i++){
            acumulador = listaNumeroDias.get(i) + acumulador;
        }
        double promedioDiario = acumulador/cantidadDiasEntregados;
        return promedioDiario;

    }

    public double pagoTotal(ProveedorEntity proveedor,
                             ArrayList<DatosImportadosEntity> todosDatosImportados,
                             ArrayList<DatosLabEntity> todosDatosLab,
                             ArrayList<PagosEntity> historialPagos,
                             ArrayList<ProveedorEntity> proveedores){

        Integer codigoProveedor = proveedor.getCodigo();
        double pagoPorKilo, pagoPorGrasa, pagoPorSolidos, pagoAcopioLeche;
        double descPorKilo, descPorGrasa, descPorSolidos, descuentos;
        double pagoTotalApagar, pagoImpuestos;
        String retencion;
        double pagoFinal = 0;
        double montoAcumulado = 0;
        double acopioLeche = 0;
        ArrayList<DatosImportadosEntity> acopioDelProveedor = filtrarProveedorEnAcopio(codigoProveedor, todosDatosImportados);
        // Se asume que el mes actual es el de la primera fecha del proveedor que se quiere
        String mes = acopioDelProveedor.get(0).getFecha();
        String mesActual = "" + mes.charAt(5) + mes.charAt(6);

        acopioLeche = totalKlsProveedor(codigoProveedor, todosDatosImportados);

        pagoPorKilo = pagoPorLeche(proveedor, acopioLeche);
        pagoPorGrasa = pagoPorGrasa(proveedor, todosDatosLab, acopioLeche);
        pagoPorSolidos = pagoPorSolidos(proveedor, todosDatosLab, acopioLeche);

        // montoAcumulado = pagoPorKilo + pagoPorGrasa + pagoPorSolidos;
        pagoAcopioLeche = sumaPagosLecheGrasaSolidoMasFrecuencia(pagoPorKilo,pagoPorGrasa,pagoPorSolidos,acopioDelProveedor);
        //pagoAcopioLeche = montoAcumulado * pagoPorFrecuencia(acopioDelProveedor);

        // Estoy aqui! Tengo que sacar el mes del proveedor (donde estoy trabajando) y terminar los descuentos.
        descPorKilo = descKiloLeche(pagoAcopioLeche,proveedor,todosDatosImportados,mesActual);
        descPorGrasa = descPorGrasa(pagoAcopioLeche,proveedor,todosDatosLab,mesActual,historialPagos);
        //descPorGrasa = pagoAcopioLeche * descGrasaRespectoMesAnterior(proveedor, todosDatosLab, mesActual, historialPagos);
        //descPorSolidos = pagoAcopioLeche * descSolidosRespectoMesAnterior(proveedor, todosDatosLab, mesActual, historialPagos);
        descPorSolidos = descPorSolidos(pagoAcopioLeche,proveedor,todosDatosLab,mesActual,historialPagos);

        descuentos = descPorKilo + descPorGrasa + descPorSolidos;
        // pagoTotalApagar = pagoAcopioLeche - descuentos;
        pagoTotalApagar = pagoTotalApagar(descPorKilo,descPorGrasa,descPorSolidos,pagoAcopioLeche);

        pagoImpuestos = retencionFinal(proveedores, proveedor, pagoTotalApagar);

        pagoFinal = pagoTotalApagar - pagoImpuestos;
        /*
        // Se obtiene la retencion del proveedor en la base de datos
        retencion = obtenerRetencion(proveedores,proveedor.getCodigo());
        if ( retencion == "Si" ){
            if( pagoTotalApagar >= 950000 ){
                pagoImpuestos = pagoTotalApagar * impuesto;
                pagoFinal = pagoTotalApagar - pagoImpuestos;
                return pagoFinal;
            }
        }
        else{
            // En caso de no aplicar el impuesto, el pago final nunca se modificó por lo que el monto
            // quedó almacenado en pagoTotalApagar.
            return pagoTotalApagar;
        }
         */
        return pagoFinal;

    }

}
