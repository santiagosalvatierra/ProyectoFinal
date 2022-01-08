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
import com.managetruck.errores.ErroresServicio;
import com.managetruck.repositorios.RepositorioProvincias;
import com.managetruck.servicios.ProveedorServicio;
import com.managetruck.servicios.TransportistaServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public String registroProveedor(ModelMap model,String nombre, String apellido, String mail, String clave1, String clave2, MultipartFile archivo, String zona, String telefono, String razonSocial, String cuilEmpresa, String nombreEmpresa) throws ErroresServicio {
        try{
        proveedorServicio.crearProveedor(nombre, apellido, mail, clave1, clave2, archivo, zona, telefono, razonSocial, cuilEmpresa, nombreEmpresa);
        } catch (ErroresServicio es) {
            List<Provincias> provincias = repositorioProvincias.buscarProvinciastotales();
            model.put("error", es.getMessage());
            model.put("nombre", nombre);
            model.put("apellido", apellido);
            model.put("mail", mail);
            model.put("clave1", clave1);
            model.put("clave2", clave2);
            model.put("archivo", archivo);
            model.put("provincias",provincias);
            model.put("zona", zona);
            model.put("telefono", telefono);
            model.put("razonSocial", razonSocial);
            model.put("cuilEmpresa", cuilEmpresa);
            model.put("nombreEmpresa", nombreEmpresa);
            return "empresaForm";//modificar nombre de vista, no debe redirigir a index si no a la vista que utilizaremos
        }
        return "index";
    }

    @GetMapping("/registro")
    public String mostrarPaginaRegistro(ModelMap modelo) {
        List<Provincias> provincias = repositorioProvincias.buscarProvinciastotales();
        modelo.put("provincias",provincias);
        return "empresaForm";
    }

    @GetMapping("/modificar-proveedor")
    public String modificarProveedor() {
        return null;
    }

    @PostMapping("/modificar-proveedor")
    public String modificacionProveedor(HttpSession session, String id) {
        //verificacion de que el usuario que esta modificando sea el mismo que va a modificar
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || login.getId().equals(id)) {
            return "redirect:/login";
        }
        return null;
    }
    @GetMapping("/listarProveedor")
    public String listarProveedores() {
        
        return "pruebaLista";
    }
    @GetMapping("/indexEmpresa")
    public String indexEmpresa(Model model) {
        List<Transportista> listado = transportistaServicio.listarTransportista();
        model.addAttribute("transportistas", listado);
        return "indexEmpresa";
    }

}
