package com.mycompany.ds_dpo3_u3_ea_hicl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

//Clase que permite dar de alta un empleado
public class AltasTrabajadores extends JInternalFrame implements ActionListener {

    private JLabel label_1, label_2, label_3, label_4, label_5; //Definimos objetos de la clase JLabel
    private JTextField txtField_1, txtField_2, txtField_3, txtField_4, txtField_5; //Definimos objetos de la clase JLabel
    private JButton btn1, btn2, btn3;

    public AltasTrabajadores() {

        setLayout(null);

        setBounds(270, 100, 450, 270);//Ubicamos la ventana interna y damos sus medidas
        setBackground(new java.awt.Color(240, 240, 240));

        //***************AGREGAMOS ETIQUETAS DE TEXTO************
        label_1 = new JLabel("           ID:");
        label_1.setBounds(25, 30, 100, 20);
        add(label_1);
        label_2 = new JLabel("       Nombre:");
        label_2.setBounds(25, 70, 100, 20);
        add(label_2);
        label_3 = new JLabel("    Apellidos:");
        label_3.setBounds(25, 110, 100, 20);
        add(label_3);
        label_4 = new JLabel("       Puesto:");
        label_4.setBounds(25, 150, 100, 20);
        add(label_4);
        label_5 = new JLabel("Sueldo Diario:");
        label_5.setBounds(25, 190, 100, 20);
        add(label_5);

        //***************AGREGAMOS CAMPOS DE TEXTO PARA CAPTURAR INFORMACIÓN************
        txtField_1 = new JTextField();
        txtField_1.setBounds(100, 30, 170, 20);
        add(txtField_1);
        txtField_1.setEnabled(false);
        txtField_2 = new JTextField();
        txtField_2.setBounds(100, 70, 170, 20);
        add(txtField_2);
        txtField_3 = new JTextField();
        txtField_3.setBounds(100, 110, 170, 20);
        add(txtField_3);
        txtField_4 = new JTextField();
        txtField_4.setBounds(100, 150, 170, 20);
        add(txtField_4);
        txtField_5 = new JTextField();
        txtField_5.setBounds(100, 190, 170, 20);
        add(txtField_5);

        //***************AGREGAMOS BOTONES************
        btn1 = new JButton();//Botón guardar
        btn1.setText("Guardar");
        btn1.setBounds(300, 30, 100, 20);
        add(btn1);
        btn1.addActionListener(this);//Escuchador del botón guardar

        btn2 = new JButton();//Botón limpiar campos
        btn2.setText("Limpiar");
        btn2.setBounds(300, 75, 100, 20);
        add(btn2);
        btn2.addActionListener(this);//Escuchador del botón limpiar

        btn3 = new JButton();//Botón salir de ventana
        btn3.setText("Salir");
        btn3.setBounds(300, 120, 100, 20);
        add(btn3);
        btn3.addActionListener(this);//Escuchador del botón Salir
        //*******************************************************                  
    }

    public void actionPerformed(ActionEvent e) {
        //Acción del botón Guardar
        if (e.getSource() == this.btn1) {//Guarda los datos

            ChecarID checarid = new ChecarID();
            int id = 1 + checarid.Verificar("empleados");
            txtField_1.setText(Integer.toString(id));

            //****INICIA SECCIÓN PARA GUARDAR EN TABLA DE BD MySQL******
            //**********Variables para consultas a la tabla**********
            PreparedStatement ps = null; //Para preparar la consulta          
            Conexion con = new Conexion();//Se crea objeto de la clase Conexión
            Connection conn = con.Conectarse();//Invocamos el método Conectarse de la clase Conexión
            //Variable SQL que realizará la insercion
            String sql = "INSERT INTO empleados (id, nombre, apellidos, puesto, sueldo_diario) VALUES (?,?,?,?,?)";

            try {
                ps = conn.prepareStatement(sql);
                //Se guardan los registros de los campos de texto
                ps.setInt(1, Integer.parseInt(txtField_1.getText()));
                ps.setString(2, txtField_2.getText());
                ps.setString(3, txtField_3.getText());
                ps.setString(4, txtField_4.getText());
                ps.setDouble(5, Double.parseDouble(txtField_5.getText()));
                int res = ps.executeUpdate();
                if (res > 0) {
                    JOptionPane.showMessageDialog(null, "Registro Guardado con Éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo guardar el registro");
                }
                ps.close();//CERRADO
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(AltasTrabajadores.class.getName()).log(Level.SEVERE, null, ex);
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
}
