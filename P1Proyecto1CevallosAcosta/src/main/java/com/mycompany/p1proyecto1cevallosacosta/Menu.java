/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.p1proyecto1cevallosacosta;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author ceval
 */
public class Menu {
     GestorTrab trabajador = new GestorTrab("", 0) {};
     GestorInventario gestor = new GestorInventario();
    CarritoCompras carrito = new CarritoCompras();
    Scanner sc = new Scanner(System.in);

    public void mostrar() {
        int opcion;
        do {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Ingresar como Cliente");
            System.out.println("2. Registrarse/Ingresar como Trabajador");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> menuCliente();
                case 2 -> menuTrabajador();
                case 3 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opcion invalida.");
            }

        } while (opcion != 3);
    }

    private void menuCliente() {
        int opcion;
        do {
            System.out.println("\n=== MENU CLIENTE ===");
            System.out.println("1. Ver catalogo y agregar al carrito");
            System.out.println("2. Ver carrito");
            System.out.println("3. Comprar");
            System.out.println("4. Volver");
            System.out.print("Opcion: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> {
                    gestor.leerDesdeCSV("inventario.csv");
                    gestor.mostrarInventario();

                    System.out.print("Codigo producto: ");
                    int codigo = sc.nextInt();
                    System.out.print("Cantidad: ");
                    int cantidad = sc.nextInt();
                    sc.nextLine();

                    ItemInventario item = gestor.buscarItemPorCodigo(codigo);
                    if (item != null) {
                    if (cantidad <= item.getProducto().getCantidad()) {
                     carrito.agregarAlCarrito(item, cantidad);
                     carrito.guardarCarritoCSV("carrito.csv");
                   System.out.println("Producto agregado al carrito.");
                   } else {
                   System.out.println("No hay suficiente stock. Disponible: " + item.getProducto().getCantidad());
                   }
                    } else {
                    System.out.println("Producto no encontrado.");
                    }
                }
                case 2 -> carrito.mostrarCarrito();
                case 3 -> {
               carrito.cargarCarritoDesdeCSV("carrito.csv");
                    if (carrito.isEmpty()) {
                        System.out.println("El carrito está vacío.");
                        break;
                    }

                    carrito.mostrarCarrito();
                    double total = carrito.calcularTotal();
                    System.out.printf("Total: $%.2f\n", total);
                    System.out.print("Confirmar compra (s/n): ");
                    if (sc.nextLine().equalsIgnoreCase("s")) {
                        System.out.println("Compra realizada.");
                        try (PrintWriter writer = new PrintWriter("carrito.csv")) {
                            writer.println("NumeroSerie,Color,Correo,Cantidad,Codigo,Marca,Modelo,Precio");
                        } catch (IOException e) {
                            System.out.println("Error limpiando carrito: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Compra cancelada.");
                    }
                }
            }
        } while (opcion != 4);
    }

   private void menuTrabajador() {
    System.out.println("\n=== Trabajador ===");
    System.out.println("1. Registrarse");
    System.out.println("2. Iniciar Sesión");
    System.out.print("Seleccione una opción: ");
    int opcion = sc.nextInt();
    sc.nextLine();

    switch (opcion) {
        case 1 -> trabajador.registraTrabajador();
        case 2 -> {
            if (trabajador.validarDatos()) {
                gestor.leerDesdeCSV("inventario.csv");
                gestor.agregarItem();
                gestor.guardarEnCSV("inventario.csv");
            }
        }
        default -> System.out.println("Opción inválida.");
    }
      }
    }

