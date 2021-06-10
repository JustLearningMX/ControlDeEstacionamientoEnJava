package com.mycompany.ds_dpo3_u3_ea_hicl;

import javax.swing.*;
import java.awt.event.*;

//Clase principal, heredará de la clase JFrame -de la librería swing- y asimismo
//implementará la Interface ActionListener para la captura de eventos
public class CasoEstudio extends JFrame implements ActionListener {

    public JDesktopPane desktop; //Contendrá los JInternalFrame

    private JLabel label_1, label_2; //Definimos dos objetos de la clase JLabel

    private JMenuBar barra_menu;//Definimos un objeto de la clase JMenuBar (sólo ocupamos 1)

    private JMenu menu_1, menu_2;//Definimos 6 objetos del tipo JMenu para nuestro menu principal
    private JMenu menu_3, menu_4, menu_5, menu_6;

    private JMenuItem sub_m1, sub_m2, sub_m3;//Definimos los objetos de la clase JMenuItem
    private JMenuItem sub_m4, sub_m5, sub_m6;//para las opciones que conformarán el submenu
    private JMenuItem sub_m7, sub_m8, sub_m9;//en cada opción del menú principal
    private JMenuItem sub_m10, sub_m11, sub_m12, sub_m13, sub_m14, sub_m15;
    private int contador; //Sólo 4 acomodadores a la vez pueden dar de alta autos

    //Constructor de la interfaz visual
    public CasoEstudio() {
        super("CONTROL DE ESTACIONAMIENTO   v1.0");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //***************CREAMOS EL OBJETO PARA EL JInternalFrame************
        desktop = new JDesktopPane();
        setContentPane(desktop);
        desktop.setBackground(new java.awt.Color(240, 240, 240));

        //***************AGREGAMOS LA BARRA DE MENU************
        barra_menu = new JMenuBar();//Creamos el objeto de la clase JMenuBar
        setJMenuBar(barra_menu);//Lo asociamos al JFrame mediante el método setJMenuBar
        //*******************************************************

        //***************AGREGAMOS EL MENU PRINCIPAL A LA BARRA DE MENU************
        menu_1 = new JMenu("Vehículos");//Creamos el objeto, primera opción del menu principal
        barra_menu.add(menu_1);//Asociamos la primera opcion a la barra de menu
        menu_2 = new JMenu("Administrador");//Hacemos lo mismo para cada opcion del menu
        barra_menu.add(menu_2);
        menu_3 = new JMenu("Nómina");
        barra_menu.add(menu_3);
        menu_4 = new JMenu("Reportes");
        barra_menu.add(menu_4);
        menu_5 = new JMenu("Salir");
        barra_menu.add(menu_5);
        //*******************************************************

        //***********AGREGAMOS LAS OPCIONES DEL SUBMENU EN EL MENU PRINCIPAL************
        //VEHÍCULOS---menu_1
        sub_m1 = new JMenuItem("Alta de unidad");//Creamos cada objeto                
        sub_m1.addActionListener(this);//Agregamos el escuchador
        menu_1.add(sub_m1);//Lo asociamos a su respectivo menu en la barra
        sub_m12 = new JMenuItem("Estatus de unidades");//Hacemos lo mismo para cada opcion del menu
        sub_m12.addActionListener(this);//Agregamos el escuchador
        menu_1.add(sub_m12);

        //ADMINISTRADOR---menu_2
        sub_m2 = new JMenuItem("Cobro de Boleto (Salida de Unidad)");//Hacemos lo mismo para cada opcion del menu
        sub_m2.addActionListener(this);//Agregamos el escuchador
        menu_2.add(sub_m2);
        sub_m3 = new JMenuItem("Altas de trabajadores");
        sub_m3.addActionListener(this);//Agregamos el escuchador
        menu_2.add(sub_m3);
        sub_m4 = new JMenuItem("Revisar Nómina");
        sub_m4.addActionListener(this);//Agregamos el escuchador
        menu_2.add(sub_m4);
        sub_m5 = new JMenuItem("Modificaciones");
        menu_2.add(sub_m5);
        sub_m5.setEnabled(false);//Opción deshabilitada por el momento

        //NÓMINA---menu_3
        sub_m6 = new JMenuItem("Calcular Nóminas");
        sub_m6.addActionListener(this);//Agregamos el escuchador
        menu_3.add(sub_m6);
        sub_m7 = new JMenuItem("Autorizar Nómina");
        sub_m7.addActionListener(this);//Agregamos el escuchador
        menu_3.add(sub_m7);

        //REPORTES---menu_4
        sub_m8 = new JMenuItem("Reimprimir boleto con folio secuencias");
        menu_4.add(sub_m8);
        sub_m8.addActionListener(this);//Agregamos el escuchador
        sub_m9 = new JMenuItem("Reporte por cajones");
        menu_4.add(sub_m9);
        sub_m9.setEnabled(false);//Opción deshabilitada por el momento
        sub_m10 = new JMenuItem("Reporte por acomodador");
        menu_4.add(sub_m10);
        sub_m10.setEnabled(false);//Opción deshabilitada por el momento

        //SALIR---menu_5
        sub_m11 = new JMenuItem("Salir de la aplicación");
        sub_m11.addActionListener(this);//Agregamos el escuchador
        menu_5.add(sub_m11);
        //*******************************************************

    }

    //De acuerdo a la opción seleccionada en el menú se implementan las acciones del escuchador
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == sub_m1) {//Tabla de ventas
            HiloAltas hiloregistro = new HiloAltas();//Creamos el hilo
            new Thread(hiloregistro).start();//Lo iniciamos
        }

        if (e.getSource() == sub_m2) {//Tabla de inventarios
            CajonesInfo cajonesinfo = new CajonesInfo();//Creamos un objeto de la clase CajonesInfor
            cajonesinfo.main();
        }

        if (e.getSource() == sub_m3) {//Módulo de Altas en inventarios
            AltasTrabajadores altasTrab = new AltasTrabajadores();//Creamos un objeto de la clase Inv_Altas
            altasTrab.setVisible(true);//Que sea visible
            desktop.add(altasTrab);//Agregamos la ventana interna al content pane de la ventana principal
        }

        if (e.getSource() == sub_m4) {//Módulo para Autorizar Nómina (Revisar nomina Admin) -Servidor-
            ViewAutorNomServer RecibirNom = new ViewAutorNomServer();
            RecibirNom.main();
        }

        if (e.getSource() == sub_m14) {//Módulo de Bajas en inventarios
        }

        if (e.getSource() == sub_m5) {//Tabla de personal
        }

        if (e.getSource() == sub_m6) {//Módulo de Calcular Nóminas
            ViewCalcNomina calcularNomina = new ViewCalcNomina();
            calcularNomina.main();
        }

        if (e.getSource() == sub_m7) {//Módulo para Autorizar Nómina -Cliente-
            ViewAutorNomCliente EnviarNom = new ViewAutorNomCliente();
            EnviarNom.main();
        }

        if (e.getSource() == sub_m15) {//Módulo de Bajas en inventarios
        }

        if (e.getSource() == sub_m8) {//Reimprimir tickets
            ReimprimirTickets reimprimir = new ReimprimirTickets();//Creamos un objeto de la clase CajonesInfor
            reimprimir.main();
        }

        if (e.getSource() == sub_m9) {//Módulo de Personal en Nóminas

        }

        if (e.getSource() == sub_m11) {//Sale del programa
            ModuloSalir salir = new ModuloSalir();//Creamos un objeto de la clase ModuloSalir
            salir.main();
        }

        if (e.getSource() == sub_m12) {//Estatus de las unidades
            CajonesEstatus cajones = new CajonesEstatus();//Creamos un objeto
            cajones.main();
        }
    }

    //Clase principal
    public static void main(String[] args) {
        CasoEstudio formulario1 = new CasoEstudio();//Creamos un objeto de la clase CasoEstudio (JFrame)
        formulario1.setBounds(150, 40, 1100, 650);//Ubicamos la ventana y damos sus medidas
        formulario1.setVisible(true);//Indicamos que la ventana sea visible
        formulario1.setResizable(false);//Indicamos que no se pueda redimensionar la ventana        
    }
}
