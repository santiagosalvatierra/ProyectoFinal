/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.managetruck.servicios;


import com.managetruck.entidades.Comprobante;
import com.managetruck.entidades.Proveedor;
import com.managetruck.entidades.Viaje;
import com.managetruck.errores.ErroresServicio;
import com.managetruck.repositorios.RepositorioComprobante;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComprobanteServicio {

    @Autowired(required = true)
    RepositorioComprobante repositorioComprobante;

    @Transactional
    public void crearComprobante(Integer valoracion, Proveedor proveedor, Viaje viaje) throws ErroresServicio {
        validarComprobante(valoracion, proveedor, viaje);
        Comprobante comprobante = new Comprobante();
        comprobante.setProveedor(proveedor);
        if (viaje.isAlta()) {
            throw new ErroresServicio("No puede emitir una valoracion si el viaje aun esta en proceso");
        }else{
            comprobante.setValoracion(valoracion);
        }
        comprobante.setViaje(viaje);
        repositorioComprobante.save(comprobante);
    }

    @Transactional
    private void ModificarComprobante(String id, Integer valoracion, Proveedor proveedor, Viaje viaje) throws ErroresServicio {
        Optional<Comprobante> respuesta = repositorioComprobante.findById(id);
        validarComprobante(valoracion, proveedor, viaje);
        if (respuesta.isPresent()) {
            Comprobante comprobante = respuesta.get();
            comprobante.setProveedor(proveedor);
            comprobante.setValoracion(valoracion);
            comprobante.setViaje(viaje);
            repositorioComprobante.save(comprobante);

        }
    }

    private void validarComprobante(Integer valoracion, Proveedor proveedor, Viaje viaje) throws ErroresServicio {
        if (valoracion == null) {
            throw new ErroresServicio("Debe ingresar una valoracion");
        }
        if (proveedor == null) {
            throw new ErroresServicio("Debe ingresar un proveedor");
        }
        if (viaje == null) {
            throw new ErroresServicio("Debe ingresar un viaje");
        }
    }
    /*private void Valoracion(){
}*/

}
