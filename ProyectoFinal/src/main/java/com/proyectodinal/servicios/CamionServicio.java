/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyectodinal.servicios;

import com.proyectofinal.entidades.Camion;
import com.proyectofinal.entidades.Foto;
import com.proyectofinal.errores.ErroresServicio;
import com.proyectofinal.repositorios.RepositorioCamion;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CamionServicio {
//PROBANDO
    @Autowired(required = true)
    RepositorioCamion repositorioCamion;

    @Transactional
    public void crearCamion(Integer pesoMaximo, String modelo, Integer anio, String patente, Integer poliza, Foto foto) throws ErroresServicio {
        validarCamion(pesoMaximo, modelo, anio, patente, poliza);
        Camion camion = new Camion();
        camion.setPesoMaximo(pesoMaximo);
        camion.setModelo(modelo);
        camion.setAnio(anio);
        camion.setPatente(patente);
        camion.setPoliza(poliza);
        camion.setFoto(foto);
        repositorioCamion.save(camion);
    }

    public void validarCamion(Integer pesoMaximo, String modelo, Integer anio, String patente, Integer poliza/*, Foto foto*/) throws ErroresServicio {
        if (pesoMaximo == null) {
            throw new ErroresServicio("Debe ingresar un peso maximo");
        }
        if (modelo == null || modelo.isEmpty()) {
            throw new ErroresServicio("Debe ingresar un modelo");
        }
        if (anio == null) {
            throw new ErroresServicio("Debe ingresar un año de fabricacion");
        }
        if (patente == null || patente.isEmpty()) {
            throw new ErroresServicio("Debe ingresar una patante");
        }
        if (poliza == null) {
            throw new ErroresServicio("Debe ingresar una poliza");
        }
    }

    @Transactional
    public void modificarCamion(String id, Integer pesoMaximo, String modelo, Integer anio, String patente, Integer poliza, Foto foto) throws ErroresServicio {
        Optional<Camion> respuesta = repositorioCamion.findById(id);
        validarCamion(pesoMaximo, modelo, anio, patente, poliza);
        if (respuesta.isPresent()) {
            Camion camion = respuesta.get();
            camion.setPesoMaximo(pesoMaximo);
            camion.setModelo(modelo);
            camion.setAnio(anio);
            camion.setPatente(patente);
            camion.setPoliza(poliza);
            camion.setFoto(foto);//REVISAR
            repositorioCamion.save(camion);
        } else {
            throw new ErroresServicio("No se encontro el camion solicitado");
        }
    }
}
