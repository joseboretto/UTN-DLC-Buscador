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
public class Frecuencia {
    private int contador;
    private Palabra palabra;

    public Frecuencia(Palabra palabra) {
        this.contador = 1;
        this.palabra = palabra;
    }
    
    public void sumar()
    {
        this.contador = this.contador+1;
    }

    public int getContador() {
        return contador;
    }

    public Palabra getPalabra() {
        return palabra;
    }

    @Override
    public int hashCode() {
        return palabra.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return palabra.equals(obj);
    }
    
    public String mostrarTodo() {
        return "Frecuencia{" + "contador=" + contador + ", palabra=" + palabra + '}';
        
    }

    @Override
    public String toString() {
        return "Frecuencia{" + "contador=" + contador + ", archivo=" + palabra.getArchivo() + '}';
    }
    
    

    
    
    
    
    
}
