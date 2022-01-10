package com.managetruck.controllers;

import com.managetruck.entidades.Comprobante;
import com.managetruck.entidades.Transportista;
import com.managetruck.entidades.Usuario;
import com.managetruck.entidades.Viaje;
import com.managetruck.errores.ErroresServicio;
import com.managetruck.repositorios.RepositorioComprobante;
import com.managetruck.repositorios.RepositorioTransportista;
import com.managetruck.repositorios.RepositorioViaje;
import com.managetruck.servicios.TransportistaServicio;
import com.managetruck.servicios.ViajeServicio;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comprobante")
public class ComprobanteController {

    @Autowired
    TransportistaServicio transportistaServicio;

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
            transportistaServicio.asignacionTransportida(id_proveedor, id_viaje, id_transportista);
        } catch (ErroresServicio ex) {
            Logger.getLogger(ComprobanteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @GetMapping("/listarComprobantes")
    public String comprobanteListado(ModelMap modelo, HttpSession session) {
        Transportista transportista;
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        try {
            
            transportista = transportistaServicio.buscarID(login.getId());
            List<Comprobante> comprobantes = transportista.getComprobante();
            modelo.put("comprobantes", comprobantes);
            
        } catch (ErroresServicio ex) {
            Logger.getLogger(ComprobanteController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "comprobanteListado";
    }
}
