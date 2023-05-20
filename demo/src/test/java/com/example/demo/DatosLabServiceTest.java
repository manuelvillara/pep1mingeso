package com.example.demo;

import com.example.demo.entities.DatosLabEntity;
import com.example.demo.repositories.DatosLabRepository;
import com.example.demo.services.DatosLabService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class DatosLabServiceTest {

    @Mock
    private DatosLabRepository datosLabRepository;

    @InjectMocks
    private DatosLabService datosLabService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void obtenerDatosLab1() {
        // Deberia retornar lista vacia cuando no hay datos
        when(datosLabRepository.findAll()).thenReturn(new ArrayList<>());

        ArrayList<DatosLabEntity> datosLab = datosLabService.obtenerDatosLab();

        assertEquals(0, datosLab.size());
        verify(datosLabRepository, times(1)).findAll();
    }

    @Test
    public void obtenerDatosLab2() {
        // Deberia retornar lista datos cuando existen datos
        DatosLabEntity dato1 = new DatosLabEntity(1, 1, 10, 20);
        DatosLabEntity dato2 = new DatosLabEntity(2, 2, 30, 40);
        ArrayList<DatosLabEntity> datos = new ArrayList<>();
        datos.add(dato1);
        datos.add(dato2);

        when(datosLabRepository.findAll()).thenReturn(datos);

        ArrayList<DatosLabEntity> datosLab = datosLabService.obtenerDatosLab();

        assertEquals(2, datosLab.size());
        assertEquals(dato1, datosLab.get(0));
        assertEquals(dato2, datosLab.get(1));
        verify(datosLabRepository, times(1)).findAll();
    }

    @Test
    void guardarDataLab() {
        // Deberia guardar datos
        DatosLabEntity datos = new DatosLabEntity();
        datosLabService.guardarDataLab(datos);
        verify(datosLabRepository, times(1)).save(datos);
    }

    @Test
    void guardarDatosLabDB() {
        // Deberia guardar datos
        Integer proveedor = 1;
        Integer grasa = 10;
        Integer solido = 20;

        DatosLabEntity newData = new DatosLabEntity();
        newData.setProveedor(proveedor);
        newData.setGrasa(grasa);
        newData.setSolido(solido);

        datosLabService.guardarDatosLabDB(proveedor, grasa, solido);
        verify(datosLabRepository, times(1)).save(newData);
    }


}
