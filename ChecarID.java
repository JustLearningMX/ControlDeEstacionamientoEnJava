package com.mycompany.ds_dpo3_u3_ea_hicl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

//En esta clase implementamos un método que verifica el último
//ID en una tabla, así no se repiten IDs en ninguna tabla
public class ChecarID {

    public int Verificar(String tabla){//Recibe por parámetro el nombre de la tabla
        int id = 0;//Para guardar el valor del último ID
        
        //**********Variables para consultas a la tabla**********
        PreparedStatement ps = null; //Para preparar la consulta
        ResultSet rs = null;//Para ResulSet
        Conexion conn = new Conexion();//Creamos un objeto de nuestra clase Conexión.Java
        Connection con = conn.Conectarse();//Invocamos el método Conectarse de la clase Conexión
        
        //Variable SQL que realizará la consulta
        String sql = "SELECT * FROM "+ tabla +" ORDER BY id DESC";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()){
                id = rs.getInt("id");
                //JOptionPane.showMessageDialog(null, "Tabla: "+tabla+" Ultimo ID: "+rs.getInt("id"));
            }
            con.close();
            rs.close();//CERRADO
            ps.close();//CERRADO
        } catch (SQLException e){ 
             JOptionPane.showMessageDialog(null, "Error en Conexion", "Error", JOptionPane.ERROR_MESSAGE); 
         }        
        return id;
    }

    public ResultSet RellenarComboBox(String tabla, int combo){//Recibe nombre de la tabla y # combo
        //**********Variables para consultas a la tabla**********
        PreparedStatement ps = null; //Para preparar la consulta
        ResultSet rs = null;//Para ResulSet
        Conexion conn = new Conexion();//Creamos un objeto de nuestra clase Conexión.Java
        Connection con = conn.Conectarse();//Invocamos el método Conectarse de la clase Conexión
            
        //Variable SQL que realizará la consulta
        String sql = "";
        
        if (combo == 0){//Si es el combo 1: niveles
        sql = "SELECT * FROM "+ tabla +" ORDER BY id ASC";
        }
        if (combo == 1){//Si es el combo 2: cajones del nivel 1
        sql = "SELECT * FROM "+ tabla +" WHERE id_nivel = 1 ORDER BY id ASC";
        }
        if (combo == 2){//Si es el combo 2: cajones del nivel 2
        sql = "SELECT * FROM "+ tabla +" WHERE id_nivel = 2 ORDER BY id ASC";
        }
        if (combo == 3){//Si es el combo 2: cajones del nivel 3
        sql = "SELECT * FROM "+ tabla +" WHERE id_nivel = 3 ORDER BY id ASC";
        }
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            //con.close();
            //ps.close();//CERRADO
        } catch (SQLException e){ 
             JOptionPane.showMessageDialog(null, "Error en Conexion", "Error", JOptionPane.ERROR_MESSAGE); 
         }
        return rs;
    }   
    
    //Método que busca el id del empleado y devuelve su nombre completo
    public String CopiarNombre(String tabla, int id){//Recibe la tabla empleados
        String nombre="", apellidos="", completo="";//Variables
        
        //**********Variables para consultas a la tabla**********
        PreparedStatement ps = null; //Para preparar la consulta
        ResultSet rs = null;//Para ResulSet
        Conexion conn = new Conexion();//Creamos un objeto de nuestra clase Conexión.Java
        Connection con = conn.Conectarse();//Invocamos el método Conectarse de la clase Conexión
            
        //Variable SQL que realizará la consulta
        String sql = "";//Buscaremos nombre y apellidos de acuerdo al Id seleccionado
        
        if (tabla=="empleados"){
            sql = "SELECT nombre, apellidos FROM "+ tabla +" WHERE id = "+id;
        }

        if (tabla=="cajon"){
            sql = "SELECT nombre FROM "+ tabla +" WHERE id = "+id;
        }

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()){
                if (tabla == "cajon"){
                    completo = rs.getString("nombre");//guardamos el nombre;
                }
                if (tabla == "empleados"){
                    nombre = rs.getString("nombre");//guardamos el nombre;
                    apellidos = rs.getString("apellidos");//guardamos los apellidos
                    completo = nombre+" "+apellidos;//Juntamos nombre y apellido
                }
            }             
            //rs.close();//CERRADO
            //ps.close();//CERRADO
            //con.close();
        } catch (SQLException e){ 
             JOptionPane.showMessageDialog(null, "Error en Conexion", "Error", JOptionPane.ERROR_MESSAGE); 
         }
        
        return completo;//Retornamos el nombre completo del empleado
    }
}
