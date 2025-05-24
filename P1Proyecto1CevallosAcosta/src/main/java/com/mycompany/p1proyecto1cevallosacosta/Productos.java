
package com.mycompany.p1proyecto1cevallosacosta;

import java.util.Scanner;

public class Productos {
    private int numeroSerie;
    private String color;
    private String correo;
     private int cantidad;
    Scanner dato1 = new Scanner(System.in);

    public Productos(int numeroSerie, String color, String correo, int cantidad) {
        this.numeroSerie = numeroSerie;
        this.color = color;
        this.correo = correo;
        this.cantidad = cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getColor() { 
        return color;
    }
    public int getNumeroSerie() { 
        return numeroSerie; 
    }
    public String getCorreo() {
        return correo;
    }

    public void setNumeroSerie() {
        boolean valido = false;
        while (!valido) {
            try {
                System.out.print("Ingrese el Numero de serie  del producto: ");
                numeroSerie = dato1.nextInt();
                if ( numeroSerie < 10000 ||  numeroSerie > 99999) 
                    throw new CampoVacioException("El numero de serie debe ser mayor a 99999 ni menor a 10000");
                this. numeroSerie =  numeroSerie;
                valido = true;
            } catch (CampoVacioException e) {
                System.out.println("Error al validar codigo: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Entrada no valida. Intente nuevamente.");
                dato1.nextLine();
            }
        }
    }

    public void setColor(String color) {
        try {
            while (color == null || color.trim().isEmpty()) {
                System.out.println("El color no puede estar vacio. Ingrese nuevamente:");
                color = dato1.nextLine();
            }
            this.color = color.trim();
        } catch (Exception e) {
            System.out.println("Error al asignar color: " + e.getMessage());
        }
    }

    public void setCorreo() throws FormatoCorreoInvalidoException { 
     boolean valido = false;
        while (!valido) {
            try {
                System.out.print("Ingrese el correo del proveedor :");
                dato1.nextLine();
                correo = dato1.nextLine();
               if (correo == null || !correo.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")||correo.trim().isEmpty())
               throw new FormatoCorreoInvalidoException("Correo invalido. Debe tener formato ejemplo@dominio.com");
                this.correo = correo;
                valido = true;
            } catch (FormatoCorreoInvalidoException e) {
                System.out.println("Error al validar correo: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Entrada no valida. Intente nuevamente.");
                dato1.nextLine();
            }
        }
    }
    
      public void setCantidad(int cantidad) {
        boolean valido = false;
        while (!valido) {
            try {
                System.out.print("Ingrese cantidad  de productos: ");
                cantidad = dato1.nextInt();
                if ( cantidad <= 0) 
                    throw new CampoVacioException("El numero de serie debe ser mayor a 0");
                this. cantidad =  cantidad;
                valido = true;
            } catch (CampoVacioException e) {
                System.out.println("Error al validar codigo: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Entrada no valida. Intente nuevamente.");
                dato1.nextLine();
            }
        }
    }


    public void mostrarDatos() {
        try {
            System.out.println("Ingrese el color del articulo :");
            setColor(dato1.nextLine());
            System.out.println("El color del producto ingresado es : " + getColor());
            setNumeroSerie();
            System.out.println("El numero de serie del producto ingresado es : " + getNumeroSerie());
            setCorreo();
            System.out.println("Correo registrado: " + getCorreo());
            System.out.println("Ingrese la cantidad del articulo :");
            setCantidad(dato1.nextInt());
            System.out.println("la cantidad de producto ingresado es : " + getColor());
        } catch (FormatoCorreoInvalidoException e) {
            System.out.println("Error: " + e.getMessage());
            mostrarDatos();
        }
    }
}
