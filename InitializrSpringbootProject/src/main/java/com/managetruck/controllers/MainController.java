/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.managetruck.controllers;

import com.managetruck.entidades.Proveedor;
import com.managetruck.entidades.Transportista;
import com.managetruck.entidades.Usuario;
import static com.managetruck.enumeracion.Role.Proveedor;
import static com.managetruck.enumeracion.Role.Transportista;
import com.managetruck.errores.ErroresServicio;
import com.managetruck.servicios.CamionServicio;
import com.managetruck.servicios.FotoServicio;
import com.managetruck.servicios.ProveedorServicio;
import com.managetruck.servicios.TransportistaServicio;
import com.managetruck.servicios.ViajeServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import jdk.nashorn.internal.ir.BreakNode;
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
    
    @Autowired
    TransportistaServicio transportistaServicio;
    
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
    
    @GetMapping("/inicio")
    public String inicio(ModelMap model,HttpSession session){
        //tambien podemos usar un switch7inicio
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        System.out.println(login.getRol());
        if (login.getRol().equals(Proveedor)) {
            List <Transportista> transportistas = transportistaServicio.listarTransportista();
            model.put("transportistas", transportistas);
            return "indexEmpresa";
        }else if(login.getRol().equals(Transportista)){
            List <Proveedor> proveedores = proveedorServicio.listarProveedor();
            model.put("proveedores", proveedores);
            return "indexTransportista";
        }
        return "index";
    }
    
}
