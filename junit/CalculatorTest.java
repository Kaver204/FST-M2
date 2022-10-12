package activities;

import activities_pckg.Calculator;
import org.junit.Assert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {
    public Calculator calculator;

    @BeforeEach
    public void setUp() throws Exception {
        calculator = new Calculator();
    }

    @Test
    @DisplayName("Simple multiplication should work")
    public void testAdd() {
        Assert.assertEquals(20, calculator.add(4, 5));
    }

    @Test
    @DisplayName("Simple multiplication should work")
    public void testMultiply() {
        Assert.assertEquals(20, calculator.multiply(4, 5));

    }

}
