
package com.managetruck.controllers;

import com.managetruck.errores.ErroresServicio;
import com.managetruck.servicios.TransportistaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/transportista")
public class TransportistaController {
    @Autowired
    TransportistaServicio transportistaServicio;
    
    @PostMapping("/registro")
    public String registroProveedor(String nombre, String apellido, String mail, String password, MultipartFile archivo, String zona, String telefono) throws ErroresServicio{
        transportistaServicio.crearTransportista(nombre, apellido, mail, password, archivo, zona, telefono);
        return "registroTransportista";
    }
    @GetMapping("/registro")
    public String mostrarPaginaRegistro(){
        return "registroTransportista";
    }
}
