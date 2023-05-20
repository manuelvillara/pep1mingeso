
package com.example.demo;

import com.example.demo.entities.ProveedorEntity;
import com.example.demo.repositories.ProveedorRepository;
import com.example.demo.services.ProveedorService;
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
class ProveedorServiceTest {

    @Mock
    private ProveedorRepository proveedorRepository;

    @InjectMocks
    private ProveedorService proveedorService;

    @Before
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void obtenerProveedores() {
        // Lista vacia
        ArrayList<ProveedorEntity> proveedores = new ArrayList<>();
        when(proveedorRepository.findAll()).thenReturn(proveedores);

        ArrayList<ProveedorEntity> resultado = proveedorService.obtenerProveedores();

        assertEquals(0, resultado.size());
        verify(proveedorRepository, times(1)).findAll();
    }

    @Test
    void ingresarProveedor() {
        // Deberia guardar proveedor
        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(1);
        proveedor.setNombre("Gavi");
        proveedor.setCategoria('A');
        proveedor.setRetencion("Si");

        proveedorService.ingresarProveedor(1, "Gavi", 'A', "Si");

        verify(proveedorRepository, times(1)).save(proveedor);
    }
}






