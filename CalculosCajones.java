package com.mycompany.ds_dpo3_u3_ea_hicl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JOptionPane;

//Esta clase implementa métodos para los calculos de pago de cada carro
public class CalculosCajones {

    //Método que, de acuerdo al cajón seleccionado, muestra la info para cobro
    public void ObtenerInfoCajon(int idnivel, int idcajon) {
        
        //Agregamos fecha de salida
        Date d1 = new Date();;//Creamos objeto de la clase calendario
        DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy   HH:mm:ss");//Formato de fecha/hora
        CajonesInfo.label_28.setText(dateFormat.format(d1));//Agregamos a su etiqueta

        //Guardamos variables para actualizar BD al cobrar
        DateFormat dtFormat = new SimpleDateFormat("dd/MMM/yyyy");//Formato fecha
        DateFormat hrFormat = new SimpleDateFormat("HH:mm:ss");//Formato hora
        CajonesInfo.FECHA_SAL=dtFormat.format(d1);//guardamos fecha
        CajonesInfo.HORA_SAL=hrFormat.format(d1);;//guardamos hora
        
        //**********Variables para consultas a la tabla**********
        PreparedStatement ps = null; //Para preparar la consulta
        ResultSet rs = null;//Para ResulSet
        Conexion conn = new Conexion();//Creamos un objeto de nuestra clase Conexión.Java
        Connection con = conn.Conectarse();//Invocamos el método Conectarse de la clase Conexión
        int idCajon;
        idCajon = idcajon+1;

        if (idnivel == 1)//Si es el nivel 1
        {
            idcajon = idcajon + 1;//Con esta suma obtenemos el ID del cajon nivel 1 en su tabla
        }
        if (idnivel == 2)//Si es el nivel 2
        {
            idcajon = idcajon + 16;//Con esta suma obtenemos el ID del cajon nivel 2 en su tabla
        }
        if (idnivel == 3)//Si es el nivel 3
        {
            idcajon = idcajon + 31;//Con esta suma obtenemos el ID del cajon nivel 3 en su tabla
        }//Esto es porque la tabla CAJON tiene los numeros consecutivos, del 1 al 45

        //Variable SQL que realizará la consulta
        String sql = "SELECT * FROM automovil WHERE estatus = 1 && id_nivel = " + idnivel + " && id_cajon = " + idcajon;// ORDER BY id ASC";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                //Guardamos variables para actualizar BD al cobrar
                CajonesInfo.ID_AUTO=rs.getInt(1);
                CajonesInfo.ID_CAJON=rs.getInt(13);
                CajonesInfo.label_31.setText(rs.getString(1));
                
                //Colocamos los datos obtenidos de la tabla en pantalla
                CajonesInfo.label_15.setText(rs.getString("fecha_ingreso"));
                CajonesInfo.label_16.setText(rs.getString("hora_ingreso"));

                ChecarID checarid = new ChecarID();//Objeto de la clase ChecarID
                String nom = "";//Variable para recibir el nombre completo del empleado
                nom = checarid.CopiarNombre("empleados",rs.getInt(11));//Buscamos el nombre mediante un método
                
                CajonesInfo.label_18.setText(rs.getString(11)+" "+nom);//Acomodador
                CajonesInfo.label_19.setText(rs.getString("marca"));
                CajonesInfo.label_20.setText(rs.getString("modelo")+" "+rs.getString("color"));
                CajonesInfo.label_21.setText(rs.getString("placas"));
                CajonesInfo.label_22.setText(rs.getString("condiciones"));
                CajonesInfo.label_23.setText(rs.getString("id_nivel"));
                CajonesInfo.label_24.setText(String.valueOf(idCajon));

                //Ponemos fecha y hora de ingreso desde la BD
                CalcularHoras(rs.getString("fecha_ingreso"), rs.getString("hora_ingreso"));

            }else{//Si no existe ningun carro en ese cajon
                JOptionPane.showMessageDialog(null, "Verifique la información, no existe auto en la BD","Atención",JOptionPane.WARNING_MESSAGE);
            }
            ps.close();
            con.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error de BD en CalculosCajones");
        }
    }
    
    //Método que calcula las horas y minutos del carro en el estacionamiento
    public void CalcularHoras(String fechaIngreso, String hrIngreso){
        
        double vHrs;

        //Formato de fecha
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MMM/uuuu HH:mm:ss");

        String concatena;//Variable para concatenar fecha y hora de la BD
        concatena = fechaIngreso+" "+hrIngreso;//Concatenamos fecha y hora        
        
        //Creamos objeto1 para la fecha de la BD y damos formato
        LocalDateTime d1 = LocalDateTime.parse(concatena, dateFormat);
        d1 = d1.withSecond(0);//Le quitamos los milisegundos
        LocalDateTime d2 = LocalDateTime.now().withSecond(0).withNano(0);//Obtenemos fecha, hr y mins actual
        
        double diaEntro = (double) d1.getDayOfMonth();//Día que entró el auto
        double horaEntro = (double) d1.getHour();//Hora que entró
        double minEntro =  (double) d1.getMinute();//minuto que entró
        
        double diaSalio = (double) d2.getDayOfMonth();//Día que entró el auto
        double horaSalio = (double) d2.getHour();//Hora que entró
        double minSalio =  (double) d2.getMinute();//minuto que entró
        
        minEntro = minEntro/60;//Convertimos minutos de ent. a hora
        minSalio = minSalio/60;//Convertimos minutos de sal. a hora
        
        //Convertimos todo a hora
        vHrs = (diaSalio-diaEntro)*24;
        vHrs = vHrs+((horaSalio+minSalio)-(horaEntro+minEntro));
        
        int horas = (int) vHrs;//Del resultado extraemos las horas (enteros)
        String strMins = String.format("%.8f", vHrs);//Convertimos la hora en String
        //Y extraemos la parte decimal recorriéndolo
        Double decNum = Double.parseDouble(strMins.substring(strMins.indexOf('.')));
        double minsTotales = decNum*60;//Convertimos decimal (minutos)
        //Colocamos horas y minutos que lleva el auto en el estacionamiento
        CajonesInfo.label_17.setText(horas+" Horas, "+(int)minsTotales+" Minutos");//Tiempo transcurrido
        CajonesInfo.TOTAL_HRS=horas;//Total de las HRS del auto a la BD
        CajonesInfo.TOTAL_MINS=minsTotales;//Total de los MINS del auto a la BD
        TotalPago(horas, minsTotales);
    }
    
    //Método que calcula el total a pagar
    public void TotalPago(int hrs, double mins){
        double total = 0;
        
        //Se calculan las fracciones de hora
        if (mins >=0){//Si son horas exactas, sin minutos adicionales
            total = (hrs*30);
        }
        if (mins >0 && mins <=20){//Primeros 20 mins
            total = (hrs*30)+10;
        }
        if (mins >20 && mins <=40){//Primeros 40 mins
            total = (hrs*30)+20;
        }
        if (mins >40 && mins <60){//Después de los 40 mins
            total = (hrs*30)+30;
        }

        //Objeto para dar formato al TOTAL
        DecimalFormat formato = new DecimalFormat("#,###.00");
        //Colocamos el TOTAL en su respectiva etiqueta
        CajonesInfo.label_27.setText("$"+formato.format(total));//Total a pagar
        CajonesInfo.TOTAL_PAGO=total;//Total del PAGO del auto a la BD
        CajonesInfo.cobrarState=true;//Ya se puede REALIZAR COBRO
    }
}