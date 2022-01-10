/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.managetruck.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Localidades {
    private String localidad;
    @Id
    private Integer id;
    @ManyToOne
    private Provincias provincias;

    public Localidades() {
    }

    public Localidades(String localidad, Integer id, Provincias provincias) {
        this.localidad = localidad;
        this.id = id;
        this.provincias = provincias;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Provincias getProvincias() {
        return provincias;
    }

    public void setProvincias(Provincias provincias) {
        this.provincias = provincias;
    }
    //prueba
}
