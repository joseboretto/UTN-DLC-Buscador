/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TSB_Viejo.CapaLogicaDeNegocios;

import DB.DB;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Jose
 */
public class Diccionario {
    private String nombre;
    private Timestamp fecha;
    private HashMap <String,  List <Frecuencia>> tablaDeFrecuencias;

    public Diccionario(String nombre, HashMap<String, List<Frecuencia>> tablaDeFrecuencias) {
        this.nombre = nombre;
        this.fecha = new Timestamp(System.currentTimeMillis());
        this.tablaDeFrecuencias = tablaDeFrecuencias;
    }

    

    public Diccionario() {
    }

    public String getNombre() {
        return nombre;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public HashMap<String, List<Frecuencia>> getTablaDeFrecuencias() {
        return tablaDeFrecuencias;
    }


    @Override
    public String toString() {
        String resultado= "Diccionario{" + "nombre=" + nombre + ", fecha=" + fecha;
        // Imprimimos el Map con un Iterador
        Iterator it = tablaDeFrecuencias.keySet().iterator();
        while(it.hasNext()){
          String nombreClave =  (String) it.next();
          resultado+="\n Clave: " + nombreClave + " -> Valor: " + tablaDeFrecuencias.get(nombreClave);
        }
        return resultado;
    }
    
    public Object[][] getDatos(){
        //se encarga de obtener la data de los 10 primaeros elementos de la tabla hash
        Object[][] data;
        // como maximo voy a mostar 10, ya que es solo una preview
        int tamanio = 10;
        if (tablaDeFrecuencias.size()<10) {
            tamanio = tablaDeFrecuencias.size();
        }
        data = new Object[tamanio][3];
        //con el index voy recorriendo el arregloc como un for'
        int index = 0;
         //obtengo el iterador de la hash map
        Iterator it = tablaDeFrecuencias.keySet().iterator();
        //saco todas las variable afuera del ciclo para no crearlas en cada ciclo
        int contadorTotal = 0;
         int cantidadArchivos = 0;
         List <Frecuencia> listaEncontrada;
         String nombreClave;
         //empiezo a recorrer
        while(it.hasNext() && index <data.length){
                //me olvide de reiniciar el contadir y el contador de archivos
                contadorTotal = 0;
                cantidadArchivos = 0;
                //itero todas las claves
               nombreClave =  (String) it.next();
               //obtengo el valor de esa clave
               listaEncontrada = tablaDeFrecuencias.get(nombreClave);
                        for (Frecuencia frecuenciaEncontrada : listaEncontrada) {
                            //una vez que tengo la lsoiyta la recorro y sumo
                            contadorTotal+= frecuenciaEncontrada.getContador();
                            cantidadArchivos++;}
               Object[] fila = {nombreClave,contadorTotal,cantidadArchivos};
               data [index] = fila;
               index++;
        }
        return data;
       
    }
    
    
    public String guardarBDDiccionaro(){
        System.out.println("Se esta guardando el diccionario");
        String consulta="";
        consulta=" INSERT INTO Diccionario VALUES (default,'"+nombre+"', TIMESTAMP('"+fecha.toString()+"'))";
        return  consulta;
    }
    

    public PreparedStatement connectarYguardarBDFrecuencias2(int pkDiccionario) throws SQLException{
         System.out.println("Se esta armando el batch de las frecuencias");
        DB baseDatos = new DB();
        PreparedStatement pstmt = baseDatos.obtenerSentenciaPreparada("INSERT INTO SUPERTABLA VALUES (?, ?, ?, ?)");

        int contador = 0;
        String archivoEncontrado =""; 
        // Imprimimos el Map con un Iterador
        Iterator it = tablaDeFrecuencias.keySet().iterator();
        while(it.hasNext()){
            
            String nombreClave =  (String) it.next();
            List <Frecuencia> listaEncontrada = tablaDeFrecuencias.get(nombreClave);
            for (Iterator<Frecuencia> iterator = listaEncontrada.iterator(); iterator.hasNext();) {
                Frecuencia next = iterator.next();
                contador = next.getContador();
                archivoEncontrado = next.getPalabra().getArchivo();
                pstmt.setString(1, archivoEncontrado);
                pstmt.setString(2, nombreClave);
                pstmt.setInt(3, pkDiccionario);
                pstmt.setInt(4, contador);
                pstmt.addBatch();
                //megaConsulta = "INSERT INTO SUPERTABLA VALUES ('"+archivoEncontrado+"','"+nombreClave+"',"+pkDiccionario+","+contador+")";
                //ESTO ESTA MAL YA QUE SE EJECUTA VARIAS VECES Y TIENE MUCHO ACOMPLAMIENTO 
                //st.executeUpdate(megaConsulta);
            }
        }
        return pstmt;
        
    }
}
