
package com.proyectofinal.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;
@Entity
public class Comprobante {
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid",strategy="uuid2")
    private String ID;
    private Integer valoracion;
    @OneToOne
    private Proveedor proveedor;
    @OneToOne
    private Viaje viaje;

    public Comprobante() {
    }

    public Comprobante(Integer valoracion, Proveedor proveedor, Viaje viaje) {
        this.valoracion = valoracion;
        this.proveedor = proveedor;
        this.viaje = viaje;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Integer getValoracion() {
        return valoracion;
    }

    public void setValoracion(Integer valoracion) {
        this.valoracion = valoracion;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Viaje getViaje() {
        return viaje;
    }

    public void setViaje(Viaje viaje) {
        this.viaje = viaje;
    }

    @Override
    public String toString() {
        return "Comprobante{" + "ID=" + ID + ", valoracion=" + valoracion + ", proveedor=" + proveedor + ", viaje=" + viaje + '}';
    }
    
}
