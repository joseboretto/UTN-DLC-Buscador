/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author a6
 */
public class Vocablo {
    private Palabra palabra;
    private int contadorArhivos;
    private int frecuenciaMaxima;

    public Palabra getPalabra() {
        return palabra;
    }

    public void setPalabra(Palabra palabra) {
        this.palabra = palabra;
    }

    public int getContadorArhivos() {
        return contadorArhivos;
    }

    public void setContadorArhivos(int contadorArhivos) {
        this.contadorArhivos = contadorArhivos;
    }

    public int getFrecuenciaMaxima() {
        return frecuenciaMaxima;
    }

    public void setFrecuenciaMaxima(int frecuenciaMaxima) {
        this.frecuenciaMaxima = frecuenciaMaxima;
    }

     
    
}
