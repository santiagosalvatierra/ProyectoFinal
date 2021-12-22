/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.managetruck.servicios;

import com.managetruck.entidades.Camion;
import com.managetruck.entidades.Foto;
import com.managetruck.errores.ErroresServicio;
import com.managetruck.repositorios.RepositorioCamion;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CamionServicio {
//PROBANDO
    @Autowired(required = true)
    RepositorioCamion repositorioCamion;
    
    @Autowired
    FotoServicio fotoServicio;

    @Transactional
    public void crearCamion(Integer pesoMaximo, String modelo, Integer anio, String patente, Integer poliza,List <MultipartFile> archivos) throws ErroresServicio {
        List<Foto> fotos = new ArrayList<>();
        for (MultipartFile archivo : archivos) {
            fotos.add(fotoServicio.guardar(archivo));
        }
        validarCamion(pesoMaximo, modelo, anio, patente, poliza);
        Camion camion = new Camion();
        camion.setPesoMaximo(pesoMaximo);
        camion.setModelo(modelo);
        camion.setAnio(anio);
        camion.setPatente(patente);
        camion.setPoliza(poliza);
        camion.setFoto(fotos);
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
    public void modificarCamion(String id, Integer pesoMaximo, String modelo, Integer anio, String patente, Integer poliza, List <MultipartFile> archivos) throws ErroresServicio {
        List<Foto> fotos = new ArrayList<>();
        for (MultipartFile archivo : archivos) {
            fotos.add(fotoServicio.guardar(archivo));
        }
        Optional<Camion> respuesta = repositorioCamion.findById(id);
        validarCamion(pesoMaximo, modelo, anio, patente, poliza);
        if (respuesta.isPresent()) {
            Camion camion = respuesta.get();
            camion.setPesoMaximo(pesoMaximo);
            camion.setModelo(modelo);
            camion.setAnio(anio);
            camion.setPatente(patente);
            camion.setPoliza(poliza);
            camion.setFoto(fotos);//REVISAR
            repositorioCamion.save(camion);
        } else {
            throw new ErroresServicio("No se encontro el camion solicitado");
        }
    }
}
