/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyectodinal.servicios;

import com.proyectofinal.entidades.Foto;
import com.proyectofinal.entidades.Proveedor;
import com.proyectofinal.entidades.Usuario;
import com.proyectofinal.errores.ErroresServicio;
import com.proyectofinal.repositorios.RepositorioProveedor;
import com.proyectofinal.repositorios.RepositorioUsuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class ProveedorServicio {
    @Autowired
    private RepositorioProveedor repositorioproveedor;
    @Autowired
    private RepositorioUsuario repositorioUsuario;
    @Autowired
    private NotificacionDeServicio notificacionServicio;

    @Transactional
    public void crearProveedor(String nombre, String apellido, String mail, String password,Foto foto, String zona, Integer telefono,String razonSocial,Integer cuilEmpresa,String nombreEmpresa) throws ErroresServicio{
        validarProveedor(nombre,apellido,mail,password,foto,zona,telefono,razonSocial,cuilEmpresa,nombreEmpresa);
        Optional<Usuario> respuesta = repositorioUsuario.buscarPorMail(mail);
        if (respuesta.isPresent()) {
            throw new ErroresServicio("El mail ya esta utilizado");

        } else {
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(nombre);
        proveedor.setApellido(apellido);
        proveedor.setMail(mail);
        String encriptada = new BCryptPasswordEncoder().encode(password);
        proveedor.setPassword(encriptada);
        proveedor.setFoto(foto);
        proveedor.setZona(zona);
        proveedor.setTelefono(telefono);
        proveedor.setRazonSocial(razonSocial);
        proveedor.setCuilEmpresa(cuilEmpresa);
        proveedor.setNombreEmpresa(nombreEmpresa);
        notificacionServicio.enviar("TEXTO DE BIENVENIDA", "NOMBRE DE LA PAGINA", proveedor.getMail());
        repositorioproveedor.save(proveedor);
        }
    }
    @Transactional
    public void modificarUsuario(String id,String nombre, String apellido, String mail, String password,Foto foto, String zona, Integer telefono,String razonSocial,Integer cuilEmpresa,String nombreEmpresa) throws ErroresServicio {
        Optional<Proveedor> respuesta = repositorioproveedor.findById(id);
        if (respuesta.isPresent()) {
        Proveedor proveedor = respuesta.get();
        proveedor.setNombre(nombre);
        proveedor.setApellido(apellido);
        proveedor.setMail(mail);
        String encriptada = new BCryptPasswordEncoder().encode(password);
        proveedor.setPassword(encriptada);
        proveedor.setFoto(foto);
        proveedor.setZona(zona);
        proveedor.setTelefono(telefono);
        proveedor.setRazonSocial(razonSocial);
        proveedor.setCuilEmpresa(cuilEmpresa);
        proveedor.setNombreEmpresa(nombreEmpresa);
            notificacionServicio.enviar("TEXTO DE MODIFICACION DE CREDENCIALES", "NOMBRE DE LA PAGINA", proveedor.getMail());
        repositorioproveedor.save(proveedor);
        }else{
        throw new ErroresServicio("No se encontro el usuario solicitado");
    }
    }
    @Transactional
    public void deshabilitarProveedor(String id) throws ErroresServicio{
         Optional<Proveedor> respuesta = repositorioproveedor.findById(id);
        if (respuesta.isPresent()) {
            Proveedor proveedor = respuesta.get();
            proveedor.setAlta(false);
        }else{
        throw new ErroresServicio("No se encontro el usuario solicitado");
        }
    }
    @Transactional
    public void habilitarProveedor(String id) throws ErroresServicio{
         Optional<Proveedor> respuesta = repositorioproveedor.findById(id);
        if (respuesta.isPresent()) {
            Proveedor proveedor = respuesta.get();
            proveedor.setAlta(true);
        }else{
        throw new ErroresServicio("No se encontro el usuario solicitado");
        }
    }
    public void validarProveedor(String nombre, String apellido, String mail, String password,Foto foto, String zona, Integer telefono,String razonSocial,Integer cuilEmpresa,String nombreEmpresa) throws ErroresServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErroresServicio("Debe ingresar un nombre");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new ErroresServicio("Debe ingresar un apellido");
        }
        if (mail == null || mail.isEmpty()) {
            throw new ErroresServicio("Debe ingresar un mail");
        }
        if (telefono == null) {
            throw new ErroresServicio("Debe ingresar un telefono ");
        }
        if (password == null || password.isEmpty()) {
            throw new ErroresServicio("Debe ingresar una contraseña");
        }
        if (zona == null || zona.isEmpty()) {
            throw new ErroresServicio("Debe ingresar una zona");
        }
        if (foto == null ) {
            throw new ErroresServicio("Debe ingresar una foto");
        }
        if (razonSocial == null || razonSocial.isEmpty()) {
            throw new ErroresServicio("Debe ingresar una zona");
        }
        if (cuilEmpresa == null ) {
            throw new ErroresServicio("Debe ingresar un cuil de la empresa");
        }
        if (nombreEmpresa == null || nombreEmpresa.isEmpty()) {
            throw new ErroresServicio("Debe ingresar una nombre para la empresa");
        }
    }
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Optional <Usuario> usuario = repositorioUsuario.buscarPorMail(mail);
        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_PROVEEDOR_REGISTRADO");
            permisos.add(p1);
            ServletRequestAttributes attr=(ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario.get());
            User user = new User(usuario.get().getMail(), usuario.get().getPassword(), permisos);
            return user;

        } else {
            return null;
        }
    }
}
