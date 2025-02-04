package com.alexfdb.file;
import java.io.*;
import java.util.*;
import com.alexfdb.model.Empleado;
import com.alexfdb.operations.Operations;
/**
 * @author alexfdb
 * @version 1.0.0
 */
public class FileOperations implements Operations {

    private static final String FILE_NAME = "gestion_ficheros_empleados\\src\\main\\resources\\empleados.txt"; // Ruta del archivo en resources

    /**
     * Crea un nuevo empleado y lo guarda en el archivo.
     * @param empleado El empleado a crear.
     * @return true si se ha creado correctamente, false si ocurre un error.
     */
    @Override
    public boolean create(Empleado empleado) {
        Map<String, Empleado> empleados = obtenerEmpleados();
        empleados.put(empleado.getIdentificador(), empleado); // Usamos el identificador como clave
        return reescribirArchivo(empleados);
    }

    /**
     * Lee un empleado por su identificador.
     * @param identificador El identificador del empleado a leer.
     * @return El empleado encontrado o null si no se encuentra.
     */
    @Override
    public Empleado read(String identificador) {
        Map<String, Empleado> empleados = obtenerEmpleados();
        return empleados.get(identificador);
    }

    /**
     * Lee un empleado por los datos proporcionados en el objeto Empleado.
     * @param empleado El objeto Empleado con los datos a buscar.
     * @return El empleado encontrado o null si no se encuentra.
     */
    @Override
    public Empleado read(Empleado empleado) {
        if (empleado == null) {
            return null;
        }
        return read(empleado.getIdentificador());
    }

    /**
     * Elimina un empleado por su identificador.
     * @param identificador El identificador del empleado a eliminar.
     * @return true si se ha eliminado correctamente, false si no se encuentra.
     */
    @Override
    public boolean delete(String identificador) {
        Map<String, Empleado> empleados = obtenerEmpleados();
        if (empleados.containsKey(identificador)) {
            empleados.remove(identificador);
            return reescribirArchivo(empleados);
        }
        return false;
    }

    /**
     * Actualiza un empleado si ya existe.
     * @param empleado El empleado con los nuevos datos.
     * @return true si se actualizó correctamente, false si no se encontró el empleado.
     */
    @Override
    public boolean update(Empleado empleado) {
        Map<String, Empleado> empleados = obtenerEmpleados();
        if (empleados.containsKey(empleado.getIdentificador())) {
            empleados.put(empleado.getIdentificador(), empleado); // Actualiza el empleado
            return reescribirArchivo(empleados);
        }
        return false;
    }

    /**
     * Obtiene un conjunto de empleados con el puesto especificado.
     * @param puesto El puesto que se busca.
     * @return Un conjunto de empleados con el puesto indicado.
     */
    @Override
    public Set<Empleado> empleadosPorPuesto(String puesto) {
        Set<Empleado> empleadosPorPuesto = new HashSet<>();
        Map<String, Empleado> empleados = obtenerEmpleados();
        for (Empleado empleado : empleados.values()) {
            if (empleado.getPuesto().equalsIgnoreCase(puesto)) {
                empleadosPorPuesto.add(empleado);
            }
        }
        return empleadosPorPuesto;
    }

    /**
     * Obtiene un conjunto de empleados cuya edad esté en el rango de fechas proporcionado.
     * @param fechaInicio Fecha de inicio del rango.
     * @param fechaFin Fecha de fin del rango.
     * @return Un conjunto de empleados dentro del rango de edad.
     */
    @Override
    public Set<Empleado> empleadosPorEdad(String fechaInicio, String fechaFin) {
        Set<Empleado> empleadosPorEdad = new HashSet<>();
        Map<String, Empleado> empleados = obtenerEmpleados();
        for (Empleado empleado : empleados.values()) {
            if (empleado.getFechaNacimiento().compareTo(fechaInicio) >= 0 &&
                empleado.getFechaNacimiento().compareTo(fechaFin) <= 0) {
                empleadosPorEdad.add(empleado);
            }
        }
        return empleadosPorEdad;
    }

    /**
     * Obtiene todos los empleados del archivo como un TreeMap, ordenado por el identificador.
     * @return Un TreeMap con empleados, ordenado por identificador.
     */
    private Map<String, Empleado> obtenerEmpleados() {
        Map<String, Empleado> empleados = new TreeMap<>(); // Usamos un TreeMap para mantener ordenados los empleados
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] datos = line.split(", ");
                if (datos.length == 5) {
                    Empleado empleado = new Empleado(datos[0], datos[1], datos[2], Double.parseDouble(datos[3]), datos[4]);
                    empleados.put(empleado.getIdentificador(), empleado); // Se agrega al TreeMap
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
    private boolean reescribirArchivo(Map<String, Empleado> empleados) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
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