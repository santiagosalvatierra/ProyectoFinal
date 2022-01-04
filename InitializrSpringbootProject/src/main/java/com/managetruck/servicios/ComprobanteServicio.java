/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.managetruck.servicios;

import com.managetruck.controllers.viajeController;
import com.managetruck.entidades.Comprobante;
import com.managetruck.entidades.Proveedor;
import com.managetruck.entidades.Transportista;
import com.managetruck.entidades.Viaje;
import com.managetruck.errores.ErroresServicio;
import com.managetruck.repositorios.RepositorioComprobante;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComprobanteServicio {

    @Autowired(required = true)
    RepositorioComprobante repositorioComprobante;
    
    @Transactional //cambio el metodo porque cuando se crea un comprobante no se puede valorar porque no esta hecho el viaje
    public void crearComprobante(Proveedor proveedor, Viaje viaje) throws ErroresServicio {
        validarComprobante(proveedor, viaje);
        Comprobante comprobante = new Comprobante();
        comprobante.setProveedor(proveedor);

        comprobante.setViaje(viaje);
        repositorioComprobante.save(comprobante);
    }

    @Transactional
    private void ModificarComprobante(String id, Integer valoracion, Proveedor proveedor, Viaje viaje) throws ErroresServicio {
        Optional<Comprobante> respuesta = repositorioComprobante.findById(id);
        validarComprobante(proveedor, viaje);

        if (respuesta.isPresent()) {
            Comprobante comprobante = respuesta.get();
                //para modificar el comprobante completo
                comprobante.setProveedor(proveedor);
                validarvaloracion(valoracion);
                comprobante.setValoracion(valoracion);
                comprobante.setViaje(viaje);
                repositorioComprobante.save(comprobante);
        }
    }
    public void ValorarTrasnportista(String id, Integer valoracion) throws ErroresServicio{
        Optional<Comprobante> respuesta = repositorioComprobante.findById(id);
        if (respuesta.isPresent()) {
            Comprobante comprobante = respuesta.get();
            if (comprobante.getViaje().isAlta() == false) {
                //para incorporar la valoracion al finalizar el viaje
                validarvaloracion(valoracion);
                comprobante.setValoracion(valoracion);
                repositorioComprobante.save(comprobante);
            } else {
                throw new ErroresServicio("El viaje no esta finalizado para poder valorarlo");
            }
        }
    }

    private void validarComprobante(Proveedor proveedor, Viaje viaje) throws ErroresServicio {
        if (proveedor == null) {
            throw new ErroresServicio("Debe ingresar un proveedor");
        }
        if (viaje == null) {
            throw new ErroresServicio("Debe ingresar un viaje");
        }
    }

    private void validarvaloracion(Integer valoracion) throws ErroresServicio {
        if (valoracion == null) {
            throw new ErroresServicio("Debe ingresar una valoracion");
        }
    }

    public Comprobante buscarComprobanteId(String id) throws ErroresServicio{

        Optional<Comprobante> respuesta = repositorioComprobante.findById(id);
        if (respuesta.isPresent()) {
            Comprobante comprobante = respuesta.get();
            return comprobante;
        }else{
            throw new ErroresServicio("El comprobante no se encontro");
        }

    }


    public Comprobante buscarComprobanteIdViaje(String id_viaje) throws ErroresServicio {
        Optional <Comprobante> respuesta = repositorioComprobante.buscarComprobanteporIdViaje(id_viaje);
        Comprobante comprobante;
        if (respuesta.isPresent()) {
            comprobante = respuesta.get();
            return comprobante;
        } else {
            throw new ErroresServicio("El comprobante no se encuentra asociado al viaje y al proveedor indicado");
        }
    }
    
}
