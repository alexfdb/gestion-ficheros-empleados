package com.alexfdb.file;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import com.alexfdb.model.Empleado;
/**
 * @author alexfdb
 * @version 1.0.0
 */
abstract class BasicFileOperations {
    
  private static String path = Paths.get("gestion_ficheros_empleados", "src", "main", "resources", "empleados.txt").toString();

    /**
     * Obtiene todos los empleados del archivo como un TreeMap, ordenado por el identificador.
     * @return Un TreeMap con empleados, ordenado por identificador.
     */
    protected Map<String, Empleado> readFileMap() {
        Map<String, Empleado> empleados = new TreeMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] datos = line.split(",");
                if(datos.length == 5) {
                    Empleado empleado = new Empleado(datos[0], datos[1], datos[2], Double.parseDouble(datos[3]), datos[4]);
                    empleados.putIfAbsent(empleado.getIdentificador(), empleado);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return empleados;
    }

    /**
     * Obtiene todos los empleados del archivo como un HashSet, ordenado por el identificador.
     * @return Un HashSet con empleados, ordenado por identificador.
     */
    protected Set<Empleado> readFileSet() {
        Set<Empleado> empleados = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] datos = line.split(",");
                if(datos.length == 5) {
                    Empleado empleado = new Empleado(datos[0], datos[1], datos[2], Double.parseDouble(datos[3]), datos[4]);
                    empleados.add(empleado);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return empleados;
    }

    /**
     * Reescribe el archivo con los empleados proporcionados en el Map.
     * @param empleados El Map de empleados que se escribiran en el archivo.
     * @return true si la operacion fue exitosa, false si ocurrio un error.
     */
    protected boolean updateFileMap(Map<String, Empleado> empleados) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (Empleado empleado : empleados.values()) {
                writer.write(empleado.toString());
                writer.newLine();                
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Reescribe el archivo con los empleados proporcionados en el Set.
     * @param empleados El Set de empleados que se escribiran en el archivo.
     * @return true si la operacion fue exitosa, false si ocurrio un error.
     */
    protected boolean updateFileSet(Set<Empleado> empleados) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (Empleado empleado : empleados) {
                writer.write(empleado.toString());
                writer.newLine();                
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}