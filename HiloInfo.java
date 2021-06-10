package com.mycompany.ds_dpo3_u3_ea_hicl;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

//Esta clase mostrará en pantalla los niveles con sus cajones disponibles y ocupados
//y además permitirá realizar acciones como ver los datos del auto, horarios, quien lo
//acomodó, y el pago hasta el momento.
public class HiloInfo implements Runnable {

    //Declaramos propiedades de la clase
    private int cajon;//número del cajón

    //Constructor de la clase
    public HiloInfo(int caja) {//Recibe por parámetro el cajon
        this.cajon = caja;//Asignamos a las variablee los valores recibidos por parámetro
    }

    //Método Run
    public void run() {//

        while (CajonesInfo.thState2) {//Se estará ejecutandp mientras sea TRUE

            //**********Variables para consultas a la tabla**********
            PreparedStatement ps = null; //Para preparar la consulta
            ResultSet rst1 = null;//Para ResulSet
            Conexion conn = new Conexion();//Creamos un objeto de nuestra clase Conexión.Java
            Connection con = conn.Conectarse();//Invocamos el método Conectarse de la clase Conexión

            //Variable SQL que realizará la consulta
            String sql = "SELECT * FROM cajon WHERE id = " + this.cajon;// ORDER BY id ASC";
            try {
                ps = con.prepareStatement(sql);
                rst1 = ps.executeQuery();

                if (rst1.next()) {
                    if (cajon <= 15) {//Cajones del nivel 1
                        if (rst1.getObject(4).equals(true)) {//Cajón ocupado
                            int id = cajon - 1;
                            CajonesInfo.boton1[id].setBackground(Color.LIGHT_GRAY);//Color de fondo
                            CajonesInfo.boton1[id].setEnabled(true);//Se habilita el botón
                            CajonesInfo.boton1[id].setBorder(BorderFactory.createLineBorder(Color.RED));//Borde
                        } else if (rst1.getObject(4).equals(false)) {//Cajón desocupado
                            int id = cajon - 1;
                            CajonesInfo.boton1[id].setBackground(new Color(243, 221, 38));//Color de fondo
                            CajonesInfo.boton1[id].setEnabled(false);//Se deshabilita el botón
                        }
                    }
                    if (cajon >= 16 && cajon <= 30) {//Cajones del nivel 2
                        if (rst1.getObject(4).equals(true)) {//Cajón ocupado
                            int id = cajon - 16;
                            CajonesInfo.boton2[id].setBackground(Color.LIGHT_GRAY);//Color de fondo
                            CajonesInfo.boton2[id].setEnabled(true);//Se habilita el botón
                            CajonesInfo.boton2[id].setBorder(BorderFactory.createLineBorder(Color.RED));//Borde
                        } else if (rst1.getObject(4).equals(false)) {//Cajón desocupado
                            int id = cajon - 16;
                            CajonesInfo.boton2[id].setBackground(new Color(243, 221, 38));//Color de fondo
                            CajonesInfo.boton2[id].setEnabled(false);//Se deshabilita el botón
                        }
                    }
                    if (cajon >= 31 && cajon <= 45) {//Cajones del nivel 3
                        if (rst1.getObject(4).equals(true)) {//Cajón ocupado
                            int id = cajon - 31;
                            CajonesInfo.boton3[id].setBackground(Color.LIGHT_GRAY);//Color de fondo
                            CajonesInfo.boton3[id].setEnabled(true);//Se habilita el botón
                            CajonesInfo.boton3[id].setBorder(BorderFactory.createLineBorder(Color.RED));//Borde
                        } else if (rst1.getObject(4).equals(false)) {//Cajón desocupado
                            int id = cajon - 31;
                            CajonesInfo.boton3[id].setBackground(new Color(243, 221, 38));//Color de fondo
                            CajonesInfo.boton3[id].setEnabled(false);//Se deshabilita el botón
                        }
                    }
                }
                ps.close();
                con.close();
                rst1.close();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error en Conexion en HiloInfo");
            }
            try {
                Thread.sleep(5000);//Dormirá por 30 segs
            } catch (InterruptedException ex) {
                Logger.getLogger(HiloInfo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
