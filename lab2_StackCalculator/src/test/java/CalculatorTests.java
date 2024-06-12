import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.ccfit.malinovskii.command.factory.CommandFactory;
import ru.nsu.ccfit.malinovskii.stackcalculator.StackCalculator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTests {
    private CommandFactory factory;
    private StackCalculator calculator;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    public void configureTest(String fileName) throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
        String filePath = file.getAbsolutePath();
        calculator.Setup(filePath);
    }
    @BeforeEach
    void setup(){
        factory = new CommandFactory();
        calculator = new StackCalculator(factory);
    }
    @Test
    public void TestMinus() {
        System.out.println("======TEST Minus1 EXECUTED=======");
        try {
            configureTest("Minus1.txt");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.setOut(new PrintStream(outContent));
        int isCalculating = 1;
        while (isCalculating == 1) {
            try {
                isCalculating = calculator.calculate();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        System.setOut(originalOut);
        assertEquals("-1.0", outContent.toString().trim());
    }

    @Test
    public void TestPlus() {
        System.out.println("======TEST Plus1 EXECUTED=======");
        try {
            configureTest("Plus1.txt");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.setOut(new PrintStream(outContent));
        int isCalculating = 1;
        while (isCalculating == 1) {
            try {
                isCalculating = calculator.calculate();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        System.setOut(originalOut);
        assertEquals("3.0", outContent.toString().trim());
    }

    @Test
    public void TestDiv() {
        System.out.println("======TEST Div1 EXECUTED=======");
        try {
            configureTest("Div1.txt");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.setOut(new PrintStream(outContent));
        int isCalculating = 1;
        while (isCalculating == 1) {
            try {
                isCalculating = calculator.calculate();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        System.setOut(originalOut);
        assertEquals("2.0", outContent.toString().trim());
    }

    @Test
    public void TestMult() {
        System.out.println("======TEST Mult1 EXECUTED=======");
        try {
            configureTest("Mult1.txt");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.setOut(new PrintStream(outContent));
        int isCalculating = 1;
        while (isCalculating == 1) {
            try {
                isCalculating = calculator.calculate();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        System.setOut(originalOut);
        assertEquals("4.0", outContent.toString().trim());
    }

    @Test
    public void TestSqrt() {
        System.out.println("======TEST Sqrt1 EXECUTED=======");
        try {
            configureTest("Sqrt1.txt");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.setOut(new PrintStream(outContent));
        int isCalculating = 1;
        while (isCalculating == 1) {
            try {
                isCalculating = calculator.calculate();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        System.setOut(originalOut);
        assertEquals("2.0", outContent.toString().trim());
    }
}
