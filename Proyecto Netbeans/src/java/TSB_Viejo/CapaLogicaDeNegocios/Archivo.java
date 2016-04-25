/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TSB_Viejo.CapaLogicaDeNegocios;

import TSB_Viejo.CapaLogicaDeNegocios.Palabra;
import Soporte.Cronometro;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Jose
 */
public class Archivo  extends File {
    // el origen es el path que ya se hereda de File
    //private String origen;
    //hacemos herencia porque file es una clase y aparte tenemos que agregar metodos propiios de nuestro dominio
    

    public Archivo(String string) {
        super(string);
    }
    

    public Palabra[] leerTodasLasPalabrasFede(){
        //voy a cronometrar el tiempo
        Cronometro cronometro = new Cronometro();
        cronometro.iniciar();
        Palabra[] palabras = null;
        try
        {
        FileReader f = new FileReader(getPath());

        Scanner sc = new Scanner(f);
        
        FileReader f1 = new FileReader(getPath());

                sc.useDelimiter("[ ,\"\n\\s\\[\\]'=/@$~#;#*!><?¿\\«¡\\».)(\\-:_]+");
                Scanner sc1 = new Scanner(f1);
                sc1.useDelimiter("[ ,\"\n\\s\\[\\]'=/@$~#;#*!><?¿\\«¡\\».)(\\-:_]+");
        int i = 0;
        while(sc.hasNext())
        {
            i++;
            sc.next();
        }
       palabras = new Palabra[i];
        int n = 0;

        
        while (sc1.hasNext()) {;
            String st = sc1.next();
                    if (validar(st)) {
                    n++;
                    String minusc = st.toLowerCase();
                    Palabra p = new Palabra(minusc,getName());  
                    palabras[n] = p;
               
                }
            }


        
        }
        catch(FileNotFoundException e)
        {
            e.toString();
        }
        Palabra[] pal = clean(palabras);
        cronometro.parar();
        System.out.println("Tiempo en leer metodo fede: "+ cronometro.tiempo());
        
        return pal;
    } 
    
    // al final no lo usamos
    public String guardarBD(){
        String consulta="";
        consulta="INSERT INTO ACHIVO VALUES (default,'"+this.getName()+"')";
        return  consulta;
    }
    
        private static boolean validar(String palabra) {
        for (char a : palabra.toCharArray()) {
            if (Character.isDigit(a)) {
                return false;

            }

        }
        return true;
    }
        
        public Palabra[] leerTodasLasPalabrasVersioJose(){
        //voy a cornometrar el tiempo
        Cronometro cronometro = new Cronometro();
        cronometro.iniciar();
        //leo todo el libro en un string
        String libroCompleto = null;
        try {
            libroCompleto = new String(Files.readAllBytes(Paths.get(getPath())));
        } catch (IOException ex) {
            Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("Sin filtro: \n" + libroCompleto);
        //para no complicarnos primero elimino las cosas que no van y luego las palabra alfanumericas
        //todo las palabras que tiene un numero
        // 2 horas reloj para encontrar la puta expresion regular y no anda.
        String filtro = "\\b[^A-Za-z]*\\b";
        filtro = "[^\\\\p{L}\\\\p{Nd}]+";
        filtro = "[^[\\s|\\t|\\n|\\r|] A-Za-z$]*";
        filtro = "[^ A-Za-z$ ]*";
        filtro = "[^A-Za-z$]*";
        String libroFiltrado;
        //anda maso menos
        libroFiltrado = libroCompleto.replaceAll("\\b[^A-Za-z]*\\b", " ");
        // como me quedan mucho espacion en balnco los camvbio por uno solo para poder despues hacer el split
        //libroFiltrado = libroCompleto.replaceAll("[ ]*", " ");
        libroFiltrado = libroFiltrado.toLowerCase();
        //System.out.println("---------- LIBRO ILTRADO");
        //System.out.println(libroFiltrado);
        //CON LO MISMO QUE REEMPLAZO
        String [] cadenas = libroFiltrado.split(" ");
        
        Palabra [] palabras = new Palabra[cadenas.length];
        //System.out.println("----------TODAS LAS PALABRAS");
        for (int i = 0; i < cadenas.length ; i++) {
            palabras[i] = new Palabra(cadenas[i],getName());
            //ystem.out.println(palabras[i]);
            
        }
        cronometro.parar();
        System.out.println("Tiempo en leer: "+ cronometro.tiempo());
        return palabras;
    } 
        
        public static Palabra[] clean(final Palabra[] v) {
    List<Palabra> list = new ArrayList<Palabra>(Arrays.asList(v));
    list.removeAll(Collections.singleton(null));
    return list.toArray(new Palabra[list.size()]);
}
 
    
}
