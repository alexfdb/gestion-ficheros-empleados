package com.alexfdb.file;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import com.alexfdb.model.Empleado;
import com.alexfdb.operations.Operations;
/**
 * @author alexfdb
 * @version 1.0.0
 */
public class FileMapOperations extends FileOperations implements Operations {

    /**
     * Crea un nuevo empleado y lo guarda en el archivo.
     * @param empleado El empleado a crear.
     * @return true si se ha creado correctamente, false si ocurre un error.
     */
    @Override
    public boolean create(Empleado empleado) {
        if(empleado == null) return false;
        if(empleado.getIdentificador() == null || empleado.getIdentificador().isEmpty()) return false;
        Map<String, Empleado> empleados = readFile();
        empleados.putIfAbsent(empleado.getIdentificador(), empleado);
        return updateFile(empleados);
    }

    /**
     * Lee un empleado por su identificador.
     * @param identificador El identificador del empleado a leer.
     * @return El empleado encontrado o null si no se encuentra.
     */
    @Override
    public Empleado read(String identificador) {
        if(identificador == null || identificador.isEmpty()) return null;
        Map<String, Empleado> empleados = readFile();
        return empleados.get(identificador);
    }

    /**
     * Lee un empleado por los datos proporcionados en el objeto Empleado.
     * @param empleado El objeto Empleado con los datos a buscar.
     * @return El empleado encontrado o null si no se encuentra.
     */
    @Override
    public Empleado read(Empleado empleado) {
        if(empleado == null) return null;
        if(empleado.getIdentificador() == null || empleado.getIdentificador().isEmpty()) return null;
        return read(empleado.getIdentificador());
    }

    /**
     * Elimina un empleado por su identificador.
     * @param identificador El identificador del empleado a eliminar.
     * @return true si se ha eliminado correctamente, false si no se encuentra.
     */
    @Override
    public boolean delete(String identificador) {
        if(identificador == null || identificador.isEmpty()) return false;
        Map<String, Empleado> empleados = readFile();
        if(empleados.remove(identificador) != null) {
            return updateFile(empleados);
        }
        return false;
    }

    /**
     * Actualiza un empleado si ya existe.
     * @param empleado El empleado con los nuevos datos.
     * @return true si se actualizo correctamente, false si no se encontro el empleado.
     */
    @Override
    public boolean update(Empleado empleado) {
        if(empleado == null) return false;
        if(empleado.getIdentificador() == null || empleado.getIdentificador().isEmpty()) return false;
        Map<String, Empleado> empleados = readFile();
        if(empleados.replace(empleado.getIdentificador(), empleado) != null) {
            return updateFile(empleados);
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
        if(puesto == null || puesto.isEmpty()) return empleadosPorPuesto;
        Map<String, Empleado> empleados = readFile();
        for (Empleado empleado : empleados.values()) {
            if (empleado.getPuesto().equalsIgnoreCase(puesto)) {
                empleadosPorPuesto.add(empleado);
            }
        }
        return empleadosPorPuesto;
    }

    /**
     * Obtiene un conjunto de empleados cuya edad este en el rango de fechas proporcionado.
     * @param fechaInicio Fecha de inicio del rango.
     * @param fechaFin Fecha de fin del rango.
     * @return Un conjunto de empleados dentro del rango de edad.
     */
    @Override
    public Set<Empleado> empleadosPorEdad(String fechaInicio, String fechaFin) {
        Set<Empleado> empleadosPorEdad = new HashSet<>();
        if(fechaInicio == null || fechaInicio.isEmpty()) return empleadosPorEdad;
        if(fechaFin == null || fechaFin.isEmpty()) return empleadosPorEdad;
        Map<String, Empleado> empleados = readFile();
        for (Empleado empleado : empleados.values()) {
            if (empleado.getFechaNacimiento().compareTo(fechaInicio) >= 0 &&
                empleado.getFechaNacimiento().compareTo(fechaFin) <= 0) {
                empleadosPorEdad.add(empleado);
            }
        }
        return empleadosPorEdad;
    }
}