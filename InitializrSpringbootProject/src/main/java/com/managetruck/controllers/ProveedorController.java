/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.managetruck.controllers;

import com.managetruck.errores.ErroresServicio;
import com.managetruck.servicios.ProveedorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/proveedor")
public class ProveedorController {
    @Autowired
    ProveedorServicio proveedorServicio;
    
    @PostMapping("/registro")
    public String registroProveedor(String nombre, String apellido,String mail,String password,MultipartFile foto,String zona,String telefono,String razonSocial,String cuilEmpresa, String nombreEmpresa) throws ErroresServicio{
       proveedorServicio.crearProveedor(nombre, apellido, mail, password, foto, zona, telefono, razonSocial, cuilEmpresa, nombreEmpresa);
        return "registroProveedor";
    }
    @GetMapping("/registro")
    public String mostrarPaginaRegistro(){
        return "registroProveedor";
    }
    
}
