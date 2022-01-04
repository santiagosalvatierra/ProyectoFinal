package com.managetruck.controllers;

import com.managetruck.entidades.Comprobante;
import com.managetruck.entidades.Transportista;
import com.managetruck.entidades.Viaje;
import com.managetruck.errores.ErroresServicio;
import com.managetruck.repositorios.RepositorioComprobante;
import com.managetruck.repositorios.RepositorioTransportista;
import com.managetruck.repositorios.RepositorioViaje;
import com.managetruck.servicios.ViajeServicio;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comprobante")
public class ComprobanteController {

    @Autowired
    private ViajeServicio viajeServicio;
    @Autowired
    private RepositorioViaje repositorioViaje;
    @Autowired
    private RepositorioComprobante repositorioComprobante;
    @Autowired
    private RepositorioTransportista repositorioTransportista;

    @GetMapping("/mostrarTranspostistas")//muestra los transportistas que aplicaron a un viaje en especifico
    public String mostrarTranspostistas(Model model, String id_viaje) {
        Optional<Viaje> viaje = repositorioViaje.findById(id_viaje);
        List<Transportista> listadoTransportistas = viaje.get().getListadoTransportista();
        model.addAttribute("tittle", "Listado Transportistas");
        model.addAttribute("listadoTransportistas", listadoTransportistas);
        return null;
    }

    @PostMapping("/votacion")//proveedor elije el transportista que va a ser responsable del viaje
    public String votacion(String id_proveedor, String id_transportista, String id_viaje) throws ErroresServicio {
        try {
            //buscamos el comprobante con el id del viaje

            Optional<Comprobante> comprobante = repositorioComprobante.buscarComprobanteporIdViaje(id_viaje);
            //comprueba que el id del proveedor sea igual al id del proveedor que creo el, comprobante
            if (comprobante.isPresent()) {
                if (id_proveedor.equals(comprobante.get().getProveedor().getId())) {
                    Optional<Transportista> transportista = repositorioTransportista.findById(id_transportista);
                    if(transportista.isPresent()){
                    transportista.get().getComprobante().add(comprobante);
                    }else{
                         throw new ErroresServicio("El transportista no existe o no se pudo encontrar");
               
                    }
                    //busca el transportista y le setea el comprobante
                } else {
                    throw new ErroresServicio("Usted no es el proveedor que creo el viaje, no puede elejir el transportista");
                }
            } else {
                throw new ErroresServicio("El comprobante no existe o no se pudo encontrar");

            }

        } catch (ErroresServicio ex) {
            Logger.getLogger(ComprobanteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
