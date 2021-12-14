
package com.proyectofinal.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Foto {
    private Byte contenido;
    private String mime;
    private String nombre;
    
     @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid",strategy="uuid2")
    private String ID;

    public Foto() {
    }

    public Foto(Byte contenido, String mime, String nombre) {
        this.contenido = contenido;
        this.mime = mime;
        this.nombre = nombre;
    }
     
    public Byte getContenido() {
        return contenido;
    }

    public void setContenido(Byte contenido) {
        this.contenido = contenido;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "Foto{" + "contenido=" + contenido + ", mime=" + mime + ", nombre=" + nombre + ", ID=" + ID + '}';
    }
    
}
