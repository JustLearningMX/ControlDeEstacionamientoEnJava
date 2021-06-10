package com.mycompany.ds_dpo3_u3_ea_hicl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

//Clase que despliega pantalla el estatus e info de los autos y permite cobrar e imprimir ticket
//Llama a la clase HiloEstatus para aplicar los multihilos
public class CajonesInfo extends JFrame implements ActionListener {
   
     //Definimos objetos de la clase JLabel para etiquetas
    private JLabel jlabel_1, jlabel_2, jlabel_3;
    //Array de objeto clase JLabel para dibujar los cajones
    public static JButton[] boton1 = new JButton[15];//Cajones del nivel 1
    public static JButton[] boton2 = new JButton[15];//Cajones del nivel 2
    public static JButton[] boton3 = new JButton[15];//Cajones del nivel 3
    int x,y;//Variables para colocar las etiquetas en eje X y Y
    int X, Y;
    
    //Este booleano va a controlar la ejecucion dentro del metodo run();
    public static volatile boolean thState2; 
    //Este booleano permite realizar el cobro sólo si se ha seleccionado un cajón
    public static volatile boolean cobrarState=false;
    
    //Variables para actualizar BD al realizar pago
    public static int ID_AUTO, ID_CAJON, TOTAL_HRS;
    public static double TOTAL_MINS, TOTAL_PAGO;
    public static String FECHA_SAL, HORA_SAL;
    
    
        //Etiquetas para mostrar los datos del auto
    public static JLabel label_1, label_2, label_3, label_4, label_5, label_6, label_7, label_8, label_9, label_10; 
    public static JLabel label_11, label_12, label_13, label_14, label_15, label_16, label_17, label_18, label_19; 
    public static JLabel label_20, label_21, label_22, label_23, label_24, label_25, label_26, label_27, label_28; 
    public static JLabel label_29, label_30, label_31;
        //Botones para las acciones de dar salida, limpiar campos, salir.
    public JButton boton_1, boton_2, boton_3;
    
    CalculosCajones calcular = new CalculosCajones();//Esta clase contiene método para calcular el pago
    
    public CajonesInfo() {        
        super("Información Completa de Autos"); 
        setAlwaysOnTop (false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
       
        setLayout(null);
        setBounds(220,100,520,575);//Ubicamos la ventana interna y damos sus medidas
        
        //setBackground(new java.awt.Color(240, 240, 240));//Color Fondo del JInternalFrame
        Font fuente1 = new Font("Arial",Font.BOLD,14);//Fuente para las letras
        Font fuente2 = new Font("Arial",Font.BOLD,12);//Fuente para las letras
        x=0;
        y=0;
        
        //****Etiqueta para los cajones del nivel 1*****
        jlabel_1=new JLabel("Cajones del Nivel 1: ");
        jlabel_1.setBounds(15,20,150,20);
        jlabel_1.setFont(fuente1);
        this.add(jlabel_1);       

        //****Etiqueta para los cajones del nivel 1*****
        jlabel_2=new JLabel("Cajones del Nivel 2: ");
        jlabel_2.setBounds(15,190,150,20);
        jlabel_2.setFont(fuente1);
        this.add(jlabel_2);       

        //****Etiqueta para los cajones del nivel 1*****
        jlabel_3=new JLabel("Cajones del Nivel 3: ");
        jlabel_3.setBounds(15,370,150,20);
        jlabel_3.setFont(fuente1);
        this.add(jlabel_3);
        
        AcomodarCajones(boton1, 15, 45, 150, 140 );//Dibujamos cajones del nivel 1
        AcomodarCajones(boton2, 15, 100, 150, 170 );//Dibujamos cajones del nivel 2
        AcomodarCajones(boton3, 15, 200, 210, 250 );//Dibujamos cajones del nivel 2

        CrearThread(true);//Creamos e iniciamos los hilos de cada carro/cajon (45 en total)                
        
        DibujaCobro();
        
    }
    
    //Método para colocar etiquetas e info de cada cajón
    public void DibujaCobro(){
        
        Font fuente1 = new Font("Arial",Font.BOLD,11);//Fuente1 para las letras        
        Font fuente2 = new Font("Arial",Font.ROMAN_BASELINE,11);//Fuente2 para las letras
        Font fuente3 = new Font("Verdana",Font.BOLD,14);//Fuente3 para las letras
        Font fuente4 = new Font("Verdana",Font.BOLD,18);//Fuente3 para las letras

        //***************AGREGAMOS ETIQUETAS DE TEXTO************
        label_29 = new JLabel("");//Recuadrito azul para el folio
        label_29.setBounds(225, 45, 65, 70);
        label_29.setHorizontalAlignment(SwingConstants.CENTER);//Texto centrado
        label_29.setBorder(BorderFactory.createLineBorder(Color.BLUE));//Borde
        add(label_29);
        
        label_30 = new JLabel("Folio");
        label_30.setBounds(225, 45, 65, 35);
        label_30.setHorizontalAlignment(SwingConstants.CENTER);//Texto centrado
        label_30.setFont(fuente3);//Fuente
        add(label_30);

        label_31 = new JLabel("");
        label_31.setBounds(225, 70, 65, 35);
        label_31.setHorizontalAlignment(SwingConstants.CENTER);//Texto centrado
        label_31.setFont(fuente4);//Fuente
        add(label_31);

        label_1 = new JLabel("");//Dibuja un cajón ocupado
        label_1.setBounds(300, 45, 25, 35);
        label_1.setBackground(Color.LIGHT_GRAY);//Color de fondo
        label_1.setOpaque(true);//Opacidad
        label_1.setBorder(BorderFactory.createLineBorder(Color.RED));//Borde
        add(label_1);

        label_2 = new JLabel("Cajones ocupados");
        label_2.setBounds(335, 45, 110, 35);
        add(label_2);

        label_1 = new JLabel("");//Dibuja un cajón desocupado
        label_1.setBounds(300, 85, 25, 35);
        label_1.setBackground(new Color(243, 221, 38 ));//Color de fondo
        label_1.setOpaque(true);//Opacidad
        label_1.setBorder(BorderFactory.createLineBorder(new Color(16, 26, 146 )));//Borde
        add(label_1);

        label_2 = new JLabel("Cajones desocupados");
        label_2.setBounds(335, 85, 130, 35);
        add(label_2);

        label_3 = new JLabel("Hora: $30   Fracción 20 min: $10");
        label_3.setBounds(300, 15, 190, 25);
        label_3.setHorizontalAlignment(SwingConstants.CENTER);//Texto centrado
        label_3.setBorder(BorderFactory.createLineBorder(Color.BLUE));//Borde
        add(label_3);
        
        label_4 = new JLabel("");
        label_4.setBounds(225, 130, 260, 245);
        label_4.setBorder(BorderFactory.createLineBorder(Color.BLUE));//Borde
        add(label_4);
    
        label_5 = new JLabel("Ingresó:");
        label_5.setBounds(230, 135, 90, 20);
        label_5.setHorizontalAlignment(SwingConstants.RIGHT);//Texto centrado
        label_5.setFont(fuente1);//Fuente
        add(label_5);
        
        label_15 = new JLabel("");//Etiqueta para poner la fecha de ingreso desde la BD
        label_15.setBounds(325, 135, 80, 20);
        label_15.setHorizontalAlignment(SwingConstants.LEFT);//Texto a la izquierda
        label_15.setFont(fuente2);//Fuente
        add(label_15);
    
        label_16 = new JLabel("");//Etiqueta para poner la hora de ingreso desde la BD
        label_16.setBounds(390, 135, 150, 20);
        label_16.setHorizontalAlignment(SwingConstants.LEFT);//Texto a la izquierda
        label_16.setFont(fuente2);//Fuente
        add(label_16);
    
        label_6 = new JLabel("Salió:");
        label_6.setBounds(230, 160, 90, 20);
        label_6.setHorizontalAlignment(SwingConstants.RIGHT);//Texto centrado
        label_6.setFont(fuente1);//Fuente
        add(label_6);
        
        label_28 = new JLabel("");//Etiqueta para probable fecha/hora de salida
        label_28.setBounds(325, 160, 150, 20);
        label_28.setHorizontalAlignment(SwingConstants.LEFT);//Texto a la izquierda
        label_28.setFont(fuente2);//Fuente
        add(label_28);
    
        label_7 = new JLabel("Total Tiempo:");
        label_7.setBounds(230, 185, 90, 20);
        label_7.setHorizontalAlignment(SwingConstants.RIGHT);//Texto centrado
        label_7.setFont(fuente1);//Fuente
        add(label_7);

        label_17 = new JLabel("");//Etiqueta para poner tiempo transcurrido
        label_17.setBounds(325, 185, 150, 20);
        label_17.setHorizontalAlignment(SwingConstants.LEFT);//Texto a la izquierda
        label_17.setFont(fuente2);//Fuente
        add(label_17);
    
        label_8 = new JLabel("Acomodador:");
        label_8.setBounds(230, 220, 90, 20);
        label_8.setHorizontalAlignment(SwingConstants.RIGHT);//Texto centrado
        label_8.setFont(fuente1);//Fuente
        add(label_8);

        label_18 = new JLabel("");//Etiqueta para poner nombre del acomodador
        label_18.setBounds(325, 220, 150, 20);
        label_18.setHorizontalAlignment(SwingConstants.LEFT);//Texto a la izquierda
        label_18.setFont(fuente2);//Fuente
        add(label_18);
    
        label_9 = new JLabel("Marca:");
        label_9.setBounds(230, 245, 90, 20);
        label_9.setHorizontalAlignment(SwingConstants.RIGHT);//Texto centrado
        label_9.setFont(fuente1);//Fuente
        add(label_9);

        label_19 = new JLabel("");//Etiqueta para poner marca del auto
        label_19.setBounds(325, 245, 150, 20);
        label_19.setHorizontalAlignment(SwingConstants.LEFT);//Texto a la izquierda
        label_19.setFont(fuente2);//Fuente
        add(label_19);
    
        label_10 = new JLabel("Modelo:");
        label_10.setBounds(230, 270, 90, 20);
        label_10.setHorizontalAlignment(SwingConstants.RIGHT);//Texto centrado
        label_10.setFont(fuente1);//Fuente
        add(label_10);

        label_20 = new JLabel("");//Etiqueta para poner color del auto
        label_20.setBounds(325, 270, 150, 20);
        label_20.setHorizontalAlignment(SwingConstants.LEFT);//Texto a la izquierda
        label_20.setFont(fuente2);//Fuente
        add(label_20);
    
        label_11 = new JLabel("Placas:");
        label_11.setBounds(230, 295, 90, 20);
        label_11.setHorizontalAlignment(SwingConstants.RIGHT);//Texto centrado
        label_11.setFont(fuente1);//Fuente
        add(label_11);

        label_21 = new JLabel("");//Etiqueta para poner placas del auto
        label_21.setBounds(325, 295, 150, 20);
        label_21.setHorizontalAlignment(SwingConstants.LEFT);//Texto a la izquierda
        label_21.setFont(fuente2);//Fuente
        add(label_21);
    
        label_12 = new JLabel("Condiciones:");
        label_12.setBounds(230, 320, 90, 20);
        label_12.setHorizontalAlignment(SwingConstants.RIGHT);//Texto centrado
        label_12.setFont(fuente1);//Fuente
        add(label_12);

        label_22 = new JLabel("");//Etiqueta para poner condiciones del auto
        label_22.setBounds(325, 320, 150, 20);
        label_22.setHorizontalAlignment(SwingConstants.LEFT);//Texto a la izquierda
        label_22.setFont(fuente2);//Fuente
        add(label_22);

        label_13 = new JLabel("Nivel:");
        label_13.setBounds(230, 345, 90, 20);
        label_13.setHorizontalAlignment(SwingConstants.RIGHT);//Texto centrado
        label_13.setFont(fuente1);//Fuente
        add(label_13);

        label_23 = new JLabel("");//Etiqueta para poner nivel en el que está el auto
        label_23.setBounds(325, 345, 50, 20);
        label_23.setHorizontalAlignment(SwingConstants.LEFT);//Texto a la izquierda
        label_23.setFont(fuente2);//Fuente
        add(label_23);

        label_14 = new JLabel("Cajón:");
        label_14.setBounds(380, 345, 50, 20);
        label_14.setHorizontalAlignment(SwingConstants.RIGHT);//Texto centrado
        label_14.setFont(fuente1);//Fuente
        add(label_14);

        label_24 = new JLabel("");//Etiqueta para poner # cajon en el que está el auto
        label_24.setBounds(435, 345, 40, 20);
        label_24.setHorizontalAlignment(SwingConstants.LEFT);//Texto a la izquierda
        label_24.setFont(fuente2);//Fuente
        add(label_24);
        
        label_25 = new JLabel("");
        label_25.setBounds(295, 380, 190, 80);
        label_25.setBorder(BorderFactory.createLineBorder(Color.BLUE));//Borde
        add(label_25);
    
        label_26 = new JLabel("Total a Pagar");
        label_26.setBounds(295, 390, 190, 20);
        label_26.setHorizontalAlignment(SwingConstants.CENTER);//Texto centrado
        label_26.setFont(fuente3);//Fuente
        add(label_26);

        label_27 = new JLabel("");
        label_27.setBounds(295, 420, 190, 20);
        label_27.setHorizontalAlignment(SwingConstants.CENTER);//Texto centrado
        label_27.setFont(fuente4);//Fuente
        add(label_27);
        
        //***************AGREGAMOS LOS BOTONES************
        boton_1 = new JButton();//Botón Dar salida
        boton_1.setText("Dar Salida");
        boton_1.setBounds(220,490, 100, 20);
        add(boton_1);
        boton_1.addActionListener(this);//Escuchador del botón guardar

        boton_2 = new JButton();//Botón limpiar campos
        boton_2.setText("Limpiar");
        boton_2.setBounds(330,490, 80, 20);
        add(boton_2);
        boton_2.addActionListener(this);//Escuchador del botón limpiar

        boton_3 = new JButton();//Botón salir de ventana
        boton_3.setText("Salir");
        boton_3.setBounds(420,490, 80, 20);
        add(boton_3);
        boton_3.addActionListener(this);//Escuchador del botón Salir
}
    
    //Acciones para los 3 botones y los 45 cajones (objeto de JButton)
    public void actionPerformed(ActionEvent e) {

        //Acción del botón Dar Salida
        if (e.getSource() == this.boton_1) {//Dar salida al auto
            if (cobrarState){//Si ya se seleccionó un cajón, ya puede cobrar
                ThreadCobrar();
                cobrarState=false;//Se pone en falso para seleccionar otro cajón
                LimpiarLabels();
            }else{//Sino ha seleccionado un cajón, se avisa
                JOptionPane.showMessageDialog(null, "No puede cobrar aún, seleccione un cajón");
            }
        }
        //Acción del botón Limpiar
        if (e.getSource() == this.boton_2) {//Limpiar los campos
            LimpiarLabels();
        }
        
        //Acción del botón Salir
        if (e.getSource() == this.boton_3) {//Cerrar la ventana
            thState2 = false;//Terminamos los hilos de CajonesInfo
            dispose();//Cerramos el JFrame
        }

        //e.getActionCommand() devuelve nombre del boton que se clickeo
        //Checamos si se ha clicado en un boton del arreglo 1 de botones (nivel 1)
        int a=0;
        while (a<=14) {
            if (e.getSource() == this.boton1[a]) {
                LimpiarLabels();
                calcular.ObtenerInfoCajon(1,a);
            }
            a+=1;
        }
        
        //Checamos si se ha clicado en un boton del arreglo 2 de botones (nivel 2)
        a=0;
        while (a<=14) {
            if (e.getSource() == this.boton2[a]) {
                LimpiarLabels();
                calcular.ObtenerInfoCajon(2,a);
            }
            a+=1;
        }
        
        //Checamos si se ha clicado en un boton del arreglo 3 de botones (nivel 3)
        a=0;
        while (a<=14) {
            if (e.getSource() == this.boton3[a]) {
                //JOptionPane.showMessageDialog(null, "Boton "+e.getActionCommand());
                LimpiarLabels();
                calcular.ObtenerInfoCajon(3,a);
            }
            a+=1;
        }
    }
    
    
    public void LimpiarLabels(){
            label_15.setText(null);//Limpia etiquetas
            label_16.setText(null);
            label_17.setText(null);
            label_18.setText(null);
            label_19.setText(null);
            label_20.setText(null);
            label_21.setText(null);
            label_22.setText(null);
            label_23.setText(null);
            label_24.setText(null);
            label_27.setText(null);        
            label_28.setText(null);        
            label_31.setText(null);        
    }

    //Método que implementa hilos para cuando se cobra, lo que hace es
    //iniciar dos hilos: uno para imprimir el ticket y otro para actualizar la BD
    public void ThreadCobrar(){//Creamos los hilos
        HiloCobrar actualizarBD = new HiloCobrar(1);//1.Actualizar BD
        HiloCobrar imprimir = new HiloCobrar(2);//2. Imprimir ticket
        
        new Thread(actualizarBD).start();//los iniciamos
        new Thread(imprimir).start();        
    }
    
    //Con este método creamos los hilos de cada cajon y se inician
    //Cada cajon vendría dando el estatus de un carro (o de que no hay nada)
    public void CrearThread(boolean thSt2){
        thState2 = thSt2;//Con esta variable indicamos que los hilos se ejecuten
        
        //Hilos de los carros en el primer nivel
        HiloInfo niv1caj1 = new HiloInfo(1);//Creamos el hilo para el cajon_1 del nivel_1
        HiloInfo niv1caj2 = new HiloInfo(2);//así para cada cajón del nivel 1, son 15.
        HiloInfo niv1caj3 = new HiloInfo(3);
        HiloInfo niv1caj4 = new HiloInfo(4);
        HiloInfo niv1caj5 = new HiloInfo(5);
        HiloInfo niv1caj6 = new HiloInfo(6);
        HiloInfo niv1caj7 = new HiloInfo(7);
        HiloInfo niv1caj8 = new HiloInfo(8);
        HiloInfo niv1caj9 = new HiloInfo(9);
        HiloInfo niv1caj10 = new HiloInfo(10);
        HiloInfo niv1caj11 = new HiloInfo(11);
        HiloInfo niv1caj12 = new HiloInfo(12);
        HiloInfo niv1caj13 = new HiloInfo(13);
        HiloInfo niv1caj14 = new HiloInfo(14);
        HiloInfo niv1caj15 = new HiloInfo(15);
        
        new Thread(niv1caj1).start();//los iniciamos
        new Thread(niv1caj2).start();
        new Thread(niv1caj3).start();
        new Thread(niv1caj4).start();
        new Thread(niv1caj5).start();
        new Thread(niv1caj6).start();
        new Thread(niv1caj7).start();
        new Thread(niv1caj8).start();
        new Thread(niv1caj9).start();
        new Thread(niv1caj10).start();
        new Thread(niv1caj11).start();
        new Thread(niv1caj12).start();
        new Thread(niv1caj13).start();
        new Thread(niv1caj14).start();
        new Thread(niv1caj15).start();
        
        //Hilos de los carros en el segundo nivel
        HiloInfo niv2caj1 = new HiloInfo(16);//Creamos el hilo para el cajon_1 del nivel_1
        HiloInfo niv2caj2 = new HiloInfo(17);//así para cada cajón del nivel 1, son 15.
        HiloInfo niv2caj3 = new HiloInfo(18);
        HiloInfo niv2caj4 = new HiloInfo(19);
        HiloInfo niv2caj5 = new HiloInfo(20);
        HiloInfo niv2caj6 = new HiloInfo(21);
        HiloInfo niv2caj7 = new HiloInfo(22);
        HiloInfo niv2caj8 = new HiloInfo(23);
        HiloInfo niv2caj9 = new HiloInfo(24);
        HiloInfo niv2caj10 = new HiloInfo(25);
        HiloInfo niv2caj11 = new HiloInfo(26);
        HiloInfo niv2caj12 = new HiloInfo(27);
        HiloInfo niv2caj13 = new HiloInfo(28);
        HiloInfo niv2caj14 = new HiloInfo(29);
        HiloInfo niv2caj15 = new HiloInfo(30);
        
        new Thread(niv2caj1).start();//los iniciamos
        new Thread(niv2caj2).start();
        new Thread(niv2caj3).start();
        new Thread(niv2caj4).start();
        new Thread(niv2caj5).start();
        new Thread(niv2caj6).start();
        new Thread(niv2caj7).start();
        new Thread(niv2caj8).start();
        new Thread(niv2caj9).start();
        new Thread(niv2caj10).start();
        new Thread(niv2caj11).start();
        new Thread(niv2caj12).start();
        new Thread(niv2caj13).start();
        new Thread(niv2caj14).start();
        new Thread(niv2caj15).start();
        
        //Hilos de los carros en el segundo nivel
        HiloInfo niv3caj1 = new HiloInfo(31);//Creamos el hilo para el cajon_1 del nivel_1
        HiloInfo niv3caj2 = new HiloInfo(32);//así para cada cajón del nivel 1, son 15.
        HiloInfo niv3caj3 = new HiloInfo(33);
        HiloInfo niv3caj4 = new HiloInfo(34);
        HiloInfo niv3caj5 = new HiloInfo(35);
        HiloInfo niv3caj6 = new HiloInfo(36);
        HiloInfo niv3caj7 = new HiloInfo(37);
        HiloInfo niv3caj8 = new HiloInfo(38);
        HiloInfo niv3caj9 = new HiloInfo(39);
        HiloInfo niv3caj10 = new HiloInfo(40);
        HiloInfo niv3caj11 = new HiloInfo(41);
        HiloInfo niv3caj12 = new HiloInfo(42);
        HiloInfo niv3caj13 = new HiloInfo(43);
        HiloInfo niv3caj14 = new HiloInfo(44);
        HiloInfo niv3caj15 = new HiloInfo(45);
        
        new Thread(niv3caj1).start();//los iniciamos
        new Thread(niv3caj2).start();
        new Thread(niv3caj3).start();
        new Thread(niv3caj4).start();
        new Thread(niv3caj5).start();
        new Thread(niv3caj6).start();
        new Thread(niv3caj7).start();
        new Thread(niv3caj8).start();
        new Thread(niv3caj9).start();
        new Thread(niv3caj10).start();
        new Thread(niv3caj11).start();
        new Thread(niv3caj12).start();
        new Thread(niv3caj13).start();
        new Thread(niv3caj14).start();
        new Thread(niv3caj15).start();
    }

    //Con este boleano se detienen los hilos, por recomendación de JAVA no se usa STOP
    public void DetenerThread(){
        thState2 = false;//Con esta variable indicamos que los hilos se detengan
    }
    
    //Con este método se acomodan y dibujan los 3 Jlabel[15] para figurar los cajones
    public void AcomodarCajones(JButton[] boton, int X, int Y, int X2, int Y2){
        Font fuente2 = new Font("Arial",Font.BOLD,12);//Fuente para las letras        
        //Con el for los acomodamos en pantalla
        for(int i = 0; i < boton.length; i++) {//
            boton[i] = new JButton();//Llenamos el array de etiquetas, y agregamos
            boton[i].addActionListener(this);
            boton[i].setBounds(new Rectangle(x+X, y+Y, 25, 35));//sus propiedades
            boton[i].setText(""+(i+1));//Número del cajón
            boton[i].setHorizontalAlignment(SwingConstants.CENTER);//Texto centrado
            boton[i].setFont(fuente2);//Fuente
            boton[i].setBackground(new Color(243, 221, 38 ));//Color de fondo
            boton[i].setOpaque(true);//Opacidad
            boton[i].setBorder(BorderFactory.createLineBorder(new Color(16, 26, 146 )));//Borde
            this.add(boton[i]);//Los agregamos
            
            //Valores de X y Y para acomodar 5 por fila
            if (x<=X2) {
                x=x+40;
            }else if(y<=Y2){
                y=y+40;
                x=0;
            }
        }//Fin del for
    }
    
    public void main() {
        CajonesInfo cajonesinfo=new CajonesInfo();//Creamos un objeto de la clase ModuloInventario (JDialog)
        cajonesinfo.setVisible(true);//Indicamos que la ventana sea visible
        cajonesinfo.setResizable(false);//Indicamos que no se pueda redimensionar la ventana
    }    
    
}