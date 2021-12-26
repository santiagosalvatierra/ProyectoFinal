/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.managetruck.repositorios;

import com.managetruck.entidades.Camion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioCamion extends JpaRepository<Camion,String>{
    @Query("SELECT c FROM Camion c WHERE c.id = :id")
    public List <Camion> buscarCamionporId(@Param("id")String id);
    
    //query para buscar en la base de datos por el numero de patente
    @Query("SELECT c FROM Camion c WHERE c.patente = :patente")
    public List <Camion> buscarCamionporPatente(@Param("patente")String patente);
    
   //query para buscar a los camiones que estan habilitados
    @Query("SELECT c FROM Camion c WHERE c.alta = true")
    public List <Camion> buscarCamionporHabilitados();
    
    //query para buscar a los camiones deshabilitados
    @Query("SELECT c FROM Camion c WHERE c.alta = false")
    public List <Camion> buscarCamionporDeshabilitados();
}
