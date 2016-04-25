/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TSB_Viejo.CapaLogicaDeNegocios;
// Esta es la forma de programar un dependencia (temporal)
// Aca explica todas las relaciones, leer bien para entender
//https://vaughnvernon.co/?page_id=31
//http://www.vogella.com/tutorials/DependencyInjection/article.html
import TSB_Viejo.CapaLogicaDeNegocios.Archivo;
import DB.DB;
import Soporte.Cronometro;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



/**
 *
 * @author Jose
 */
public class GestorDiccionarios {
    
    //ESTO ESTA MAL PORQUE ES UNA ASOCIACION, ALGO ESTATICO.
    // NO SE ME OCURRE OTRA FORMA DE TENER LOS DICCIONARIOS EN MEMORIA
    private Diccionario diccionarioEnMemoria;
    
    
    public GestorDiccionarios() {
    }
    
    public Diccionario nuevoDiccionario(String nombreDic, Archivo[] archivosTexto){
        //En este punto tengo todas las palabras filtradas pero sin contar.
        //COMO TENGO VARIOs archivos TENGO QUE RECORRERLOS Y SACAR LAS PALABRAS FILTRADAS EN UN SOLLO VECTOR
        Palabra[] TodasLaspalabras =  new Palabra[0];
        for (int i = 0; i < archivosTexto.length; i++) {
            Palabra[] palabras = archivosTexto[i].leerTodasLasPalabrasFede();
            Palabra[] temp = new Palabra[palabras.length+TodasLaspalabras.length];
            System.arraycopy( palabras, 0, temp, 0, palabras.length );
            System.arraycopy( TodasLaspalabras, 0, temp, palabras.length, TodasLaspalabras.length );
            TodasLaspalabras = temp;
        }

        //CORNOMETRO LA TABLA HAS
        Cronometro cronometro = new  Cronometro();
        cronometro.iniciar();
        //EL HASH MAP COMO MAXIMO VA A TENER EL TAMANIO DE TODAS LAS PALABRAS (SI FUERAN TODAS UNICAS)
        HashMap <String,  List <Frecuencia>> tablaDeFrecuencias = new HashMap <String, List <Frecuencia>>(TodasLaspalabras.length);
        //recorro todas las palabras y las agrego a la tabla.
        for (int i = 0; i < TodasLaspalabras.length; i++) {
            // una palabra va a tener una nombre y un archivo
            Palabra palabraAagregar = TodasLaspalabras[i];      
            // la funcion constains busca por valor y utiliza el hashCode que solo depende del nombre
            // etocnes 2 palabras distintas pero con el mismmo nombre van a estar en el mismo luagr
            if (tablaDeFrecuencias.get(palabraAagregar.getNombre()) != null){
                    // una vez que se que la palabra (clave) esta en la tabla voy a buscar si es igual, es decir en el mismo archivo
                    List <Frecuencia> listaEncontrada = tablaDeFrecuencias.get(palabraAagregar.getNombre());
                    //pregunto si esta en la lista
                        //en caso de estar voy a sumarle uno al contador
                        boolean estaEnLaLista = false;
                        for (Frecuencia frecuenciaEncontrada : listaEncontrada) {
                            
                            if (frecuenciaEncontrada.getPalabra().equals(palabraAagregar)) {
                                frecuenciaEncontrada.sumar();
                                estaEnLaLista = true;
                                break;
                                }
                        }
                        //si no esta en la lista esa palabra la voya a gregar
                        if (!estaEnLaLista) {
                            listaEncontrada.add(new Frecuencia(palabraAagregar));}
                }
            else{
                    //creo una lista de frecuencia, con lo mucho se leera de 5 archivos
                    List<Frecuencia> listaDeFrecuencias = new ArrayList<Frecuencia>(3);
                    // agrego la frecunai a la lista
                    listaDeFrecuencias.add(new Frecuencia(palabraAagregar));
                    //agrego la lista a la tablaHASh
                    tablaDeFrecuencias.put(palabraAagregar.getNombre(), listaDeFrecuencias);              
                }
            }
        Diccionario diccionario = new Diccionario(nombreDic, tablaDeFrecuencias);
        // una vez que lo creo lo asigno o se lo devuelvo a la pantalla?
        cronometro.parar();
        System.out.println("Tiempo de tabla Hash: "+ cronometro.tiempo());   
        System.out.println("Cantitdad de palabras en el diciocnario: " +tablaDeFrecuencias.size());
        return diccionario;
        
    }

    
    public String mostrarDicionario(Diccionario dic)
    {
        return dic.toString();
                
    }
    
    
    public boolean GuardarDicionario(Diccionario diccionario) throws SQLException{
        //antes de guardarlo lo puedo mostar en consola
        mostrarDicionario(diccionario);
        //CORNOMETRO
        Cronometro cronometro = new  Cronometro();
        cronometro.iniciar();
        //me conecto con la db
        //esto tiene que ser interno, ahy que armar 2 ducniones como en pav, una para consultar y otra para actualiza (insert)
        DB baseDatos = new DB();
        String consulta;
        //el diccionario sabe como guardarse entonces le pido a el que haga el trbajo
        consulta= diccionario.guardarBDDiccionaro();
        //inserto el nuevo diccionario y
        //busco el di del diciconario que acabo de insertar
        ResultSet rs = baseDatos.obtenerDatosyClaveGenerados(consulta);
        int generatedKey = 0;
        if (rs.next()) {
            generatedKey = rs.getInt(1);
        }
        System.out.println("Inserted record's ID: " + generatedKey);
        //ESTO ANDA PERO ESTA MAL, ES INEFICIENTE Y MUY ACOPLADO
        //LE DIGO AL DICCIONARIO EN MEORARI QUE SE RECORRA E INSERTE FILA POR FIILA.
        //ANTES QUERIA ARMAR UNA SOLA CONSULTA GIGANTE Y EJECUTARLA TODA DE UNA PERO NO PUDE HACERLO, SE PUEDE PERO NO ME SALE.
        PreparedStatement sentenciaPreparadaYCargado = diccionario.connectarYguardarBDFrecuencias2(generatedKey);
        sentenciaPreparadaYCargado.executeBatch();
       cronometro.parar();
       System.out.println("Tiempo que atrda en guardar: " + cronometro.tiempo());
       baseDatos.mostarDBEnCoslosa();
       return true; 
    }
}
