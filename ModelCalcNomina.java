package com.mycompany.ds_dpo3_u3_ea_hicl;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

//Clase que realiza los cálculos de la nómina de los empleados
//de acuerdo a un intervalo de días
public class ModelCalcNomina {

    //Variables para realizar cálculos de nómina
    private String fechaIni, fechaFin;//Periodo
    private int opCB;//Opción del combobox: empleados todos o uno

    //Constructor
    public ModelCalcNomina(String fIni, String fFin, int opt) {
        this.fechaIni = fIni;//Fecha inicial del periodo
        this.fechaFin = fFin;//Fecha final del periodo
        this.opCB = opt;//Opción del combobox
    }

    public void ObtenerDatos() {//Método que obtiene info de los servicios de cada acomodador

        try {
            String sql = "";//Variable para la primera consulta
            String sql2 = "";//Variable para la segunda consulta

            //Variable 2 SQL que realizará la segunda consulta (total de servicios)
            sql2 = "SELECT a.id_empleado, e.nombre, e.apellidos, e.sueldo_diario, \n"
                    + "a.fecha_ingreso, COUNT(*) AS servicios FROM automovil a INNER JOIN \n"
                    + "empleados e ON a.id_empleado=e.id WHERE a.fecha_ingreso>='" + fechaIni + "' \n"
                    + "AND a.fecha_salida<='" + fechaFin + "' GROUP BY a.id_empleado \n"
                    + "ORDER BY servicios DESC;";

            if (opCB == 0) {//Calculo para todos los empleados
                //Variable 1 SQL que realizará la primera consulta (servicios por dia)
                sql = "SELECT a.id_empleado, e.nombre, e.apellidos, e.sueldo_diario, \n"
                        + "a.fecha_ingreso, COUNT(*) AS servicios FROM automovil a INNER JOIN \n"
                        + "empleados e ON a.id_empleado=e.id WHERE a.fecha_ingreso>='" + fechaIni + "' \n"
                        + "AND a.fecha_salida<='" + fechaFin + "' GROUP BY a.id_empleado, \n"
                        + "a.fecha_ingreso ORDER BY id_empleado;";

            } else {//Si es un empleado en específico
            //Variable 1 SQL que realizará la primera consulta (servicios por dia y empleado especifico)
                sql = "SELECT a.id_empleado, e.nombre, e.apellidos, e.sueldo_diario, \n"
                        + "a.fecha_ingreso, COUNT(*) AS servicios FROM automovil a INNER JOIN \n"
                        + "empleados e ON a.id_empleado=e.id WHERE a.id_empleado=" + opCB + " \n"
                        + "AND a.fecha_ingreso>='" + fechaIni + "' AND a.fecha_salida<='" + fechaFin + "' \n"
                        + "GROUP BY a.id_empleado, a.fecha_ingreso ORDER BY id_empleado;";
            }

            //Declaramos dos arrays de objetos para guardar los datos obtenidos de las consultas
            Object filas[][] = null;//Guardará los datos de la consulta SQL1
            Object filas2[][] = null;//Guardará los datos de la consulta SQL2

            //**********Variables para consultas a la tabla**********
            PreparedStatement ps = null; //Para preparar la consulta 1 (SQL1)
            PreparedStatement ps2 = null; //Para preparar la consulta 2 (SQL2)
            ResultSet rs = null;//Para ResulSet 1 (SQL1)
            ResultSet rs2 = null;//Para ResulSet 2 (SQL1)
            Conexion conn = new Conexion();//Creamos un objeto de nuestra clase Conexión.Java
            Connection con = conn.Conectarse();//Invocamos el método Conectarse de la clase Conexión       
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ps2 = con.prepareStatement(sql2);
            rs2 = ps2.executeQuery();

            if (!rs.next()) {//Verificamos que la consulta no esté vacía (hubo registros en el periodo)
                JOptionPane.showMessageDialog(null, "No existen registros para ese periodo");
            } else {//Si hay registros en la consulta de ese periodo

                //******* SE GUARDA TODO LA CONSULTA 1******
                rs.last();//Última posición de la consulta
                int fila = rs.getRow();//Obtenemos total de filas de la consulta
                rs.beforeFirst();//Posición inicial en la consulta
                int l = 0;//Variable para las filas del arreglo, y guardar la consulta
                filas = new Object[fila][6];//Dimensionamos el arreglo de objetos

                while (rs.next()) {//Recorremos los datos obtenidos de la consulta
                    //Pasamos los datos de la consulta al objeto
                    for (int i = 0; i < 6; i++) {//Columnas
                        filas[l][i] = rs.getObject(i + 1);//Guardamos todas las columnas de esa fila
                    }
                    l++;//Siguiente fila
                }//*****FIN DEL GUARDADO TODO DE LA CONSULTA 1*

                //*********SE GUARDA TODO LA CONSULTA 2*****
                rs2.last();//Última posición de la consulta
                int fila2 = rs2.getRow();//Obtenemos total de filas de la consulta
                rs2.beforeFirst();//Posición inicial en la consulta
                int m = 0;//Variable para las filas del arreglo, y guardar la consulta
                filas2 = new Object[fila2][6];//Dimensionamos el arreglo de objetos

                while (rs2.next()) {//Recorremos los datos obtenidos de la consulta
                    //Pasamos los datos de la consulta al objeto
                    for (int z = 0; z < 6; z++) {//Columnas
                        filas2[m][z] = rs2.getObject(z + 1);//Guardamos todas las columnas de esa fila
                    }
                    m++;//Siguiente fila
                }//****FIN A GUARDADO TODO DE LA CONSULTA 2*****

                //Método que llena el área de texto (JTextPane)
                //Se envían arrays de objetos con los registros obtenidos
                LlenadoAreaTexto(fila, 6, filas, fila2, filas2);
            }
            con.close();//Cerramos conexiones
            ps.close();
            rs.close();
            ps2.close();
            rs2.close();
        } catch (SQLException e) {//Error de conexion
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error en Conexion en ModelCalcNomina");
        }
    }

    //Método que llena el área de texto (JTextPane)
    public void LlenadoAreaTexto(int fila, int row, Object filas[][], int fila2, Object filas2[][]) {
        //Objetos para dar formato a la fecha
        Date d1 = new Date();;//Creamos objeto de la clase calendario
        DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");//Formato de fecha
        
        //Letras negras en área texto
        ViewCalcNomina.PaneTxt1.setDisabledTextColor(Color.BLACK);


        //****DEFINIMOS ESTILOS PARA EL CONTENIDO DEL ÁREA DE TEXTO*
        StyledDocument doc = ViewCalcNomina.PaneTxt1.getStyledDocument();

        //Estilo predefinido
        Style def = StyleContext.getDefaultStyleContext().
                getStyle(StyleContext.DEFAULT_STYLE);
        StyleConstants.setFontSize(def, 12);
        StyleConstants.setBold(def, true);
        StyleConstants.setFontFamily(def, "Verdana");
        StyleConstants.setAlignment(def, StyleConstants.ALIGN_CENTER);

        //Estilo que acompaña al predefinido (valores de la consulta)
        Style reg = doc.addStyle("registros", null);
        StyleConstants.setFontSize(reg, 12);
        StyleConstants.setFontFamily(reg, "Verdana");

        //Estilo para el encabezado
        Style encabezado = doc.addStyle("encabezado", null);
        StyleConstants.setBold(encabezado, true);
        StyleConstants.setFontSize(encabezado, 20);
        StyleConstants.setFontFamily(encabezado, "Verdana");

        //Estilo para letras chiquitas en negritas
        Style sty1 = doc.addStyle("Sty1", null);
        StyleConstants.setItalic(sty1, true);
        StyleConstants.setFontSize(sty1, 10);
        StyleConstants.setFontFamily(sty1, "SansSerif");
        StyleConstants.setBold(sty1, true);

        //Estilo para letras chiquitas sin negritas
        Style sty2 = doc.addStyle("Sty2", null);
        StyleConstants.setItalic(sty2, true);
        StyleConstants.setFontSize(sty2, 10);
        StyleConstants.setFontFamily(sty2, "SansSerif");
        //****FIN A LA DEFINICIÓN DE ESTILOS*
        
        //Variables para imprimir consulta y realizar operaciones
        int j = 0, k = 0, id = 0, totalServicios = 0, aumento = 0;
        try {

            //Encabezado
            doc.insertString(doc.getLength(), "Cálculo de Nómina", encabezado);
            doc.insertString(doc.getLength(), "\nFecha: ", sty1);
            doc.insertString(doc.getLength(), (dateFormat.format(d1)) + "           ", sty2);
            doc.insertString(doc.getLength(), "               Periodo: ", sty1);
            doc.insertString(doc.getLength(), fechaIni + " al " + fechaFin, sty2);

            //Restamos el periodo para saber los días
            DateTimeFormatter dF = DateTimeFormatter.ofPattern("dd/MMM/uuuu");
            LocalDate f1 = LocalDate.parse(fechaIni, dF);
            LocalDate f2 = LocalDate.parse(fechaFin, dF);
            int fIn = f1.getDayOfMonth();
            int fFin = f2.getDayOfMonth();

            //Asignamos las filas del objeto 2 a una variable para realizar cálculos del 10%
            int numFila = 0;
            if (fila2 <= 4) {
                numFila = fila2;
            } else {
                numFila = 5;
            }

            //ESCRIBIMOS la consulta en la caja de texto
            for (j = 0; j < fila; j++) {//Recorre las filas del objeto
                if (id != Integer.parseInt(filas[j][0].toString())) {

                    if (id > 0) {
                        doc.insertString(doc.getLength(), "\n\n                                    Total Servicios: ", def);
                        doc.insertString(doc.getLength(), "" + totalServicios + "\n", reg);
                        doc.insertString(doc.getLength(), "\n    Sueldo Diario: ", def);
                        doc.insertString(doc.getLength(), "$" + (filas[j][3]), reg);
                        doc.insertString(doc.getLength(), "\nDías Trabajados: ", def);
                        doc.insertString(doc.getLength(), "" + ((fFin - fIn) + 1), reg);
                        doc.insertString(doc.getLength(), "\n      Sueldo Total: ", def);
                        doc.insertString(doc.getLength(), "$" + ((fFin - fIn) + 1) * 357.143, reg);

                        boolean sinAumento = true;
                        for (int s = 0; s < numFila; s++) {//Recorre las primeras 5 filas del objeto 2
                            if (totalServicios == Integer.parseInt(filas2[s][5].toString()) & id == Integer.parseInt(filas2[s][0].toString())) {
                                doc.insertString(doc.getLength(), "\n 10% Aumento : ", def);
                                doc.insertString(doc.getLength(), "$" + (((fFin - fIn) + 1) * 357.143) * .10, reg);
                                doc.insertString(doc.getLength(), "\n SUELDO NETO : ", def);
                                doc.insertString(doc.getLength(), "$" + (((fFin - fIn) + 1) * 357.143) * 1.10, def);
                                doc.insertString(doc.getLength(), "\n\n**¡FELICIDADES! ESTÁS ENTRE LOS PRIMEROS 5 ACOMODADORES**", reg);
                                sinAumento = false;
                            } else if (sinAumento == true && s == 4) {
                                doc.insertString(doc.getLength(), "\n SUELDO NETO : ", def);
                                doc.insertString(doc.getLength(), "$" + ((fFin - fIn) + 1) * 357.143, reg);
                                doc.insertString(doc.getLength(), "\n\n**NO ESTÁS ENTRE LOS PRIMEROS 5 ACOMODADORES. ¡ESFUÉRZATE MÁS!**", reg);
                            }
                        }
                        doc.insertString(doc.getLength(), "\n---------------------------------------------", def);
                        totalServicios = 0;
                    }

                    doc.insertString(doc.getLength(), "\n\n\n---------------------------------------------", def);
                    doc.insertString(doc.getLength(), "\nAcomodador: ", def);
                    doc.insertString(doc.getLength(), "" + ((filas[j][1]) + " " + (filas[j][2])), reg);
                    doc.insertString(doc.getLength(), "           Id: ", def);
                    doc.insertString(doc.getLength(), "" + (filas[j][0]), reg);
                    doc.insertString(doc.getLength(), "\n", def);
                    doc.insertString(doc.getLength(), "\n       Fecha: ", def);
                    doc.insertString(doc.getLength(), "" + (filas[j][4]), reg);
                    doc.insertString(doc.getLength(), "      Servicios: ", def);
                    doc.insertString(doc.getLength(), "" + (filas[j][5]), reg);
                    totalServicios += Integer.parseInt(filas[j][5].toString());//Sumamos los servicios del día

                } else if (id == Integer.parseInt(filas[j][0].toString())) {
                    doc.insertString(doc.getLength(), "\n       Fecha: ", def);
                    doc.insertString(doc.getLength(), "" + (filas[j][4]), reg);
                    doc.insertString(doc.getLength(), "      Servicios: ", def);
                    doc.insertString(doc.getLength(), "" + (filas[j][5]), reg);
                    totalServicios += Integer.parseInt(filas[j][5].toString());//Sumamos los servicios del día
                }
                id = Integer.parseInt(filas[j][0].toString());//Guardamos el ID del empleado
            }

            doc.insertString(doc.getLength(), "\n\n                                    Total Servicios: ", def);
            doc.insertString(doc.getLength(), "" + totalServicios + "\n", reg);
            doc.insertString(doc.getLength(), "\n    Sueldo Diario: ", def);
            doc.insertString(doc.getLength(), "$357.143", reg);
            doc.insertString(doc.getLength(), "\nDías Trabajados: ", def);
            doc.insertString(doc.getLength(), "" + ((fFin - fIn) + 1), reg);
            doc.insertString(doc.getLength(), "\n      Sueldo Total: ", def);
            doc.insertString(doc.getLength(), "$" + ((fFin - fIn) + 1) * 357.143, reg);

            boolean sinAumento = true;//Si el empleado no tuvo aumento
            for (int s = 0; s < numFila; s++) {//Recorre las primeras 5 filas del objeto 2
                if (totalServicios == Integer.parseInt(filas2[s][5].toString()) & id == Integer.parseInt(filas2[s][0].toString())) {
                    doc.insertString(doc.getLength(), "\n 10% Aumento : ", def);
                    doc.insertString(doc.getLength(), "$" + (((fFin - fIn) + 1) * 357.143) * .10, reg);
                    doc.insertString(doc.getLength(), "\n SUELDO NETO : ", def);
                    doc.insertString(doc.getLength(), "$" + (((fFin - fIn) + 1) * 357.143) * 1.10, def);
                    doc.insertString(doc.getLength(), "\n\n**¡FELICIDADES! ESTÁS ENTRE LOS PRIMEROS 5 ACOMODADORES**", reg);
                    sinAumento = false;
                } else if (sinAumento == true && s == 4) {
                    doc.insertString(doc.getLength(), "\n SUELDO NETO : ", def);
                    doc.insertString(doc.getLength(), "$" + ((fFin - fIn) + 1) * 357.143, reg);
                    doc.insertString(doc.getLength(), "\n\n**NO ESTÁS ENTRE LOS PRIMEROS 5 ACOMODADORES. ¡ESFUÉRZATE MÁS!**", reg);
                }
            }
            doc.insertString(doc.getLength(), "\n---------------------------------------------", def);

        } catch (BadLocationException ble) {//Si hay error al insertar texto en el área de texto
            ble.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se pudo insertar texto");
        }
    }
}
