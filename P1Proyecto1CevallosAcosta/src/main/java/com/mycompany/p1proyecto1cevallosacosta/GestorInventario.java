
package com.mycompany.p1proyecto1cevallosacosta;


import java.io.*;
import java.util.*;

class GestorInventario {
    private ArrayList<ItemInventario> inventario = new ArrayList<>();

    public void agregarItem() {
        System.out.println("\n=== Ingreso de Catalogo de celulares ===");
        Categoria categoria = new Categoria(0, "", "", 0);
        Productos producto = new Productos(0, "","",0);
        categoria.mostrarDatosCat();
        producto.mostrarDatos();

        try {
            verificarDuplicado(producto.getNumeroSerie(), categoria.getCodigo());
            ItemInventario nuevoItem = new ItemInventario(producto, categoria);
            inventario.add(nuevoItem);
        } catch (DatosDuplicadosException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void mostrarInventario() {
        System.out.println("\n=== Inventario Registrado ===");
        for (ItemInventario item : inventario) {
            item.mostrarDatosCompletos();
        }
    }

    public void guardarEnCSV(String nombreArchivo) {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write("NumeroSerie,Color,Correo,Cantidad,Codigo,Marca,Modelo,Precio\n");
            for (ItemInventario item : inventario) {
                writer.write(item.toCSV() + "\n");
            }
            System.out.println("Inventario guardado en CSV correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar CSV: " + e.getMessage());
        }
    }

 public void leerDesdeCSV(String nombreArchivo) {
     inventario.clear();
    try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
        String linea;
        boolean esPrimeraLinea = true;
        int cargados = 0;
        while ((linea = br.readLine()) != null) {
            if (esPrimeraLinea) {
                esPrimeraLinea = false;
                continue;
            }

            String[] partes = linea.split(",");
            if (partes.length == 8) {
                int numeroSerie = Integer.parseInt(partes[0].trim());
                String color = partes[1].trim();
                String correo = partes[2].trim();
                int cantidad = Integer.parseInt(partes[3].trim());
                int codigo = Integer.parseInt(partes[4].trim());
                String marca = partes[5].trim();
                String modelo = partes[6].trim();
                double precio = Double.parseDouble(partes[7].trim());

                try {
                    verificarDuplicado(numeroSerie, codigo);
                    Productos producto = new Productos(numeroSerie, color, correo,cantidad);
                    Categoria categoria = new Categoria(codigo, marca, modelo, precio);
                    inventario.add(new ItemInventario(producto, categoria));
                    cargados++;
                } catch (DatosDuplicadosException e) {
                    System.out.println("Duplicado no cargado: " + e.getMessage());
                }
            }
        }
        System.out.println("\nCSV leido correctamente. Total cargados: " + cargados);
    } catch (Exception e) {
        System.out.println("Error al leer CSV: " + e.getMessage());
    }
}

public void verificarDuplicado(int numeroSerie, int codigo) throws DatosDuplicadosException {
    boolean encontradoSerie = false;
    boolean encontradoCodigo = false;

    for (ItemInventario item : inventario) {
        if (item.getProducto().getNumeroSerie() == numeroSerie) {
            encontradoSerie = true;
        }
        if (item.getCategoria().getCodigo() == codigo) {
            encontradoCodigo = true;
        }
    }

    if (encontradoSerie && encontradoCodigo) {
        throw new DatosDuplicadosException("Numero de serie y codigo ya existen: " + numeroSerie + ", " + codigo);
    } else if (encontradoSerie) {
        throw new DatosDuplicadosException("Numero de serie duplicado: " + numeroSerie);
    } else if (encontradoCodigo) {
        throw new DatosDuplicadosException("Codigo duplicado: " + codigo);
    }
}



public void verContenidoCSV(String nombreArchivo) {
    System.out.println("\n=== Contenido del archivo CSV ===");
    try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            System.out.println(linea);
        }
    } catch (IOException e) {
        System.out.println("Error al leer el archivo CSV: " + e.getMessage());
    }
}
public ItemInventario buscarItemPorCodigo(int codigo) {
    for (ItemInventario item : inventario) {
        if (item.getCategoria().getCodigo() == codigo) {
            return item;
        }
    }
    return null;
}

public List<ItemInventario> leerTodosDesdeCSV(String nombreArchivo) throws IOException {
    List<ItemInventario> lista = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
        String linea;
        br.readLine(); // skip header
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(",");
            if (partes.length == 8) {
                int numeroSerie = Integer.parseInt(partes[0].trim());
                String color = partes[1].trim();
                String correo = partes[2].trim();
                int cantidad = Integer.parseInt(partes[3].trim());
                int codigo = Integer.parseInt(partes[4].trim());
                String marca = partes[5].trim();
                String modelo = partes[6].trim();
                double precio = Double.parseDouble(partes[7].trim());

                Productos producto = new Productos(numeroSerie, color, correo, cantidad);
                Categoria categoria = new Categoria(codigo, marca, modelo, precio);
                lista.add(new ItemInventario(producto, categoria));
            }
        }
    }
    return lista;
}

public void guardarInventarioDesdeLista(String nombreArchivo, List<ItemInventario> lista) throws IOException {
    try (FileWriter writer = new FileWriter(nombreArchivo)) {
        writer.write("NumeroSerie,Color,Correo,Cantidad,Codigo,Marca,Modelo,Precio\n");
        for (ItemInventario item : lista) {
            writer.write(item.toCSV() + "\n");
        }
    }
}


}
