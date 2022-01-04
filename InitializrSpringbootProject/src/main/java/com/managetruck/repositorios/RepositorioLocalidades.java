/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.managetruck.repositorios;

import com.managetruck.entidades.Localidades;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioLocalidades  extends JpaRepository<Localidades,String> {
    
       @Query("SELECT c FROM Localidades c WHERE c.id = :id")
    public List <Localidades> buscarLocalidadporId(@Param("id")String id);
    
       @Query("SELECT c FROM Localidades c WHERE c.provincias.id = :id")
    public List <Localidades> buscarLocalidadporIddeProvincia(@Param("id")String id);
    
}
