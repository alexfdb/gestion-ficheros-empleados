# Informe de Prueba - Gestión de Empleados

Este proyecto gestiona la información de empleados a través de un archivo de texto, permitiendo operaciones como la creación, lectura, actualización, eliminación y consultas específicas.

## Salida del programa

A continuación se muestra la salida generada al ejecutar el programa principal (`Main`), donde se prueban las funciones básicas sobre un conjunto de empleados.

```code

Creando empleados...

Leyendo empleado con identificador 'E001':
E001, Juan Pérez, Desarrollador, 2500.5, 1990-05-15

Actualizando el salario de 'E002'...

Leyendo empleado con identificador 'E002' después de actualización:
E002, Ana Gómez, Diseñadora, 2400.0, 1985-08-22

Eliminando empleado con identificador 'E003'...
Empleado con identificador 'E003' eliminado correctamente.

Consultando empleados con el puesto 'Desarrollador':
E001, Juan Pérez, Desarrollador, 2500.5, 1990-05-15

Consultando empleados con fecha de nacimiento entre '1990-01-01' y '1992-12-31':        
E001, Juan Pérez, Desarrollador, 2500.5, 1990-05-15

```

## A continuacion adjunto las clases 

### Operations

```java

public interface Operations {
    boolean create(Empleado empleado);
    boolean delete(String identificador);
    boolean update(Empleado empleado);
    Empleado read(String identificador);
    Empleado read(Empleado empleado);
    Set<Empleado> empleadosPorPuesto(String puesto);
    Set<Empleado> empleadosPorEdad(String fechaInicio, String fechaFin);
}

```

### FileOperations

```java

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
                if(datos.length == 5) {
                    Empleado empleado = new Empleado(datos[0], datos[1], datos[2], Double.parseDouble(datos[3]), datos[4]);
                    empleados.putIfAbsent(empleado.getIdentificador(), empleado);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return empleados;
    }

    /**
     * Reescribe el archivo con los empleados proporcionados en el TreeMap.
     * @param empleados El TreeMap de empleados que se escribiran en el archivo.
     * @return true si la operacion fue exitosa, false si ocurrio un error.
     */
    protected boolean updateFile(Map<String, Empleado> empleados) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (Empleado empleado : empleados.values()) {
                writer.write(empleado.toString());
                writer.newLine();                
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error al actualizar el fichero: " + e.getMessage());
        }
        return false;
    }
}

```

### FileMapOperations

```java

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

```