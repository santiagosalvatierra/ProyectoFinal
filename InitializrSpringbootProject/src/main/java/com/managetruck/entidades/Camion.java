
package com.managetruck.entidades;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;
@Entity
public class Camion {
    private String descripcion;
    private Integer pesoMaximo;
    private String modelo;
    private Integer anio;
    private String patente;
    private Integer poliza;
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid",strategy="uuid2")
    private String ID;
    
    @OneToMany
    private List<Foto> foto;

    public Camion() {
    }

    public Camion(String descripcion, Integer pesoMaximo, String modelo, Integer anio, String patente, Integer poliza, Foto foto) {
        this.descripcion = descripcion;
        this.pesoMaximo = pesoMaximo;
        this.modelo = modelo;
        this.anio = anio;
        this.patente = patente;
        this.poliza = poliza;
        this.foto =  (List<Foto>) foto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getPesoMaximo() {
        return pesoMaximo;
    }

    public void setPesoMaximo(Integer pesoMaximo) {
        this.pesoMaximo = pesoMaximo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public Integer getPoliza() {
        return poliza;
    }

    public void setPoliza(Integer poliza) {
        this.poliza = poliza;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Foto getFoto() {
        return (Foto) foto;
    }
    
    public void setFoto(List<Foto> foto) {
        this.foto =  (List<Foto>) foto;
    }
    
}
