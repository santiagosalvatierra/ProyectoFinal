/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.managetruck.servicios;

import com.managetruck.entidades.Foto;
import com.managetruck.entidades.Proveedor;
import com.managetruck.entidades.Usuario;
import com.managetruck.enumeracion.Role;
import com.managetruck.enumeracion.Rubro;
import com.managetruck.errores.ErroresServicio;
import com.managetruck.repositorios.RepositorioProveedor;
import com.managetruck.repositorios.RepositorioUsuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProveedorServicio {

    @Autowired(required = true)
    RepositorioProveedor repositorioproveedor;

    @Autowired(required = true)
    RepositorioUsuario repositorioUsuario;

    @Autowired(required = true)
    NotificacionDeServicio notificacionServicio;

    @Autowired
    private FotoServicio fotoServicio;

    @Transactional
    public void crearProveedor(String nombre, String apellido, String mail, String password, String password2, MultipartFile archivo, String zona, String telefono, String razonSocial, String cuilEmpresa, String nombreEmpresa, Rubro rubro) throws ErroresServicio {
        Foto foto = fotoServicio.guardar(archivo);
        validarProveedor(nombre, apellido, mail, password, password2, archivo, zona, telefono, razonSocial, cuilEmpresa, nombreEmpresa);

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
            proveedor.setRubro(rubro);
            proveedor.setCuilEmpresa(cuilEmpresa);
            proveedor.setNombreEmpresa(nombreEmpresa);
            proveedor.setRol(Role.Proveedor);
            //notificacionServicio.enviar("TEXTO DE BIENVENIDA", "NOMBRE DE LA PAGINA", proveedor.getMail());
            repositorioproveedor.save(proveedor);
        }
    }

    @Transactional
    public void modificarUsuario(String id, String nombre, String apellido, String mail, MultipartFile archivo, String zona, String telefono, String razonSocial, String cuilEmpresa, String nombreEmpresa, Rubro rubro) throws ErroresServicio {
        Optional<Proveedor> respuesta = repositorioproveedor.findById(id);
        if (respuesta.isPresent()) {
            Proveedor proveedor = respuesta.get();

            proveedor.setNombre(nombre);
            proveedor.setApellido(apellido);
            proveedor.setMail(mail);
//            String encriptada = new BCryptPasswordEncoder().encode(password)
//            proveedor.setPassword(encriptada);
            if (archivo != null && !archivo.isEmpty()) {
                Foto foto = fotoServicio.guardar(archivo);
                proveedor.setFoto(foto);
            }
            proveedor.setRubro(rubro);//se agrega rubro
            proveedor.setZona(zona);
            proveedor.setTelefono(telefono);
            proveedor.setRazonSocial(razonSocial);
            proveedor.setCuilEmpresa(cuilEmpresa);
            proveedor.setNombreEmpresa(nombreEmpresa);
            //notificacionServicio.enviar("TEXTO DE MODIFICACION DE CREDENCIALES", "NOMBRE DE LA PAGINA", proveedor.getMail());
            repositorioproveedor.save(proveedor);
        } else {
            throw new ErroresServicio("No se encontro el usuario solicitado");
        }
    }

    @Transactional
    public void deshabilitarProveedor(String id) throws ErroresServicio {
        Optional<Proveedor> respuesta = repositorioproveedor.findById(id);
        if (respuesta.isPresent()) {
            Proveedor proveedor = respuesta.get();
            proveedor.setAlta(false);
        } else {
            throw new ErroresServicio("No se encontro el usuario solicitado");
        }
    }

    @Transactional
    public void habilitarProveedor(String id) throws ErroresServicio {
        Optional<Proveedor> respuesta = repositorioproveedor.findById(id);
        if (respuesta.isPresent()) {
            Proveedor proveedor = respuesta.get();
            proveedor.setAlta(true);
        } else {
            throw new ErroresServicio("No se encontro el usuario solicitado");
        }
    }

    public void validarProveedor(String nombre, String apellido, String mail, String password, String password2, MultipartFile foto, String zona, String telefono, String razonSocial, String cuilEmpresa, String nombreEmpresa) throws ErroresServicio {

        Pattern pString = Pattern.compile("^([A-Za-z]+[ ]*){1,3}$");
        Pattern pNum = Pattern.compile("^[0-9]{8}$");
        Pattern pNumPhone = Pattern.compile("^[0-9]{8,10}$");
        Pattern pMail = Pattern.compile("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b");

        Matcher mName = pString.matcher(nombre);
        Matcher mMail = pMail.matcher(mail);

        if (nombre == null || nombre.isEmpty()) {
            throw new ErroresServicio("Debe ingresar un nombre");
        } else if (!mName.matches()) {
            throw new ErroresServicio("Debe ingresar un nombre válido");
        }

        if (apellido == null || apellido.isEmpty()) {
            throw new ErroresServicio("Debe ingresar un apellido");
        }
        if (mail == null || mail.isEmpty()) {
            throw new ErroresServicio("Debe ingresar un mail");
        } else if (!mMail.matches()) {
            throw new ErroresServicio("Debe ingresar un mail válido");
        }
        if (telefono == null || telefono.isEmpty()) {
            throw new ErroresServicio("Debe ingresar un telefono ");
        }
        verificarnumeros(telefono);
        if (password == null || password.isEmpty()) {
            throw new ErroresServicio("Debe ingresar una contraseña");
        }
        if (password2 == null || password2.isEmpty()) {

            throw new ErroresServicio("Debe ingresar una contraseña2");
        }
        if (!password.equals(password2)) {
            throw new ErroresServicio("Las dos contrasenas deben ser iguales");
        }
        if (zona == null || zona.isEmpty()) {
            throw new ErroresServicio("Debe ingresar una zona");
        }
        if (foto == null) {
            throw new ErroresServicio("Debe ingresar una foto");
        }
        if (razonSocial == null || razonSocial.isEmpty()) {
            throw new ErroresServicio("Debe ingresar una zona");
        }
        if (cuilEmpresa == null || cuilEmpresa.isEmpty()) {
            throw new ErroresServicio("Debe ingresar un cuil de la empresa");
        }
        verificarnumeros(cuilEmpresa);
        if (nombreEmpresa == null || nombreEmpresa.isEmpty()) {
            throw new ErroresServicio("Debe ingresar una nombre para la empresa");
        }
    }
//para verificar que los datos ingresados sean numeros

    public void verificarnumeros(String datos) throws ErroresServicio {
        try {
            Long numero = Long.parseLong(datos);
        } catch (Exception e) {
            throw new ErroresServicio("El dato de" + datos + " ingresado no es un numero");
        }

    }

    public List listarProveedor() {
        List<Proveedor> listado = repositorioproveedor.findAll();
        return listado;
    }

    //metodo para buscar por id
    public Proveedor buscarID(String id_proveedor) throws ErroresServicio {
        Proveedor proveedor;
        Optional<Proveedor> respuesta = repositorioproveedor.findById(id_proveedor);
        if (respuesta.isPresent()) {
            proveedor = respuesta.get();
            return proveedor;
        } else {
            throw new ErroresServicio("No se encontro ningun proveedor con ese id");
        }
    }
//    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
//        Optional <Usuario> usuario = repositorioUsuario.buscarPorMail(mail);
//        if (usuario != null) {
//            List<GrantedAuthority> permisos = new ArrayList<>();
//            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_PROVEEDOR_REGISTRADO");
//            permisos.add(p1);
//            ServletRequestAttributes attr=(ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
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
