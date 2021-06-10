package com.mycompany.ds_dpo3_u3_ea_hicl;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

//Clase que permite ingresar un auto al estacionamiento, obtiene toda la info
//y la guarda en la BD. La clase HiloAltas crea los hilos para ir ingresando los carros
//y manda a llamar a esta clase.
public class AltasAutos extends JFrame implements ActionListener {

    private JLabel label_1, label_2, label_3, label_4, label_5, label_6; //Definimos objetos de la clase JLabel
    private JLabel label_7, label_8, label_9, label_10, label_11, label_12;
    private JLabel label_13, label_14, label_15;
    private JComboBox comboBox1, comboBox2, comboBox3;
    private JTextField txtField_1, txtField_2, txtField_3, txtField_4, txtField_5; //Definimos objetos de la clase JLabel
    private JTextField txtField_6, txtField_7, txtField_8, txtField_9, txtField_10;
    private JTextField txtField_11, txtField_12, txtField_13, txtField_14, txtField_15;
    private JButton btn1, btn2, btn3;

    public AltasAutos() {
        super("Ingresar un vehículo");
        setLayout(null);
        setBounds(170, 150, 500, 300);//Ubicamos la ventana interna y damos sus medidas

        //***************AGREGAMOS ETIQUETAS DE TEXTO************
        label_1 = new JLabel("ID:");
        label_1.setBounds(15, 30, 100, 20);
        add(label_1);
        label_2 = new JLabel("Fecha:");
        label_2.setBounds(73, 30, 100, 20);
        add(label_2);
        label_3 = new JLabel("Hora:");
        label_3.setBounds(230, 30, 100, 20);
        add(label_3);
        label_4 = new JLabel("Acomodador:");
        label_4.setBounds(15, 60, 100, 20);
        add(label_4);
        label_5 = new JLabel("               ");
        label_5.setBounds(158, 60, 150, 20);
        add(label_5);
        label_6 = new JLabel("Marca:");
        label_6.setBounds(15, 90, 100, 20);
        add(label_6);
        label_7 = new JLabel("Modelo:");
        label_7.setBounds(15, 120, 100, 20);
        add(label_7);
        label_8 = new JLabel("Color:");
        label_8.setBounds(15, 150, 100, 20);
        add(label_8);
        label_9 = new JLabel("Placas:");
        label_9.setBounds(15, 180, 100, 20);
        add(label_9);
        label_10 = new JLabel("Condiciones:");
        label_10.setBounds(15, 210, 100, 20);
        add(label_10);
        label_11 = new JLabel("Nivel:");
        label_11.setBounds(230, 90, 100, 20);
        add(label_11);
        label_12 = new JLabel("Cajón:");
        label_12.setBounds(230, 150, 100, 20);
        add(label_12);

        //***************AGREGAMOS CAMPOS DE TEXTO PARA CAPTURAR INFORMACIÓN************
        txtField_1 = new JTextField();//ID
        txtField_1.setBounds(38, 30, 25, 20);
        add(txtField_1);
        txtField_1.setEnabled(false);
        txtField_1.setDisabledTextColor(Color.GRAY);
        txtField_2 = new JTextField();//Fecha ingreso
        txtField_2.setBounds(115, 30, 85, 20);
        add(txtField_2);
        txtField_2.setEnabled(false);
        txtField_3 = new JTextField();//Hora ingreso
        txtField_3.setBounds(270, 30, 85, 20);
        add(txtField_3);
        txtField_3.setEnabled(false);
        comboBox3 = new JComboBox();//Empleados
        comboBox3.setBounds(100, 60, 50, 20);
        add(comboBox3);
        
        txtField_5 = new JTextField();//Marca
        txtField_5.setBounds(100, 90, 110, 20);
        add(txtField_5);
        txtField_6 = new JTextField();//Modelo
        txtField_6.setBounds(100, 120, 110, 20);
        add(txtField_6);
        txtField_7 = new JTextField();//Color
        txtField_7.setBounds(100, 150, 110, 20);
        add(txtField_7);
        txtField_8 = new JTextField();//Placas
        txtField_8.setBounds(100, 180, 110, 20);
        add(txtField_8);
        txtField_9 = new JTextField();//Condicones
        txtField_9.setBounds(100, 210, 200, 20);
        add(txtField_9);
        comboBox1 = new JComboBox();//Niveles
        comboBox1.setBounds(270, 90, 50, 20);
        add(comboBox1);
        comboBox2 = new JComboBox();//Cajones
        comboBox2.setBounds(270, 150, 50, 20);
        add(comboBox2);
        label_13 = new JLabel("Cajon A");
        label_13.setBounds(325, 150, 50, 20);
        add(label_13);

        /***Agregamos la fecha y hora del ingreso**/
        Date d1 = new Date();;//Creamos objeto de la clase calendario
        DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");//Formato de fecha
        txtField_2.setText(dateFormat.format(d1));//Se agrega la fecha
        txtField_2.setDisabledTextColor(Color.GRAY);
                
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");//Formato de la hora
        txtField_3.setText(hourFormat.format(d1));//Se agrega la hora
        txtField_3.setDisabledTextColor(Color.GRAY);

        //**********RELLENAMOS LOS COMBOBOXS DESDE LA BD************
        ChecarID checarid = new ChecarID();//Objeto de la clase ChecarID
        ResultSet rs;//Para almacenar la consulta del método que checa la tabla
        
        rs = checarid.RellenarComboBox("niveles",0);//Método para el primer combobox
        try {
            // Bucle while obtener los registros
            while (rs.next())
            {
                //Rellenamos el combobox
                comboBox1.addItem(rs.getObject(1));
            }
            //rs.close();//CERRADO
        } catch (SQLException ex) {
            Logger.getLogger(AltasAutos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        rs = checarid.RellenarComboBox("cajon",1);//Método para el segundo combobox
          try {
              // Bucle while para obtener los registros
              while (rs.next())
              {//Si el estatus del cajón es ocupado "1=true", no lo pone
                  if (rs.getObject(4).equals(false)){//Si está desocupado "0=false", lo pone
                  //Rellenamos el combobox
                  comboBox2.addItem(rs.getObject(1));//Pone el cajón en el combobox2
                  }
              } 
              String nom2 = "";//Variable para recibir el nombre completo del cajón
              nom2 = checarid.CopiarNombre("cajon",1);//Buscamos el nombre mediante un método
              label_13.setText(nom2);//Asignamos el nombre al label
          } catch (SQLException ex) {              
              Logger.getLogger(AltasAutos.class.getName()).log(Level.SEVERE, null, ex);
          }

        rs = checarid.RellenarComboBox("empleados",0);//Método para el tercer combobox
        try {
            // Bucle while obtener los registros
            while (rs.next())
            {
                //Rellenamos el combobox
                comboBox3.addItem(rs.getObject(1));
            }
            String nom = "";//Variable para recibir el nombre completo del empleado
            nom = checarid.CopiarNombre("empleados",1);//Buscamos el nombre mediante un método
            label_5.setText(nom);//Asignamos el nombre al label
        } catch (SQLException ex) {
            Logger.getLogger(AltasAutos.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Listener para el combobox3 (Id del acomodador)
        comboBox3.addActionListener(new ActionListener(){
          public void actionPerformed (ActionEvent e){
              int cb3 = (int) comboBox3.getSelectedItem();//Variable para saber Nivel elegido
              
              String nom = "";//Variable para recibir el nombre completo del empleado
              nom = checarid.CopiarNombre("empleados",cb3);//Buscamos el nombre mediante un método
              label_5.setText(nom);//Asignamos el nombre al label
          }
        });

        //Listener para el combobox2 (Id del cajon)
        comboBox2.addActionListener(new ActionListener(){
          public void actionPerformed (ActionEvent e){
              try{
                int cb2 = (int) comboBox2.getSelectedItem();//Variable para saber cajon elegido

                String nom2 = "";//Variable para recibir el nombre completo del cajón
                nom2 = checarid.CopiarNombre("cajon",cb2);//Buscamos el nombre mediante un método
                label_13.setText(nom2);//Asignamos el nombre al label
              }
              catch (NullPointerException ex) {              
                  Logger.getLogger(AltasAutos.class.getName()).log(Level.SEVERE, null, ex);}
          }
        });        
        
        //Listener para el combobox1 (nivel del estacionamiento elegido)
        comboBox1.addActionListener(new ActionListener(){
          public void actionPerformed (ActionEvent e){
              ResultSet rs = null;//Declaramos variable para almacenar consulta a BD
              int cb1 = (int) comboBox1.getSelectedItem();//Variable para saber Nivel elegido
              
              if (cb1==1){//Si se eligió el Nivel 1
                  comboBox2.removeAllItems();//Se limpia el ComboBox2
                  rs = checarid.RellenarComboBox("cajon",1);//Se obtiene cajones disponibles del nivel 1
              }
              if (cb1==2){//Si se eligió el Nivel 2
                  comboBox2.removeAllItems();//Se limpia el ComboBox2
                  rs = checarid.RellenarComboBox("cajon",2);//Se obtiene cajones disponibles del nivel 2
              }
              if (cb1==3){//Si se eligió el Nivel 3
                  comboBox2.removeAllItems();//Se limpia el ComboBox2
                  rs = checarid.RellenarComboBox("cajon",3);//Se obtiene cajones disponibles del nivel 3
              }

              try {
                  // Bucle while para obtener los registros
                  while (rs.next())
                  {   //Si el estatus del cajón es ocupado "1=true", no lo pone
                      if (rs.getObject(4).equals(false)){//Si está desocupado "0=false", lo pone
                      //Rellenamos el combobox
                      comboBox2.addItem(rs.getObject(1));//Pone el cajón en el combobox2
                      }
                  } } catch (SQLException ex) {              
                  Logger.getLogger(AltasAutos.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
        });

        
        //***************AGREGAMOS BOTONES************
        btn1 = new JButton();//Botón guardar
        btn1.setText("Guardar");
        btn1.setBounds(380, 30, 100, 20);
        add(btn1);
        btn1.addActionListener(this);//Escuchador del botón guardar

        btn2 = new JButton();//Botón limpiar campos
        btn2.setText("Limpiar");
        btn2.setBounds(380, 75, 100, 20);
        add(btn2);
        btn2.addActionListener(this);//Escuchador del botón limpiar

        btn3 = new JButton();//Botón salir de ventana
        btn3.setText("Salir");
        btn3.setBounds(380, 120, 100, 20);
        add(btn3);
        btn3.addActionListener(this);//Escuchador del botón Salir
        //******************************************************* 
        
        //Damos el foco al primer campo: id del acomodador
        addWindowFocusListener(new WindowAdapter() {
            public void windowGainedFocus(WindowEvent e) {
                txtField_5.requestFocusInWindow();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        //Acción del botón Guardar
        if (e.getSource() == this.btn1) {//Guarda los datos
            
            ChecarID checarid = new ChecarID();
            int id = 1+checarid.Verificar("automovil");
            txtField_1.setText(Integer.toString(id));

            //****INICIA SECCIÓN PARA GUARDAR EN TABLA DE BD MySQL******
            //**********Variables para consultas a la tabla**********
            PreparedStatement ps = null; //Para preparar la consulta          
            Conexion con = new Conexion();//Se crea objeto de la clase Conexión
            Connection conn = con.Conectarse();//Invocamos el método Conectarse de la clase Conexión
            //Variable SQL que realizará la insercion
            String sql = "INSERT INTO automovil (id, marca, modelo, color, placas, condiciones,"
                    +" fecha_ingreso, hora_ingreso, fecha_salida, hora_salida, id_empleado,"
                    +"id_nivel, id_cajon, estatus) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            
            try {
                ps = conn.prepareStatement(sql);

                ps.setInt(1, Integer.parseInt(txtField_1.getText()));//Id
                ps.setString(2, txtField_5.getText());//Marca
                ps.setString(3, txtField_6.getText());//Modelo
                ps.setString(4, txtField_7.getText());//Color
                ps.setString(5, txtField_8.getText());//Placas
                ps.setString(6, txtField_9.getText());//Condiciones
                ps.setString(7, txtField_2.getText());//Fecha ingreso
                ps.setString(8, txtField_3.getText());//Hora ingreso
                ps.setString(9, null);//
                ps.setString(10, null);//
                ps.setInt(11, (int) comboBox3.getSelectedItem());//id_empleado
                ps.setInt(12, (int) comboBox1.getSelectedItem());//id_nivel
                ps.setInt(13, (int) comboBox2.getSelectedItem());//id_cajon
                ps.setInt(14, 1);//estatus del auto en el estacionamiento 1=true-->Adentro aún
                int res =0;
                res = ps.executeUpdate();
                if (res > 0) {
                    JOptionPane.showMessageDialog(null, "Registro Guardado con Éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo guardar el registro");
                }
            } catch (SQLException ex) {
                Logger.getLogger(AltasAutos.class.getName()).log(Level.SEVERE, null, ex);
            }

            int idcajon = (int) comboBox2.getSelectedItem();
            String sql2 = "UPDATE cajon SET estatus = ? where id = "+idcajon;
            try {
                ps = conn.prepareStatement(sql2);

                ps.setBoolean(1, true);//Estatus del cajón en OCUPADO 1=true
                int res =0;
                res = ps.executeUpdate();
                if (res > 0) {
                    JOptionPane.showMessageDialog(null, "Estatus del cajon "+idcajon+" actualizado con éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo actualizar el estatus del cajón");
                }
            } catch (SQLException ex) {
                Logger.getLogger(AltasAutos.class.getName()).log(Level.SEVERE, null, ex);
            }
            

        }

        //Acción del botón Limpiar
        if (e.getSource() == this.btn2) {//Limpia los campos
            txtField_1.setText(null);
            txtField_2.setText(null);
            txtField_3.setText(null);
            txtField_4.setText(null);
            txtField_5.setText(null);
        }
        
        //Acción del botón Salir
        if (e.getSource() == this.btn3) {//Cierra la ventana
            dispose();
        }
    }
    
    public void main() {
        AltasAutos altasautos=new AltasAutos();//Creamos un objeto de la clase ModuloInventario (JDialog)
        altasautos.setVisible(true);//Indicamos que la ventana sea visible
        altasautos.setResizable(false);//Indicamos que no se pueda redimensionar la ventana
    }    
}
