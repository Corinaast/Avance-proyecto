package com.mycompany.p1proyecto1cevallosacosta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public abstract class GestorTrab extends Trabajadores {
    private String ARCHIVO = "trabajadores.csv";
    Scanner dato3 = new  Scanner (System.in);
  
    public GestorTrab(String usuario, int password) {
        super(usuario, password);
    }
    
     public void registraTrabajador() {
    String archivo = "trabajadores.csv";

    System.out.print("Nuevo usuario: ");
    String nuevoUsuario = dato3.nextLine();
    System.out.print("Nueva contraseña (5 dígitos): ");
    String nuevaPass = dato3.nextLine();

    if (!nuevaPass.matches("\\d{5}")) {
        System.out.println("La contraseña debe tener exactamente 5 dígitos.");
        return;
    }

    // Crea el archivo si no existe
    File file = new File(archivo);
    try {
        file.createNewFile();
    } catch (IOException e) {
        System.out.println("Error creando archivo: " + e.getMessage());
        return;
    }

    // Verifica si el usuario ya existe
    try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(",");
            if (partes.length == 2 && partes[0].equals(nuevoUsuario)) {
                System.out.println("El usuario ya está registrado.");
                return;
            }
        }
    } catch (IOException e) {
        System.out.println("Error leyendo archivo: " + e.getMessage());
    }

    // Escribe el nuevo usuario
    try (FileWriter writer = new FileWriter(archivo, true)) {
        writer.write(nuevoUsuario + "," + nuevaPass + "\n");
        System.out.println("Registro exitoso.");
    } catch (IOException e) {
        System.out.println("Error guardando usuario: " + e.getMessage());
    }
}


    @Override
    public void ingresarDatos() {
        System.out.print("Ingrese usuario nuevo: ");
        setUsuario(dato3.nextLine());
        System.out.print("Ingrese contraseña (5 dígitos): ");
        setPassword(Integer.parseInt(dato3.nextLine()));

        try (FileWriter fw = new FileWriter(ARCHIVO, true)) {
            fw.write(getUsuario() + "," + getPassword () + "\n");
            System.out.println("Trabajador registrado exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al registrar trabajador: " + e.getMessage());
        }
    }

    @Override
    public boolean validarDatos() {
        System.out.print("Ingrese usuario: ");
        String userInput = dato3.nextLine();
        System.out.print("Ingrese contraseña: ");
        String passInput = dato3.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 2 && partes[0].equals(userInput) && partes[1].equals(passInput)) {
                    System.out.println("Acceso concedido.");
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer trabajadores: " + e.getMessage());
        }

        System.out.println("Usuario o contraseña incorrectos.");
        return false;
    }
    
   
}