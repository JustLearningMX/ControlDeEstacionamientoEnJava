package com.mycompany.ds_dpo3_u3_ea_hicl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

//Clase que muestra los tickets de los autos que han salido (pagado)
//para reimprimirlos. Ojo, si un auto no ha salida no se puede reimprimir nada
public class ReimprimirTickets extends JFrame implements ActionListener{
    private JLabel label_1; //Definimos un objeto de la clase JLabel
    private JButton btn1, btn2;
    public static TableModel tableModel;//Para recuperar los datos de la tabla
    public static JTable tabla;

    //Constructor de la interfaz visual
    public ReimprimirTickets() {
        setLayout(null);
        setAlwaysOnTop(false);

        //***************AGREGAMOS ETIQUETAS DE TEXTO************
        label_1 = new JLabel("Reimpresión de Tickets");
        label_1.setBounds(200,20,150,20);
        add(label_1);
        //*******************************************************                  

        //***************AGREGAMOS BOTONES************
        btn1 = new JButton();//Botón Reimprimir
        btn1.setText("Reimprimir"); 
        btn1.setBounds(300,450,100,35);
        add(btn1);
        btn1.addActionListener(this);//Escuchador del botón Salir

        btn2 = new JButton();//Botón salirReimprimir
        btn2.setText("Salir"); 
        btn2.setBounds(450,450,100,35);
        add(btn2);
        btn2.addActionListener(this);//Escuchador del botón Salir
        //*******************************************************                  
        
      try {
        //Instanciamos la clase DefaultTableModel de JTable (datos, columnNames)
        DefaultTableModel tab_tickets = new DefaultTableModel(){     
            
            //Sobreescribimos el método isCellEditable de DefaultTableModel para que
            //no se puedan modificar los campos
            @Override
            public boolean isCellEditable(int row, int column) 
            {
                return false;
            }
        };
        
        //Agregamos los nombres de las columnas
        tab_tickets.addColumn("Folio");
        tab_tickets.addColumn("Marca");
        tab_tickets.addColumn("Modelo");
        tab_tickets.addColumn("Color");
        tab_tickets.addColumn("Placas");
        tab_tickets.addColumn("Condiciones");
        tab_tickets.addColumn("Entró");
        tab_tickets.addColumn("Hora");
        tab_tickets.addColumn("Salió");
        tab_tickets.addColumn("Hora");
        tab_tickets.addColumn("Nombre");
        tab_tickets.addColumn("Apellidos");
        tab_tickets.addColumn("Nivel");
        tab_tickets.addColumn("Cajón");
        tab_tickets.addColumn("Pagó");
        
        //Creamos la tabla para añadirle los datos
        tabla = new JTable(tab_tickets);
        tabla.setBounds(10,55,980,350);//Ubicamos la tabla y damos sus medidas
        tabla.setVisible(true);//Indicamos que la tabla sea visible

        //Creamos un scrollpane para cuando tengamos muchos registros
        JScrollPane scroll=new JScrollPane(tabla);//Le pasamos por argumento la tabla
        add(scroll);//Añadimos el scrollpane a la ventana
        scroll.setBounds(10,55,980,350);//Ubicamos el scroll y damos sus medidas
        scroll.setVisible(true);//Indicamos que el scroll sea visible        
        
        //Arreglo de enteros para el ancho de las columnas de la tabla
        int[] anchos = {5, 37, 50, 35, 40, 100, 43, 36, 43, 36, 44, 44, 5, 5, 10};

        //Bucle for para recorrer las columnas de la tabla
        for(int i = 0; i < tabla.getColumnCount(); i++) {
            //Ajustamos los anchos de acuerdo al arreglo de enteros
            tabla.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }        
        //*******************************************************   

        //****INICIA SECCIÓN PARA OBTENER TABLA DE BD MySQL******
        //**********Variables para consultas a la tabla**********
        PreparedStatement ps = null; //Para preparar la consulta
        ResultSet rs = null;//Para ResulSet
        Conexion conn = new Conexion();//Creamos un objeto de nuestra clase Conexión.Java
        Connection con = conn.Conectarse();//Invocamos el método Conectarse de la clase Conexión
        
        //Variable para realizar la consulta a la BD. Ocupado--> 1=true, Desocupado-->0=false
        String sql = "SELECT automovil.id, automovil.marca, automovil.modelo, automovil.color, \n"
                + " automovil.placas, automovil.condiciones, automovil.fecha_ingreso, \n"
                + " automovil.hora_ingreso, automovil.fecha_salida, automovil.hora_salida, \n"
                + " empleados.nombre, empleados.apellidos, automovil.id_nivel, automovil.id_cajon, \n"
                + " automovil.total_pago FROM automovil INNER JOIN empleados ON automovil.id_empleado = \n"
                + " empleados.id WHERE automovil.estatus=0";//0=false-->Salió del estacionamiento

        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        
        //Creamos un resultset para la tabla, le pasamos el resultado de la consulta
        ResultSetMetaData rsMd = rs.getMetaData();
        int cantCol = rsMd.getColumnCount();//Para saber cuantas columnas tiene la consulta
        
        //Recorremos los datos de la consulta
        while(rs.next()){
            //Declaramos un objeto para guardar los datos de las filas de la tabla
            Object[] filas = new Object[cantCol];   
            
            //Pasaremos los datos de la consulta al objeto
            for(int i=0; i<cantCol; i++){
                filas[i] = rs.getObject(i+1);
            }
            
            //Agregamos los datos a nuestro JTable
            tab_tickets.addRow(filas);
        }
        con.close();
        ps.close();
        rs.close();
        tableModel = tabla.getModel();//Recuperamos el modelo de datos de la tabla

        //****FINALIZA SECCIÓN PARA OBTENER TABLA DE BD MySQL******
            
      } catch (SQLException e){ 
            Logger.getLogger(ReimprimirTickets.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, "Error de Conexión con BD", "Reimprimir tickets", JOptionPane.WARNING_MESSAGE); 
      }
    }
    
    //Acción del botón Salir
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()== this.btn1){//Reimprimir ticket
            
            LeerFila(tabla.getSelectedRow());
            JOptionPane.showMessageDialog(null, "Se mandó la reimpresión");
        }

        if (e.getSource()== this.btn2){//Sale del programa
            dispose();
        }
    }

    //Método que lee la fila seleccionada y manda los datos para reimprimir ticket
    public void LeerFila(int row) {//Se recibe la fila seleccionada
        int cols = tableModel.getColumnCount();//Contamos las columnas de la tabla
        String cadena1=null, cadena=null;//En esta variable se guardan los datos
        
            for(int j=0; j<cols; j++) {//Bucle for para recorrer las columnas de la fila
                cadena1 = tableModel.getColumnName(j)+": ";
                cadena = tableModel.getValueAt(row,j).toString();//Guardamos valores e imprimimos
                System.out.println(cadena1+cadena);
            }
    }

    public void main() {
        ReimprimirTickets formulario1=new ReimprimirTickets();//Creamos un objeto de la clase ModuloInventario (JDialog)
        formulario1.setBounds(170,110,1050,550);//Ubicamos la ventana y damos sus medidas
        formulario1.setVisible(true);//Indicamos que la ventana sea visible
        formulario1.setResizable(false);//Indicamos que no se pueda redimensionar la ventana
    }

}