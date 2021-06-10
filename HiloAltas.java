package com.mycompany.ds_dpo3_u3_ea_hicl;

//Clase que implementa los hilos de ejecución para la entrada de los vehículos
public class HiloAltas implements Runnable{
    
    //Constructor de la clase
    public HiloAltas(){//Recibe por parámetro el acomodador
    }
    
    //Método Run
    public void run()
    {//Invocamos la clase para dar de alta autos
        AltasAutos altasautos = new AltasAutos();
        altasautos.main();
    }    
}
