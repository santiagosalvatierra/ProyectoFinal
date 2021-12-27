
package com.managetruck.controllers;

import com.managetruck.errores.ErroresServicio;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/transportista")
public class TransportistaController {
    
    @GetMapping("")
    public String transportista(){
  
    return "transportista.html";
    }
    @PostMapping("/registro")
    public String registroProveedor() throws ErroresServicio{
        return "registroTransportista";
    }
    @GetMapping("/registro")
    public String mostrarPaginaRegistro(){
        return "registroTransportista";
    }
}
