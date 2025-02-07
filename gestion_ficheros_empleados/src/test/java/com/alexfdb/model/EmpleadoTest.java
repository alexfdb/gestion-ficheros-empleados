package com.alexfdb.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmpleadoTest {

    @Test
    public void testConstructor() {
        Empleado empleado = new Empleado("123", "Juan", "Desarrollador", 3000, "1985-03-15");
        
        assertEquals("123", empleado.getIdentificador());
        assertEquals("Juan", empleado.getNombre());
        assertEquals("Desarrollador", empleado.getPuesto());
        assertEquals(3000, empleado.getSalario());
        assertEquals("1985-03-15", empleado.getFechaNacimiento());
    }

    @Test
    public void testValidarIdentificador() {
        Empleado empleado = new Empleado();
        assertTrue(empleado.validarIdentificador("123"));
        assertFalse(empleado.validarIdentificador(""));  // Identificador vacío
    }
}