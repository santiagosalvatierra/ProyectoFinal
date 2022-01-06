/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.managetruck.controllers;

import com.managetruck.errores.ErroresServicio;
import com.managetruck.servicios.CamionServicio;
import com.managetruck.servicios.FotoServicio;
import com.managetruck.servicios.ProveedorServicio;
import com.managetruck.servicios.ViajeServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    CamionServicio camionServicio;
    
    @Autowired
    ProveedorServicio proveedorServicio;
    
    @GetMapping("")
    public String index() {

        return "index";
    }

    

    @GetMapping("/login")
    public String loginUs(ModelMap model, @RequestParam(required = false) String error) throws ErroresServicio {
        if (error != null) {
            model.put("error", "El usuario o contraseña ingresada son incorrectas");

        }

        return "login";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_Proveedor')")
    @GetMapping("/inicio")
    public String inicio(){
        
       return"inicio";
    }
    @GetMapping("/fragmentos")
    public String Fragmentos(){
        return"fragments.html";
    }
}
