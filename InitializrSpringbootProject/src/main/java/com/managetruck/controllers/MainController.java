
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.managetruck.controllers;

import com.managetruck.entidades.Comprobante;
import com.managetruck.entidades.Proveedor;
import com.managetruck.entidades.Transportista;
import com.managetruck.entidades.Usuario;
import static com.managetruck.enumeracion.Role.Proveedor;
import static com.managetruck.enumeracion.Role.Transportista;
import com.managetruck.errores.ErroresServicio;
import com.managetruck.repositorios.RepositorioTransportista;
import com.managetruck.servicios.CamionServicio;
import com.managetruck.servicios.ComprobanteServicio;
import com.managetruck.servicios.FotoServicio;
import com.managetruck.servicios.NotificacionDeServicio;
import com.managetruck.servicios.ProveedorServicio;
import com.managetruck.servicios.TransportistaServicio;
import com.managetruck.servicios.ViajeServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    @Autowired
    RepositorioTransportista repositorioTransportista;

    @Autowired
    NotificacionDeServicio notificacionDeServicio;

    @Autowired
    ComprobanteServicio comprobanteServicio;

    @GetMapping("")
    public String index() {

        return "index";
    }

    @GetMapping("/login")
    public String loginUs(ModelMap model, @RequestParam(required = false) String error) throws ErroresServicio {
        if (error != null) {
            model.put("error", "El usuario o contraseña ingresada son incorrectas");
        }

        return "index";
    }

    @GetMapping("/inicio")
    public String inicio(ModelMap model, HttpSession session, @RequestParam(required = false) String error) {
        //tambien podemos usar un switch7inicio
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login.getRol().equals(Proveedor)) {
            List<Transportista> transportistas = transportistaServicio.listarTransportista();
            model.put("transportistas", transportistas);
            List<Transportista> transportistas2 = repositorioTransportista.buscarTransportistaPorZona(login.getZona());
            if (!transportistas2.isEmpty()) {
                model.addAttribute("tittle", "Listado Transportistas");
                model.addAttribute("transportistas2", transportistas2);
                if (error != null) {
                    model.put("error", "Usted no puede aplicar al viaje");
                }
            }

            return "indexEmpresa";
        } else if (login.getRol().equals(Transportista)) {
            List<Comprobante> comprobantes;
            try {
                comprobantes = comprobanteServicio.comprobantesAbiertos();
                if (!comprobantes.isEmpty()) {
                    model.put("comprobantes", comprobantes);
                }
                
                return "indexTransportista";
            } catch (ErroresServicio ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                
            }

        }
        return "index";
    }

    @GetMapping("/servicios")
    public String serviciosMT() {
        return "servicios";
    }

    @GetMapping("/contacto")
    public String contactoMG() {
        return "contacto";
    }

    @PostMapping("/contacto")
    public String contactar(String nombre, String apellido, String mail, String comentario) {
        notificacionDeServicio.contactar(comentario, mail);
        notificacionDeServicio.enviar("Su mensaje ha sido enviado y esta siendo procesado", "ContactUs", mail);
        return "index";
    }

    @GetMapping("/nosotros")
    public String nosotros() {
        return "nosotros";
    }

    @GetMapping("/logout")
    public String salir() {
        return "index";
    }
}
