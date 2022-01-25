/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.managetruck.controllers;

import com.managetruck.entidades.Proveedor;
import com.managetruck.entidades.Provincias;
import com.managetruck.entidades.Transportista;
import com.managetruck.entidades.Usuario;
import static com.managetruck.enumeracion.Role.Transportista;
import com.managetruck.enumeracion.Rubro;
import com.managetruck.errores.ErroresServicio;
import com.managetruck.repositorios.RepositorioProvincias;
import com.managetruck.servicios.ProveedorServicio;
import com.managetruck.servicios.TransportistaServicio;
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
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/proveedor")
public class ProveedorController {

    @Autowired
    ProveedorServicio proveedorServicio;
    @Autowired
    TransportistaServicio transportistaServicio;
    
    @Autowired
    RepositorioProvincias repositorioProvincias;

    @PostMapping("/registro")
    public String registroProveedor(ModelMap model,String nombre, String apellido, String mail, String clave1, String clave2, MultipartFile archivo1, String provincia, String telefono, String razonSocial, String cuilEmpresa, String nombreEmpresa, Rubro rubro) throws ErroresServicio {
        try{
        proveedorServicio.crearProveedor(nombre, apellido, mail, clave1, clave2, archivo1, provincia, telefono, razonSocial, cuilEmpresa, nombreEmpresa,rubro);
        } catch (ErroresServicio es) {
            List<Provincias> provincias = repositorioProvincias.buscarProvinciastotales();
            model.put("error", es.getMessage());
            model.put("nombre", nombre);
            model.put("apellido", apellido);
            model.put("mail", mail);
            model.put("clave1", clave1);
            model.put("clave2", clave2);
            model.put("archivo", archivo1);
            model.put("provincias",provincias);
            model.put("provincia", provincia);
            model.put("telefono", telefono);
            model.put("razonSocial", razonSocial);
            model.put("cuilEmpresa", cuilEmpresa);
            model.put("nombreEmpresa", nombreEmpresa);
            model.put("rubros", Rubro.values());
            return "empresaForm";
        }
        return "index";
    }

    @GetMapping("/registro")
    public String mostrarPaginaRegistro(ModelMap modelo) {
        List<Provincias> provincias = repositorioProvincias.buscarProvinciastotales();
        modelo.put("provincias",provincias);
        modelo.put("rubros", Rubro.values());
        return "empresaForm";
    }

    @GetMapping("/modificar-proveedor")
    public String modificarProveedor() {
        return null;
    }

    @PostMapping("/modificar-proveedor")
    public String modificacionProveedor(HttpSession session, String id, String nombreEmpresa,String zona,String mail,String razonSocial,String telefono, String cuilEmpresa,@RequestParam(required = false)MultipartFile foto, String nombre, String apellido,Rubro rubro) {
      //verificacion de que el usuario que esta modificando sea el mismo que va a modificar
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/login";
        }
        try {
            proveedorServicio.modificarUsuario(id, nombre, apellido, mail, foto, zona, telefono, razonSocial, cuilEmpresa, nombreEmpresa, rubro);
            
        } catch (ErroresServicio ex) {
            Logger.getLogger(ProveedorController.class.getName()).log(Level.SEVERE, null, ex);
            return "redirect:/proveedor//perfil-proveedor?id="+login.getId();
        }
        return "redirect:/inicio";
    }
    @GetMapping("/listarProveedor")
    public String listarProveedores() {
        
        return "pruebaLista";
        
    }
    
    //para mi esta vista esta de mas ya que la decuelve en el inicio cuando se registra el proveedor
    @GetMapping("/indexEmpresa")
    public String indexEmpresa(Model model) {
        List<Transportista> listado = transportistaServicio.listarTransportista();
        model.addAttribute("transportistas", listado);
        return "indexEmpresa";
    }
    @GetMapping("/perfil-proveedor")
    public String perfilProveedor(@RequestParam(required = true) String id,ModelMap modelo,Model model){
        try {
            System.out.println(id);
            Proveedor proveedor = proveedorServicio.buscarID(id);
            model.addAttribute("perfil", proveedor);
            List<Provincias> provincias = repositorioProvincias.buscarProvinciastotales();
            modelo.put("provincias",provincias);
            modelo.put("rubros", Rubro.values());
            
            return "perfilEmpresa";
        } catch (ErroresServicio ex) {
            Logger.getLogger(ProveedorController.class.getName()).log(Level.SEVERE, null, ex);
            return "redirect:/inicio";
        }
        
    }
    
    

}
