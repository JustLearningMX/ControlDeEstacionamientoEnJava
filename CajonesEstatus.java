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
import javax.swing.SwingConstants;

//Clase que muestra los cajones de los niveles y llama a la clase HiloEstatus 
//para actualizar constantemente los cajones. No realiza más acciones.
public class CajonesEstatus extends JFrame implements ActionListener{

     //Definimos objetos de la clase JLabel para etiquetas
    private JLabel jlabel_1, jlabel_2, jlabel_3;
    //Array de objeto clase JLabel para dibujar los cajones
    public static JLabel[] label1 = new JLabel[15];//Cajones del nivel 1
    public static JLabel[] label2 = new JLabel[15];//Cajones del nivel 2
    public static JLabel[] label3 = new JLabel[15];//Cajones del nivel 3
    int x,y;//Variables para colocar las etiquetas en eje X y Y
    int X, Y;
    
    public JButton boton_1;//Botón salir
            
    //Este booleano va a controlar la ejecucion dentro del metodo run();
    public static volatile boolean thState; 
    
    public CajonesEstatus() {        
        super("Vista Preliminar de los Cajones Disponibles"); 
        setAlwaysOnTop (true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


        setLayout(null);
        setBounds(770,100,320,575);//Ubicamos la ventana interna y damos sus medidas
        setBackground(new java.awt.Color(240, 240, 240));//Color Fondo del JInternalFrame
        Font fuente1 = new Font("Arial",Font.BOLD,14);//Fuente para las letras
        Font fuente2 = new Font("Arial",Font.BOLD,12);//Fuente para las letras
        x=0;
        y=0;

        //****Etiqueta para los cajones del nivel 1*****
        jlabel_1=new JLabel("Cajones del Nivel 1: ");
        jlabel_1.setBounds(15,20,150,20);
        jlabel_1.setFont(fuente1);
        add(jlabel_1);       

        //****Etiqueta para los cajones del nivel 1*****
        jlabel_2=new JLabel("Cajones del Nivel 2: ");
        jlabel_2.setBounds(15,190,150,20);
        jlabel_2.setFont(fuente1);
        add(jlabel_2);       

        //****Etiqueta para los cajones del nivel 1*****
        jlabel_3=new JLabel("Cajones del Nivel 3: ");
        jlabel_3.setBounds(15,370,150,20);
        jlabel_3.setFont(fuente1);
        add(jlabel_3);

        boton_1 = new JButton();//Botón salir de ventana
        boton_1.setText("Salir");
        boton_1.setBounds(220,175, 70, 40);
        add(boton_1);
        boton_1.addActionListener(this);//Escuchador del botón Salir
        
        
        AcomodarEtiquetas(label1, 15, 45, 150, 140 );//Dibujamos cajones del nivel 1
        AcomodarEtiquetas(label2, 15, 100, 150, 170 );//Dibujamos cajones del nivel 2
        AcomodarEtiquetas(label3, 15, 200, 210, 250 );//Dibujamos cajones del nivel 2
        
        CrearThread(true);//Creamos e iniciamos los hilos de cada carro/cajon (45 en total)         
    }
    
    //Con este método creamos los hilos de cada cajon
    public void CrearThread(boolean thSt){
        thState = thSt;//Con esta variable indicamos que los hilos se ejecuten
        
        //Hilos de los carros en el primer nivel
        HiloEstatus niv1caj1 = new HiloEstatus(1);//Creamos el hilo para el cajon_1 del nivel_1
        HiloEstatus niv1caj2 = new HiloEstatus(2);//así para cada cajón del nivel 1, son 15.
        HiloEstatus niv1caj3 = new HiloEstatus(3);
        HiloEstatus niv1caj4 = new HiloEstatus(4);
        HiloEstatus niv1caj5 = new HiloEstatus(5);
        HiloEstatus niv1caj6 = new HiloEstatus(6);
        HiloEstatus niv1caj7 = new HiloEstatus(7);
        HiloEstatus niv1caj8 = new HiloEstatus(8);
        HiloEstatus niv1caj9 = new HiloEstatus(9);
        HiloEstatus niv1caj10 = new HiloEstatus(10);
        HiloEstatus niv1caj11 = new HiloEstatus(11);
        HiloEstatus niv1caj12 = new HiloEstatus(12);
        HiloEstatus niv1caj13 = new HiloEstatus(13);
        HiloEstatus niv1caj14 = new HiloEstatus(14);
        HiloEstatus niv1caj15 = new HiloEstatus(15);
        
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
        HiloEstatus niv2caj1 = new HiloEstatus(16);//Creamos el hilo para el cajon_1 del nivel_2
        HiloEstatus niv2caj2 = new HiloEstatus(17);//así para cada cajón del nivel 2, son 15.
        HiloEstatus niv2caj3 = new HiloEstatus(18);
        HiloEstatus niv2caj4 = new HiloEstatus(19);
        HiloEstatus niv2caj5 = new HiloEstatus(20);
        HiloEstatus niv2caj6 = new HiloEstatus(21);
        HiloEstatus niv2caj7 = new HiloEstatus(22);
        HiloEstatus niv2caj8 = new HiloEstatus(23);
        HiloEstatus niv2caj9 = new HiloEstatus(24);
        HiloEstatus niv2caj10 = new HiloEstatus(25);
        HiloEstatus niv2caj11 = new HiloEstatus(26);
        HiloEstatus niv2caj12 = new HiloEstatus(27);
        HiloEstatus niv2caj13 = new HiloEstatus(28);
        HiloEstatus niv2caj14 = new HiloEstatus(29);
        HiloEstatus niv2caj15 = new HiloEstatus(30);
        
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
        
        //Hilos de los carros en el tercer nivel
        HiloEstatus niv3caj1 = new HiloEstatus(31);//Creamos el hilo para el cajon_1 del nivel_3
        HiloEstatus niv3caj2 = new HiloEstatus(32);//así para cada cajón del nivel 3, son 15.
        HiloEstatus niv3caj3 = new HiloEstatus(33);
        HiloEstatus niv3caj4 = new HiloEstatus(34);
        HiloEstatus niv3caj5 = new HiloEstatus(35);
        HiloEstatus niv3caj6 = new HiloEstatus(36);
        HiloEstatus niv3caj7 = new HiloEstatus(37);
        HiloEstatus niv3caj8 = new HiloEstatus(38);
        HiloEstatus niv3caj9 = new HiloEstatus(39);
        HiloEstatus niv3caj10 = new HiloEstatus(40);
        HiloEstatus niv3caj11 = new HiloEstatus(41);
        HiloEstatus niv3caj12 = new HiloEstatus(42);
        HiloEstatus niv3caj13 = new HiloEstatus(43);
        HiloEstatus niv3caj14 = new HiloEstatus(44);
        HiloEstatus niv3caj15 = new HiloEstatus(45);

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
    
    //Con este método detenemos los hilos de cada cajon
    //Cada cajon vendría dando el estatus de un carro (o de que no hay nada)
    public void DetenerThread(){
        thState = false;//Con esta variable indicamos que los hilos se detengan
    }
    
    //Con este método reiniciamos los hilos de cada cajon
    //Cada cajon vendría dando el estatus de un carro (o de que no hay nada)
    public void ReiniciarThread(){
        thState = true;//Con esta variable indicamos que los hilos se detengan
    }
    
    public void CerrarVentana(){
        dispose();
    }
    
    public void actionPerformed(ActionEvent e) {//Si se preciona el único botón: Salir
            thState = false;//Terminamos los hilos de CajonesEstatus
            dispose();//Cerramos el JFrame
    }
    

    //Con este método se acomodan y dibujan los 3 Jlabel[15] para figurar los cajones
    public void AcomodarEtiquetas(JLabel[] label, int X, int Y, int X2, int Y2){
        Font fuente2 = new Font("Arial",Font.BOLD,12);//Fuente para las letras        
        //Con el for los acomodamos en pantalla
        for(int i = 0; i < label.length; i++) {//
            label[i] = new JLabel();//Llenamos el array de etiquetas, y agregamos
            label[i].setBounds(new Rectangle(x+X, y+Y, 25, 35));//sus propiedades
            label[i].setText(""+(i+1));//Número del cajón
            label[i].setHorizontalAlignment(SwingConstants.CENTER);//Texto centrado
            label[i].setFont(fuente2);//Fuente
            label[i].setBackground(new Color(243, 221, 38 ));//Color de fondo
            label[i].setOpaque(true);//Opacidad
            label[i].setBorder(BorderFactory.createLineBorder(new Color(16, 26, 146 )));//Borde
            add(label[i]);//Los agregamos
            
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
        CajonesEstatus cajonesestatus=new CajonesEstatus();//Creamos un objeto de la clase ModuloInventario (JDialog)
        cajonesestatus.setVisible(true);//Indicamos que la ventana sea visible
        cajonesestatus.setResizable(false);//Indicamos que no se pueda redimensionar la ventana
    }    
    
}