package com.example.demo;

import com.example.demo.entities.DatosImportadosEntity;
import com.example.demo.entities.DatosLabEntity;
import com.example.demo.entities.PagosEntity;
import com.example.demo.entities.ProveedorEntity;
import com.example.demo.repositories.DatosImportadosRepository;
import com.example.demo.repositories.PagosRepository;
import com.example.demo.services.PagosService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class PagosServiceTest {

    @Autowired
    private DatosImportadosRepository datosImportadosRepository;

    PagosEntity pagosEntity = new PagosEntity();

    @Mock
    private PagosRepository pagosRepository;

    @InjectMocks
    private PagosService pagosService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void obtenerTodosPagos() {
        // Deberia retornar lista de Pagos
        ArrayList<PagosEntity> listaPagos = new ArrayList<>();
        when(pagosRepository.findAll()).thenReturn(listaPagos);

        ArrayList<PagosEntity> resultado = pagosService.obtenerTodosPagos();

        assertEquals(listaPagos, resultado);
    }

    @Test
    void guardarPago() {
        // Deberia guardar el Pago
        PagosEntity pago = new PagosEntity();
        pagosService.guardarPago(pago);

        verify(pagosRepository, times(1)).save(pago);
    }

    @Test
    void eliminarPagos() {
        // Deberia eliminar todos los Pagos
        pagosService.eliminarPagos();

        verify(pagosRepository, times(1)).deleteAll();
    }

    @Test
    void filtrarProveedorEnAcopio() {
        // Deberia retornar datos filtrados
        Integer proveedor = 1;
        ArrayList<DatosImportadosEntity> datosImportados = new ArrayList<>();
        DatosImportadosEntity dato1 = new DatosImportadosEntity();
        dato1.setProveedor(1);
        datosImportados.add(dato1);
        DatosImportadosEntity dato2 = new DatosImportadosEntity();
        dato2.setProveedor(2);
        datosImportados.add(dato2);

        ArrayList<DatosImportadosEntity> resultado = pagosService.filtrarProveedorEnAcopio(proveedor, datosImportados);

        assertEquals(1, resultado.size());
        assertEquals(dato1, resultado.get(0));
    }

    @Test
    void filtrarProveedorEnLab() {
        // Deberia retornar datos filtrados
        Integer proveedor = 1;
        ArrayList<DatosLabEntity> datosLab = new ArrayList<>();
        DatosLabEntity dato1 = new DatosLabEntity();
        dato1.setProveedor(1);
        datosLab.add(dato1);
        DatosLabEntity dato2 = new DatosLabEntity();
        dato2.setProveedor(2);
        datosLab.add(dato2);

        ArrayList<DatosLabEntity> resultado = pagosService.filtrarProveedorEnLab(proveedor, datosLab);

        assertEquals(1, resultado.size());
        assertEquals(dato1, resultado.get(0));
    }

    @Test
    void totalKlsProveedor(){
        DatosImportadosEntity acopio1 = new DatosImportadosEntity();
        ArrayList<DatosImportadosEntity> lista = new ArrayList<>();

        acopio1.setFecha("20/10/2022");
        acopio1.setProveedor(90909);
        acopio1.setKls(100);
        acopio1.setTurno('M');

        lista.add(acopio1);

        double totalKls = pagosService.totalKlsProveedor(90909, lista);
        assertEquals(100,totalKls,0.0);
    }

    @Test
    void entregasMismoMes(){
        DatosImportadosEntity acopio1 = new DatosImportadosEntity();
        DatosImportadosEntity acopio2 = new DatosImportadosEntity();
        ArrayList<DatosImportadosEntity> lista = new ArrayList<>();
        ArrayList<DatosImportadosEntity> listaEquals = new ArrayList<>();

        acopio1.setFecha("2022/08/11");
        acopio1.setProveedor(90909);
        acopio1.setKls(100);
        acopio1.setTurno('M');

        acopio2.setFecha("2022/09/01");
        acopio2.setProveedor(90909);
        acopio2.setKls(200);
        acopio2.setTurno('T');

        lista.add(acopio1);
        lista.add(acopio2);

        listaEquals.add(acopio2);

        ArrayList<DatosImportadosEntity> mismoMes = pagosService.entregasMismoMes(lista,"09");
        assertEquals(listaEquals,mismoMes);

    }

    @Test
    void pagoPorFrecuencia1(){
        DatosImportadosEntity acopio1 = new DatosImportadosEntity();
        ArrayList<DatosImportadosEntity> lista = new ArrayList<>();

        acopio1.setFecha("2022/08/11");
        acopio1.setProveedor(90909);
        acopio1.setKls(100);
        acopio1.setTurno('M');

        lista.add(acopio1);
        double datos = pagosService.pagoPorFrecuencia(lista);
        assertEquals(0,datos,0.0);

    }

    @Test
    void pagoPorFrecuencia2(){
        DatosImportadosEntity acopio1 = new DatosImportadosEntity();
        DatosImportadosEntity acopio2 = new DatosImportadosEntity();
        DatosImportadosEntity acopio3 = new DatosImportadosEntity();
        DatosImportadosEntity acopio4 = new DatosImportadosEntity();
        DatosImportadosEntity acopio5 = new DatosImportadosEntity();
        DatosImportadosEntity acopio6 = new DatosImportadosEntity();
        DatosImportadosEntity acopio7 = new DatosImportadosEntity();
        DatosImportadosEntity acopio8 = new DatosImportadosEntity();
        DatosImportadosEntity acopio9 = new DatosImportadosEntity();
        DatosImportadosEntity acopio10 = new DatosImportadosEntity();
        DatosImportadosEntity acopio11 = new DatosImportadosEntity();

        ArrayList<DatosImportadosEntity> lista = new ArrayList<>();

        acopio1.setFecha("2022/08/01");
        acopio1.setProveedor(90909);
        acopio1.setKls(100);
        acopio1.setTurno('M');

        acopio2.setFecha("2022/08/02");
        acopio2.setProveedor(90909);
        acopio2.setKls(100);
        acopio2.setTurno('T');

        acopio3.setFecha("2022/08/03");
        acopio3.setProveedor(90909);
        acopio3.setKls(100);
        acopio3.setTurno('M');

        acopio4.setFecha("2022/08/04");
        acopio4.setProveedor(90909);
        acopio4.setKls(100);
        acopio4.setTurno('T');

        acopio5.setFecha("2022/08/05");
        acopio5.setProveedor(90909);
        acopio5.setKls(100);
        acopio5.setTurno('M');

        acopio6.setFecha("2022/08/06");
        acopio6.setProveedor(90909);
        acopio6.setKls(100);
        acopio6.setTurno('M');

        acopio7.setFecha("2022/08/07");
        acopio7.setProveedor(90909);
        acopio7.setKls(100);
        acopio7.setTurno('M');

        acopio8.setFecha("2022/08/08");
        acopio8.setProveedor(90909);
        acopio8.setKls(100);
        acopio8.setTurno('M');

        acopio9.setFecha("2022/08/09");
        acopio9.setProveedor(90909);
        acopio9.setKls(100);
        acopio9.setTurno('T');

        acopio10.setFecha("2022/08/10");
        acopio10.setProveedor(90909);
        acopio10.setKls(100);
        acopio10.setTurno('M');

        acopio11.setFecha("2022/08/11");
        acopio11.setProveedor(90909);
        acopio11.setKls(100);
        acopio11.setTurno('M');

        lista.add(acopio1);
        lista.add(acopio2);
        lista.add(acopio3);
        lista.add(acopio4);
        lista.add(acopio5);
        lista.add(acopio6);
        lista.add(acopio7);
        lista.add(acopio8);
        lista.add(acopio9);
        lista.add(acopio10);
        lista.add(acopio11);

        double datos = pagosService.pagoPorFrecuencia(lista);
        assertEquals(0.20,datos,0.0);

    }

    @Test
    void pagoPorFrecuencia3(){
        DatosImportadosEntity acopio1 = new DatosImportadosEntity();
        DatosImportadosEntity acopio2 = new DatosImportadosEntity();
        DatosImportadosEntity acopio3 = new DatosImportadosEntity();
        DatosImportadosEntity acopio4 = new DatosImportadosEntity();
        DatosImportadosEntity acopio5 = new DatosImportadosEntity();
        DatosImportadosEntity acopio6 = new DatosImportadosEntity();
        DatosImportadosEntity acopio7 = new DatosImportadosEntity();
        DatosImportadosEntity acopio8 = new DatosImportadosEntity();
        DatosImportadosEntity acopio9 = new DatosImportadosEntity();
        DatosImportadosEntity acopio10 = new DatosImportadosEntity();
        DatosImportadosEntity acopio11 = new DatosImportadosEntity();

        ArrayList<DatosImportadosEntity> lista = new ArrayList<>();

        acopio1.setFecha("2022/08/01");
        acopio1.setProveedor(90909);
        acopio1.setKls(100);
        acopio1.setTurno('M');

        acopio2.setFecha("2022/08/02");
        acopio2.setProveedor(90909);
        acopio2.setKls(100);
        acopio2.setTurno('M');

        acopio3.setFecha("2022/08/03");
        acopio3.setProveedor(90909);
        acopio3.setKls(100);
        acopio3.setTurno('M');

        acopio4.setFecha("2022/08/04");
        acopio4.setProveedor(90909);
        acopio4.setKls(100);
        acopio4.setTurno('M');

        acopio5.setFecha("2022/08/05");
        acopio5.setProveedor(90909);
        acopio5.setKls(100);
        acopio5.setTurno('M');

        acopio6.setFecha("2022/08/06");
        acopio6.setProveedor(90909);
        acopio6.setKls(100);
        acopio6.setTurno('M');

        acopio7.setFecha("2022/08/07");
        acopio7.setProveedor(90909);
        acopio7.setKls(100);
        acopio7.setTurno('M');

        acopio8.setFecha("2022/08/08");
        acopio8.setProveedor(90909);
        acopio8.setKls(100);
        acopio8.setTurno('M');

        acopio9.setFecha("2022/08/09");
        acopio9.setProveedor(90909);
        acopio9.setKls(100);
        acopio9.setTurno('M');

        acopio10.setFecha("2022/08/10");
        acopio10.setProveedor(90909);
        acopio10.setKls(100);
        acopio10.setTurno('M');

        acopio11.setFecha("2022/08/11");
        acopio11.setProveedor(90909);
        acopio11.setKls(100);
        acopio11.setTurno('M');

        lista.add(acopio1);
        lista.add(acopio2);
        lista.add(acopio3);
        lista.add(acopio4);
        lista.add(acopio5);
        lista.add(acopio6);
        lista.add(acopio7);
        lista.add(acopio8);
        lista.add(acopio9);
        lista.add(acopio10);
        lista.add(acopio11);

        // Son todos manana
        double datos = pagosService.pagoPorFrecuencia(lista);
        assertEquals(0.12,datos,0.0);

    }

    @Test
    void pagoPorFrecuencia4(){
        DatosImportadosEntity acopio1 = new DatosImportadosEntity();
        DatosImportadosEntity acopio2 = new DatosImportadosEntity();
        DatosImportadosEntity acopio3 = new DatosImportadosEntity();
        DatosImportadosEntity acopio4 = new DatosImportadosEntity();
        DatosImportadosEntity acopio5 = new DatosImportadosEntity();
        DatosImportadosEntity acopio6 = new DatosImportadosEntity();
        DatosImportadosEntity acopio7 = new DatosImportadosEntity();
        DatosImportadosEntity acopio8 = new DatosImportadosEntity();
        DatosImportadosEntity acopio9 = new DatosImportadosEntity();
        DatosImportadosEntity acopio10 = new DatosImportadosEntity();
        DatosImportadosEntity acopio11 = new DatosImportadosEntity();

        ArrayList<DatosImportadosEntity> lista = new ArrayList<>();

        acopio1.setFecha("2022/08/01");
        acopio1.setProveedor(90909);
        acopio1.setKls(100);
        acopio1.setTurno('T');

        acopio2.setFecha("2022/08/02");
        acopio2.setProveedor(90909);
        acopio2.setKls(100);
        acopio2.setTurno('T');

        acopio3.setFecha("2022/08/03");
        acopio3.setProveedor(90909);
        acopio3.setKls(100);
        acopio3.setTurno('T');

        acopio4.setFecha("2022/08/04");
        acopio4.setProveedor(90909);
        acopio4.setKls(100);
        acopio4.setTurno('T');

        acopio5.setFecha("2022/08/05");
        acopio5.setProveedor(90909);
        acopio5.setKls(100);
        acopio5.setTurno('T');

        acopio6.setFecha("2022/08/06");
        acopio6.setProveedor(90909);
        acopio6.setKls(100);
        acopio6.setTurno('T');

        acopio7.setFecha("2022/08/07");
        acopio7.setProveedor(90909);
        acopio7.setKls(100);
        acopio7.setTurno('T');

        acopio8.setFecha("2022/08/08");
        acopio8.setProveedor(90909);
        acopio8.setKls(100);
        acopio8.setTurno('T');

        acopio9.setFecha("2022/08/09");
        acopio9.setProveedor(90909);
        acopio9.setKls(100);
        acopio9.setTurno('T');

        acopio10.setFecha("2022/08/10");
        acopio10.setProveedor(90909);
        acopio10.setKls(100);
        acopio10.setTurno('T');

        acopio11.setFecha("2022/08/11");
        acopio11.setProveedor(90909);
        acopio11.setKls(100);
        acopio11.setTurno('T');

        lista.add(acopio1);
        lista.add(acopio2);
        lista.add(acopio3);
        lista.add(acopio4);
        lista.add(acopio5);
        lista.add(acopio6);
        lista.add(acopio7);
        lista.add(acopio8);
        lista.add(acopio9);
        lista.add(acopio10);
        lista.add(acopio11);

        // Son todos tarde
        double datos = pagosService.pagoPorFrecuencia(lista);
        assertEquals(0.08,datos,0.0);

    }

    @Test
    void pagoPorKiloCategoria(){

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(60601);
        proveedor.setNombre("Xavi Hernandez");
        proveedor.setCategoria('A');
        proveedor.setRetencion("No");

        Integer pago = pagosService.pagoPorKiloCategoria(proveedor);
        assertEquals(700,pago,0.0);

    }

    @Test
    void pagoPorKiloCategoria2(){

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(60601);
        proveedor.setNombre("Pedro Rodriguez");
        proveedor.setCategoria('B');
        proveedor.setRetencion("No");

        Integer pago = pagosService.pagoPorKiloCategoria(proveedor);
        assertEquals(550,pago,0.0);

    }

    @Test
    void pagoPorKiloCategoria3(){

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(60601);
        proveedor.setNombre("Iker Casillas");
        proveedor.setCategoria('C');
        proveedor.setRetencion("No");

        Integer pago = pagosService.pagoPorKiloCategoria(proveedor);
        assertEquals(400,pago,0.0);

    }

    @Test
    void pagoPorKiloCategoria4(){

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(60601);
        proveedor.setNombre("Claudio Bravo");
        proveedor.setCategoria('D');
        proveedor.setRetencion("No");

        Integer pago = pagosService.pagoPorKiloCategoria(proveedor);
        assertEquals(250,pago,0.0);

    }

    @Test
    void pagoPorKiloGrasa(){

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(60601);
        proveedor.setNombre("Xavi Hernandez");
        proveedor.setCategoria('A');
        proveedor.setRetencion("No");

        ArrayList<DatosLabEntity> listaLab = new ArrayList<>();
        DatosLabEntity datos = new DatosLabEntity();
        datos.setProveedor(60601);
        datos.setGrasa(10);
        datos.setSolido(10);
        listaLab.add(datos);

        Integer pago = pagosService.pagoPorKiloGrasa(proveedor,listaLab);
        assertEquals(30,pago,0.0);

    }

    @Test
    void pagoPorKiloGrasa2(){

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(60601);
        proveedor.setNombre("Xavi Hernandez");
        proveedor.setCategoria('A');
        proveedor.setRetencion("No");

        ArrayList<DatosLabEntity> listaLab = new ArrayList<>();
        DatosLabEntity datos = new DatosLabEntity();
        datos.setProveedor(60601);
        datos.setGrasa(30);
        datos.setSolido(30);
        listaLab.add(datos);

        Integer pago = pagosService.pagoPorKiloGrasa(proveedor,listaLab);
        assertEquals(80,pago,0.0);

    }

    @Test
    void pagoPorKiloGrasa3(){

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(60601);
        proveedor.setNombre("Xavi Hernandez");
        proveedor.setCategoria('A');
        proveedor.setRetencion("No");

        ArrayList<DatosLabEntity> listaLab = new ArrayList<>();
        DatosLabEntity datos = new DatosLabEntity();
        datos.setProveedor(60601);
        datos.setGrasa(60);
        datos.setSolido(60);
        listaLab.add(datos);

        Integer pago = pagosService.pagoPorKiloGrasa(proveedor,listaLab);
        assertEquals(120,pago,0.0);

    }

    @Test
    void pagoPorKiloSolidosTotales(){

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(60601);
        proveedor.setNombre("Xavi Hernandez");
        proveedor.setCategoria('A');
        proveedor.setRetencion("No");

        ArrayList<DatosLabEntity> listaLab = new ArrayList<>();
        DatosLabEntity datos = new DatosLabEntity();
        datos.setProveedor(60601);
        datos.setGrasa(10);
        datos.setSolido(10);
        listaLab.add(datos);

        Integer pago = pagosService.pagoPorKiloSolidosTotales(proveedor,listaLab);
        assertEquals(-90,pago,0.0);

    }

    @Test
    void pagoPorKiloSolidosTotales2(){

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(60601);
        proveedor.setNombre("Xavi Hernandez");
        proveedor.setCategoria('A');
        proveedor.setRetencion("No");

        ArrayList<DatosLabEntity> listaLab = new ArrayList<>();
        DatosLabEntity datos = new DatosLabEntity();
        datos.setProveedor(60601);
        datos.setGrasa(5);
        datos.setSolido(5);
        listaLab.add(datos);

        Integer pago = pagosService.pagoPorKiloSolidosTotales(proveedor,listaLab);
        assertEquals(-130,pago,0.0);

    }

    @Test
    void pagoPorKiloSolidosTotales3(){

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(60601);
        proveedor.setNombre("Xavi Hernandez");
        proveedor.setCategoria('A');
        proveedor.setRetencion("No");

        ArrayList<DatosLabEntity> listaLab = new ArrayList<>();
        DatosLabEntity datos = new DatosLabEntity();
        datos.setProveedor(60601);
        datos.setGrasa(25);
        datos.setSolido(25);
        listaLab.add(datos);

        Integer pago = pagosService.pagoPorKiloSolidosTotales(proveedor,listaLab);
        assertEquals(95,pago,0.0);

    }

    @Test
    void pagoPorKiloSolidosTotales4(){

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(60601);
        proveedor.setNombre("Xavi Hernandez");
        proveedor.setCategoria('A');
        proveedor.setRetencion("No");

        ArrayList<DatosLabEntity> listaLab = new ArrayList<>();
        DatosLabEntity datos = new DatosLabEntity();
        datos.setProveedor(60601);
        datos.setGrasa(50);
        datos.setSolido(50);
        listaLab.add(datos);

        Integer pago = pagosService.pagoPorKiloSolidosTotales(proveedor,listaLab);
        assertEquals(150,pago,0.0);

    }

    @Test
    void encontrarMesAnterior1(){
        String mes = pagosService.encontrarMesAnterior("09");
        assertEquals("08",mes);
    }

    @Test
    void encontrarMesAnterior2(){
        String mes = pagosService.encontrarMesAnterior("10");
        assertEquals("09",mes);
    }

    @Test
    void encontrarMesAnterior3(){
        String mes = pagosService.encontrarMesAnterior("01");
        assertEquals("12",mes);
    }

    @Test
    void variacionKls1(){

        ArrayList<DatosImportadosEntity> lista = new ArrayList<>();
        DatosImportadosEntity datos1 = new DatosImportadosEntity();
        DatosImportadosEntity datos2 = new DatosImportadosEntity();

        datos1.setFecha("2022/08/08");
        datos1.setProveedor(90909);
        datos1.setKls(100);
        datos1.setTurno('M');

        datos2.setFecha("2022/09/08");
        datos2.setProveedor(90909);
        datos2.setKls(101);
        datos2.setTurno('M');

        lista.add(datos1);
        lista.add(datos2);

        double variacionKls = pagosService.variacionKls(lista,"09");
        assertEquals(0,variacionKls,0.0);

    }

    @Test
    void variacionKls2(){

        ArrayList<DatosImportadosEntity> lista = new ArrayList<>();
        DatosImportadosEntity datos1 = new DatosImportadosEntity();
        DatosImportadosEntity datos2 = new DatosImportadosEntity();

        datos1.setFecha("2022/08/08");
        datos1.setProveedor(90909);
        datos1.setKls(100);
        datos1.setTurno('M');

        datos2.setFecha("2022/09/08");
        datos2.setProveedor(90909);
        datos2.setKls(50);
        datos2.setTurno('M');

        lista.add(datos1);
        lista.add(datos2);

        double variacionKls = pagosService.variacionKls(lista,"09");
        assertEquals(0.5,variacionKls,0.0);

    }

    @Test
    void variacionKls3(){

        ArrayList<DatosImportadosEntity> lista = new ArrayList<>();
        DatosImportadosEntity datos1 = new DatosImportadosEntity();
        DatosImportadosEntity datos2 = new DatosImportadosEntity();

        datos1.setFecha("2022/08/08");
        datos1.setProveedor(90901);
        datos1.setKls(100);
        datos1.setTurno('M');

        datos2.setFecha("2022/09/08");
        datos2.setProveedor(90909);
        datos2.setKls(101);
        datos2.setTurno('M');

        lista.add(datos1);
        lista.add(datos2);

        double variacionKls = pagosService.variacionKls(lista,"09");
        assertEquals(0.0,variacionKls,0.0);

    }

    @Test
    void descKlsRespectoMesAnterior2(){

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(90909);
        proveedor.setNombre("Xavi Hernandez");
        proveedor.setCategoria('A');
        proveedor.setRetencion("No");

        ArrayList<DatosImportadosEntity> lista = new ArrayList<>();
        DatosImportadosEntity datos1 = new DatosImportadosEntity();
        DatosImportadosEntity datos2 = new DatosImportadosEntity();
        DatosImportadosEntity datos3 = new DatosImportadosEntity();

        datos1.setFecha("2022/08/08");
        datos1.setProveedor(90909);
        datos1.setKls(100);
        datos1.setTurno('M');

        datos2.setFecha("2022/09/08");
        datos2.setProveedor(90909);
        datos2.setKls(90);
        datos2.setTurno('M');

        datos3.setFecha("2021/09/08");
        datos3.setProveedor(90912);
        datos3.setKls(52);
        datos3.setTurno('T');

        lista.add(datos1);
        lista.add(datos2);
        lista.add(datos3);

        double descuento = pagosService.descKlsRespectoMesAnterior(proveedor,lista,"09");
        assertEquals(0.07,descuento);

    }

    @Test
    void descKlsRespectoMesAnterior(){
        /*
        (ProveedorEntity proveedor,
                          ArrayList<DatosImportadosEntity> todosLosDatos,
                                              String mesActual)
         */
        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(90909);
        proveedor.setNombre("Xavi Hernandez");
        proveedor.setCategoria('A');
        proveedor.setRetencion("No");

        ArrayList<DatosImportadosEntity> lista = new ArrayList<>();
        DatosImportadosEntity datos1 = new DatosImportadosEntity();
        DatosImportadosEntity datos2 = new DatosImportadosEntity();
        DatosImportadosEntity datos3 = new DatosImportadosEntity();

        datos1.setFecha("2022/08/08");
        datos1.setProveedor(90909);
        datos1.setKls(100);
        datos1.setTurno('M');

        datos2.setFecha("2022/09/08");
        datos2.setProveedor(90909);
        datos2.setKls(50);
        datos2.setTurno('M');

        datos3.setFecha("2021/09/08");
        datos3.setProveedor(90912);
        datos3.setKls(52);
        datos3.setTurno('T');

        lista.add(datos1);
        lista.add(datos2);
        lista.add(datos3);

        double descuento = pagosService.descKlsRespectoMesAnterior(proveedor,lista,"09");
        assertEquals(0.30,descuento);

    }

    @Test
    void descKlsRespectoMesAnterior3(){

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(90909);
        proveedor.setNombre("Xavi Hernandez");
        proveedor.setCategoria('A');
        proveedor.setRetencion("No");

        ArrayList<DatosImportadosEntity> lista = new ArrayList<>();
        DatosImportadosEntity datos1 = new DatosImportadosEntity();
        DatosImportadosEntity datos2 = new DatosImportadosEntity();
        DatosImportadosEntity datos3 = new DatosImportadosEntity();

        datos1.setFecha("2022/08/08");
        datos1.setProveedor(90909);
        datos1.setKls(100);
        datos1.setTurno('M');

        datos2.setFecha("2022/09/08");
        datos2.setProveedor(90909);
        datos2.setKls(70);
        datos2.setTurno('M');

        datos3.setFecha("2021/09/08");
        datos3.setProveedor(90912);
        datos3.setKls(52);
        datos3.setTurno('T');

        lista.add(datos1);
        lista.add(datos2);
        lista.add(datos3);

        double descuento = pagosService.descKlsRespectoMesAnterior(proveedor,lista,"09");
        assertEquals(0.15,descuento);

    }

    @Test
    void descKlsRespectoMesAnterior4(){

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(90909);
        proveedor.setNombre("Xavi Hernandez");
        proveedor.setCategoria('A');
        proveedor.setRetencion("No");

        ArrayList<DatosImportadosEntity> lista = new ArrayList<>();
        DatosImportadosEntity datos1 = new DatosImportadosEntity();
        DatosImportadosEntity datos2 = new DatosImportadosEntity();
        DatosImportadosEntity datos3 = new DatosImportadosEntity();

        datos1.setFecha("2022/08/08");
        datos1.setProveedor(90909);
        datos1.setKls(100);
        datos1.setTurno('M');

        datos2.setFecha("2022/09/08");
        datos2.setProveedor(90909);
        datos2.setKls(900);
        datos2.setTurno('M');

        datos3.setFecha("2021/09/08");
        datos3.setProveedor(90912);
        datos3.setKls(52);
        datos3.setTurno('T');

        lista.add(datos1);
        lista.add(datos2);
        lista.add(datos3);

        double descuento = pagosService.descKlsRespectoMesAnterior(proveedor,lista,"09");
        assertEquals(0.0,descuento);

    }

    @Test
    void descKlsRespectoMesAnterior5(){

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(90909);
        proveedor.setNombre("Xavi Hernandez");
        proveedor.setCategoria('A');
        proveedor.setRetencion("No");

        ArrayList<DatosImportadosEntity> lista = new ArrayList<>();
        DatosImportadosEntity datos1 = new DatosImportadosEntity();
        DatosImportadosEntity datos2 = new DatosImportadosEntity();

        datos1.setFecha("2022/08/08");
        datos1.setProveedor(90909);
        datos1.setKls(100);
        datos1.setTurno('M');

        datos2.setFecha("2022/09/08");
        datos2.setProveedor(90909);
        datos2.setKls(95);
        datos2.setTurno('M');

        lista.add(datos1);
        lista.add(datos2);

        double descuento = pagosService.descKlsRespectoMesAnterior(proveedor,lista,"09");
        assertEquals(0.00,descuento);

    }

    @Test
    void variacionGrasa1(){

        ArrayList<DatosLabEntity> listaLab = new ArrayList<>();
        DatosLabEntity datos = new DatosLabEntity();

        datos.setProveedor(60601);
        datos.setGrasa(10);
        datos.setSolido(10);

        listaLab.add(datos);

        ArrayList<PagosEntity> historial = new ArrayList<>();

        double variacion = pagosService.variacionGrasa(listaLab,historial);
        assertEquals(0,variacion);

    }


    @Test
    void variacionGrasa2(){

        ArrayList<DatosLabEntity> listaLab = new ArrayList<>();
        DatosLabEntity datos = new DatosLabEntity();

        datos.setProveedor(60601);
        datos.setGrasa(10);
        datos.setSolido(10);

        listaLab.add(datos);

        ArrayList<PagosEntity> historial = new ArrayList<>();
        PagosEntity pagos = new PagosEntity();
        pagos.setID(1);
        pagos.setCodigoProveedor(60601);
        pagos.setPorcentajeGrasa(50);
        historial.add(pagos);

        double variacion = pagosService.variacionGrasa(listaLab,historial);
        assertEquals(0.8,variacion);

    }

    @Test
    void variacionGrasa3(){

        ArrayList<DatosLabEntity> listaLab = new ArrayList<>();
        DatosLabEntity datos = new DatosLabEntity();

        datos.setProveedor(60601);
        datos.setGrasa(60);
        datos.setSolido(10);

        listaLab.add(datos);

        ArrayList<PagosEntity> historial = new ArrayList<>();
        PagosEntity pagos = new PagosEntity();
        pagos.setID(1);
        pagos.setCodigoProveedor(60601);
        pagos.setPorcentajeGrasa(50);
        historial.add(pagos);

        double variacion = pagosService.variacionGrasa(listaLab,historial);
        assertEquals(0.0,variacion);

    }

    @Test
    void descGrasaRespectoMesAnterior(){

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(90909);
        proveedor.setNombre("Andres Iniesta");
        proveedor.setCategoria('B');
        proveedor.setRetencion("No");

        ArrayList<DatosLabEntity> listaLab = new ArrayList<>();
        DatosLabEntity datos = new DatosLabEntity();

        datos.setProveedor(90909);
        datos.setGrasa(10);
        datos.setSolido(10);
        listaLab.add(datos);

        ArrayList<PagosEntity> historial = new ArrayList<>();
        PagosEntity pagos = new PagosEntity();
        pagos.setID(1);
        pagos.setCodigoProveedor(90909);
        pagos.setPorcentajeGrasa(300);
        historial.add(pagos);

        double descGrasa = pagosService.descGrasaRespectoMesAnterior(proveedor,listaLab,"05",historial);
        assertEquals(0.3,descGrasa);

    }

    @Test
    void descGrasaRespectoMesAnterior2(){

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(90909);
        proveedor.setNombre("Andres Iniesta");
        proveedor.setCategoria('B');
        proveedor.setRetencion("No");

        ArrayList<DatosLabEntity> listaLab = new ArrayList<>();
        DatosLabEntity datos = new DatosLabEntity();

        datos.setProveedor(90909);
        datos.setGrasa(10);
        datos.setSolido(10);
        listaLab.add(datos);

        ArrayList<PagosEntity> historial = new ArrayList<>();
        PagosEntity pagos = new PagosEntity();
        pagos.setID(1);
        pagos.setCodigoProveedor(90909);
        pagos.setPorcentajeGrasa(15);
        historial.add(pagos);

        double descGrasa = pagosService.descGrasaRespectoMesAnterior(proveedor,listaLab,"05",historial);
        assertEquals(0.2,descGrasa);

    }

    @Test
    void descGrasaRespectoMesAnterior3(){

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(90909);
        proveedor.setNombre("Andres Iniesta");
        proveedor.setCategoria('B');
        proveedor.setRetencion("No");

        ArrayList<DatosLabEntity> listaLab = new ArrayList<>();
        DatosLabEntity datos = new DatosLabEntity();

        datos.setProveedor(90909);
        datos.setGrasa(10);
        datos.setSolido(10);
        listaLab.add(datos);

        ArrayList<PagosEntity> historial = new ArrayList<>();
        PagosEntity pagos = new PagosEntity();
        pagos.setID(1);
        pagos.setCodigoProveedor(90909);
        pagos.setPorcentajeGrasa(12);
        historial.add(pagos);

        double descGrasa = pagosService.descGrasaRespectoMesAnterior(proveedor,listaLab,"05",historial);
        assertEquals(0.12,descGrasa);

    }

    @Test
    void descGrasaRespectoMesAnterior4(){

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(90909);
        proveedor.setNombre("Andres Iniesta");
        proveedor.setCategoria('B');
        proveedor.setRetencion("No");

        ArrayList<DatosLabEntity> listaLab = new ArrayList<>();
        DatosLabEntity datos = new DatosLabEntity();

        datos.setProveedor(90909);
        datos.setGrasa(10);
        datos.setSolido(10);
        listaLab.add(datos);

        ArrayList<PagosEntity> historial = new ArrayList<>();
        PagosEntity pagos = new PagosEntity();
        pagos.setID(1);
        pagos.setCodigoProveedor(90909);
        pagos.setPorcentajeGrasa(11);
        historial.add(pagos);

        double descGrasa = pagosService.descGrasaRespectoMesAnterior(proveedor,listaLab,"05",historial);
        assertEquals(0.0,descGrasa);

    }

    @Test
    void descGrasaRespectoMesAnterior5(){

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(90909);
        proveedor.setNombre("Andres Iniesta");
        proveedor.setCategoria('B');
        proveedor.setRetencion("No");

        ArrayList<DatosLabEntity> listaLab = new ArrayList<>();
        DatosLabEntity datos = new DatosLabEntity();

        datos.setProveedor(90909);
        datos.setGrasa(10);
        datos.setSolido(10);
        listaLab.add(datos);

        ArrayList<PagosEntity> historial = new ArrayList<>();
        PagosEntity pagos = new PagosEntity();
        pagos.setID(1);
        pagos.setCodigoProveedor(90901);
        pagos.setPorcentajeGrasa(11);
        historial.add(pagos);

        double descGrasa = pagosService.descGrasaRespectoMesAnterior(proveedor,listaLab,"05",historial);
        assertEquals(0.0,descGrasa);

    }

    @Test
    void descGrasaRespectoMesAnterior6(){

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(90909);
        proveedor.setNombre("Andres Iniesta");
        proveedor.setCategoria('B');
        proveedor.setRetencion("No");

        ArrayList<DatosLabEntity> listaLab = new ArrayList<>();
        DatosLabEntity datos = new DatosLabEntity();

        datos.setProveedor(90909);
        datos.setGrasa(100);
        datos.setSolido(10);
        listaLab.add(datos);

        ArrayList<PagosEntity> historial = new ArrayList<>();
        PagosEntity pagos = new PagosEntity();
        pagos.setID(1);
        pagos.setCodigoProveedor(90909);
        pagos.setPorcentajeGrasa(11);
        historial.add(pagos);

        double descGrasa = pagosService.descGrasaRespectoMesAnterior(proveedor,listaLab,"05",historial);
        assertEquals(0.0,descGrasa);

    }

    @Test
    void variacionSolido(){
        ArrayList<DatosLabEntity> listaLab = new ArrayList<>();
        DatosLabEntity datos = new DatosLabEntity();

        datos.setProveedor(60601);
        datos.setGrasa(10);
        datos.setSolido(10);

        listaLab.add(datos);

        ArrayList<PagosEntity> historial = new ArrayList<>();

        double variacion = pagosService.variacionSolido(listaLab,historial);
        assertEquals(0,variacion);
    }

    @Test
    void variacionSolido2(){

        ArrayList<DatosLabEntity> listaLab = new ArrayList<>();
        DatosLabEntity datos = new DatosLabEntity();

        datos.setProveedor(60601);
        datos.setGrasa(10);
        datos.setSolido(10);

        listaLab.add(datos);

        ArrayList<PagosEntity> historial = new ArrayList<>();
        PagosEntity pagos = new PagosEntity();
        pagos.setID(1);
        pagos.setCodigoProveedor(60601);
        pagos.setPorcentajeSolidos(50);
        historial.add(pagos);

        double variacion = pagosService.variacionSolido(listaLab,historial);
        assertEquals(0.8,variacion);

    }

    @Test
    void variacionSolido3(){

        ArrayList<DatosLabEntity> listaLab = new ArrayList<>();
        DatosLabEntity datos = new DatosLabEntity();

        datos.setProveedor(60601);
        datos.setGrasa(60);
        datos.setSolido(60);

        listaLab.add(datos);

        ArrayList<PagosEntity> historial = new ArrayList<>();
        PagosEntity pagos = new PagosEntity();
        pagos.setID(1);
        pagos.setCodigoProveedor(60601);
        pagos.setPorcentajeSolidos(50);
        historial.add(pagos);

        double variacion = pagosService.variacionSolido(listaLab,historial);
        assertEquals(0.0,variacion);

    }

    @Test
    void descSolidosRespectoMesAnterior(){

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(90909);
        proveedor.setNombre("Andres Iniesta");
        proveedor.setCategoria('B');
        proveedor.setRetencion("No");

        ArrayList<DatosLabEntity> listaLab = new ArrayList<>();
        DatosLabEntity datos = new DatosLabEntity();

        datos.setProveedor(90909);
        datos.setGrasa(10);
        datos.setSolido(10);
        listaLab.add(datos);

        ArrayList<PagosEntity> historial = new ArrayList<>();
        PagosEntity pagos = new PagosEntity();
        pagos.setID(1);
        pagos.setCodigoProveedor(90909);
        pagos.setPorcentajeSolidos(300);
        historial.add(pagos);

        double descSolidos = pagosService.descSolidosRespectoMesAnterior(proveedor,listaLab,"05",historial);
        assertEquals(0.45,descSolidos);

    }

    @Test
    void descSolidosRespectoMesAnterior2(){

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(90909);
        proveedor.setNombre("Andres Iniesta");
        proveedor.setCategoria('B');
        proveedor.setRetencion("No");

        ArrayList<DatosLabEntity> listaLab = new ArrayList<>();
        DatosLabEntity datos = new DatosLabEntity();

        datos.setProveedor(90909);
        datos.setGrasa(10);
        datos.setSolido(10);
        listaLab.add(datos);

        ArrayList<PagosEntity> historial = new ArrayList<>();
        PagosEntity pagos = new PagosEntity();
        pagos.setID(1);
        pagos.setCodigoProveedor(90909);
        pagos.setPorcentajeSolidos(15);
        historial.add(pagos);

        double descGrasa = pagosService.descSolidosRespectoMesAnterior(proveedor,listaLab,"05",historial);
        assertEquals(0.27,descGrasa);

    }

    @Test
    void descSolidosRespectoMesAnterior3(){

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(90909);
        proveedor.setNombre("Andres Iniesta");
        proveedor.setCategoria('B');
        proveedor.setRetencion("No");

        ArrayList<DatosLabEntity> listaLab = new ArrayList<>();
        DatosLabEntity datos = new DatosLabEntity();

        datos.setProveedor(90909);
        datos.setGrasa(10);
        datos.setSolido(100);
        listaLab.add(datos);

        ArrayList<PagosEntity> historial = new ArrayList<>();
        PagosEntity pagos = new PagosEntity();
        pagos.setID(1);
        pagos.setCodigoProveedor(90909);
        pagos.setPorcentajeSolidos(110);
        historial.add(pagos);

        double descGrasa = pagosService.descSolidosRespectoMesAnterior(proveedor,listaLab,"05",historial);
        assertEquals(0.18,descGrasa);

    }

    @Test
    void descSolidosRespectoMesAnterior4(){

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(90909);
        proveedor.setNombre("Andres Iniesta");
        proveedor.setCategoria('B');
        proveedor.setRetencion("No");

        ArrayList<DatosLabEntity> listaLab = new ArrayList<>();
        DatosLabEntity datos = new DatosLabEntity();

        datos.setProveedor(90909);
        datos.setGrasa(10);
        datos.setSolido(100);
        listaLab.add(datos);

        ArrayList<PagosEntity> historial = new ArrayList<>();
        PagosEntity pagos = new PagosEntity();
        pagos.setID(1);
        pagos.setCodigoProveedor(90909);
        pagos.setPorcentajeSolidos(105);
        historial.add(pagos);

        double descGrasa = pagosService.descSolidosRespectoMesAnterior(proveedor,listaLab,"05",historial);
        assertEquals(0.0,descGrasa);

    }

    @Test
    void descSolidosRespectoMesAnterior5(){

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(90909);
        proveedor.setNombre("Andres Iniesta");
        proveedor.setCategoria('B');
        proveedor.setRetencion("No");

        ArrayList<DatosLabEntity> listaLab = new ArrayList<>();
        DatosLabEntity datos = new DatosLabEntity();

        datos.setProveedor(90909);
        datos.setGrasa(10);
        datos.setSolido(100);
        listaLab.add(datos);

        ArrayList<PagosEntity> historial = new ArrayList<>();
        PagosEntity pagos = new PagosEntity();
        pagos.setID(1);
        pagos.setCodigoProveedor(90901);
        pagos.setPorcentajeSolidos(105);
        historial.add(pagos);

        double descGrasa = pagosService.descSolidosRespectoMesAnterior(proveedor,listaLab,"05",historial);
        assertEquals(0.0,descGrasa);

    }

    @Test
    void descSolidosRespectoMesAnterior6(){

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(90909);
        proveedor.setNombre("Andres Iniesta");
        proveedor.setCategoria('B');
        proveedor.setRetencion("No");

        ArrayList<DatosLabEntity> listaLab = new ArrayList<>();
        DatosLabEntity datos = new DatosLabEntity();

        datos.setProveedor(90909);
        datos.setGrasa(10);
        datos.setSolido(150);
        listaLab.add(datos);

        ArrayList<PagosEntity> historial = new ArrayList<>();
        PagosEntity pagos = new PagosEntity();
        pagos.setID(1);
        pagos.setCodigoProveedor(90909);
        pagos.setPorcentajeSolidos(105);
        historial.add(pagos);

        double descGrasa = pagosService.descSolidosRespectoMesAnterior(proveedor,listaLab,"05",historial);
        assertEquals(0.0,descGrasa);

    }

    @Test
    void pagoPorLeche(){

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(60601);
        proveedor.setNombre("Xavi Hernandez");
        proveedor.setCategoria('A');
        proveedor.setRetencion("No");

        double pagoLeche = pagosService.pagoPorLeche(proveedor,100);
        assertEquals(70000,pagoLeche);

    }

    @Test
    void pagoPorGrasa(){

        /*
        Integer pago = pagosService.pagoPorKiloGrasa(proveedor,listaLab);
        assertEquals(30,pago,0.0);
         */

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(90909);
        proveedor.setNombre("Andres Iniesta");
        proveedor.setCategoria('B');
        proveedor.setRetencion("No");

        ArrayList<DatosLabEntity> listaLab = new ArrayList<>();
        DatosLabEntity datos = new DatosLabEntity();

        datos.setProveedor(90909);
        datos.setGrasa(10);
        datos.setSolido(10);
        listaLab.add(datos);

        double pagoGrasa = pagosService.pagoPorGrasa(proveedor,listaLab,100);
        assertEquals(3000,pagoGrasa);

    }

    @Test
    void pagoPorSolidos(){

        /*
        Integer pago = pagosService.pagoPorKiloSolidosTotales(proveedor,listaLab);
        assertEquals(-90,pago,0.0);
         */

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(60601);
        proveedor.setNombre("Xavi Hernandez");
        proveedor.setCategoria('A');
        proveedor.setRetencion("No");

        ArrayList<DatosLabEntity> listaLab = new ArrayList<>();
        DatosLabEntity datos = new DatosLabEntity();
        datos.setProveedor(60601);
        datos.setGrasa(10);
        datos.setSolido(10);
        listaLab.add(datos);

        double pagoSolidos = pagosService.pagoPorSolidos(proveedor,listaLab,100);
        assertEquals(-9000,pagoSolidos);

    }

    @Test
    void sumaPagosLecheGrasaSolidoMasFrecuencia(){
        /*
        double datos = pagosService.pagoPorFrecuencia(lista);
        assertEquals(0,datos,0.0);
         */

        DatosImportadosEntity acopio1 = new DatosImportadosEntity();
        ArrayList<DatosImportadosEntity> lista = new ArrayList<>();

        acopio1.setFecha("2022/08/11");
        acopio1.setProveedor(90909);
        acopio1.setKls(100);
        acopio1.setTurno('M');

        lista.add(acopio1);

        double pagoFrecuencia = pagosService.sumaPagosLecheGrasaSolidoMasFrecuencia(100,100,100,lista);
        assertEquals(300,pagoFrecuencia);

    }

    @Test
    void descKiloLeche(){
        /*
        double descuento = pagosService.descKlsRespectoMesAnterior(proveedor,lista,"09");
        assertEquals(0.30,descuento);
         */

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(90909);
        proveedor.setNombre("Xavi Hernandez");
        proveedor.setCategoria('A');
        proveedor.setRetencion("No");

        ArrayList<DatosImportadosEntity> lista = new ArrayList<>();
        DatosImportadosEntity datos1 = new DatosImportadosEntity();
        DatosImportadosEntity datos2 = new DatosImportadosEntity();
        DatosImportadosEntity datos3 = new DatosImportadosEntity();

        datos1.setFecha("2022/08/08");
        datos1.setProveedor(90909);
        datos1.setKls(100);
        datos1.setTurno('M');

        datos2.setFecha("2022/09/08");
        datos2.setProveedor(90909);
        datos2.setKls(50);
        datos2.setTurno('M');

        datos3.setFecha("2021/09/08");
        datos3.setProveedor(90912);
        datos3.setKls(52);
        datos3.setTurno('T');

        lista.add(datos1);
        lista.add(datos2);
        lista.add(datos3);

        double descLeche = pagosService.descKiloLeche(100,proveedor,lista,"09");
        assertEquals(30,descLeche);

    }

    @Test
    void descPorGrasa(){
        /*
        double descGrasa = pagosService.descGrasaRespectoMesAnterior(proveedor,listaLab,"05",historial);
        assertEquals(0.3,descGrasa);
         */

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(90909);
        proveedor.setNombre("Andres Iniesta");
        proveedor.setCategoria('B');
        proveedor.setRetencion("No");

        ArrayList<DatosLabEntity> listaLab = new ArrayList<>();
        DatosLabEntity datos = new DatosLabEntity();

        datos.setProveedor(90909);
        datos.setGrasa(10);
        datos.setSolido(10);
        listaLab.add(datos);

        ArrayList<PagosEntity> historial = new ArrayList<>();
        PagosEntity pagos = new PagosEntity();
        pagos.setID(1);
        pagos.setCodigoProveedor(90909);
        pagos.setPorcentajeGrasa(300);
        historial.add(pagos);

        double descGrasa = pagosService.descPorGrasa(100,proveedor,listaLab,"06",historial);
        assertEquals(30,descGrasa);

    }

    @Test
    void descPorSolidos(){
        /*
        double descSolidos = pagosService.descSolidosRespectoMesAnterior(proveedor,listaLab,"05",historial);
        assertEquals(0.45,descSolidos);
         */
        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(90909);
        proveedor.setNombre("Andres Iniesta");
        proveedor.setCategoria('B');
        proveedor.setRetencion("No");

        ArrayList<DatosLabEntity> listaLab = new ArrayList<>();
        DatosLabEntity datos = new DatosLabEntity();

        datos.setProveedor(90909);
        datos.setGrasa(10);
        datos.setSolido(10);
        listaLab.add(datos);

        ArrayList<PagosEntity> historial = new ArrayList<>();
        PagosEntity pagos = new PagosEntity();
        pagos.setID(1);
        pagos.setCodigoProveedor(90909);
        pagos.setPorcentajeSolidos(300);
        historial.add(pagos);

        double descSolidos = pagosService.descPorSolidos(100,proveedor,listaLab,"06",historial);
        assertEquals(45,descSolidos);

    }

    @Test
    void pagoTotalApagar(){
        double pago = pagosService.pagoTotalApagar(100,100,100,500);
        assertEquals(200,pago);
    }

    @Test
    void nroDiasEnvios(){
        DatosImportadosEntity acopio1 = new DatosImportadosEntity();
        DatosImportadosEntity acopio2 = new DatosImportadosEntity();
        ArrayList<DatosImportadosEntity> lista = new ArrayList<>();

        acopio1.setFecha("20/10/2022");
        acopio1.setProveedor(90909);
        acopio1.setKls(100);
        acopio1.setTurno('M');

        acopio2.setFecha("21/10/2022");
        acopio2.setProveedor(90909);
        acopio2.setKls(150);
        acopio2.setTurno('T');

        lista.add(acopio1);
        lista.add(acopio2);

        double nroDias = pagosService.nroDiasEnvios(lista);
        assertEquals(2.0,nroDias);
    }

    @Test
    void nroDiasEnvios2(){
        DatosImportadosEntity acopio1 = new DatosImportadosEntity();
        DatosImportadosEntity acopio2 = new DatosImportadosEntity();
        DatosImportadosEntity acopio3 = new DatosImportadosEntity();
        ArrayList<DatosImportadosEntity> lista = new ArrayList<>();

        acopio1.setFecha("20/10/2022");
        acopio1.setProveedor(90909);
        acopio1.setKls(100);
        acopio1.setTurno('M');

        acopio2.setFecha("21/10/2022");
        acopio2.setProveedor(90909);
        acopio2.setKls(150);
        acopio2.setTurno('T');

        acopio3.setFecha("20/10/2022");
        acopio3.setProveedor(90909);
        acopio3.setKls(150);
        acopio3.setTurno('T');

        lista.add(acopio1);
        lista.add(acopio2);
        lista.add(acopio3);

        double nroDias = pagosService.nroDiasEnvios(lista);
        assertEquals(2.0,nroDias);
    }

    @Test
    void retencionFinal(){
        ArrayList<ProveedorEntity> proveedores = new ArrayList<>();
        ProveedorEntity proveedor1 = new ProveedorEntity();
        ProveedorEntity proveedor2 = new ProveedorEntity();

        proveedor1.setCodigo(90909);
        proveedor1.setNombre("Andres Iniesta");
        proveedor1.setCategoria('B');
        proveedor1.setRetencion("No");

        proveedor2.setCodigo(30301);
        proveedor2.setNombre("Arturo Vidal");
        proveedor2.setCategoria('A');
        proveedor2.setRetencion("Si");

        proveedores.add(proveedor1);
        proveedores.add(proveedor2);

        double retencion = pagosService.retencionFinal(proveedores,proveedor2,1000000);
        assertEquals(130000,retencion);
    }

    @Test
    void retencionFinal2(){
        ArrayList<ProveedorEntity> proveedores = new ArrayList<>();
        ProveedorEntity proveedor1 = new ProveedorEntity();
        ProveedorEntity proveedor2 = new ProveedorEntity();

        proveedor1.setCodigo(90909);
        proveedor1.setNombre("Andres Iniesta");
        proveedor1.setCategoria('B');
        proveedor1.setRetencion("No");

        proveedor2.setCodigo(30301);
        proveedor2.setNombre("Arturo Vidal");
        proveedor2.setCategoria('A');
        proveedor2.setRetencion("Si");

        proveedores.add(proveedor1);
        proveedores.add(proveedor2);

        double retencion = pagosService.retencionFinal(proveedores,proveedor2,500000);
        assertEquals(0,retencion);
    }

    @Test
    void retencionFinal3(){
        ArrayList<ProveedorEntity> proveedores = new ArrayList<>();
        ProveedorEntity proveedor1 = new ProveedorEntity();
        ProveedorEntity proveedor2 = new ProveedorEntity();

        proveedor1.setCodigo(90909);
        proveedor1.setNombre("Andres Iniesta");
        proveedor1.setCategoria('B');
        proveedor1.setRetencion("No");

        proveedor2.setCodigo(30301);
        proveedor2.setNombre("Arturo Vidal");
        proveedor2.setCategoria('A');
        proveedor2.setRetencion("No");

        proveedores.add(proveedor1);
        proveedores.add(proveedor2);

        double retencion = pagosService.retencionFinal(proveedores,proveedor2,500000);
        assertEquals(0,retencion);
    }

    @Test
    void promedioDiarioKls(){
        DatosImportadosEntity acopio1 = new DatosImportadosEntity();
        DatosImportadosEntity acopio2 = new DatosImportadosEntity();
        ArrayList<DatosImportadosEntity> lista = new ArrayList<>();

        acopio1.setFecha("20/10/2022");
        acopio1.setProveedor(90909);
        acopio1.setKls(100);
        acopio1.setTurno('M');

        acopio2.setFecha("21/10/2022");
        acopio2.setProveedor(90909);
        acopio2.setKls(200);
        acopio2.setTurno('T');

        lista.add(acopio1);
        lista.add(acopio2);

        double promedio = pagosService.promedioDiarioKls(lista);
        assertEquals(150,promedio);
    }

    @Test
    void promedioDiarioKls2(){
        DatosImportadosEntity acopio1 = new DatosImportadosEntity();
        DatosImportadosEntity acopio2 = new DatosImportadosEntity();
        DatosImportadosEntity acopio3 = new DatosImportadosEntity();
        ArrayList<DatosImportadosEntity> lista = new ArrayList<>();

        acopio1.setFecha("20/10/2022");
        acopio1.setProveedor(90909);
        acopio1.setKls(100);
        acopio1.setTurno('M');

        acopio2.setFecha("21/10/2022");
        acopio2.setProveedor(90909);
        acopio2.setKls(200);
        acopio2.setTurno('T');

        acopio3.setFecha("20/10/2022");
        acopio3.setProveedor(90909);
        acopio3.setKls(200);
        acopio3.setTurno('T');

        lista.add(acopio1);
        lista.add(acopio2);
        lista.add(acopio3);

        double promedio = pagosService.promedioDiarioKls(lista);
        assertEquals(250,promedio);
    }


}
