package com.example.demo.services;

import com.example.demo.entities.DatosLabEntity;
import com.example.demo.repositories.DatosLabRepository;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

@Service
public class DatosLabService {

    @Autowired
    private DatosLabRepository datosLabRepository;

    private final Logger logger = LoggerFactory.getLogger(DatosImportadosService.class);

    public ArrayList<DatosLabEntity> obtenerDatosLab(){
        return (ArrayList<DatosLabEntity>) datosLabRepository.findAll();
    }

    public void guardarDataLab(DatosLabEntity datos){
        datosLabRepository.save(datos);
    }

    public void guardarDatosLabDB(Integer proveedor, Integer grasa, Integer solido){
        DatosLabEntity newData = new DatosLabEntity();
        newData.setProveedor(proveedor);
        newData.setGrasa(grasa);
        newData.setSolido(solido);
        guardarDataLab(newData);
    }

    @Generated
    public String guardarArchivo(MultipartFile file){
        String filenameLab = file.getOriginalFilename();
        if(filenameLab != null){
            if(!file.isEmpty()){
                try{
                    byte [] bytes = file.getBytes();
                    Path path = Paths.get(file.getOriginalFilename());
                    Files.write(path,bytes);
                    logger.info("Archivo guardado");
                }
                catch (IOException e) {
                    logger.error("ERROR", e);
                }
            }
            return "Se logró guardar el archivo";
        }
        else{
            return "No se logró guardar el archivo";
        }
    }

    @Generated
    public void leerCsvLab(String direccion){
        String texto = "";
        BufferedReader bf = null;
        datosLabRepository.deleteAll();
        try{
            bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            int count = 1;

            while((bfRead = bf.readLine()) != null){
                if(count == 1){
                    count = 0;
                }else{
                    guardarDatosLabDB( parseInt(bfRead.split(";")[0]),
                            parseInt(bfRead.split(";")[1]),
                            parseInt(bfRead.split(";")[2]) );
                    temp = temp + "\n" + bfRead;
                }
            }
            texto = temp;
            System.out.println("Se logró leer el archivo");
        }
        catch (Exception e){
            System.out.println("No se logró leer el archivo");
        }finally {
            if(bf != null){
                try{
                    bf.close();
                }catch(IOException e){
                    logger.error("Error",e);
                }
            }
        }
    }




}
