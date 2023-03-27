package com.example.demo.repositories;

import com.example.demo.entities.ProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

@Repository
public interface ProveedorRepository extends JpaRepository<ProveedorEntity, Long> {

    @Query("select e from ProveedorEntity e where e.codigo =: codigo")
    ProveedorEntity findByCodigo(@Param("codigo") Integer codigo);

    @Query("select e from ProveedorEntity e where e.nombre =: nombre")
    ProveedorEntity findByNombre(@Param("nombre") String nombre);

    @Query("select e from ProveedorEntity e where e.categoria =: categoria")
    ProveedorEntity findByCategoria(@Param("categoria") Character categoria);


}
