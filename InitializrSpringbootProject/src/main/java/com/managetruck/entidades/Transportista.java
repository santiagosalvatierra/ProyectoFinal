package com.managetruck.entidades;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Transportista extends Usuario {

    private boolean estado;
    private boolean viajando;
    @OneToOne
    private Camion camion;
    private Integer cantidadViajes;
    private double valoracion;
    
    @OneToMany
    private List<Comprobante> comprobante;

    public Transportista() {
    }

    public Transportista(boolean estado, Camion camion, Integer cantidadViajes, double valoracion, List comprobante) {
        this.estado = estado;
        this.camion = camion;
        this.cantidadViajes = cantidadViajes;
        this.valoracion = valoracion;
        this.comprobante = comprobante;
    }

    public boolean isViajando() {
        return viajando;
    }

    public void setViajando(boolean viajando) {
        this.viajando = viajando;
    }

    
    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Camion getCamion() {
        return camion;
    }

    public void setCamion(Camion camion) {
        this.camion = camion;
    }

    public Integer getCantidadViajes() {
        return cantidadViajes;
    }

    public void setCantidadViajes(Integer cantidadViajes) {
        this.cantidadViajes = cantidadViajes;
    }

    public double getValoracion() {
        return valoracion;
    }

    public void setValoracion(double valoracion) {
        this.valoracion = valoracion;
    }

    public List getComprobante() {
        return comprobante;
    }

    public void setComprobante(List comprobante) {
        this.comprobante = comprobante;
    }

    @Override
    public String toString() {
        return "Camion : " + camion + " Cantidad de Viajes : " + cantidadViajes + " Valoracion: " + valoracion ;
    }

}
