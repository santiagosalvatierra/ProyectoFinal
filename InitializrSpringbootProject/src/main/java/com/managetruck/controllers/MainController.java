/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.managetruck.controllers;

import com.managetruck.errores.ErroresServicio;
import com.managetruck.servicios.FotoServicio;
import com.managetruck.servicios.ProveedorServicio;
import com.managetruck.servicios.ViajeServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class MainController {
    
    @Autowired
    ProveedorServicio proveedorServicio;
    
    @GetMapping("")
    public String index(){
  
    return "index";
    }
    
    @PostMapping("")
    public String crear(@RequestParam String nombre,@RequestParam String apellido,MultipartFile archivo,@RequestParam String mail,@RequestParam String password,@RequestParam String zona,@RequestParam Integer telefono,@RequestParam String razonSocial,@RequestParam Integer cuilEmpresa,@RequestParam String nombreEmpresa) throws ErroresServicio{
        proveedorServicio.crearProveedor(nombre, apellido, mail, password,archivo, zona, telefono, razonSocial, cuilEmpresa, nombreEmpresa);
    return "index";
    }
}
