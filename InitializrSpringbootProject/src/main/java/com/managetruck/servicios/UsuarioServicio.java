/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.managetruck.servicios;

import com.managetruck.entidades.Usuario;
import com.managetruck.enumeracion.Role;
import com.managetruck.errores.ErroresServicio;
import com.managetruck.repositorios.RepositorioUsuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioServicio implements UserDetailsService{
    
    @Autowired
    RepositorioUsuario repositorioUsuario;
    
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Optional<Usuario> usuario = repositorioUsuario.buscarPorMail(mail);
        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_"+usuario.get().getRol());
            permisos.add(p1);
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario.get());
            User user = new User(usuario.get().getMail(), usuario.get().getPassword(), permisos);
            return user;

        } else {
            return null;
        }
    }
    //metodo para buscar el usuario por id
    public Usuario buscarUsuarioId(String id) throws ErroresServicio{
        Optional <Usuario> respuesta = repositorioUsuario.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            return usuario;
        }else{
            throw new ErroresServicio ("No se encontro el usuario");
        }
    }
    //metodo para cambiar la contraseña segun las vistas
    public void modificarContrasena(String id, String claveNueva)throws ErroresServicio{
       Usuario usuario=buscarUsuarioId(id);
        if (claveNueva.isEmpty()){
            String encriptada = new BCryptPasswordEncoder().encode(claveNueva);
            usuario.setPassword(encriptada);
        }
        
    }
    //metodo para buscar usuario por email
    public Usuario buscarUsuarioEmail(String mail)throws ErroresServicio{
        Optional <Usuario> respuesta = repositorioUsuario.buscarPorMail(mail);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            return usuario;
        }else{
            throw new ErroresServicio ("No se encontro el usuario asociado a ese correo");
        }
    }
    //metodo para recuperar contraseña
    public void olvideContrasena(String mail)throws ErroresServicio{
        if (mail.isEmpty()) {
            Usuario usuario = buscarUsuarioEmail(mail);
            String encriptada = new BCryptPasswordEncoder().encode(regenerar());
            usuario.setPassword(encriptada);
            //notificacionServicio.enviar("Cambio contraseña", "NOMBRE DE LA PAGINA",usuario.getMail());
        }
    }
    //metodo para crear una contraseña aleatroria
    public String regenerar(){
        String claveRegenerada="";
        for (int i = 0; i < 10; i++) {
            int aleatorio= (int)Math.random()*57+65;
            char letra = (char)aleatorio;
            claveRegenerada=claveRegenerada+letra;
        }
        return claveRegenerada;
    }
}
