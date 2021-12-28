package com.managetruck.controllers;

import com.managetruck.entidades.Transportista;
import com.managetruck.errores.ErroresServicio;
import com.managetruck.repositorios.RepositorioTransportista;
import com.managetruck.servicios.TransportistaServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/transportista")
public class TransportistaController {

    @Autowired
    TransportistaServicio transportistaServicio;
    
    @Autowired
    RepositorioTransportista repositorioTransportista;


    @PostMapping("/registro")
    public String registroProveedor(ModelMap modelo, String nombre, String apellido, String mail, String password, MultipartFile archivo, String zona, String telefono) throws ErroresServicio {
        try {
            transportistaServicio.crearTransportista(nombre, apellido, mail, password, archivo, zona, telefono);
        } catch (ErroresServicio es) {
            modelo.put("error", es.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("mail", mail);
            modelo.put("password", password);
            modelo.put("archivo", archivo);
            modelo.put("zona", zona);
            modelo.put("telefono", telefono);
            return "registroTransportista";
        }
        return "redirect:/registroTransportista";
    }

    @GetMapping("/registro")
    public String mostrarPaginaRegistro() {
        return "registroTransportista";
    }

    @GetMapping("")
    public String listarTransportista(ModelMap modelo, @RequestParam(required = false) String error, @RequestParam(required = false) String nombre) {
        if (nombre != null) {
            List<Transportista> transportistas= repositorioTransportista.buscarTransportistaPorNombre2(nombre);
            modelo.addAttribute("tittle","Listado Transportistas");
            modelo.addAttribute("transportistas",transportistas);
        }else{
            List<Transportista> transportistas= repositorioTransportista.findAll();
            modelo.addAttribute("tittle","Listado Transportistas");
            modelo.addAttribute("transportistas",transportistas);
        }
        
            return "";
        }
    
}
