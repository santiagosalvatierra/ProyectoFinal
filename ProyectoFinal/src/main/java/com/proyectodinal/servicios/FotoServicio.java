/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyectodinal.servicios;

import com.proyectofinal.entidades.Foto;
import com.proyectofinal.errores.ErroresServicio;
import com.proyectofinal.repositorios.RepositorioFoto;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoServicio {
    
    @Autowired
    private RepositorioFoto repositorioFoto;
    
    //Metodo para guardar la foto
    @Transactional
    public Foto guardar(MultipartFile archivo) throws ErroresServicio {
        if (archivo != null && !archivo.isEmpty()) {
            try {
                Foto foto = new Foto();
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());

                return repositorioFoto.save(foto);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }
    
    //Metodo para actualizar una foto
    @Transactional
    public Foto actualizar(String idFoto, MultipartFile archivo) throws ErroresServicio{
        if (archivo != null && !archivo.isEmpty()) {
            try {
                Foto foto = new Foto();
                //como es un metodo para modificar hay que verificar si existe una foto anterior para reemplazarla
                if (idFoto !=null) {
                    Optional<Foto> respuesta = repositorioFoto.findById(idFoto);
                    if (respuesta.isPresent()) {
                        //si todo esto se cumple se pisa la foto anterior con la actual
                        foto=respuesta.get();
                    }  
                }
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());

                return repositorioFoto.save(foto);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
        return null;
    }
    
}
