package com.alexfdb.model;
import java.util.Objects;
/**
 * @author alexfdb
 * @version 1.0.0
 */
public class Empleado {
    private String identificador;
    private String nombre;
    private String puesto;
    private double salario;
    private String fechaNacimiento;

    /**
     * Constructor vacío.
     */
    public Empleado() {
    }

    /**
     * Constructor solo con identificador.
     * @param identificador
     */
    public Empleado(String identificador) {
        validarIdentificador(identificador);
        this.identificador = identificador;
    }

    /**
     * Constructor completo.
     * @param identificador
     * @param nombre
     * @param puesto
     * @param salario
     * @param fechaNacimiento
     */
    public Empleado(String identificador, String nombre, String puesto, double salario, String fechaNacimiento) {
        validarIdentificador(identificador);
        this.identificador = identificador;
        this.nombre = nombre;
        this.puesto = puesto;
        this.salario = salario;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getIdentificador() {
        return this.identificador;
    }

    public void setIdentificador(String identificador) {
        validarIdentificador(identificador);
        this.identificador = identificador;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPuesto() {
        return this.puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public double getSalario() {
        return this.salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public boolean validarIdentificador(String identificador) {
        return !(identificador == null || identificador.isEmpty());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Empleado)) {
            return false;
        }
        Empleado empleado = (Empleado) o;
        return Objects.equals(identificador, empleado.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }

    @Override
    public String toString() {
        return identificador + ", " + nombre + ", " + puesto + ", " + salario + ", " + fechaNacimiento;
    }
}