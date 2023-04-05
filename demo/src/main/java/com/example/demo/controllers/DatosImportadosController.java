package com.example.demo.controllers;

import com.example.demo.entities.DatosImportadosEntity;
import com.example.demo.services.DatosImportadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping
public class DatosImportadosController {

    @Autowired
    private DatosImportadosService guardarDatos;

    @GetMapping("/subir-archivo")
    public String main(){
        return "subir-archivo";
    }

    @PostMapping("/subir-archivo")
    public String subir(@RequestParam("file")MultipartFile file, RedirectAttributes redirectAttributes){
        guardarDatos.guardar(file);
        redirectAttributes.addFlashAttribute("mensaje","Se cargó correctamente el archivo");
        guardarDatos.leerCsv("Acopio.csv");
        return "redirect:/subir-archivo";
    }

    @GetMapping("/info-del-archivo")
    public String listarDatos(Model model){
        ArrayList<DatosImportadosEntity> data = guardarDatos.obtenerDatos();
        model.addAttribute("data",data);
        return "info-del-archivo";
    }
}
