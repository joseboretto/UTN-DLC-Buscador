/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jose
 */
public class AccesoDatos {
    private Statement statment;
    private Connection conexion;
    private int seleccionBaseDatos;
    private static final int derby = 1;
    private static final int onlineSQLserver = 2;

    
    
    private final String nombreBD_Derby = "jdbc:derby://localhost:1527/dlc";
    private final String usuarioBD_Derby = "dlc";
    private final String contrasenaBD_Derby = "dlc";
    
    private final String nombreBD_onlineSQLserver = "jdbc:sqlserver://trabajoDLC.mssql.somee.com";
    private final String usuarioBD_onlineSQLserver = "boretto95_SQLLogin_1";
    private final String contrasenaBD_onlineSQLserver = "vsc5753kcf";
    

    
 


    public AccesoDatos() {
        seleccionBaseDatos = onlineSQLserver;
    }
    
    
    private void conectarBD(){
        switch (seleccionBaseDatos){
            case derby:
                try {
                    Class.forName("org.apache.derby.jdbc.ClientDriver");
                    conexion = DriverManager.getConnection(nombreBD_Derby, usuarioBD_Derby, contrasenaBD_Derby);
                    statment = conexion.createStatement();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AccesoDatos.class.getName()).log(Level.SEVERE,
                            "No se pudo cargar el driver de la base de datos", ex);
                } catch (SQLException ex) {
                    Logger.getLogger(AccesoDatos.class.getName()).log(Level.SEVERE,
                            "No se pudo obtener la conexión a la base de datos", ex);
                }
            case onlineSQLserver:
                try {
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    conexion = DriverManager.getConnection(nombreBD_onlineSQLserver, usuarioBD_onlineSQLserver, contrasenaBD_onlineSQLserver);
                    statment = conexion.createStatement();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AccesoDatos.class.getName()).log(Level.SEVERE,
                            "No se pudo cargar el driver de la base de datos", ex);
                } catch (SQLException ex) {
                    Logger.getLogger(AccesoDatos.class.getName()).log(Level.SEVERE,
                            "No se pudo obtener la conexión a la base de datos", ex);
                }
        }
                
    }
    
    private  void DesconectarBD() {
        try {
            if (conexion != null) {
                if (statment != null) {
                    statment.close();
                }
                conexion.close();
            }
        } catch (Exception ex) {
            Logger.getLogger(AccesoDatos.class.getName()).log(Level.SEVERE,
                    "No se pudo CERRAR la conexión a la base de datos", ex);
        }
    }
    
    public void ejecutarUpdate (String consulta) throws SQLException{
        try {
            conectarBD();
            statment.executeUpdate(consulta);
            DesconectarBD();
        } catch (SQLException ex) {
            Logger.getLogger(AccesoDatos.class.getName()).log(Level.SEVERE, "Error al ejecutar Update", ex);
            throw ex;
            
        }
        DesconectarBD();
    }
    
     public ResultSet ejecutarConsulta (String consulta){
         try {
            conectarBD();
            ResultSet rs = statment.executeQuery(consulta);
            return rs;
            
        } catch (SQLException ex) {
            Logger.getLogger(AccesoDatos.class.getName()).log(Level.SEVERE, "Error al ejecutar Consulta", ex);
        }
        return null;
        
    }
}
