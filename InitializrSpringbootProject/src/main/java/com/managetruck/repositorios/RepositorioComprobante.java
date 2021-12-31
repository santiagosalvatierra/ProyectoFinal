/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.managetruck.repositorios;


import com.managetruck.entidades.Comprobante;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioComprobante extends JpaRepository<Comprobante, String> {
    @Query("SELECT c FROM Comprobante c WHERE c.id = :id")
    public List <Comprobante> buscarComprobanteporId(@Param("id")String id);

    @Query("SELECT c FROM Comprobante c WHERE c.viaje.id LIKE :viaje_id")
    public Optional <Comprobante> buscarComprobanteporIdViaje(@Param("viaje_id")String viaje_id);
}
