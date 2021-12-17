package com.proyectodinal.servicios;

import com.proyectofinal.entidades.Viaje;
import com.proyectofinal.errores.ErroresServicio;
import com.proyectofinal.repositorios.RepositorioViaje;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViajeServicio {

    @Autowired
    private RepositorioViaje repositorioViaje;

    @Transactional

    public void crearViaje(Integer peso, Integer kmRecorridos, String tipoCargas, String destino, String origen) throws ErroresServicio {
        ValidarViaje(peso, kmRecorridos, tipoCargas, destino, origen);
        Viaje viaje = new Viaje();

        viaje.setDestino(destino);
        viaje.setKmRecorridos(kmRecorridos);
        viaje.setOrigen(origen);
        viaje.setPeso(peso);
        viaje.setTipoCargas(tipoCargas);
        // no hago setID por que es "uuid"
        //no hago setAlta por que ya es true desde el atributo de la entidad
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
        }
    }

}
