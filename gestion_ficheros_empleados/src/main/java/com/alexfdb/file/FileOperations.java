package com.alexfdb.file;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import com.alexfdb.model.Empleado;
/**
 * @author alexfdb
 * @version 1.0.0
 */
abstract class FileOperations {
    
    private static String path = "gestion_ficheros_empleados\\src\\main\\resources\\empleados.txt";

    /**
     * Obtiene todos los empleados del archivo como un TreeMap, ordenado por el identificador.
     * @return Un TreeMap con empleados, ordenado por identificador.
     */
    protected Map<String, Empleado> readFile() {
        Map<String, Empleado> empleados = new TreeMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] datos = line.split(", ");
                if (datos.length == 5) {
                    Empleado empleado = new Empleado(datos[0], datos[1], datos[2], Double.parseDouble(datos[3]), datos[4]);
                    empleados.put(empleado.getIdentificador(), empleado);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        return empleados;
    }

    /**
     * Reescribe el archivo con los empleados proporcionados en el TreeMap.
     * @param empleados El TreeMap de empleados que se escribirán en el archivo.
     * @return true si la operación fue exitosa, false si ocurrió un error.
     */
    protected boolean updateFile(Map<String, Empleado> empleados) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (Empleado empleado : empleados.values()) {
                writer.write(empleado.toString());
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
            return false;
        }
    }
}