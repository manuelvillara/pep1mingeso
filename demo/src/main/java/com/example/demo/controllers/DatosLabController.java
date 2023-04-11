package com.example.demo.controllers;

import com.example.demo.entities.DatosImportadosEntity;
import com.example.demo.entities.DatosLabEntity;
import com.example.demo.services.DatosImportadosService;
import com.example.demo.services.DatosLabService;
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
public class DatosLabController {

    @Autowired
    private DatosLabService guardarDatosLab;

    @GetMapping("/subir-archivo-lab")
    public String main(){
        return "subir-archivo-lab";
    }

    @PostMapping("/subir-archivo-lab")
    public String subirLab(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
        guardarDatosLab.guardarArchivo(file);
        redirectAttributes.addFlashAttribute("mensaje","Se cargó correctamente el archivo");
        guardarDatosLab.leerCsvLab("LabAcopio.csv");
        return "redirect:/subir-archivo-lab";
    }

    @GetMapping("/info-archivo-lab")
    public String listarDatosLab(Model model){
        ArrayList<DatosLabEntity> data= guardarDatosLab.obtenerDatosLab();
        model.addAttribute("data",data);
        return "info-archivo-lab";
    }




}
