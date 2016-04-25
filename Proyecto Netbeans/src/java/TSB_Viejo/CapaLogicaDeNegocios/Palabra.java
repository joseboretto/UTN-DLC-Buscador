/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TSB_Viejo.CapaLogicaDeNegocios;

import java.util.Objects;

/**
 *
 * @author Jose
 */
public class Palabra {
    private String nombre;
    private String archivo;

    public Palabra(String nombre, String archivo) {
        this.nombre = nombre;
        this.archivo = archivo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Palabra other = (Palabra) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.archivo, other.archivo)) {
            return false;
        }
        return true;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    @Override
    public String toString() {
        return "Palabra{" + "nombre=" + nombre + ", archivo=" + archivo + '}';
    }
    
    public String guardarBD(){
        String consulta="";
        consulta="INSERT INTO PALABRA VALUES (default,'"+nombre+"')";
        return  consulta;
    }

    
    
    
    
    
    
}
