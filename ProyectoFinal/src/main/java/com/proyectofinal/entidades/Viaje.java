
package com.proyectofinal.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Viaje {
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid",strategy="uuid2")
    private String ID;
    private Integer peso;
    private Integer kmRecorridos;
    private String tipoCargas;
    private boolean alta;
    private String destino;
    private String origen;

    public Viaje() {
    }

    public Viaje(Integer peso, Integer kmRecorridos, String tipoCargas, boolean alta, String destino, String origen) {
 
        this.peso = peso;
        this.kmRecorridos = kmRecorridos;
        this.tipoCargas = tipoCargas;
        this.alta = alta;
        this.destino = destino;
        this.origen = origen;
    }

    @Override
    public String toString() {
        return "Viaje{" + "ID=" + ID + ", peso=" + peso + ", kmRecorridos=" + kmRecorridos + ", tipoCargas=" + tipoCargas + ", alta=" + alta + ", destino=" + destino + ", origen=" + origen + '}';
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public Integer getKmRecorridos() {
        return kmRecorridos;
    }

    public void setKmRecorridos(Integer kmRecorridos) {
        this.kmRecorridos = kmRecorridos;
    }

    public String getTipoCargas() {
        return tipoCargas;
    }

    public void setTipoCargas(String tipoCargas) {
        this.tipoCargas = tipoCargas;
    }

    public boolean isAlta() {
        return alta;
    }

    public void setAlta(boolean alta) {
        this.alta = alta;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }
    
}
