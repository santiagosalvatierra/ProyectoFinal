package com.managetruck.controllers;

import com.managetruck.entidades.Camion;
import com.managetruck.entidades.Comprobante;
import com.managetruck.entidades.Foto;
import com.managetruck.entidades.Proveedor;
import com.managetruck.entidades.Provincias;
import com.managetruck.entidades.Transportista;
import com.managetruck.entidades.Usuario;
import com.managetruck.entidades.Viaje;
import com.managetruck.errores.ErroresServicio;
import com.managetruck.servicios.ComprobanteServicio;
import com.managetruck.servicios.NotificacionDeServicio;
import com.managetruck.servicios.ProveedorServicio;
import com.managetruck.servicios.ProvinciasServicio;
import com.managetruck.servicios.TransportistaServicio;
import com.managetruck.servicios.ViajeServicio;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private ProveedorServicio proveedorServicio;
    @Autowired
    private ViajeServicio viajeServicio;
    @Autowired
    private TransportistaServicio transportistaServicio;
    @Autowired
    private ComprobanteServicio comprobanteServicio;
     @Autowired
    private NotificacionDeServicio notif;
    @Autowired
    ProvinciasServicio provinciaServicio;
    
    @PreAuthorize("hasRole('ROLE_Proveedor')")
    @GetMapping("/pedido")
    public String inicioViaje(ModelMap modelo) {
        List<Provincias> provincias = provinciaServicio.listarProvinciasTotales();
        Viaje viaje = new Viaje();
        modelo.put("viaje", viaje);
        modelo.put("provincias",provincias);
        return "FormNuevaCarga";
    }
    @PreAuthorize("hasRole('ROLE_Proveedor')")
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
        return "redirect:/inicio";
    }
    @PreAuthorize("hasRole('ROLE_Proveedor')")
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
    @PreAuthorize("hasRole('ROLE_Proveedor')")
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
    @PreAuthorize("hasRole('ROLE_Proveedor')")
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
    @PreAuthorize("hasRole('ROLE_Proveedor')")
    @GetMapping("/valorar")
    public String puntear (@RequestParam(required = true)String id_viaje,ModelMap modelo){
        try {
            System.out.println("el id del viaje es= "+ id_viaje);
            Viaje viaje =viajeServicio.buscarViajeId(id_viaje);
            Comprobante comprobante = comprobanteServicio.buscarComprobanteIdViaje(id_viaje);
            System.out.println("el id del comprobante encontrado es= "+comprobante.getID());
            System.out.println("el viaje que tiene el comprobante es= "+comprobante.getViaje().getID());
            modelo.put("viaje",viaje);
            modelo.put("comprobante", comprobante);
        } catch (ErroresServicio ex) {
            Logger.getLogger(viajeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "verfuncionamiento";
    }
    @PreAuthorize("hasRole('ROLE_Proveedor')")
    @PostMapping("/validar")
    public String finalizarPuntuacion(@RequestParam(required = true)String id_comprobante, Integer estrellas){
        System.out.println("el id del comprobante es= "+id_comprobante);
        System.out.println("el valor de la estrella es= "+ estrellas);
        Comprobante comprobante;
        try {
            comprobante = comprobanteServicio.buscarComprobanteId(id_comprobante);
            System.out.println("el id del comprobante encontrado es="+ comprobante.getID());
            comprobanteServicio.ValorarTrasnportista(comprobante.getID(), estrellas);
            System.out.println("el id del trasnportista aplicado es= "+comprobante.getViaje().getTransportistaAplicado().getId());
            transportistaServicio.valoracion(comprobante.getViaje().getTransportistaAplicado().getId());
            transportistaServicio.enViaje(comprobante.getViaje().getTransportistaAplicado().getId());
            viajeServicio.BajaViaje2(comprobante.getViaje().getID());
        } catch (ErroresServicio ex) {
            Logger.getLogger(viajeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/inicio";
    }
    @PreAuthorize("hasRole('ROLE_Transportista')")
    @GetMapping("/aplicar")
    public String aplicar(ModelMap model, @RequestParam(required = true)String id_transportista,@RequestParam(required = false) String error, @RequestParam(required = true)String id_viaje){
       
        try {
            viajeServicio.aplicar(id_transportista, id_viaje);
            //transportistaServicio.enViaje(id_transportista);

        } catch (ErroresServicio ex) {
            Logger.getLogger(viajeController.class.getName()).log(Level.SEVERE, null, ex);
            model.put("error", "Usted no puede aplicar al viaje");
            return "redirect:/inicio";
        }
        
        return "redirect:/inicio";
    }
    @PreAuthorize("hasRole('ROLE_Proveedor')")
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
    @PreAuthorize("hasRole('ROLE_Proveedor')")
    @GetMapping("/listar-postulantes")
    public String postulantes(@RequestParam(required = true)String id_viaje, ModelMap modelo){
      
        try {
            Viaje viaje=viajeServicio.buscarViajeId(id_viaje);
            List<Transportista> postulantes = viaje.getListadoTransportista();
            List<Transportista> postulantesActivos = new ArrayList();
            for(Transportista postulante : postulantes) {
                if (postulante.isViajando() == false ) {
                    postulantesActivos.add(postulante);
                }
                System.out.println("el boolean de viajando es= "+ postulante.isViajando());
            }
            
            modelo.put("id_viaje", id_viaje);
            modelo.put("transportistas", postulantesActivos); 
            return "TransportistasPostulados";
        } catch (ErroresServicio ex){
            Logger.getLogger(viajeController.class.getName()).log(Level.SEVERE, null, ex);
            return "redirect:/inicio";
        }
    }
    @PreAuthorize("hasRole('ROLE_Proveedor')")
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
    @PreAuthorize("hasRole('ROLE_Proveedor')")
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
            return "contactar";
        } catch (ErroresServicio ex) {
            Logger.getLogger(viajeController.class.getName()).log(Level.SEVERE, null, ex);
            return "redirect:/inicio";
        }        
    }
    @PreAuthorize("hasRole('ROLE_Proveedor')")//Revisar metodo falta
    @GetMapping("/contactar")
    public String contactartransportista(ModelMap modelo){
      
        try {
            
            List<Transportista> postulantes = transportistaServicio.listarTrasportistaLibres();
            if (postulantes.isEmpty()) {
                throw new ErroresServicio("No hay ningun transportista libre");
            }
//            for (Transportista postulante : postulantes) {
//                System.out.println("el boolean de viajando es= "+ postulante.isViajando());
//            }
            
            //modelo.put("id_viaje", id_viaje);
            modelo.put("transportistas", postulantes); 
            return "contactar";
        } catch (ErroresServicio ex){
            Logger.getLogger(viajeController.class.getName()).log(Level.SEVERE, null, ex);
            return "redirect:/inicio";
        }
    }
     @PostMapping("/contactar")
    public String contactarTransportista(ModelMap modelo, String id_transportista, String id_proveedor) throws ErroresServicio{
        Transportista transportista = transportistaServicio.buscarID(id_transportista);
        Proveedor proveedor = proveedorServicio.buscarID(id_proveedor);
         System.out.println("TRANSPORTISTA: "+transportista);
         System.out.println("TRANSPORTISTA_id: "+id_transportista);
        
            notif.enviar(proveedor.getNombreEmpresa()+" se ha contactado con usted por que quiere que realice un viaje", "Contacto", transportista.getMail());
            return "redirect:/inicio";
       
    }
}
    

