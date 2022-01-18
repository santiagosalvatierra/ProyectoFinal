/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.managetruck.repositorios;

import com.managetruck.entidades.Transportista;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioTransportista extends JpaRepository<Transportista, String> {
      @Query("SELECT c FROM Transportista c WHERE c.id = :id")
    public List <Transportista> buscarTransportistaporId(@Param("id")String id);
    
     @Query("SELECT c FROM Transportista c WHERE c.nombre LIKE :nombre")
    public Optional <Transportista> buscarTransportistaPorNombre(@Param("nombre")String nombre);
    
   @Query("SELECT c FROM Transportista c WHERE c.nombre LIKE :nombre%")
    public List <Transportista> buscarTransportistaPorNombre2(@Param("nombre")String nombre);
    
    @Query("SELECT c FROM Transportista c WHERE c.zona = :zona")
    public List <Transportista> buscarTransportistaPorZona(@Param("zona")String zona);
    
    //query para traer a los trasnportista que no hayan aceptado un viaje o lo hayan contratado
    @Query("SELECT c FROM Transportista c WHERE c.viajando = false")
    public List <Transportista> buscarTransportistaporViajando();
}
