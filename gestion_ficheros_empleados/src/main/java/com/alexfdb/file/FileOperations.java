package com.alexfdb.file;
import java.util.Set;
import com.alexfdb.model.Empleado;
/**
 * @author alexfdb
 * @version 1.0.0
 */
public interface FileOperations {
    boolean create(Empleado empleado);
    boolean delete(String identificador);
    boolean update(Empleado empleado);
    Empleado read(String identificador);
    Empleado read(Empleado empleado);
    Set<Empleado> empleadosPorPuesto(String puesto);
    Set<Empleado> empleadosPorEdad(String fechaInicio, String fechaFin);
}
