/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.managetruck.servicios;

import com.managetruck.entidades.Localidades;
import com.managetruck.entidades.Provincias;
import com.managetruck.errores.ErroresServicio;
import com.managetruck.repositorios.RepositorioLocalidades;
import com.managetruck.repositorios.RepositorioProvincias;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProvinciasServicio {

    @Autowired
    private RepositorioLocalidades repositorioLocalidades;
    
    @Autowired
    private RepositorioProvincias repositorioProvincias;

    public List buscarLocalidadesPorProvincia(String id_Provincia) {

        List<Localidades> localidades = repositorioLocalidades.buscarLocalidadporIddeProvincia(id_Provincia);
        return localidades;
    }

    public List listarProvincias() {

        List<Provincias> provincias = repositorioProvincias.findAll();
        return provincias;
    }
    public List listarProvinciasPorNombre(String nombre){
        List<Provincias> provincias = repositorioProvincias.buscarProvinciaPorNombre(nombre);
        return provincias;
    }
    //metodo para buscar todas las provincias
    public List<Provincias> listarProvinciasTotales(){
        List<Provincias> provincias = repositorioProvincias.buscarProvinciastotales();
        return provincias;
    }
    
}
