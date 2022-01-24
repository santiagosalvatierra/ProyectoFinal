/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.managetruck.controllers;

import com.managetruck.entidades.Transportista;
import com.managetruck.errores.ErroresServicio;
import com.managetruck.servicios.CamionServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/camion")
public class CamionController {

    @Autowired
    CamionServicio camionServicio;

    @GetMapping("/crear")
    public String crearCamion() {

        return "crearCamion";
    }

    @PostMapping("/crear")
    public String crearCamion(@RequestParam Integer pesoMaximo, String descripcion, @RequestParam String modelo, Integer anio, @RequestParam String patente, @RequestParam Integer poliza, List<MultipartFile> fotos) throws ErroresServicio {
        camionServicio.crearCamion(pesoMaximo, modelo, descripcion, anio, patente, poliza, fotos);
        return "crearCamion";
    }
    @PostMapping("/modificar")
    public String crearCamion(HttpSession session,String id,@RequestParam Integer pesoMaximo, String descripcion, @RequestParam String modelo, Integer anio, @RequestParam String patente, @RequestParam Integer poliza, List<MultipartFile> fotos) throws ErroresServicio {
        //metodo para revisar que sea la misma persona hay que testearlo
        Transportista login = (Transportista) session.getAttribute("usuariosession");
        if (login == null || login.getId().equals(id)) {
            return "redirect:/login";
        }
        camionServicio.modificarCamion(id, pesoMaximo, modelo, descripcion, anio, patente, poliza, fotos, Boolean.FALSE);
        return "crearCamion";//no debe retornar esa pagina, tenemos que ver si creamos una vista o hacemos un modal
        
    }
}
