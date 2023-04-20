package com.example.demo.repositories;

import com.example.demo.entities.PagosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagosRepository extends JpaRepository<PagosEntity, Integer> {
}
