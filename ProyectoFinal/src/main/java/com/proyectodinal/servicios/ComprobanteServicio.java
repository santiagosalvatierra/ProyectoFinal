/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyectodinal.servicios;

import com.proyectofinal.entidades.Camion;
import com.proyectofinal.entidades.Comprobante;
import com.proyectofinal.entidades.Proveedor;
import com.proyectofinal.entidades.Viaje;
import com.proyectofinal.errores.ErroresServicio;
import com.proyectofinal.repositorios.RepositorioComprobante;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComprobanteServicio {
    @Autowired
    private RepositorioComprobante repositorioComprobante;
 @Transactional
  public void crearComprobante(Integer valoracion, Proveedor proveedor, Viaje viaje) throws ErroresServicio {
  validarComprobante(valoracion, proveedor, viaje);
  Comprobante comprobante = new Comprobante();
  comprobante.setProveedor(proveedor);
  comprobante.setValoracion(valoracion);
  comprobante.setViaje(viaje);
  repositorioComprobante.save(comprobante);
  }
  @Transactional
  private void ModificarComprobante(String id,Integer valoracion, Proveedor proveedor, Viaje viaje)throws ErroresServicio{
  Optional<Comprobante> respuesta = repositorioComprobante.findById(id);
   validarComprobante(valoracion, proveedor, viaje);
      if (respuesta.isPresent()){
          Comprobante comprobante = respuesta.get();
          comprobante.setProveedor(proveedor);
  comprobante.setValoracion(valoracion);
  comprobante.setViaje(viaje);
   repositorioComprobante.save(comprobante);
  
      }
  }
    private void validarComprobante(Integer valoracion, Proveedor proveedor, Viaje viaje) throws ErroresServicio {
        if (valoracion ==null) {
            throw new ErroresServicio("Debe ingresar una valoracion");
        }
        if (proveedor ==null) {
            throw new ErroresServicio("Debe ingresar un proveedor");
        }
        if (viaje ==null) {
            throw new ErroresServicio("Debe ingresar un viaje");
        }
    }
    /*private void Valoracion(){
}*/
    
}
