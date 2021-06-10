package com.mycompany.ds_dpo3_u3_ea_hicl;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import com.toedter.calendar.JDateChooser;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

//Clase que muestra en pantalla las opciones a elegir para  calcular la nómina 
//semanal de los ACOMODADORES (ya sea de uno o todos) 
public class ViewCalcNomina extends JFrame implements ActionListener {

    //Declaración de objetos tipo Etiquetas
    public static JLabel label_1, label_2, label_3, label_4, label_5, label_6;

    //Botones para las acciones de dar salida, limpiar campos, salir.
    public JButton boton_1, boton_2, boton_3, boton_4;

    //Declaramos dos objetos de la clase JDateChooser para elegir el periodo
    public JDateChooser dateChIni, dateChFin;

    //Combobox para mostrar los empleados
    private JComboBox cB1;

    public static JTextPane PaneTxt1;//Definimos el objeto de la clase JTextPane

    //Fuente para las letras
    Font fuente1 = new Font("Arial", Font.BOLD, 14);
    Font fuente2 = new Font("Arial", Font.BOLD, 12);
    Font fuente3 = new Font("Verdana", Font.BOLD, 14);
    Font fuente4 = new Font("Verdana", Font.BOLD, 18);

    public ViewCalcNomina() {//Constructor
        super("Calcular Nómina");
        setAlwaysOnTop(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        setLayout(null);
        setBounds(400, 100, 450, 580);//Ubicamos la ventana interna y damos sus medidas

        DibujaEtiquetas();//Invocamos el método que agrega las etiquetas
        RellenarComboB1();
    }

    //Método para colocar etiquetas e info de cada cajón
    public void DibujaEtiquetas() {

        //****Periodo a calcular*****
        label_1 = new JLabel("");//Dibuja un recuadro azul
        label_1.setBounds(15, 10, 400, 90);
        label_1.setBorder(BorderFactory.createLineBorder(Color.BLUE));//Borde
        add(label_1);

        label_2 = new JLabel("Elegir Periodo");
        label_2.setBounds(15, 20, 400, 20);
        label_2.setHorizontalAlignment(SwingConstants.CENTER);//Texto centrado
        label_2.setFont(fuente3);
        this.add(label_2);

        label_3 = new JLabel("Inicial: ");
        label_3.setBounds(60, 55, 100, 20);
        label_3.setFont(fuente2);
        add(label_3);

        dateChIni = new JDateChooser();//Se elige fecha inicial
        dateChIni.setBounds(100, 55, 100, 20);
        dateChIni.setFont(fuente2);
        add(dateChIni);

        label_4 = new JLabel("Final: ");
        label_4.setBounds(240, 55, 100, 20);
        label_4.setFont(fuente2);
        add(label_4);

        dateChFin = new JDateChooser();//Se elige fecha final
        dateChFin.setBounds(280, 55, 100, 20);
        dateChFin.setFont(fuente2);
        add(dateChFin);

        //****Muestra los empleados*****
        label_5 = new JLabel("");//Dibuja un recuadro azul
        label_5.setBounds(15, 110, 400, 75);
        label_5.setBorder(BorderFactory.createLineBorder(Color.BLUE));//Borde
        add(label_5);

        label_6 = new JLabel("Seleccionar Empleado");
        label_6.setBounds(15, 120, 400, 20);
        label_6.setHorizontalAlignment(SwingConstants.CENTER);//Texto centrado
        label_6.setFont(fuente3);
        this.add(label_6);

        cB1 = new JComboBox();//Mostrará los empleados de la BD
        cB1.setBounds(100, 150, 250, 20);
        add(cB1);
        cB1.addItem("Todos");//Opción TODOS los empleaods

        //***************AGREGAMOS CUADRO DE TEXTO************
        PaneTxt1 = new JTextPane();//JTextPane para mostrar el contenido de la consulta
        PaneTxt1.setBounds(15, 190, 400, 260);
        PaneTxt1.setEnabled(false);
        PaneTxt1.setBorder(BorderFactory.createLineBorder(Color.BLUE));//Borde
        add(PaneTxt1);
        //*******************************************************

        //Agregamos un scrollPane y lo añadimos al cuadro de texto
        JScrollPane scrollPane = new JScrollPane(PaneTxt1);
        scrollPane.setBounds(15, 190, 400, 280);
        scrollPane.setBorder(null);
        add(scrollPane);
        //*******************************************************

        //***************AGREGAMOS LOS BOTONES************
        boton_1 = new JButton();//Botón Calcular nómina
        boton_1.setText("Calcular");
        boton_1.setBounds(50, 510, 90, 20);
        add(boton_1);
        boton_1.addActionListener(this);//Escuchador del botón guardar

        boton_2 = new JButton();//Botón limpiar campos
        boton_2.setText("Limpiar");
        boton_2.setBounds(190, 510, 80, 20);
        add(boton_2);
        boton_2.addActionListener(this);//Escuchador del botón limpiar

        boton_3 = new JButton();//Botón salir de ventana
        boton_3.setText("Salir");
        boton_3.setBounds(320, 510, 80, 20);
        add(boton_3);
        boton_3.addActionListener(this);//Escuchador del botón Salir

        boton_4 = new JButton();//Botón Generar Reporte
        boton_4.setText("Generar Reporte");
        boton_4.setBounds(50, 478, 180, 20);
        add(boton_4);
        boton_4.addActionListener(this);//Escuchador del botón Salir
        boton_4.setVisible(false);
    }

    //Método para rellenar el combobox de los empleados desde la BD
    public void RellenarComboB1() {
        ChecarID checarid = new ChecarID();//Objeto de la clase ChecarID
        ResultSet rs;//Para almacenar la consulta del método que checa la tabla
        rs = checarid.RellenarComboBox("empleados", 0);//Invocamos el método
        try {
            // Bucle while obtener los registros
            while (rs.next()) {
                //Rellenamos el combobox
                cB1.addItem(rs.getObject(1) + " " + rs.getObject(2) + " " + rs.getObject(3));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AltasAutos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Acciones para los 3 botones y los 45 cajones (objeto de JButton)
    public void actionPerformed(ActionEvent e) {

        //Acción del botón Calcular Nómina
        if (e.getSource() == this.boton_1) {
            PaneTxt1.setText(null);
            if (dateChIni.getDate() == null || dateChFin.getDate() == null) {//Si las fechas están vacías
                JOptionPane.showMessageDialog(null, "Ingrese fechas válidas ");
            } else {
                if (dateChFin.getDate().before(dateChIni.getDate())) {//Si fecha final es mayor a fecha ini
                    JOptionPane.showMessageDialog(null, "Fecha Final debe ser mayor o igual \n"
                            + "a la Fecha Inicial");
                } else {
                    //Objeto para dar formato a la fecha
                    DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
                    //Creamos objeto de la clase que realiza los cálculos de nómina desde la BD
                    ModelCalcNomina calculos = new ModelCalcNomina(dateFormat.format(dateChIni.getDate()), dateFormat.format(dateChFin.getDate()), cB1.getSelectedIndex());
                    calculos.ObtenerDatos();
                    boton_4.setVisible(true);
                }
            }
        }

        //Acción del botón Limpiar
        if (e.getSource() == this.boton_2) {//Limpiar los campos
            Limpiar();
        }

        //Acción del botón Salir
        if (e.getSource() == this.boton_3) {//Cerrar la ventana
            dispose();//Cerramos el JFrame
        }

        //Acción del botón Generar Reporte
        if (e.getSource() == this.boton_4) {//Cerrar la ventana
            ArchivosStreams genRep = new ArchivosStreams();
            genRep.CrearArchivo();
        }
    }

    public void Limpiar() {//Reestablece los campos, combobox, jtextpane y botones
        dateChIni.setCalendar(null);
        dateChFin.setCalendar(null);
        cB1.setSelectedIndex(0);
        PaneTxt1.setText(null);
        boton_4.setVisible(false);
    }

    public void main() {
        ViewCalcNomina calcularNom = new ViewCalcNomina();//Creamos un objeto de la clase ModuloInventario (JDialog)
        calcularNom.setVisible(true);//Indicamos que la ventana sea visible
        calcularNom.setResizable(false);//Indicamos que no se pueda redimensionar la ventana
    }
}
