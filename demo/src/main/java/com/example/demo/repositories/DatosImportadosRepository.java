package com.example.demo.repositories;

import com.example.demo.entities.DatosImportadosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatosImportadosRepository extends JpaRepository<DatosImportadosEntity, Integer> {
}
