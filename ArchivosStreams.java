package com.mycompany.ds_dpo3_u3_ea_hicl;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

//Clase que implementa los métodos para trabajar con archivos: entrada y salida
//para ello se inmplementan STREAMS del tipo CHARACTERSTREAMS
//mediante las clases FILEWRITER y BUFFEREDWRITER
public class ArchivosStreams {

    File ARCHIVO = null;//constante ARCHIVO para las modificaciones

    //Método que seleccionar y lee un archivo
    public void LeerArchivo(int a) {
        try {//Se realiza la selección del archivo mediante un método
            JFileChooser fichero = new JFileChooser();//Creamos objeto de la clase JFileChooser
            fichero.showOpenDialog(null);//Abrimos la ventana para seleccionar el archivo
            File archivo = fichero.getSelectedFile();//Creamos objeto de clase File y asignamos el objeto fichero

            //Hacemos apertura y lectura del archivo
            FileReader fr = new FileReader(archivo);//Creamos objeto FileReader para abrir y leer archivo
            BufferedReader br = new BufferedReader(fr);//Creamos objeto BufferedReader para leer líneas enteras
            String linea = "";//Variable para almacenar la línea actual
            String texto = "";//Variable para concatenar las líneas leídas
            while (((linea = br.readLine()) != null))//Hacer mientras la línea actual sea diferente a nula
            {
                texto += linea + "\n";//concatenamos las líneas
            }
            ViewAutorNomCliente.PaneTxt1.setText(texto);//Escribimos el texto completo en la caja de texto
            ViewAutorNomCliente.PaneTxt1.setDisabledTextColor(Color.BLACK);
            if (a == 1) {//Si se está Modificando un archivo
                ARCHIVO = archivo;//Se almacena el archivo en la constante ARCHIVO
            }
            br.close();//Cerramos el archivo
        } catch (IOException e2)//Por si hay un error
        {
            JOptionPane.showMessageDialog(null, "No se pudo abrir el archivo. Error: " + e2);
        } catch (NullPointerException e2)//Por si hay un error
        {
            JOptionPane.showMessageDialog(null, "Error " + e2);
        }
    }

    public void CrearArchivo()//Método que crea el archivo de texto y lo guarda en una carpeta
    {
        boolean opt = false;

        try {//Solicitamos al usuario el nombre del archivo
            JFileChooser guardar = new JFileChooser();//Creamos objeto de la clase JFileChooser
            guardar.setSelectedFile(new File("SinTitulo.txt"));//Damos un nombre predefinido al archivo
            guardar.showSaveDialog(null);//Se abre la ventana 
            guardar.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);//Se busca directorio p/guardarlo
            File archivo = guardar.getSelectedFile();//Se crea el archivo del objeto File, le pasamos el nombre

            if (archivo.exists() == true & archivo.getName() != "SinTitulo.txt")//Checamos si el archivo ya existe
            {//Si existe preguntará si deseamos reemplazarlo, SI o NO
                //En una variable guardamos la elección del usuario
                int jOpt = JOptionPane.showConfirmDialog(null, "El fichero existe, ¿deseas reemplazarlo?", "Ya existe el archivo", JOptionPane.YES_NO_CANCEL_OPTION);

                if (jOpt == 0)//Si eligieron SI entonces se reemplaza el archivo
                {
                    opt = true;//Ponemos en true esta var para ahorrar líneas de código
                }

                if (jOpt == 1)//Si eligieron NO entonces vuelve a pedir el nombre del archivo
                {
                    CrearArchivo();
                }
            }

            //Si no existe el archivo (o eligieron reemplazarlo) entonces lo crea o reemplaza
            if (archivo.exists() == false || opt == true) {
                FileWriter fw = new FileWriter(archivo);//Creamos el objeto FileWriter para escribir en el archivo
                BufferedWriter bw = new BufferedWriter(fw);//Creamos el objeto BufferedWriter para escribir filewriter
                bw.write(ViewCalcNomina.PaneTxt1.getText());//Escribimos en el BufferedWriter lo que contiene la caja de texto
                bw.close();//Cerramos el BufferedWriter (archivo)
                JOptionPane.showMessageDialog(null, "Archivo creado y guardado");
            }
        } catch (FileNotFoundException ex)//Por si hay un error
        {
            JOptionPane.showMessageDialog(null, "Error al guardar, ponga nombre al archivo");
        } catch (IOException e3)//Por si hay un error
        {
            JOptionPane.showMessageDialog(null, "Error al guardar, en la salida");
        }
    }

    public void ModificarArchivo() {//Método que "guarda" o "guarda como" el archivo de texto modificado
        //boolean opt = false;

        try {//Guardamos las modificaciones
            FileWriter fw = new FileWriter(ARCHIVO);//Creamos el objeto FileWriter para escribir en el archivo
            BufferedWriter bw = new BufferedWriter(fw);//Creamos el objeto BufferedWriter para escribir filewriter
            bw.write(ViewCalcNomina.PaneTxt1.getText());//Escribimos en el BufferedWriter lo que contiene la caja de texto
            bw.close();//Cerramos el BufferedWriter (archivo)
            ARCHIVO = null;
            JOptionPane.showMessageDialog(null, "¡Cambios guardados!");
            //LimpiarCaja();//Limpia la caja de texto
            //DesbloquearBotones();//Se habilitan nuevamente los botones
        } catch (FileNotFoundException ex)//Por si hay un error
        {
            JOptionPane.showMessageDialog(null, "Error al guardar, ponga nombre al archivo");
        } catch (IOException e3)//Por si hay un error
        {
            JOptionPane.showMessageDialog(null, "Error al guardar, en la salida");
        }
    }

    public void RenameArchivo()//Método que renombra un archivo
    {   //Primero se selecciona el archivo a renombrar
        JFileChooser fichero = new JFileChooser();//Creamos objeto de la clase JFileChooser
        fichero.showOpenDialog(null);//Abrimos la ventana para seleccionar el archivo
        ARCHIVO = fichero.getSelectedFile();//En objeto File asignamos el objeto fichero a renombrar

        //Se ingresa el nuevo nombre
        String newName = JOptionPane.showInputDialog(null, "Ingrese el nuevo nombre del archivo: ");
        newName = ARCHIVO.getParent() + "\\" + newName + ".txt";//Agregamos el path y le concatenamos nombre y extension
        File archivo = new File(newName);//Creamos nuevo archivo para que almacene el nuevo nombre
        boolean ren = ARCHIVO.renameTo(archivo);//Realizamos el renombramiento

        if (ren)//Si se hizo correctamente, muestra le mensaje
        {
            JOptionPane.showMessageDialog(null, "Renombrado correcto del archivo en: " + archivo.getParent());
        } else//Si no, muestra mensaje de error
        {
            JOptionPane.showMessageDialog(null, "El renombrado no se ha podido realizar");
        }
        ARCHIVO = null;//Volvemos a null la constante ARCHIVO
    }

    public void EliminarArchivo()//Método que eliminar un archivo
    {//Primero se selecciona el archivo a eliminar
        JFileChooser fichero = new JFileChooser();//Creamos objeto de la clase JFileChooser
        fichero.showOpenDialog(null);//Abrimos la ventana para seleccionar el archivo
        File archivo = fichero.getSelectedFile();//En objeto File asignamos el objeto fichero a renombrar

        if (archivo.delete())//Si se eliminó correctamente, muestra le mensaje
        {
            JOptionPane.showMessageDialog(null, "Archivo correctamente eliminado");
        } else//Si no, muestra el mensaje
        {
            JOptionPane.showMessageDialog(null, "El archivo no ha podido ser eliminado");
        }
    }

}
