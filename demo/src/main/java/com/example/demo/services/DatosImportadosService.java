package com.example.demo.services;

import com.example.demo.entities.DatosImportadosEntity;
import com.example.demo.repositories.DatosImportadosRepository;
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
public class DatosImportadosService {

    @Autowired
    private DatosImportadosRepository datosImportadosRepository;

    private final Logger logger = LoggerFactory.getLogger(DatosImportadosService.class);

    public ArrayList<DatosImportadosEntity> obtenerDatos(){
        return (ArrayList<DatosImportadosEntity>) datosImportadosRepository.findAll();
    }

    public void guardarData(DatosImportadosEntity datos){
        datosImportadosRepository.save(datos);
    }
    public void guardarDatosDb(String fecha, Character turno, Integer proveedor, Integer kls){
        DatosImportadosEntity newData = new DatosImportadosEntity();
        newData.setFecha(fecha);
        newData.setTurno(turno);
        newData.setProveedor(proveedor);
        newData.setKls(kls);
        guardarData(newData);
    }
    public void eliminarDatos(ArrayList<DatosImportadosEntity> data){
        datosImportadosRepository.deleteAll(data);
    }

    @Generated
    public String guardar(MultipartFile file){
        String filename = file.getOriginalFilename();
        if(filename != null){
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
    public void leerCsv(String direccion){
        String texto = "";
        BufferedReader bf = null;
        datosImportadosRepository.deleteAll();
        try{
            bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            int count = 1;

            while((bfRead = bf.readLine()) != null){
                if(count == 1){
                    count = 0;
                }else{
                    guardarDatosDb( bfRead.split(";")[0],
                                    bfRead.split(";")[1].charAt(0),
                                    parseInt(bfRead.split(";")[2]),
                                    parseInt(bfRead.split(";")[3]) );
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
