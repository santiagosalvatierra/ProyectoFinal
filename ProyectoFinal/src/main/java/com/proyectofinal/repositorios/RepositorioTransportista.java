/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyectofinal.repositorios;

import com.proyectofinal.entidades.Proveedor;
import com.proyectofinal.entidades.Transportista;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioTransportista extends JpaRepository<Transportista, String> {
      @Query("SELECT c FROM Transportista c WHERE c.id = :id")
    public List <Transportista> buscarTransportistaporId(@Param("id")String id);
}
