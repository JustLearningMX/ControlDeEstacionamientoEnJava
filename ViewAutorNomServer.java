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
import com.toedter.calendar.JDateChooser;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

//Clase que muestra en pantalla las opciones a elegir para  calcular la nómina 
//semanal de los ACOMODADORES (ya sea de uno o todos) 
public class ViewAutorNomServer extends JFrame implements ActionListener {//, Runnable {

    //Declaración de objetos tipo Etiquetas
    public static JLabel label_1, label_2, label_3, label_4, label_5, label_6;

    //Botones para las acciones de dar salida, limpiar campos, salir.
    public JButton boton_1, boton_2, boton_3, boton_4;

    //Declaramos dos objetos de la clase JDateChooser para elegir el periodo
    public JDateChooser dateChIni, dateChFin;

    //Combobox para mostrar los empleados
    private JComboBox cB1;

    //Áreas de texto
    public static JTextPane PaneTxt1, PaneTxt2, PaneTxt3;//Definimos 3 JTextPane

    //Icono para el botón del chat
    ImageIcon icnBtn4 = new ImageIcon("src/imagenes/avion.png");
    ImageIcon EscBtn4 = new ImageIcon(icnBtn4.getImage().getScaledInstance(22, -1, java.awt.Image.SCALE_DEFAULT));

    //Fuente para las letras
    Font fuente1 = new Font("Arial", Font.BOLD, 14);
    Font fuente2 = new Font("Arial", Font.BOLD, 12);
    Font fuente3 = new Font("Verdana", Font.BOLD, 14);
    Font fuente4 = new Font("Verdana", Font.BOLD, 18);

    public ViewAutorNomServer() {//Constructor
        super("Autorizar Nómina -Servidor-");
        setAlwaysOnTop(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        setLayout(null);
        setBounds(770, 100, 475, 580);//Ubicamos la ventana interna y damos sus medidas

        DibujaEtiquetas();//Invocamos el método que agrega las etiquetas
        FocusEnChat();

        //Creamos el hilo para el socket del server y lo iniciamos (archivo)
        HiloSockets MsgSocket = new HiloSockets(5000, 1);//Recibir archivo de nómina
        new Thread(MsgSocket).start();

        //Creamos el hilo para el socket del server y lo iniciamos 
        HiloSockets chatSocket = new HiloSockets(5001, 1);//(chat recibir msg)
        new Thread(chatSocket).start();

    }

    //Método para colocar etiquetas e info de cada cajón
    public void DibujaEtiquetas() {

        //******AGREGAMOS PANETEXT1 PARA LEER EL ARCHIVO************
        PaneTxt1 = new JTextPane();//JTextPane para mostrar el contenido del archivo
        PaneTxt1.setBounds(15, 10, 430, 260);
        PaneTxt1.setEnabled(false);
        PaneTxt1.setBorder(BorderFactory.createLineBorder(Color.BLUE));//Borde
        add(PaneTxt1);
        PaneTxt1.setDisabledTextColor(Color.BLACK);//Letras negras en área texto
        //*******************************************************
        //Agregamos scrollPane y lo añadimos panetext1
        JScrollPane scrollPane = new JScrollPane(PaneTxt1);
        scrollPane.setBounds(15, 10, 430, 260);
        scrollPane.setBorder(null);
        add(scrollPane);
        //*******************************************************

        //******AGREGAMOS PANETEXT2 PARA COLOCAR LOS MENSAJES DEL CHAT************
        PaneTxt2 = new JTextPane();
        PaneTxt2.setBounds(15, 340, 430, 150);
        PaneTxt2.setEnabled(false);
        PaneTxt2.setOpaque(false);
        PaneTxt2.setBorder(BorderFactory.createLineBorder(Color.BLUE));//Borde
        add(PaneTxt2);
        PaneTxt2.setDisabledTextColor(Color.BLACK);//Letras negras en área texto
        //*******************************************************
        //Agregamos scrollPane y lo añadimos panetext2
        JScrollPane scrollPane2 = new JScrollPane(PaneTxt2);
        scrollPane2.setBounds(15, 340, 430, 150);
        scrollPane2.setBorder(null);
        add(scrollPane2);
        //*******************************************************

        //******AGREGAMOS PANETEXT3 PARA ESCRIBIR LOS MENSAJES A ENVIAR************
        PaneTxt3 = new JTextPane();
        PaneTxt3.setBounds(15, 280, 430, 50);
        PaneTxt3.setEnabled(true);
        PaneTxt3.setBorder(BorderFactory.createLineBorder(Color.BLUE));//Borde
        add(PaneTxt3);
        //*******************************************************
        //Agregamos scrollPane y lo añadimos panetext2
        JScrollPane scrollPane3 = new JScrollPane(PaneTxt3);
        scrollPane3.setBounds(15, 280, 430, 50);
        scrollPane3.setBorder(null);
        add(scrollPane3);
        //*******************************************************

        boton_2 = new JButton();//Botón limpiar campos
        boton_2.setText("Limpiar");
        boton_2.setBounds(15, 500, 110, 30);
        add(boton_2);
        boton_2.addActionListener(this);//Escuchador del botón limpiar

        boton_3 = new JButton();//Botón salir de ventana
        boton_3.setText("Salir");
        boton_3.setBounds(175, 500, 110, 30);
        add(boton_3);
        boton_3.addActionListener(this);//Escuchador del botón Salir

        boton_4 = new JButton();//Botón Enviar msg al chat
        boton_4.setText("");
        boton_4.setIcon(EscBtn4);
        boton_4.setBounds(335, 500, 110, 30);
        add(boton_4);
        boton_4.addActionListener(this);//Escuchador del botón Salir
    }

    public void actionPerformed(ActionEvent e) {

        //Acción del botón Seleccionar
        if (e.getSource() == this.boton_1) {
        }

        //Acción del botón Limpiar
        if (e.getSource() == this.boton_2) {//Limpiar los campos
            Limpiar();
        }

        //Acción del botón Salir
        if (e.getSource() == this.boton_3) {//Cerrar la ventana
            dispose();//Cerramos el JFrame
        }

        //Acción del botón Enviar Msg al chat
        if (e.getSource() == this.boton_4) {//Cerrar la ventana
            CrearSockets skServer = new CrearSockets();//Objeto para crear el socket
            skServer.SocketChatServerEnv(5002);//Creamos el socket y pasamos el puerto
            PaneTxt3.setText(null);//Limpiamos el área de texto de enviar msg al chat
            PaneTxt3.requestFocus(true);//Pone el cursor en esta caja de texto
        }
    }

    public void Limpiar() {//Reestablece los campos, combobox, jtextpane y botones
        PaneTxt3.requestFocus(true);//Pone el cursor en esta caja de texto
        PaneTxt1.setText(null);//Limpia el área de texto del archivo
    }

    //Pone el foco en el área de texto del chat
    public void FocusEnChat() {
        PaneTxt3.requestFocus(true);//Pone el cursor en esta caja de texto
    }

    public void main() {
        ViewAutorNomServer AutorNom = new ViewAutorNomServer();//Creamos un objeto de la clase ModuloInventario (JDialog)
        AutorNom.setVisible(true);//Indicamos que la ventana sea visible
        AutorNom.setResizable(false);//Indicamos que no se pueda redimensionar la ventana
    }
}
