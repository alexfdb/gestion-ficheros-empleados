package com.alexfdb.file;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import com.alexfdb.model.Empleado;
import com.alexfdb.operations.Operations;

public class FileOperations implements Operations {

    private static final String PATH = "gestion_ficheros_empleados\\src\\main\\resources\\empleados.txt";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Empleado parseEmpleado(String line) {
        String[] parts = line.split(", ");
        if (parts.length != 5) return null;
        return new Empleado(
                parts[0].trim(),
                parts[1].trim(),
                parts[2].trim(),
                Double.parseDouble(parts[3].trim()),
                parts[4].trim()
        );
    }

    @Override
    public boolean create(Empleado empleado) {
        if (empleado == null) return false;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH, true))) {
            writer.write(empleado.toString());
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String identificador) {
        if (identificador == null || identificador.isEmpty()) return false;

        File inputFile = new File(PATH);
        File tempFile = new File("temp_" + PATH);

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                Empleado empleado = parseEmpleado(currentLine);
                if (empleado != null && !empleado.getIdentificador().equals(identificador)) {
                    writer.write(currentLine);
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return inputFile.delete() && tempFile.renameTo(inputFile);
    }

    @Override
    public boolean update(Empleado empleado) {
        if (empleado == null || empleado.getIdentificador() == null || empleado.getIdentificador().isEmpty()) {
            return false;
        }
        return delete(empleado.getIdentificador()) && create(empleado);
    }

    @Override
    public Empleado read(String identificador) {
        if (identificador == null || identificador.isEmpty()) return null;
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Empleado empleado = parseEmpleado(line);
                if (empleado != null && empleado.getIdentificador().equals(identificador)) {
                    return empleado;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Empleado read(Empleado empleado) {
        return (empleado == null) ? null : read(empleado.getIdentificador());
    }

    @Override
    public Set<Empleado> empleadosPorPuesto(String puesto) {
        Set<Empleado> empleados = new HashSet<>();
        if (puesto == null || puesto.isEmpty()) return empleados;

        try (BufferedReader reader = new BufferedReader(new FileReader(PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Empleado empleado = parseEmpleado(line);
                if (empleado != null && empleado.getPuesto().equalsIgnoreCase(puesto.trim())) {
                    empleados.add(empleado);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return empleados;
    }

    @Override
    public Set<Empleado> empleadosPorEdad(String fechaInicio, String fechaFin) {
        Set<Empleado> empleados = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH))) {
            LocalDate startDate = LocalDate.parse(fechaInicio, DATE_FORMATTER);
            LocalDate endDate = LocalDate.parse(fechaFin, DATE_FORMATTER);

            String line;
            while ((line = reader.readLine()) != null) {
                Empleado empleado = parseEmpleado(line);
                if (empleado != null) {
                    LocalDate birthDate = LocalDate.parse(empleado.getFechaNacimiento(), DATE_FORMATTER);
                    if (!birthDate.isBefore(startDate) && !birthDate.isAfter(endDate)) {
                        empleados.add(empleado);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return empleados;
    }
}