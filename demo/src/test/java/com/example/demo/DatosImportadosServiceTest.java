
package com.example.demo;

import com.example.demo.entities.DatosImportadosEntity;
import com.example.demo.repositories.DatosImportadosRepository;
import com.example.demo.services.DatosImportadosService;
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
class DatosImportadosServiceTest {

    @Mock
    private DatosImportadosRepository datosImportadosRepository;

    @InjectMocks
    private DatosImportadosService datosImportadosService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void obtenerDatos(){
        // Deberia Retornar Lista Vacia
        ArrayList<DatosImportadosEntity> datos = new ArrayList<>();
        when(datosImportadosRepository.findAll()).thenReturn(datos);

        ArrayList<DatosImportadosEntity> resultado = datosImportadosService.obtenerDatos();

        assertEquals(0, resultado.size());
        verify(datosImportadosRepository, times(1)).findAll();
    }

    @Test
    void guardarData(){
        // Deberia Guardar Datos
        DatosImportadosEntity datos = new DatosImportadosEntity();
        when(datosImportadosRepository.save(datos)).thenReturn(datos);

        datosImportadosService.guardarData(datos);

        verify(datosImportadosRepository, times(1)).save(datos);
    }

    @Test
    void guardarDatosDb() {
        // DeberiaGuardarDatos
        String fecha = "2023/05/19";
        Character turno = 'M';
        Integer proveedor = 1;
        Integer kls = 100;

        datosImportadosService.guardarDatosDb(fecha, turno, proveedor, kls);

        DatosImportadosEntity newData = new DatosImportadosEntity();
        newData.setFecha(fecha);
        newData.setTurno(turno);
        newData.setProveedor(proveedor);
        newData.setKls(kls);

        verify(datosImportadosRepository, times(1)).save(newData);
    }

}


