package com.managetruck.controllers;

import com.managetruck.entidades.Camion;
import com.managetruck.entidades.Comprobante;
import com.managetruck.entidades.Foto;
import com.managetruck.entidades.Provincias;
import com.managetruck.entidades.Transportista;
import com.managetruck.entidades.Usuario;
import com.managetruck.entidades.Viaje;
import com.managetruck.errores.ErroresServicio;
import com.managetruck.servicios.ComprobanteServicio;
import com.managetruck.servicios.ProvinciasServicio;
import com.managetruck.servicios.TransportistaServicio;
import com.managetruck.servicios.ViajeServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/viaje")
public class viajeController {

    @Autowired
    private ViajeServicio viajeServicio;
    @Autowired
    private TransportistaServicio transportistaServicio;
    @Autowired
    private ComprobanteServicio comprobanteServicio;
    
    @Autowired
    ProvinciasServicio provinciaServicio;

    @GetMapping("/pedido")
    public String inicioViaje(ModelMap modelo) {
        List<Provincias> provincias = provinciaServicio.listarProvinciasTotales();
        Viaje viaje = new Viaje();
        modelo.put("viaje", viaje);
        modelo.put("provincias",provincias);
        return "FormNuevaCarga";
    }

    @PostMapping("/pedido")
    public String comienzoViaje(HttpSession session,String idProveedor, String idViaje,@RequestParam String origen, @RequestParam String destino, @RequestParam String tipoCargas, @RequestParam Integer peso, @RequestParam Integer kmRecorridos) {
        Usuario login =(Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(idProveedor)) {
                return "redirect:/login";
            }
        try {
            
            if (idViaje.isEmpty()) {
                
              viajeServicio.crearViaje(idProveedor, peso, kmRecorridos, tipoCargas, destino, origen);  
            }else{
               
                viajeServicio.ModificarViaje(idViaje, peso, kmRecorridos, tipoCargas, destino, origen);
            }
            
        } catch (ErroresServicio ex) {
            Logger.getLogger(viajeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "indexEmpresa";
    }

    @GetMapping("/modificar-viaje")
    public String modificarViaje(@RequestParam(required = true) String id_viaje,ModelMap modelo) {        
        try {
            List<Provincias> provincias = provinciaServicio.listarProvinciasTotales();
            Viaje viaje = viajeServicio.buscarViajeId(id_viaje);
            modelo.put("viaje", viaje);
            modelo.put("provincias", provincias);
        } catch (ErroresServicio ex) {
            Logger.getLogger(viajeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "FormNuevaCarga";
    }

    @PostMapping("/modificar-viaje")
    public String cambiarViaje(HttpSession session, @RequestParam String id, @RequestParam Integer peso, @RequestParam Integer kmRecorridos, @RequestParam String tipoCargas, @RequestParam String destino, @RequestParam String origen) {

        try {
            //lohago para verificar que el provedor y el viaje estan en el mismo comprobante y poder usar 
            //el comprobante para verificar que el usuario que se conecto es el mismo
            Comprobante comprobante = comprobanteServicio.buscarComprobanteIdViaje(id);
            Usuario login = (Usuario) session.getAttribute("usuariosession");
            if (login == null || login.getId().equals(comprobante.getProveedor().getId())) {
                return "redirect:/login";
            }
            viajeServicio.ModificarViaje(id, peso, kmRecorridos, tipoCargas, destino, origen);
        } catch (ErroresServicio ex) {
            Logger.getLogger(viajeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @GetMapping("/finalizar")
    public String finalizarViaje(HttpSession session,@RequestParam(required = true) String id_viaje,ModelMap modelo) {
        System.out.println(id_viaje);
        try {
            Comprobante comprobante = comprobanteServicio.buscarComprobanteIdViaje(id_viaje);
            Usuario login = (Usuario) session.getAttribute("usuariosession");
            if (login == null || !login.getId().equals(comprobante.getProveedor().getId())) {
                return "redirect:/login";
            }
            viajeServicio.cambioEstado(id_viaje);
            //viajeServicio.BajaViaje2(id);
            //comprobanteServicio.ValorarTrasnportista(comprobante.getID(), valoracion);
            List<Viaje> viajes = viajeServicio.viajesCreadosProveedor(comprobante.getProveedor().getId());
            modelo.put("viajes",viajes);
            return "ListadoCargas";
        } catch (ErroresServicio ex) {
            Logger.getLogger(viajeController.class.getName()).log(Level.SEVERE, null, ex);
            return "redirect:/inicio";
        }
        
    }
    
    @GetMapping("/valorar")
    public String puntear (String id_comprobante){
        return null;
    }
    @PostMapping("/validar")
    public String finalizarPuntuacion(String id_comprobante, Integer valoracion){
        
        Comprobante comprobante;
        try {
            comprobante = comprobanteServicio.buscarComprobanteId(id_comprobante);
            comprobanteServicio.ValorarTrasnportista(comprobante.getID(), valoracion);
        } catch (ErroresServicio ex) {
            Logger.getLogger(viajeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    @GetMapping("/aplicar")
    public String aplicar(ModelMap model, @RequestParam(required = true)String id_transportista,@RequestParam(required = false) String error, @RequestParam(required = true)String id_viaje){
       
        try {
            viajeServicio.aplicar(id_transportista, id_viaje);
                  

        } catch (ErroresServicio ex) {
            Logger.getLogger(viajeController.class.getName()).log(Level.SEVERE, null, ex);
            model.put("error", "Usted no puede aplicar al viaje");
            return "redirect:/inicio";
        }
        
        return "redirect:/inicio";
    }
    @GetMapping("/listar-viajes")
    public String listarviajes(@RequestParam (required=true)String id,ModelMap modelo){
        try {
            
            List<Viaje> viajes = viajeServicio.viajesCreadosProveedor(id);
            System.out.println(viajes);
            modelo.put("viajes",viajes);
            return "ListadoCargas";
        } catch (ErroresServicio ex) {
            Logger.getLogger(viajeController.class.getName()).log(Level.SEVERE, null, ex);
            return "redirect:/inicio";
        }
        
    }
    @GetMapping("/listar-postulantes")
    public String postulantes(@RequestParam(required = true)String id_viaje, ModelMap modelo){
      
        try {
            Viaje viaje=viajeServicio.buscarViajeId(id_viaje);
            List<Transportista> postulantes = viaje.getListadoTransportista();
          
            modelo.put("id_viaje", id_viaje);
            modelo.put("transportistas", postulantes); 
            return "TransportistasPostulados";
        } catch (ErroresServicio ex){
            Logger.getLogger(viajeController.class.getName()).log(Level.SEVERE, null, ex);
            return "redirect:/inicio";
        }
    }
    @GetMapping("/perfil-aplicado")
    public String perfilAplicado(@RequestParam(required = true)String id_viaje, ModelMap modelo,Model model){
        try {
            Viaje viaje=viajeServicio.buscarViajeId(id_viaje);
            Transportista transportista= viaje.getTransportistaAplicado();
            Camion camion = transportista.getCamion();
            List<Foto> fotos = camion.getFoto();
            model.addAttribute("fotos", fotos);
            modelo.put("perfil", transportista);
            modelo.put("camion", camion);
            return "Perfil";
        } catch (ErroresServicio ex) {
            Logger.getLogger(viajeController.class.getName()).log(Level.SEVERE, null, ex);
            return "redirect:/inicio";
        }
    }
    @GetMapping("/eliminar")
    public String eliminarViaje(HttpSession session,@RequestParam(required = true)String id_viaje,ModelMap modelo){
        
        try {
            Comprobante comprobante = comprobanteServicio.buscarComprobanteIdViaje(id_viaje);
            Usuario login = (Usuario) session.getAttribute("usuariosession");
            if (login == null || !login.getId().equals(comprobante.getProveedor().getId())) {
                return "redirect:/login";
            }
            viajeServicio.BajaViaje(id_viaje);
            List<Viaje> viajes = viajeServicio.viajesCreadosProveedor(comprobante.getProveedor().getId());
            modelo.put("viajes",viajes);
            return "ListadoCargas";
        } catch (ErroresServicio ex) {
            Logger.getLogger(viajeController.class.getName()).log(Level.SEVERE, null, ex);
            return "redirect:/inicio";
        }        
    }
    
}
