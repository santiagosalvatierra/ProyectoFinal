/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.managetruck.servicios;

import com.managetruck.entidades.Usuario;
import com.managetruck.enumeracion.Role;
import static com.managetruck.enumeracion.Role.Proveedor;
import static com.managetruck.enumeracion.Role.Transportista;
import com.managetruck.errores.ErroresServicio;
import com.managetruck.repositorios.RepositorioUsuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
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
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    RepositorioUsuario repositorioUsuario;
    @Autowired
    private NotificacionDeServicio notificacion;

    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Optional<Usuario> usuario = repositorioUsuario.buscarPorMail(mail);
        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_" + usuario.get().getRol());
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
    public int getFiveDigitsNumber() {
    double fiveDigits = 10000 + Math.random() * 90000;
    return (int) fiveDigits;
}
    //metodo para buscar el usuario por id
    public Usuario buscarUsuarioId(String id) throws ErroresServicio {
        Optional<Usuario> respuesta = repositorioUsuario.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            return usuario;
        } else {
            throw new ErroresServicio("No se encontro el usuario");
        }
    }

    //metodo para cambiar la contraseña segun las vistas
    @Transactional
    public void modificarContrasena(String id, String claveNueva, String claveNueva1, String claveVieja) throws ErroresServicio {
        Usuario usuario = buscarUsuarioId(id);
        if (usuario != null) {
            
            verificarPassword(claveVieja, usuario);
            if (!claveNueva.isEmpty()) {
                if (claveNueva.equals(claveNueva1)) {
                    String encriptada = new BCryptPasswordEncoder().encode(claveNueva);
                  
                    usuario.setPassword(encriptada);
                } else {
                    throw new ErroresServicio("Las nuevas contraseñas no son iguales");
                }
            } else {
                throw new ErroresServicio("La clave no puede ser nula");
            }
        } else {
            throw new ErroresServicio("No se encuentra a ningun usuario");
        }
    }

    //metodo para verificar que la clave vieja ingresada sea correcta
    public void verificarPassword(String claveVieja, Usuario usuario) throws ErroresServicio {

        if (!claveVieja.equals(usuario.getPassword())) {
            throw new ErroresServicio("La clave actual no coincide con la clave guardada");
        }
    }

    //metodo para buscar usuario por email
    public Usuario buscarUsuarioEmail(String mail) throws ErroresServicio {
        Optional<Usuario> respuesta = repositorioUsuario.buscarPorMail(mail);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            return usuario;
        } else {
            throw new ErroresServicio("No se encontro el usuario asociado a ese correo");
        }
    }

    //metodo para recuperar contraseña
    @Transactional
    public void olvideContrasena(String mail) throws ErroresServicio {
        if (!mail.isEmpty()) {
            Usuario usuario = buscarUsuarioEmail(mail);
            String claveNueva=regenerar();
            String encriptada = new BCryptPasswordEncoder().encode(claveNueva);
            usuario.setPassword(encriptada);
            notificacion.enviar("Su nueva contrasena es "+claveNueva+" puede utilizarla para ingresar y posteriormente cambiarla", "Contrasena nueva", mail);
        } else {
            throw new ErroresServicio("No se encontro el usuario asociado a ese correo");
        }
        
    }

    //metodo para crear una contraseña aleatoria
    public String regenerar() {
    String randomStrings=null ;
    Random random = new Random(); 
    for(int i = 0; i < 1; i++) 
    { 
     char[] word = new char[random.nextInt(8)+3]; // words of length 3 through 10. (1 and 2 letter words are boring.) 
     for(int j = 0; j < word.length; j++) 
     { 
      word[j] = (char)('a' + random.nextInt(26)); 
     } 
     randomStrings = new String(word); 
    } 
    return randomStrings;
    }

    //metodo para retornar si es proveedor o trasnportista
    public String determinarClase(Usuario usuario) {
        String direccion = "";
        if (usuario.getRol().equals(Proveedor)) {
            direccion ="/proveedor/perfil-proveedor?id=" + usuario.getId();
        } else if (usuario.getRol().equals(Transportista)) {
            direccion ="/transportista//perfil-transportista" + usuario.getId();
            return "index";
        }
        return direccion;
    }

}
