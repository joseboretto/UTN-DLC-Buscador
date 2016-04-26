/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;




/**
 *
 * @author jorge
 */
public class DB {
    
    
    private Connection conexion;
    private Statement sentencia;
    private ResultSet resultado;

    public DB() {
        try {
            crearTablasNecesarias();
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void conectaryCrearDB(){
        final String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        try {
            Class.forName(driver).newInstance();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }

        final String protocol = "jdbc:derby:";
        final String dbName = "derbyDB";
        try {
            //Connection connection = DriverManager.getConnection(protocol + dbName + ";create=true");
            conexion = DriverManager.getConnection(protocol + dbName + ";create=true");
            sentencia = conexion.createStatement();
        
        System.out.println("=====    Started/Connected DB    =====");
        
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

   private  void DesconectarBD() {
        try {
            if (conexion != null) {
                if (sentencia != null) {
                    sentencia.close();
                }
                conexion.close();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error ", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    
    public void mostarDBEnCoslosa() throws SQLException{
        ResultSet rs = sentencia.executeQuery("select * from diccionario");
        ResultSetMetaData metadata = rs.getMetaData();
 
        //Imprimimos la cabecera de la tabla
        int columnas = metadata.getColumnCount();
        for (int i = 1; i <= columnas; i++) {
            System.out.format("%15s", metadata.getColumnName(i) + " || ");
        }
 
        while (rs.next()) {
            //Imprimimos cada una de las filas de la tabla
            System.out.println("");
            for (int j = 1; j <= columnas; j++) {
                 System.out.format("%15s", rs.getString(j) + " || ");
            }
        }
    }
    
    private void crearTablasNecesarias() throws SQLException{
        
        if (!isTableExist("Diccionario")) {
            String tabla = "  CREATE TABLE Diccionario (\n" +
                                    " id_diccionario INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n" +
                                    " nombre VARCHAR(25) NOT NULL,\n" +
                                    " fecha TIMESTAMP NOT NULL) \n" +
                                    "\n";
            
            ejecutarUpdate(tabla);
            System.out.println("Creando la tabla: Diccionario");
        }
        
        if (!isTableExist("superTabla")) {
            String tabla = "CREATE TABLE  superTabla (\n" +
                                    " archivo VARCHAR(25) NOT NULL,\n" +
                                    " palabra VARCHAR(25) NOT NULL,\n" +
                                    " id_diccionario INT NOT NULL,\n" +
                                    " frecuencia INT NOT NULL,\n" +
                                    " PRIMARY KEY (archivo, palabra, id_diccionario))\n" +
                                    "\n";
            
            ejecutarUpdate(tabla);
            System.out.println("Creando la tabla: Supertabla");
            
            String alter = "Alter Table superTabla \n" +
                                    " Add FOREIGN KEY (id_diccionario)\n" +
                                    " References DICCIONARIO (id_diccionario)";
            ejecutarUpdate(alter);
        }

    
    }
    
    private  boolean isTableExist(String sTablename) throws SQLException{
        //ConectarBD();
        conectaryCrearDB();
        DatabaseMetaData dbmd = conexion.getMetaData();
        ResultSet rs = dbmd.getTables(null, null, sTablename.toUpperCase(),null);
        if(rs.next())
        {
            DesconectarBD();
            return true;
        }
        else
        {
            
            DesconectarBD();
            return false;
        }
    

}
    
    public ResultSet obtenerDatos(String consulta){
        try {
            conectaryCrearDB();
            System.out.println("DB: obtener datos");
            //resultado = sentencia.executeQuery(consulta);
            ResultSet rs = sentencia.executeQuery(consulta);
            return rs;
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error ", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
    
    public ResultSet obtenerDatosyClaveGenerados(String consulta){
        try {
            conectaryCrearDB();
            System.out.println("DB: obtener datos y clves ");
            //resultado = sentencia.executeQuery(consulta);
            sentencia.executeUpdate(consulta, Statement.RETURN_GENERATED_KEYS);
            //busco el di del diciconario que acabo de insertar
            ResultSet rs = sentencia.getGeneratedKeys();
            return rs;
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error ", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
    
    
    public PreparedStatement obtenerSentenciaPreparada(String consulta) throws SQLException
    {
        conectaryCrearDB();
        //        PreparedStatement pstmt = conexion.prepareStatement("INSERT INTO SUPERTABLA VALUES (?, ?, ?, ?)");
        PreparedStatement pstmt = conexion.prepareStatement(consulta);
        return pstmt;
    }
    
     public void ejecutarUpdate (String consulta){
        try {
            conectaryCrearDB();
            sentencia.executeUpdate(consulta);
            DesconectarBD();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error ", JOptionPane.ERROR_MESSAGE);
        }
        DesconectarBD();
    }
    
    
    
    

}
