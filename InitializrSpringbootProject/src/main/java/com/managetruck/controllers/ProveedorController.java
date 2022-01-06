/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.managetruck.controllers;

import com.managetruck.entidades.Usuario;
import com.managetruck.errores.ErroresServicio;
import com.managetruck.servicios.ProveedorServicio;
import javax.servlet.http.HttpSession;
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
    public String registroProveedor(String nombre, String apellido,String mail,String password,String password2,MultipartFile foto,String zona,String telefono,String razonSocial,String cuilEmpresa, String nombreEmpresa) throws ErroresServicio{
        
        proveedorServicio.crearProveedor(nombre, apellido, mail, password,password2, foto, zona, telefono, razonSocial, cuilEmpresa, nombreEmpresa);
        return "registroProveedor";
    }
    @GetMapping("/registro")
    public String mostrarPaginaRegistro(){
        return "empresaForm";
    }
    
    @GetMapping("/modificar-proveedor")
    public String modificarProveedor(){
        return null;
    }
    
    @PostMapping("/modificar-proveedor")
    public String modificacionProveedor(HttpSession session,String id){
        //verificacion de que el usuario que esta modificando sea el mismo que va a modificar
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || login.getId().equals(id)) {
            return "redirect:/login";
        }
        return null;
    }
}
