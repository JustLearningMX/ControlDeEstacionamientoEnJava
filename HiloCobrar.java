package com.mycompany.ds_dpo3_u3_ea_hicl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

//Esta clase implementa los hilos para cobrar: imprimir el ticket y actualizar la BD
public class HiloCobrar implements Runnable {

    //Declaramos propiedades de la clase
    private int tarea;//número del cajón

    //Constructor de la clase
    public HiloCobrar(int tr) {//Recibe por parámetro el cajon
        this.tarea = tr;//Asignamos a las variablee los valores recibidos por parámetro
    }

    //Método Run
    public void run() {//

        if (tarea == 1) {//Si se mandó a actualizar la BD

            //**********Variables para consultas a las tablas**********
            PreparedStatement ps = null; //Para preparar consulta a tabla automovil
            PreparedStatement ps2 = null; //Para preparar consulta a tabla cajon
            ResultSet rst1 = null;//Para ResulSet tabla automovil
            ResultSet rst2 = null;//Para ResulSet tabla cajon
            Conexion conn = new Conexion();//Creamos un objeto de nuestra clase Conexión.Java
            Connection con = conn.Conectarse();//Invocamos el método Conectarse de la clase Conexión

            //Variable SQL que realizará la actualización
            String sql = "UPDATE automovil SET fecha_salida=?, hora_salida=?, estatus=?, \n"
                    + "total_hrs=?, total_mins=?, total_pago=? WHERE id=" + CajonesInfo.ID_AUTO;
            String sql2 = "UPDATE cajon SET estatus=? WHERE id=" + CajonesInfo.ID_CAJON;

            try {//Primer query: a la tabla automóvil
                ps = con.prepareStatement(sql);
                ps.setString(1, CajonesInfo.FECHA_SAL);//Guardamos fecha de salida
                ps.setString(2, CajonesInfo.HORA_SAL);//Guardamos hora de salida
                ps.setInt(3, 0);//Estatus del vehículo: salió del estacionamiento. 0=false
                ps.setInt(4, CajonesInfo.TOTAL_HRS);
                ps.setDouble(5, CajonesInfo.TOTAL_MINS);
                ps.setDouble(6, CajonesInfo.TOTAL_PAGO);
                
                ps.executeUpdate();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error en Conexion, HiloCobrar: automoviles");
            }

            try {//Segundo query: a la tabla cajon, 0=false-->Desocupado
                ps2 = con.prepareStatement(sql2);
                ps2.setInt(1, 0);//Ponemos el estatus del cajon en false, de este modo se inactiva
                ps2.executeUpdate();
                ps2.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error en Conexion, HiloCobrar: cajon");
            }

        } else if (tarea == 2) {//Si se mandó a imprimir el ticket
            JOptionPane.showMessageDialog(null, "Entregue ticket al cliente");
        }
    }
}
