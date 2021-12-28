/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.managetruck.repositorios;

import com.managetruck.entidades.Proveedor;
import com.managetruck.entidades.Transportista;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioProveedor extends JpaRepository<Proveedor, String> {
      @Query("SELECT c FROM Proveedor c WHERE c.id = :id")
    public List <Proveedor> buscarProveedorporId(@Param("id")String id);
    
     
     @Query("SELECT c FROM Transportista c WHERE c.nombre LIKE :nombre")
    public Optional <Proveedor> buscarProveedorPorNombre(@Param("nombre")String nombre);
    
   @Query("SELECT c FROM Transportista c WHERE c.nombre LIKE :nombre%")
    public List <Proveedor> buscarProveedorPorNombre2(@Param("nombre")String nombre);
}
