/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.managetruck.repositorios;

import com.managetruck.entidades.Provincias;
import com.managetruck.entidades.Transportista;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioProvincias extends JpaRepository<Provincias,String>{
    @Query("SELECT c FROM Provincias c WHERE c.id = :id")
    public List <Provincias> buscarProvinciaporId(@Param("id")String id);
    
    @Query("SELECT c FROM Provincias c WHERE c.provincia LIKE :provincia%")
    public List <Provincias> buscarProvinciaPorNombre(@Param("provincia")String provincia);
    
    //query para traer todas las provicnias
    @Query("SELECT c FROM Provincias c")
    public List <Provincias> buscarProvinciastotales();
}

