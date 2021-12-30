
package com.managetruck.controllers;

import com.managetruck.errores.ErroresServicio;
import com.managetruck.servicios.ComprobanteServicio;
import com.managetruck.servicios.TransportistaServicio;
import com.managetruck.servicios.ViajeServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    
    @GetMapping ("/pedido")
    public String inicioViaje(){
        return null;
    }
    
    @PostMapping("/pedido")
    public String comienzoViaje (String idProveedor,@RequestParam Integer peso, @RequestParam Integer kmRecorridos, @RequestParam String tipoCargas, @RequestParam String destino,@RequestParam String origen){
        try {
            viajeServicio.crearViaje(idProveedor,peso, kmRecorridos, tipoCargas, destino, origen);
        } catch (ErroresServicio ex) {
            Logger.getLogger(viajeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @GetMapping ("/modificar-viaje")
    public String modificarViaje(){
        return null;
    }
    
    @PostMapping("/modificar-viaje")
    public String cambiarViaje(@RequestParam String id, @RequestParam Integer peso, @RequestParam Integer kmRecorridos,@RequestParam String tipoCargas,@RequestParam String destino,@RequestParam String origen){
        try {
            viajeServicio.ModificarViaje(id, peso, kmRecorridos, tipoCargas, destino, origen);
        } catch (ErroresServicio ex) {
            Logger.getLogger(viajeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    @PostMapping("/finalizar")
    public String finalizarViaje(@RequestParam String id, @RequestParam  Integer valoracion){
        try {
            viajeServicio.BajaViaje(id);
           // comprobanteServicio.Valoracion(valoracion, id); revisar
        } catch (ErroresServicio ex) {
            Logger.getLogger(viajeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
