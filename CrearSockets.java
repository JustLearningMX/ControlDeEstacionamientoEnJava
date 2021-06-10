package com.mycompany.ds_dpo3_u3_ea_hicl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

//Clase que crea los sockets necesarios
public class CrearSockets {

    //Socket de conexión entre el cliente (envía archivo de nómina) y server
    //puerto 5000
    public void SocketMsgCliente(int puerto) {
        try {
            //Creamos el socket
            Socket skCliente = new Socket("127.0.0.1", puerto);//InetAddress.getLocalHost(), 9998);

            //Creamos flujo de datos de salida, datos circularán por el socket creado
            DataOutputStream salida = new DataOutputStream(skCliente.getOutputStream());

            //Indicamos al flujo de datos de salida que es lo que va a circular por ahí
            //Serán los datos del área de texto con el archivo cargado
            salida.writeUTF(ViewAutorNomCliente.PaneTxt1.getText());

            //Cerramos el flujo de salida
            salida.close();

        } catch (IOException ex) {
            Logger.getLogger(CrearSockets.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error en CrearSocketCliente, Clase: CrearSockets");
        }
    }

    //Socket de conexión entre el server (recibe el archivo de nómina) y cliente 
    //puerto 5000. Server en espera.
    public void SocketMsgServer(int puerto) {

        ServerSocket sskServidor = null;
        Socket skServidor = null;

        try {
            //Creamos un objeto serversocket para abrir el puerto
            sskServidor = new ServerSocket(puerto);

            while (true) {
                //Creamos el socket de entrada y especificamos que acepte las conexiones
                skServidor = sskServidor.accept();//Variable tipo socket para aceptar las conexiones

                //Creamos flujo de datos de entrada, datos enviados por el cliente
                DataInputStream entrada = new DataInputStream(skServidor.getInputStream());

                //Leemos los datos que vienen en el flujo de entrada
                String mensaje = entrada.readUTF();

                //Escribimos los datos en el área de texto correspondiente
                ViewAutorNomServer.PaneTxt1.setText(null);//Limpiamos el área de texto
                ViewAutorNomServer.PaneTxt1.setText(mensaje);//Agregamos el mensaje

                skServidor.close();//Cierro conexión con el cliente
            }

        } catch (IOException ex) {
            Logger.getLogger(CrearSockets.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error en CrearSocketServer, Clase: CrearSockets");
        }
    }

    //Socket de conexión entre el cliente (envía el texto del chat) y server
    //puerto 5001
    public void SocketChatClienteEnv(int puerto) {
        try {
            //Creamos el socket
            Socket sk1Cliente = new Socket("127.0.0.1", puerto);//InetAddress.getLocalHost(), 9998);

            //Creamos flujo de datos de salida, datos circularán por el socket creado
            DataOutputStream salida1 = new DataOutputStream(sk1Cliente.getOutputStream());

            //Indicamos al flujo de datos de salida que es lo que va a circular por ahí
            //estos serán los datos del área de texto del chat
            salida1.writeUTF("CLIENTE: " + ViewAutorNomCliente.PaneTxt3.getText());

            //Colocamos el chat enviado en el area de texto de lectura
            String cont = ViewAutorNomCliente.PaneTxt2.getText();
            ViewAutorNomCliente.PaneTxt2.setText(cont+"\n"+"TÚ: "+ ViewAutorNomCliente.PaneTxt3.getText());

            //Cerramos el flujo de salida
            salida1.close();

        } catch (IOException ex) {
            Logger.getLogger(CrearSockets.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error en CrearSocketCliente, Clase: SocketChatCliente");
        }
    }

    //Socket de conexión entre el server (recepción del texto) y el cliente para el chat
    //puerto 5001. Server en espera (escucha).
    public void SocketChatServerEsp(int puerto) {

        ServerSocket ssk1Servidor = null;
        Socket sk1Servidor = null;

        try {
            //Creamos un objeto serversocket para abrir el puerto
            ssk1Servidor = new ServerSocket(puerto);

            while (true) {
                //Creamos el socket de entrada y especificamos que acepte las conexiones
                sk1Servidor = ssk1Servidor.accept();//Variable tipo socket para aceptar las conexiones

                //Creamos flujo de datos de entrada, datos enviados por el cliente
                DataInputStream entrada1 = new DataInputStream(sk1Servidor.getInputStream());

                //Leemos los datos que vienen en el flujo de entrada
                String mensaje = entrada1.readUTF();

                //Escribimos los datos en el área de texto correspondiente
                String cont = ViewAutorNomServer.PaneTxt2.getText();
                ViewAutorNomServer.PaneTxt2.setText(cont + "\n" + mensaje);//Agregamos el mensaje

                sk1Servidor.close();//Cierro conexión con el cliente
            }

        } catch (IOException ex) {
            Logger.getLogger(CrearSockets.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error en CrearSocketServer, Clase: CrearSockets");
        }
    }

    //Socket de conexión entre el cliente y server (envía el texto)
    //puerto 5002
    public void SocketChatServerEnv(int puerto) {
        try {
            //Creamos el socket
            Socket sk2Cliente = new Socket("127.0.0.1", puerto);//InetAddress.getLocalHost(), 9998);

            //Creamos flujo de datos de salida, datos circularán por el socket creado
            DataOutputStream salida2 = new DataOutputStream(sk2Cliente.getOutputStream());

            //Indicamos al flujo de datos de salida que es lo que va a circular por ahí
            //Serán los datos del área de texto del chat
            salida2.writeUTF("SERVER: " + ViewAutorNomServer.PaneTxt3.getText());

            //Colocamos el chat enviado en el area de texto de lectura
            String cont = ViewAutorNomServer.PaneTxt2.getText();
            ViewAutorNomServer.PaneTxt2.setText(cont + "\n" + "TÚ: " + ViewAutorNomServer.PaneTxt3.getText());

            //Cerramos el flujo de salida
            salida2.close();

        } catch (IOException ex) {
            Logger.getLogger(CrearSockets.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error en CrearSocketCliente, Clase: SocketChatCliente");
        }
    }

    //Socket de conexión entre el server y el cliente (espera el texto del chat) para el chat
    //puerto 5002. Cliente en espera (escucha).
    public void SocketChatClienteEsp(int puerto) {

        ServerSocket ssk2Servidor = null;
        Socket sk2Servidor = null;

        try {
            //Creamos un objeto serversocket para abrir el puerto
            ssk2Servidor = new ServerSocket(puerto);

            while (true) {
                //Creamos el socket de entrada y especificamos que acepte las conexiones
                sk2Servidor = ssk2Servidor.accept();//Variable tipo socket para aceptar las conexiones

                //Creamos flujo de datos de entrada, datos enviados por el cliente
                DataInputStream entrada2 = new DataInputStream(sk2Servidor.getInputStream());

                //Leemos los datos que vienen en el flujo de entrada
                String mensaje = entrada2.readUTF();

                //Escribimos los datos en el área de texto correspondiente
                String cont = ViewAutorNomCliente.PaneTxt2.getText();
                ViewAutorNomCliente.PaneTxt2.setText(cont+"\n"+mensaje);//Agregamos el mensaje

                sk2Servidor.close();//Cierro conexión con el cliente
            }
        } catch (IOException ex) {
            Logger.getLogger(CrearSockets.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Error en CrearSocketServer, Clase: CrearSockets");
        }
    }
}