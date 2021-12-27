/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.managetruck.servicios;


import com.managetruck.entidades.Camion;
import com.managetruck.entidades.Foto;
import com.managetruck.entidades.Proveedor;
import com.managetruck.entidades.Transportista;
import com.managetruck.entidades.Usuario;
import com.managetruck.enumeracion.Role;
import com.managetruck.errores.ErroresServicio;
import com.managetruck.repositorios.RepositorioCamion;
import com.managetruck.repositorios.RepositorioProveedor;
import com.managetruck.repositorios.RepositorioTransportista;
import com.managetruck.repositorios.RepositorioUsuario;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class TransportistaServicio {
 
    @Autowired(required = true)
    RepositorioTransportista repositorioTransportista;
    
    @Autowired(required = true)
    RepositorioUsuario repositorioUsuario;
    
    @Autowired
    RepositorioCamion repositorioCamion;
    
    @Autowired(required = true)
    NotificacionDeServicio notificacionServicio;
    
    @Autowired
    FotoServicio fotoServicio;

    @Transactional 
    public void crearTransportista(String nombre, String apellido, String mail, String password, MultipartFile archivo, String zona, String telefono, String patente, Integer cantidadViajes) throws ErroresServicio {
        Foto foto= fotoServicio.guardar(archivo);
        validarTransportista(nombre, apellido, mail, password, foto, zona, telefono, patente);
        Optional<Usuario> respuesta = repositorioUsuario.buscarPorMail(mail);
        if (respuesta.isPresent()) {
            throw new ErroresServicio("El mail ya esta utilizado");

        } else {
            //se crea la entidad transportista
            Transportista transportista = new Transportista();
            //se setea con todos los atributos
            transportista.setNombre(nombre);
            transportista.setApellido(apellido);
            transportista.setMail(mail);
            String encriptada = new BCryptPasswordEncoder().encode(password);
            transportista.setPassword(encriptada);
            transportista.setFoto(foto);
            transportista.setZona(zona);
            transportista.setTelefono(telefono);
            List<Camion> camion=repositorioCamion.buscarCamionporPatente(patente);
            transportista.setCamion((Camion)camion);
            transportista.setCantidadViajes(0);
            transportista.setValoracion(0);
            transportista.setRol(Role.Transportista);
            transportista.setEstado(true);
            //se envia notificacion que lo realizo correctamente
            notificacionServicio.enviar("TEXTO DE BIENVENIDA", "NOMBRE DE LA PAGINA", transportista.getMail());
            //se guarda en el repositorio o base de datos
            repositorioTransportista.save(transportista);
        }
    }

    @Transactional
    public void modificarUsuario(String id, String nombre, String apellido, String mail, String password, MultipartFile archivo, String zona, String telefono, Camion camion, double valoracion, Integer cantidadViajes) throws ErroresServicio {
        Foto foto= fotoServicio.guardar(archivo);
        Optional<Transportista> respuesta = repositorioTransportista.findById(id);
        if (respuesta.isPresent()) {
            Transportista transportista = respuesta.get();
            transportista.setNombre(nombre);
            transportista.setApellido(apellido);
            transportista.setMail(mail);
            String encriptada = new BCryptPasswordEncoder().encode(password);
            transportista.setPassword(encriptada);
            transportista.setFoto(foto);
            transportista.setZona(zona);
            transportista.setTelefono(telefono);
            transportista.setCamion(camion);
            //no debe estar la valoracion y cantidad de viajes porque eso no lo deberia poder cambiar el transportista
            transportista.setValoracion(valoracion);
            transportista.setCantidadViajes(cantidadViajes);
            notificacionServicio.enviar("TEXTO DE MODIFICACION DE CREDENCIALES", "NOMBRE DE LA PAGINA", transportista.getMail());
            repositorioTransportista.save(transportista);
        } else {
            throw new ErroresServicio("No se encontro el usuario solicitado");
        }
    }
    @Transactional
    public void deshabilitarTransportista(String id) throws ErroresServicio{
         Optional<Transportista> respuesta = repositorioTransportista.findById(id);
        if (respuesta.isPresent()) {
            Transportista transportista = respuesta.get();
            transportista.setAlta(false);
        }else{
        throw new ErroresServicio("No se encontro el usuario solicitado");
        }
    }
    
    @Transactional
    public void habilitarTransportista(String id) throws ErroresServicio{
         Optional<Transportista> respuesta = repositorioTransportista.findById(id);
        if (respuesta.isPresent()) {
            Transportista transportista = respuesta.get();
            transportista.setAlta(true);
        }else{
        throw new ErroresServicio("No se encontro el usuario solicitado");
        }
    }
    public void validarTransportista(String nombre, String apellido, String mail, String password, Foto foto, String zona, String telefono, String patente) throws ErroresServicio {
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
        if (foto == null) {
            throw new ErroresServicio("Debe ingresar una foto");
        }
        if (patente == null) {
            throw new ErroresServicio("Debe ingresar una patente de un camion");
        }

    }

//    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
//        Optional<Usuario> usuario = repositorioUsuario.buscarPorMail(mail);
//        if (usuario != null) {
//            List<GrantedAuthority> permisos = new ArrayList<>();
//            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_"+usuario.get().getRol());
//            permisos.add(p1);
//            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//            HttpSession session = attr.getRequest().getSession(true);
//            session.setAttribute("usuariosession", usuario.get());
//            User user = new User(usuario.get().getMail(), usuario.get().getPassword(), permisos);
//            return user;
//
//        } else {
//            return null;
//        }
//    }
}
