
package com.managetruck.controllers;

import com.managetruck.entidades.Usuario;
import com.managetruck.errores.ErroresServicio;
import com.managetruck.servicios.UsuarioServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @PostMapping("/cambio-password")
    public String cambiocontrasenia(HttpSession session,@RequestParam(required = true) String id,String claveVieja, String claveNueva, String claveNueva1,ModelMap modelo){
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/login";
        }
        try {
            System.out.println("esta por entrar al try de cambio de pass");
            usuarioServicio.modificarContrasena(id,claveNueva,claveNueva1,claveVieja);
        } catch (ErroresServicio ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
            modelo.put("error", ex.getMessage());
            return "redirect:"+usuarioServicio.determinarClase(login);
        }
        return "redirect:/inicio";
    }
}
