
package com.managetruck.entidades;

import com.managetruck.enumeracion.Role;
import com.managetruck.enumeracion.Rubro;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Proveedor extends Usuario{

    private String razonSocial;
    private String cuilEmpresa;
    private String nombreEmpresa;
    
    @Enumerated(EnumType.STRING)
    private Rubro rubro;

    public Proveedor() {
    }

    public Proveedor(String razonSocial, String cuilEmpresa, String nombreEmpresa) {
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

    public String getCuilEmpresa() {
        return cuilEmpresa;
    }

    public void setCuilEmpresa(String cuilEmpresa) {
        this.cuilEmpresa = cuilEmpresa;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public Rubro getRubro() {
        return rubro;
    }

    public void setRubro(Rubro rubro) {
        this.rubro = rubro;
    }

    @Override
    public String toString() {
        return nombreEmpresa;
    }

   
    
}
