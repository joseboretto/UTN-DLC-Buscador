/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Jose
 */
public class PosteoPersistencia{
    private AccesoDatos accesoDatos;

    public PosteoPersistencia() {
        accesoDatos = new AccesoDatos();
    }
    


    public boolean crear(Object objeto) {
        return true;
    }

    public boolean eliminar(Object clavePrimaria) {
        return true;
    }


    public Object leer(Object clavePrimaria) {
        return true;
    }

    public boolean Actualizar (Object objeto, int clavePrimaria) {
        return true;
    
    }

    public Object listar() {
        //se encarga de armar la consulta
        String consulta = "SELECT * FROM asdasdasdadsasd";        
        return accesoDatos.ejecutarConsulta(consulta);
        
    }
    
}
