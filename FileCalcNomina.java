package com.mycompany.ds_dpo3_u3_ea_hicl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class FileCalcNomina {

    //Método que guarda el reporte de nómina en un archivo
    //para ello se inmplementan STREAMS del tipo CHARACTERSTREAMS
    //mediante las clases FILEWRITER y BUFFEREDWRITER
    public void CrearArchivo() {
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

}
