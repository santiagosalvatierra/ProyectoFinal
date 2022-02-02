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
import com.managetruck.enumeracion.EstadoEnum;
import static com.managetruck.enumeracion.EstadoEnum.ELEGIR;
import static com.managetruck.enumeracion.Role.Transportista;
import com.managetruck.errores.ErroresServicio;
import com.managetruck.repositorios.RepositorioComprobante;
import java.util.ArrayList;
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
                comprobante.getViaje().setEstado(EstadoEnum.FINALIZADA);
                repositorioComprobante.save(comprobante);
            } else {
                throw new ErroresServicio("El viaje no esta finalizado para poder valorarlo");
            }
        }
    }
    public List buscarComprobantePorProveedor(String nombre) throws ErroresServicio{
        List <Comprobante> comprobantes = repositorioComprobante.buscarComprobanteporNombrePorveedor(nombre);
        if (!comprobantes.isEmpty()) {
            return comprobantes;
            
        }else{
            comprobantes = comprobantesAbiertos();
            return comprobantes;
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
    //METODO PARA TRAER TODOS LOS COMPROBANTES ABIERTOS PARA MOSTRAR AL TRANSPORTISTA
    public List<Comprobante> comprobantesAbiertos()throws ErroresServicio{
        
        List<Comprobante> comprobantes = repositorioComprobante.buscarComprobanteporAbiertos(new Viaje().getEstado().ELEGIR);
        
           
            return comprobantes;
        
        
    }
    //Metodo para borrar comprobantes
    @Transactional
    public void eliminarComprobantes(String idViaje)throws ErroresServicio{
        Optional<Comprobante> comprobantes = repositorioComprobante.buscarComprobanteporIdViaje(idViaje);
       
        if (comprobantes.isPresent()) {
            Comprobante comprobante = comprobantes.get();
            repositorioComprobante.delete(comprobante);
            
            
        }else {
        throw new ErroresServicio("El comprobante no se pudo comprar");
         }
    }
    public Comprobante buscarComprobanteAbierto(List<Comprobante> comprobantes)throws ErroresServicio{
        System.out.println("el numero de elementos de la lista es "+comprobantes.size());
        Comprobante comprobante1 = null;
        for (Comprobante comprobante : comprobantes) {
            System.out.println("comprobaante valoracion es = "+comprobante.getValoracion());
                if (comprobante.getValoracion()==0) {
                    comprobante1 = comprobante;
                    System.out.println("el comprobante= "+comprobante1);
                }
            }
        System.out.println("el comprobante1 es= "+comprobante1);
        return comprobante1;
    }
}
