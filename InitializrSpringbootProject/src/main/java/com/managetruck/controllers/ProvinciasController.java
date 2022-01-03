/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.managetruck.controllers;

import com.managetruck.entidades.Localidades;
import com.managetruck.entidades.Provincias;
import com.managetruck.errores.ErroresServicio;
import com.managetruck.servicios.ProvinciasServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/provincia")
public class ProvinciasController {

    @Autowired
    ProvinciasServicio provinciasServicio;

    @GetMapping("/localidades")
    public String listarLocalidadesporIdProvincia(Model modelo, String id_Provincia) {

        List<Localidades> localidades = provinciasServicio.buscarLocalidadesPorProvincia(id_Provincia);
        modelo.addAttribute("tittle", "Listado Localidades");
        modelo.addAttribute("localidades", localidades);
        return null;
    }

    @GetMapping("")
    public String listarProvincias(Model modelo, @RequestParam(required = false) String nombre) {
        if (nombre != null) {
            List<Provincias> provincias = provinciasServicio.listarProvinciasPorNombre(nombre);
            modelo.addAttribute("tittle", "Listado Provincias");
            modelo.addAttribute("provincias", provincias);
        } else {
            List<Provincias> provincias2 = provinciasServicio.listarProvincias();
            modelo.addAttribute("tittle", "Listado Provincias");
            modelo.addAttribute("provincias", provincias2);
        }
        return null;

    }

}
