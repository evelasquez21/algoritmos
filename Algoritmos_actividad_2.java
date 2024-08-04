package com.mycompany.algoritmos_actividad_2;

import java.util.Scanner;

/**
 * Esta clase fue creada para la actividad no.2 de algoritmos y fue subida a gitHub (tercer edición)
 * @author Eduardo Velasquez
 * @since 2024-07-02
 * 
 */

public class Algoritmos_actividad_2 {

    public static void main(String[] args) {
     /**
     * Método principal de la case algoritmos_actividad_2
     * @since 2024-07-02
     * @param args prámetros introducidos al iniciar el programa.
     */
        
        //Herramienta para la lectura de datos
        Scanner scan = new Scanner(System.in);
        
        // Lectura de datos
        System.out.println("Ingrese el monto en quetzales: ");
        double quetzales = scan.nextInt();
        
        // Método para convertir quetzales a dolares
        double dolares;
        dolares = quetzales / 7.60;
        
        // Salida
        System.out.println("El monto en dolares es: " + dolares);
    }
}
