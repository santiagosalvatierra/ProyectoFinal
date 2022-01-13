
package com.managetruck.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Provincias {
    
     private String provincia;
     
     @Id
     private Integer id;

    public Provincias() {
    }

    public Provincias(String provincia, Integer id) {
        this.provincia = provincia;
        this.id = id;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return provincia;
    }
    
}
