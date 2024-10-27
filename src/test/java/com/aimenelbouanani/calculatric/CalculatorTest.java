package com.aimenelbouanani.calculatric;

import org.junit.jupiter.api.Test;  // Pour JUnit 5
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    private final Calculator calculator = new Calculator();  // Instance de Calculator

    @Test
    public void testSum() {
        assertEquals(8, calculator.sum(3, 5));  // Vérifier que la somme est correcte
    }
}
