
package com.managetruck.entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Proveedor extends Usuario{

    private String razonSocial;
    private Integer cuilEmpresa;
    private String nombreEmpresa;

    public Proveedor() {
    }

    public Proveedor(String razonSocial, Integer cuilEmpresa, String nombreEmpresa) {
        this.razonSocial = razonSocial;
        this.cuilEmpresa = cuilEmpresa;
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Integer getCuilEmpresa() {
        return cuilEmpresa;
    }

    public void setCuilEmpresa(Integer cuilEmpresa) {
        this.cuilEmpresa = cuilEmpresa;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    @Override
    public String toString() {
        return "Proveedor{" + "razonSocial=" + razonSocial + ", cuilEmpresa=" + cuilEmpresa + ", nombreEmpresa=" + nombreEmpresa + '}';
    }
    
}
