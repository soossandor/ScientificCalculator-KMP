package hu.calculator.core.types;

import hu.calculator.core.exceptions.CalculatorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RealNumberTest {

    @Test
    void testAddition() {
        RealNumber a = new RealNumber("10.5");
        RealNumber b = new RealNumber("20.2");
        MathValue<?> result = a.add(b);

        assertEquals("30.7", result.toString(), "Az összeadás eredménye hibás");
    }

    @Test
    void testSubtraction() {
        RealNumber a = new RealNumber("10.5");
        RealNumber b = new RealNumber("20.2");
        MathValue<?> result = a.subtract(b);

        assertEquals("-9.7", result.toString(), "A kivonás eredménye hibás");
    }

    @Test
    void testMultiplication() {
        RealNumber a = new RealNumber("12.5");
        RealNumber b = new RealNumber("4");
        MathValue<?> result = a.multiply(b);

        assertEquals("50", result.toString(), "A szorzás eredménye hibás");
    }

    @Test
    void testDivision() {
        RealNumber a = new RealNumber("100");
        RealNumber b = new RealNumber("8");
        MathValue<?> result = a.divide(b);

        assertEquals("12.5", result.toString(), "Az osztás eredménye hibás");
    }

    @Test
    void testDivideByZero() {
        RealNumber a = new RealNumber("10");
        RealNumber b = new RealNumber("0");

        // Ellenőrizzük, hogy valóban eldobja-e a saját kivételünket
        Exception exception = assertThrows(CalculatorException.class, () -> {
            a.divide(b);
        });

        assertEquals("Nullával való osztás nem értelmezett!", exception.getMessage());
    }

    @Test
    void testHighPrecision() {
        // A feladatkiírás 30+ jegyű pontosságot kért. Teszteljük le!
        RealNumber a = new RealNumber("123456789012345678901234567890.12345");
        RealNumber b = new RealNumber("987654321098765432109876543210.98765");
        MathValue<?> result = a.add(b);

        // Elvárt eredmény: 1111111110111111111011111111101.1111
        assertEquals("1111111110111111111011111111101.1111", result.toString(), "A nagy pontosságú összeadás elbukott!");
    }
}
