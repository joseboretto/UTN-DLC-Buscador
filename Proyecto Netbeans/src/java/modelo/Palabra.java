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
public class Palabra implements Comparable {
    private String nombre;
    
    //en una clase llamada vocablo, es decir, cada objeto del vocabulario;
    // aca hay que tirar un metodo compare to, por si la palabra viene con mayuscula, aunque creo qeu el filtro ya lo omite
    //delegacion pura, aguante DSI!

    @Override
    public int compareTo(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

