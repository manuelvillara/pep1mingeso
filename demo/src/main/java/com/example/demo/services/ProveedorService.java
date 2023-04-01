package com.example.demo.services;

import com.example.demo.entities.ProveedorEntity;
import com.example.demo.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired
    ProveedorRepository proveedorRepository;

    public ArrayList<ProveedorEntity> obtenerProveedores(){
        return (ArrayList<ProveedorEntity>) proveedorRepository.findAll();
    }

    public void ingresarProveedor(Integer codigo, String nombre,Character categoria, String retencion){
        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(codigo);
        proveedor.setNombre(nombre);
        proveedor.setCategoria(categoria);
        proveedor.setRetencion(retencion);

        proveedorRepository.save(proveedor);
    }

    public ProveedorEntity obtenerPorCodigo(Integer codigo){
        return proveedorRepository.findByCodigo(codigo);
    }

    public ProveedorEntity obtenerPorNombre(String nombre){
        return proveedorRepository.findByNombre(nombre);
    }

    /* No se si esto es necesario o esta bueno */
    public ProveedorEntity obtenerPorCategoria(Character categoria){
        return proveedorRepository.findByCategoria(categoria);
    }
}
