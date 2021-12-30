package com.managetruck.servicios;


import com.managetruck.entidades.Foto;
import com.managetruck.entidades.Proveedor;
import com.managetruck.entidades.Transportista;
import com.managetruck.entidades.Usuario;
import com.managetruck.entidades.Viaje;
import com.managetruck.errores.ErroresServicio;
import com.managetruck.repositorios.RepositorioProveedor;
import com.managetruck.repositorios.RepositorioTransportista;
import com.managetruck.repositorios.RepositorioUsuario;
import com.managetruck.repositorios.RepositorioViaje;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViajeServicio {

    @Autowired(required = true)
    RepositorioViaje repositorioViaje;
    
    @Autowired
    ComprobanteServicio comprobanteServicio;
    
    @Autowired
    RepositorioProveedor repositorioProveedor;
    
    @Transactional

    public void crearViaje(String idProveedor,Integer peso, Integer kmRecorridos, String tipoCargas, String destino, String origen) throws ErroresServicio {
        ValidarViaje(peso, kmRecorridos, tipoCargas, destino, origen);
        Viaje viaje = new Viaje();
        viaje.setDestino(destino);
        viaje.setKmRecorridos(kmRecorridos);
        viaje.setOrigen(origen);
        viaje.setPeso(peso);
        viaje.setTipoCargas(tipoCargas);
        Optional <Proveedor> proveedor = repositorioProveedor.findById(idProveedor);
        comprobanteServicio.crearComprobante(proveedor.get(), viaje);
        repositorioViaje.save(viaje);
    }

    @Transactional
    public void ModificarViaje(String id, Integer peso, Integer kmRecorridos, String tipoCargas, String destino, String origen) throws ErroresServicio {
        Optional<Viaje> respuesta = repositorioViaje.findById(id);
        ValidarViaje(peso, kmRecorridos, tipoCargas, destino, origen);
        if (respuesta.isPresent()) {
            Viaje viaje = respuesta.get();
            viaje.setDestino(destino);
            viaje.setKmRecorridos(kmRecorridos);
            viaje.setOrigen(origen);
            viaje.setPeso(peso);
            viaje.setTipoCargas(tipoCargas);
            repositorioViaje.save(viaje);
        } else {
            throw new ErroresServicio("No se encontro el viaje solicitado");
        }
    }

    public void ValidarViaje(Integer peso, Integer kmRecorridos, String tipoCargas, String destino, String origen) throws ErroresServicio {
        if (peso == null) {
            throw new ErroresServicio("Debe ingresar un peso");
        }
        if (kmRecorridos == null) {
            throw new ErroresServicio("Debe ingresar los kilometros recorridos");
        }
        if (tipoCargas.isEmpty()) {
            throw new ErroresServicio("Debe ingresar el tipo de carga");
        }
        if (destino.isEmpty()) {
            throw new ErroresServicio("Debe ingresar un destino");
        }
        if (origen.isEmpty()) {
            throw new ErroresServicio("Debe ingresar un origen");
        }
    }

    
    @Transactional
    public void BajaViaje(String id) throws ErroresServicio {
        Optional<Viaje> respuesta = repositorioViaje.findById(id);

        if (respuesta.isPresent()) {
            Viaje viaje = respuesta.get();
            viaje.setAlta(false);
            repositorioViaje.save(viaje);
        } else {
            throw new ErroresServicio("No se encontro el viaje solicitado");
        }
    }

}
