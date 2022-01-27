package com.managetruck.servicios;

import com.managetruck.entidades.Comprobante;
import com.managetruck.entidades.Foto;
import com.managetruck.entidades.Proveedor;
import com.managetruck.entidades.Transportista;
import com.managetruck.entidades.Usuario;
import com.managetruck.entidades.Viaje;
import com.managetruck.enumeracion.EstadoEnum;
import com.managetruck.errores.ErroresServicio;
import com.managetruck.repositorios.RepositorioComprobante;
import com.managetruck.repositorios.RepositorioProveedor;
import com.managetruck.repositorios.RepositorioTransportista;
import com.managetruck.repositorios.RepositorioUsuario;
import com.managetruck.repositorios.RepositorioViaje;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViajeServicio {

    @Autowired(required = true)
    NotificacionDeServicio notificacionServicio;
    @Autowired
    TransportistaServicio transportistaServicio;

    @Autowired(required = true)
    RepositorioViaje repositorioViaje;
    @Autowired
    RepositorioTransportista repositorioTransportista;
    @Autowired
    ComprobanteServicio comprobanteServicio;

    @Autowired
    RepositorioProveedor repositorioProveedor;

    @Autowired
    RepositorioComprobante repositorioComprobante;

    @Transactional
    public void crearViaje(String idProveedor, Integer peso, Integer kmRecorridos, String tipoCargas, String destino, String origen) throws ErroresServicio {
        ValidarViaje(peso, kmRecorridos, tipoCargas, destino, origen);
        Viaje viaje = new Viaje();
        viaje.setDestino(destino);
        viaje.setKmRecorridos(kmRecorridos);
        viaje.setOrigen(origen);
        viaje.setPeso(peso);
        viaje.setTipoCargas(tipoCargas);
        viaje.setListadoTransportista(new ArrayList()); //esto agrego a ver si la lista deja de salir nula
        Optional<Proveedor> proveedor = repositorioProveedor.findById(idProveedor);
        comprobanteServicio.crearComprobante(proveedor.get(), viaje);
        viaje.setEstado(EstadoEnum.ELEGIR);
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

    //metodo para generar que el trasnportista se una a la lista de posibles seleccionados
    @Transactional
    public void aplicar(String id_transportista, String id_viaje) throws ErroresServicio {
        Comprobante comprobante = comprobanteServicio.buscarComprobanteId(id_viaje);
        if (comprobante == null) {
            throw new ErroresServicio("No se ha encontrado el comprobante solicitado");
        } else {
            Optional<Viaje> viaje = repositorioViaje.findById(comprobante.getViaje().getID());
            if (viaje.isPresent()) {
                Optional<Transportista> transportista = repositorioTransportista.findById(id_transportista);
                if (transportista.isPresent()) {//si se cumplen la condiciones de que exita el viaje y el trasnportista
                    if (transportista.get().getCamion().getPesoMaximo() >= viaje.get().getPeso()) {
                        //metodo para verificar que el trasnportista no este ya agregado a la lista
                        verificacionListaTransportista(viaje.get().getID(),transportista.get().getId());
                        viaje.get().getListadoTransportista().add(transportista.get());
                        //notificacionServicio.enviar("TEXTO DE APLICACION A VIAJE", "NOMBRE DE LA PAGINA", transportista.get().getMail());
                    } else {
                        throw new ErroresServicio("la capacidad del camion no es la adecuada para aplicar a este viaje");
                    }
                } else {
                    throw new ErroresServicio("no se ha encontrado al trasnportista");
                }
            } else {
                throw new ErroresServicio("no se ha encontrado el viaje");
            }
        }

    }//metodo para que un transportista aplique a un viaje, lo agrega dentro de un array
    //luego el rpoveedor eligira entre todos los transportistas que haya en su viaje

    //metodo para mostrar la lista de trasnportistas que estan en condiciones de aceptar el viaje
    public List<Transportista> candidatosTrasnportistas(String id_viaje) throws ErroresServicio {
        List<Transportista> depurada = null;
        Optional<Viaje> viaje = repositorioViaje.findById(id_viaje);
        if (viaje.isPresent()) {
            //busco la lista completa para desmenuzar quien esta en condiciones de acpetar un viaje
            List<Transportista> completa = viaje.get().getListadoTransportista();
            for (Transportista transportista : completa) {
                //porque pense que el boolean es true cuando viaja y false cuando no
                if (transportista.isViajando()) {
                } else {
                    depurada.add(transportista);
                }
            }
        } else {
            throw new ErroresServicio("no se ha encontrado el viaje");
        }
        return depurada;
    }

    //metodo para traer los viajes del proveedor
    public List<Viaje> viajesCreadosProveedor(String proveedor_id) throws ErroresServicio {
        
        List<Comprobante> comprobantes = repositorioComprobante.buscarComprobanteporIdPorveedor(proveedor_id);
        if (comprobantes.isEmpty()) {          
            throw new ErroresServicio("Nose encontro ningun comprobante creado por este proveedor");
        }
       List<Viaje> viajes = new ArrayList();
        for (Comprobante comprobante : comprobantes) {  
            System.out.println(comprobante.getViaje().isAlta());
            if (comprobante.getViaje().isAlta()) {
                System.out.println("entrea cuando dice"+comprobante.getViaje().isAlta());
                viajes.add(comprobante.getViaje());
            }
            
        }

        return viajes;
    }
    //metodo para verificar que el trasnportista no este en la lista ya inscripto
    public void verificacionListaTransportista(String id_viaje,String id_transportista)throws ErroresServicio{
        Optional<Viaje> respuesta=repositorioViaje.findById(id_viaje);
        if (respuesta.isPresent()) {
            Viaje viaje= respuesta.get();
            for (Transportista transportista : viaje.getListadoTransportista()) {
                if (transportista.getId().equals(id_transportista)) {
                    throw new ErroresServicio("Ya estas registrado en este viaje");
                }
            }
        }
    }
    
    //metodo para devolver un viaje por su id
    public Viaje buscarViajeId(String id_viaje)throws ErroresServicio{
       Optional<Viaje> respuesta=repositorioViaje.findById(id_viaje);
        if (respuesta.isPresent()) {
            Viaje viaje= respuesta.get(); 
            return viaje;
        }else{
           throw new ErroresServicio("No se encontro ningun viaje con ese ID");
        }  
    }

}
