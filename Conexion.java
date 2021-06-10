package com.mycompany.ds_dpo3_u3_ea_hicl;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane; 

//Clase que implementa el método para establecer conexión con la BD
public class Conexion{ 
    //Parámetros para conectarse a la BD: Nombre de la BD, usuario y contraseña
    private static final String URL = "jdbc:mysql://localhost:3306/estacionamiento";
    private static final String USERNAME = "root";
    private final String PASSWORD = "";
    //Declaramos una variable del tipo Connection
    private Connection con = null;
    
    //Método que realiza la conexión
    public Connection Conectarse(){ 
        
         try{//Agregamos un Try-Catch para los posibles errores
             
            //Para realizar la conexión
             Class.forName("com.mysql.cj.jdbc.Driver");
             //Pasamos parámetros a nuestra variable del tipo Connection
             con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             //JOptionPane.showMessageDialog(null, "Valor de con "+con);
         //Para los posibles errores
         }catch (SQLException e){ 
             JOptionPane.showMessageDialog(null, "Error en Conexion"); 
             Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
         } catch (ClassNotFoundException ex) {
             Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
         }
        return con;
    }
}