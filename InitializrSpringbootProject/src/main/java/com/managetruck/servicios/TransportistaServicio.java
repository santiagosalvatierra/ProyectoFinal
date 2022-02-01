/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.managetruck.servicios;

import com.managetruck.controllers.ComprobanteController;
import com.managetruck.entidades.Camion;
import com.managetruck.entidades.Comprobante;
import com.managetruck.entidades.Foto;
import com.managetruck.entidades.Proveedor;
import com.managetruck.entidades.Transportista;
import com.managetruck.entidades.Usuario;
import com.managetruck.entidades.Viaje;
import com.managetruck.enumeracion.EstadoEnum;
import com.managetruck.enumeracion.Role;
import com.managetruck.errores.ErroresServicio;
import com.managetruck.repositorios.RepositorioCamion;
import com.managetruck.repositorios.RepositorioComprobante;
import com.managetruck.repositorios.RepositorioProveedor;
import com.managetruck.repositorios.RepositorioTransportista;
import com.managetruck.repositorios.RepositorioUsuario;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    RepositorioComprobante repositorioComprobante;

    @Autowired
    RepositorioCamion repositorioCamion;

    @Autowired(required = true)
    NotificacionDeServicio notificacionServicio;

    @Autowired
    FotoServicio fotoServicio;

    @Autowired
    ComprobanteServicio comprobanteServicio;
   

    @Transactional
    public void crearTransportista(String nombre, String apellido, String mail, String clave1, String clave2, MultipartFile archivo, String zona, String telefono) throws ErroresServicio {
        Foto foto = fotoServicio.guardar(archivo);
        validarTransportista(nombre, apellido, mail, clave1, clave2, archivo, zona, telefono);
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
            String encriptada = new BCryptPasswordEncoder().encode(clave1);
            transportista.setPassword(encriptada);
            transportista.setFoto(foto);
            transportista.setZona(zona);
            transportista.setTelefono(telefono); 
            transportista.setCamion(null);
            transportista.setCantidadViajes(0);
            transportista.setValoracion(0);
            transportista.setRol(Role.Transportista);
            transportista.setEstado(true);
            transportista.setViajando(false);
            //se envia notificacion que lo realizo correctamente
            //notificacionServicio.enviar("TEXTO DE BIENVENIDA", "NOMBRE DE LA PAGINA", transportista.getMail());
            //se guarda en el repositorio o base de datos
            repositorioTransportista.save(transportista);
        }
    }
//metodo para setear un camion aun trnasportista
    @Transactional
    public void SetearCamion(String id_camion, String mail)throws ErroresServicio{
        Optional<Usuario> respuesta = repositorioUsuario.buscarPorMail(mail);
        if (respuesta.isPresent()) {
        Optional<Camion> respuesta2 = repositorioCamion.findById(id_camion);
            if (respuesta2.isPresent()) {
                Transportista transportista = (Transportista) respuesta.get();
                transportista.setCamion(respuesta2.get());
            }      
        } else {
        throw new ErroresServicio("No se encontro un usuario con ese correo electronico");
        }
    }
    @Transactional
    public void modificarUsuario(String id, String nombre, String apellido, String mail, MultipartFile archivo, String zona, String telefono) throws ErroresServicio {

        validarTransportista2(nombre, apellido, mail, zona, telefono);
        Optional<Transportista> respuesta = repositorioTransportista.findById(id);
        if (respuesta.isPresent()) {
            Transportista transportista = respuesta.get();
            transportista.setNombre(nombre);
            transportista.setApellido(apellido);
            transportista.setMail(mail);
//            String encriptada = new BCryptPasswordEncoder().encode(clave1);
//            transportista.setPassword(encriptada);
            if (archivo != null && !archivo.isEmpty()) {
                Foto foto = fotoServicio.guardar(archivo);
                transportista.setFoto(foto);
            }
            transportista.setZona(zona);
            transportista.setTelefono(telefono);
            //no debe estar la valoracion y cantidad de viajes porque eso no lo deberia poder cambiar el transportista
            //transportista.setValoracion(valoracion);
            //transportista.setCantidadViajes(cantidadViajes);
            //notificacionServicio.enviar("TEXTO DE MODIFICACION DE CREDENCIALES", "NOMBRE DE LA PAGINA", transportista.getMail());
            repositorioTransportista.save(transportista);
        } else {
            throw new ErroresServicio("No se encontro el usuario solicitado");
        }
    }

    @Transactional
    public void deshabilitarTransportista(String id) throws ErroresServicio {
        Optional<Transportista> respuesta = repositorioTransportista.findById(id);
        if (respuesta.isPresent()) {
            Transportista transportista = respuesta.get();
            transportista.setAlta(false);
        } else {
            throw new ErroresServicio("No se encontro el usuario solicitado");
        }
    }

    @Transactional
    public void habilitarTransportista(String id) throws ErroresServicio {
        Optional<Transportista> respuesta = repositorioTransportista.findById(id);
        if (respuesta.isPresent()) {
            Transportista transportista = respuesta.get();
            transportista.setAlta(true);
        } else {
            throw new ErroresServicio("No se encontro el usuario solicitado");
        }
    }

    public void validarTransportista(String nombre, String apellido, String mail, String clave1, String clave2, MultipartFile foto, String zona, String telefono) throws ErroresServicio {
        
        validarTransportista2(nombre,apellido,mail,zona,telefono);
        
        if (clave1 == null || clave1.isEmpty()) {
            throw new ErroresServicio("Debe ingresar una contraseña");
        }
        if (clave2 == null || clave2.isEmpty()) {
            throw new ErroresServicio("Debe ingresar una contraseña para verificar");
        }
        if (!clave2.equals(clave1)) {
            throw new ErroresServicio("Las claves ingresadas no son iguales");
        }
        if (foto == null) {
            throw new ErroresServicio("Debe ingresar una foto");
        }

    }
    
    //metodo para verificar en el metodo modificar ya que son menos campos, y se usa ese metodo dentro del otro para cuando creamos
    public void validarTransportista2(String nombre, String apellido, String mail, String zona, String telefono) throws ErroresServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErroresServicio("Debe ingresar un nombre");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new ErroresServicio("Debe ingresar un apellido");
        }
        if (mail == null || mail.isEmpty()) {
            throw new ErroresServicio("Debe ingresar un mail");
        }
        if (telefono == null || telefono.isEmpty()) {
            throw new ErroresServicio("Debe ingresar un telefono ");
        }
        verificarnumeros(telefono);
        if (zona == null || zona.isEmpty()) {
            throw new ErroresServicio("Debe ingresar una zona");
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

    //metodo para calcular el promedio de valoracion del transportista
    public Integer valoracion(Transportista transportista) {
        List<Comprobante> comprobante = transportista.getComprobante();
        //comprobar que de la cantidad de elementos no nulos
        Integer cantidad = comprobante.size();
        Integer valoracion = 0;
        for (Comprobante factura : comprobante) {
            if (factura.getValoracion() != null) {
                valoracion = factura.getValoracion() + valoracion;
            }
        }
        Integer promedio = (int) valoracion / cantidad;
        
        return promedio;
    }

    //metodo para buscar a un transportista por ID y separar la capa
    public Transportista buscarID(String trasnportistaID) throws ErroresServicio {
        Optional<Transportista> respuesta = repositorioTransportista.findById(trasnportistaID);
        if (respuesta.isPresent()) {
            Transportista transportista = respuesta.get();
            return transportista;
        } else {
            throw new ErroresServicio("No se encuentra un transportista con ese id");
        }
    }

    //metodo para asignar al trasnportista que escogio el proveedor al comprobante
    @Transactional
    public void asignacionTransportida(String id_proveedor, String id_viaje, String id_transportista) throws ErroresServicio {

        Optional<Comprobante> comprobante = repositorioComprobante.buscarComprobanteporIdViaje(id_viaje);
        //comprueba que el id del proveedor sea igual al id del proveedor que creo el, comprobante
        if (comprobante.isPresent()) {
            if (id_proveedor.equals(comprobante.get().getProveedor().getId())) {
                Optional<Transportista> transportista = repositorioTransportista.findById(id_transportista);
                if (transportista.isPresent()) {

                    transportista.get().getComprobante().add(comprobante.get());
                    System.out.println(comprobante.get());
                    comprobante.get().getViaje().setTransportistaAplicado(transportista.get());
                    comprobante.get().setValoracion(0);
                    comprobante.get().getViaje().setEstado(EstadoEnum.VIAJANDO);
                    enViaje(transportista.get().getId());

                } else {
                    throw new ErroresServicio("El transportista no existe o no se pudo encontrar");

                }
                //busca el transportista y le setea el comprobante
            } else {
                throw new ErroresServicio("Usted no es el proveedor que creo el viaje, no puede elejir el transportista");
            }
        } else {
            throw new ErroresServicio("El comprobante no existe o no se pudo encontrar");

        }
    }

    //metodo para cambiar el el boolean de viajando
    @Transactional
    public void enViaje(String id_trasnportista) throws ErroresServicio {
        Transportista transportista = buscarID(id_trasnportista);
        if (transportista.isViajando()) {
            transportista.setViajando(false);
        } else {
            transportista.setViajando(true);
        }
    }

    public List listarTransportista() {
        List<Transportista> listado = repositorioTransportista.findAll();
        return listado;
    }
    
    //metoso para listar a los trasportistas que se encuentran disponible
    public List listarTrasportistaLibres(){
        List<Transportista> libres=repositorioTransportista.buscarTransportistaLibres();
        return libres;
    }
    //metodo para comunicarle al proveedor sobre como va el viaje
    public String comunicarAvance(String id_trasnportista, String option)throws ErroresServicio{
        Optional<Transportista> respuesta = repositorioTransportista.findById(id_trasnportista);  
        if (respuesta.isPresent()) {
            List <Comprobante> comprobantes = respuesta.get().getComprobante();
            System.out.println("la lista de comprobantes es");
            System.out.println(comprobantes);
            System.out.println(comprobantes.size()+" es el numero de elementos de la lista");
         
            Comprobante comprobante1= comprobanteServicio.buscarComprobanteAbierto(comprobantes);
            Viaje viaje=comprobante1.getViaje();
//            for (Comprobante comprobante : comprobantes) {
//                if (comprobante.getValoracion()==0) {
//                    viaje = comprobante.getViaje();
//                    System.out.println("el viaje= "+viaje);
//                    comprobante1 = comprobante;
//                    System.out.println("el comprobante= "+comprobante1);
//                }
//            }
            opciones(option,viaje);
            return viaje.getID();
            
        }else{
            throw new ErroresServicio("no se encontro el trasnportista");
        } 
    }
    
    @Transactional
    public void opciones(String option,Viaje viaje){
        switch(option){
                case "1":
                    System.out.println("envia correo opcion 1");
                    //notificacionServicio.enviar("El trasnportista "+respuesta.get().getApellido()+", "+respuesta.get().getNombre()+" a recogido la carga del lugar de origen", "Carga Recogida", comprobante1.getProveedor().getMail());
                    //enviar una notificacion
                    break;
                case "2":
                    System.out.println("envia correo opcion 2");
                    //notificacionServicio.enviar("El trasnportista "+respuesta.get().getApellido()+", "+respuesta.get().getNombre()+" va camino a su destino", "En camino", comprobante1.getProveedor().getMail());
                    //enviar una notificacion
                    break;
                case "3":
                    System.out.println("enviar correo opcion 3");
                    //notificacionServicio.enviar("El trasnportista "+respuesta.get().getApellido()+", "+respuesta.get().getNombre()+" a entregado la carga, aho", "Carga Entregada", comprobante1.getProveedor().getMail());
                    //enviar una notificaion
                    break;
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
