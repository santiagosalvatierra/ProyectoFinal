/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyectofinal.repositorios;

import com.proyectofinal.entidades.Comprobante;
import com.proyectofinal.entidades.Foto;
import com.proyectofinal.entidades.Transportista;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface RepositorioFoto extends JpaRepository<Foto, String>{
     @Query("SELECT c FROM Foto c WHERE c.id = :id")
    public List <Foto> buscarFotoporId(@Param("id")String id);
}
