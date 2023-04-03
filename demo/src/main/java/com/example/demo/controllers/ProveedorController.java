package com.example.demo.controllers;

import com.example.demo.entities.ProveedorEntity;
import com.example.demo.services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@RequestMapping
public class ProveedorController {

    @Autowired
    ProveedorService proveedorService;

    @GetMapping("/listar-proveedores")
    public String listarProveedores(Model model){
        ArrayList<ProveedorEntity>proveedores=proveedorService.obtenerProveedores();
        model.addAttribute("proveedores",proveedores);
        return "index";
    }

    @GetMapping("anadir-proveedores")
    public String proveedor(){
        return "anadir-proveedores";
    }
    @PostMapping("/anadir-proveedores")
    public String anadirProveedores( @RequestParam("codigo") Integer codigo,
                                     @RequestParam("nombre") String nombre,
                                     @RequestParam("categoria") Character categoria,
                                     @RequestParam("retencion") String retencion){
        proveedorService.ingresarProveedor(codigo, nombre, categoria, retencion);
        return "redirect:/anadir-proveedores";
    }
}
