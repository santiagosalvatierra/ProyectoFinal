/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.managetruck.servicios;

import com.managetruck.entidades.Camion;
import com.managetruck.entidades.Foto;
import com.managetruck.entidades.Transportista;
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

    @Autowired(required = true)
    RepositorioCamion repositorioCamion;

    @Autowired
    FotoServicio fotoServicio;

    @Autowired
    private TransportistaServicio transportistaServicio;

    @Transactional
    public Camion crearCamion(Integer pesoMaximo, String modelo, String descripcion, Integer anio, String patente, Integer poliza, List<MultipartFile> archivos) throws ErroresServicio {
        List<Foto> fotos = new ArrayList<>();
        for (MultipartFile archivo : archivos) {
            fotos.add(fotoServicio.guardar(archivo));
        }
        validarCamion(pesoMaximo, modelo, anio, patente, poliza);
        Camion camion = new Camion();
        camion.setDescripcion(descripcion);
        camion.setPesoMaximo(pesoMaximo);
        camion.setModelo(modelo);
        camion.setAnio(anio);
        camion.setPatente(patente);
        camion.setPoliza(poliza);
        camion.setAlta(true);
        camion.setFoto(fotos);
        repositorioCamion.save(camion);
        return camion;
    }

    public void validarCamion(Integer pesoMaximo, String modelo, Integer anio, String patente, Integer poliza) throws ErroresServicio {
        validarCamion2(pesoMaximo, modelo, anio, patente, poliza);
        buscarCamionPatente(patente);
    }

    public void validarCamion2(Integer pesoMaximo, String modelo, Integer anio, String patente, Integer poliza) throws ErroresServicio {
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
    public void modificarCamion(String id_trasnportista, String id, Integer pesoMaximo, String modelo, String descripcion, Integer anio, String patente, Integer poliza, List<MultipartFile> archivos) throws ErroresServicio {
        Transportista transporista = transportistaServicio.buscarID(id_trasnportista);
        if (transporista.getCamion().getID().equals(id)) {
            Optional<Camion> respuesta = repositorioCamion.findById(id);
            validarCamion2(pesoMaximo, modelo, anio, patente, poliza);
            if (respuesta.isPresent()) {
                Camion camion = respuesta.get();
                camion.setDescripcion(descripcion);
                camion.setPesoMaximo(pesoMaximo);
                camion.setModelo(modelo);
                camion.setAnio(anio);
                if (id.equals(verificarCamionPatente(patente))|| verificarCamionPatente(patente).equals("") ) {
                    camion.setPatente(patente);  
                } 
                camion.setPoliza(poliza);
                //camion.setAlta(alta);
                if (archivos != null && !archivos.isEmpty()) {
                    List<Foto> fotos = new ArrayList<>();
                    for (MultipartFile archivo : archivos) {                      
                        fotos.add(fotoServicio.guardar(archivo));                    
                    }
                    camion.setFoto(fotos);
                }
                repositorioCamion.save(camion);
            }

        } else {
            throw new ErroresServicio("No se encontro el camion solicitado");
        }
    }

    //metodo para deshabilitar un camion
    @Transactional
    public void deshabilitarCamion(String id) throws ErroresServicio {
        Camion camion = buscarCamionId(id);
        camion.setAlta(false);
        repositorioCamion.save(camion);
    }

    //metodo para habilitar un camion
    @Transactional
    public void habilitarCamion(String id) throws ErroresServicio {
        Camion camion = buscarCamionId(id);
        camion.setAlta(true);
        repositorioCamion.save(camion);
    }

    //metodo para buscar un camion por id
    public Camion buscarCamionId(String id) throws ErroresServicio {
        Optional<Camion> respuesta = repositorioCamion.findById(id);
        if (respuesta.isPresent()) {
            Camion camion = respuesta.get();
            return camion;
        } else {
            throw new ErroresServicio("No se encontro el camion");
        }
    }

    //metodo para comprobar que el camion no exista mediante la patente
    public void buscarCamionPatente(String patente) throws ErroresServicio {
        List<Camion> respuesta = repositorioCamion.buscarCamionporPatente(patente);
        if (!respuesta.isEmpty()) {
            Camion camion = respuesta.get(0);
            throw new ErroresServicio("El numero de patente ya esta asociado a otro camion");
        }
    }
    
    public String verificarCamionPatente(String patente) throws ErroresServicio {
        String id_camion="";
        List<Camion> respuesta = repositorioCamion.buscarCamionporPatente(patente);
        if (!respuesta.isEmpty()) {
            Camion camion = respuesta.get(0);
            id_camion=camion.getID();
        }
        return id_camion;
    }
}
