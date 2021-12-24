
package com.managetruck.controllers;

import com.managetruck.entidades.Camion;
import com.managetruck.entidades.Usuario;
import com.managetruck.errores.ErroresServicio;
import com.managetruck.servicios.CamionServicio;
import com.managetruck.servicios.ProveedorServicio;
import com.managetruck.servicios.TransportistaServicio;
import com.managetruck.servicios.UsuarioServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/foto")
public class FotoController {
    
    @Autowired
    private ProveedorServicio proveedorServicio;
    
    @Autowired
    private TransportistaServicio transportistsServicio;
    
    @Autowired
    private CamionServicio camionServicio;
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    //metodo get para la foto del usuario
    @GetMapping("/usuario-imagen/{id}")
    public ResponseEntity <byte[]> fotoUsuario(@PathVariable String id){
        try{
        Usuario usuario = usuarioServicio.buscarUsuarioId(id);
        if (usuario.getFoto() == null) {
            throw new ErroresServicio ("El usuario no tiene una imagen para mostrar");
        }
        byte[] foto = usuario.getFoto().getContenido();
        
        HttpHeaders headers = new HttpHeaders();
        //coloco all para probar sino la voy a cambiar a jpeg
        headers.setContentType(MediaType.IMAGE_JPEG);
        
        return new ResponseEntity<>(foto, headers,HttpStatus.OK);
        }catch(ErroresServicio ex){
            Logger.getLogger(FotoController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //metodo get para la foto del camion para revisar bien si funciona
    @GetMapping("/camion-imagenes/{id}")
    public ResponseEntity <byte[]> fotoCamion(@PathVariable String id){
        try{
        Camion camion = camionServicio.buscarCamionId(id);
        if (camion.getFoto() == null) {
            throw new ErroresServicio ("El usuario no tiene una imagen para mostrar");
        }
        byte[] foto = camion.getFoto().getContenido();
        
        HttpHeaders headers = new HttpHeaders();
        //coloco all para probar sino la voy a cambiar a jpeg
        headers.setContentType(MediaType.IMAGE_JPEG);
        
        return new ResponseEntity<>(foto, headers,HttpStatus.OK);
        }catch(ErroresServicio ex){
            Logger.getLogger(FotoController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
