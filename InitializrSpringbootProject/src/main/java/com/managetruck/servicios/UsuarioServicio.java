/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.managetruck.servicios;

import com.managetruck.entidades.Usuario;
import com.managetruck.repositorios.RepositorioUsuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class UsuarioServicio {
    
    @Autowired
    RepositorioUsuario repositorioUsuario;
    
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
