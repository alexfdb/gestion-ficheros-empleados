package com.alexfdb;
import java.util.Set;
import com.alexfdb.file.FileMapOperations;
import com.alexfdb.model.Empleado;
/**
 * @author alexfdb
 * @version 1.0.0
 */
public class Main {

    public static void main(String[] args) {

        FileMapOperations fileOperations = new FileMapOperations();

        Empleado empleado1 = new Empleado("E001", "Juan Pérez", "Desarrollador", 2500.50, "1990-05-15");
        Empleado empleado2 = new Empleado("E002", "Ana Gómez", "Diseñadora", 2200.75, "1985-08-22");
        Empleado empleado3 = new Empleado("E003", "Carlos López", "Desarrollador", 2800.00, "1992-03-10");

        System.out.println("Creando empleados...");
        fileOperations.create(empleado1);
        fileOperations.create(empleado2);
        fileOperations.create(empleado3);

        System.out.println("\nLeyendo empleado con identificador 'E001':");
        Empleado empleadoLeido = fileOperations.read("E001");
        if (empleadoLeido != null) {
            System.out.println(empleadoLeido);
        } else {
            System.out.println("Empleado no encontrado.");
        }

        System.out.println("\nActualizando el salario de 'E002'...");
        empleado2.setSalario(2400.00);
        fileOperations.update(empleado2);

        System.out.println("\nLeyendo empleado con identificador 'E002' después de actualización:");
        empleadoLeido = fileOperations.read("E002");
        if (empleadoLeido != null) {
            System.out.println(empleadoLeido);
        } else {
            System.out.println("Empleado no encontrado.");
        }

        System.out.println("\nEliminando empleado con identificador 'E003'...");
        boolean eliminado = fileOperations.delete("E003");
        if (eliminado) {
            System.out.println("Empleado con identificador 'E003' eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar el empleado.");
        }

        System.out.println("\nConsultando empleados con el puesto 'Desarrollador':");
        Set<Empleado> empleadosDesarrolladores = fileOperations.empleadosPorPuesto("Desarrollador");
        for (Empleado empleado : empleadosDesarrolladores) {
            System.out.println(empleado);
        }

        System.out.println("\nConsultando empleados con fecha de nacimiento entre '1990-01-01' y '1992-12-31':");
        Set<Empleado> empleadosPorEdad = fileOperations.empleadosPorEdad("1990-01-01", "1992-12-31");
        for (Empleado empleado : empleadosPorEdad) {
            System.out.println(empleado);
        }
    }
}