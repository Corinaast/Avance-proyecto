
package com.mycompany.p1proyecto1cevallosacosta;

import java.util.Scanner;
public abstract class Trabajadores {
    
   private String usuario;
   private int password;

Scanner dato2 = new Scanner (System.in);
    public Trabajadores(String usuario, int password) {
        this.usuario = usuario;
        this.password = password;
    }

    public String getUsuario() {
        return usuario;
    }

    public int getPassword() {
        return password;
    }

    public void setUsuario(String usuario) {
          try {
            while (usuario == null || usuario.trim().isEmpty()) {
                System.out.println("El color no puede estar vacio. Ingrese nuevamente:");
                usuario = dato2.nextLine();
            }
            this.usuario = usuario.trim();
        } catch (Exception e) {
            System.out.println("Error al asignar color: " + e.getMessage());
        }
    }
  

    public void setPassword(int password) {
          
    }
        
    
    
    public abstract void ingresarDatos();
    
    public abstract boolean validarDatos();
    
}
