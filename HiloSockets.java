package com.mycompany.ds_dpo3_u3_ea_hicl;

//Clase que implementa los hilos para las conexiones entre Cliente-Servidor
import javax.swing.JOptionPane;

//Recibir texto en primer plano y permanecer a la escucha en segundo plano
public class HiloSockets implements Runnable {

    //Variables para el puerto, y tipo de aplicación: 1-server, 2-cliente
    int puerto, tipo;

    public HiloSockets(int port, int tip) {
        puerto = port;
        tipo = tip;
    }

    public void run() {
        //****SI ES EL SERVER QUIEN ESTÁ A LA ESPERA
        if (puerto == 5000 && tipo == 1) {//Puerto para el archivo  de nomina a autorizar
            //Creamos un socket para el servidor
            CrearSockets skServer = new CrearSockets();//Objeto para crear el socket
            skServer.SocketMsgServer(puerto);//Creamos el socket y pasamos el puerto
        }
        if (puerto == 5001 && tipo == 1) {//Puerto para el chat, recibe texto del cliente
            //Creamos un socket para el servidor
            CrearSockets skServer = new CrearSockets();//Objeto para crear el socket
            skServer.SocketChatServerEsp(puerto);//Creamos el socket y pasamos el puerto
        }

        //****SI ES EL CLIENTE QUIEN ESTÁ A LA ESPERA
        if (puerto == 5002 && tipo == 2) {//Puerto para el chat, recibe texto del servidor
            //Creamos un socket para el servidor
            CrearSockets skServer = new CrearSockets();//Objeto para crear el socket
            skServer.SocketChatClienteEsp(puerto);//Creamos el socket y pasamos el puerto
        }
    }
}
