package com.example.demo.repositories;

import com.example.demo.entities.DatosLabEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatosLabRepository extends JpaRepository<DatosLabEntity, Integer> {
}
