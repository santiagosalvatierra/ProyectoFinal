/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.managetruck.repositorios;

import com.managetruck.entidades.Viaje;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioViaje extends JpaRepository <Viaje,String>{
    
    @Query("SELECT c FROM Viaje c WHERE c.id = :id")
    public List <Viaje> buscarViajeporId(@Param("id")String id);
    
}
