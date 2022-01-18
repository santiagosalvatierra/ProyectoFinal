package com.managetruck.entidades;

import com.managetruck.enumeracion.EstadoEnum;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Viaje {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String ID;
    private Integer peso;
    private Integer kmRecorridos;
    private String tipoCargas;
    private boolean alta = true;
    private String destino;
    
    @Enumerated(EnumType.STRING)
    private EstadoEnum estado;
    private String origen;
    @OneToMany
    private List <Transportista> listadoTransportista;
    @OneToOne
    private Transportista transportistaAplicado;
    public Viaje() {
    }

    public Viaje(String ID, Integer peso, Integer kmRecorridos, String tipoCargas, String destino, EstadoEnum estado, String origen, List<Transportista> listadoTransportista, Transportista transportistaAplicado) {
        this.ID = ID;
        this.peso = peso;
        this.kmRecorridos = kmRecorridos;
        this.tipoCargas = tipoCargas;
        this.destino = destino;
        this.estado = estado;
        this.origen = origen;
        this.listadoTransportista = listadoTransportista;
        this.transportistaAplicado = transportistaAplicado;
    }

    public EstadoEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoEnum estado) {
        this.estado = estado;
    }

    

    

   
    public Transportista getTransportistaAplicado() {
        return transportistaAplicado;
    }

    public void setTransportistaAplicado(Transportista transportistaAplicado) {
        this.transportistaAplicado = transportistaAplicado;
    }

    

    public List<Transportista> getListadoTransportista() {
        return listadoTransportista;
    }

    public void setListadoTransportista(List<Transportista> listadoTransportista) {
        this.listadoTransportista = listadoTransportista;
    }

    

    @Override
    public String toString() {
        return "Peso en Kilogramos=" + peso  + " Tipo de Cargas=" + tipoCargas + " Origen=" + origen + " Destino=" + destino+ " kmRecorrer=" + kmRecorridos;
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
