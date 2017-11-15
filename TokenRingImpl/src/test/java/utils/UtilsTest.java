package utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UtilsTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private final String TEST_STRING = "test";

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Test
    public void IfInputIsStringAndNotSilent_LogIsFull() {
        Utils.log(TEST_STRING, false);
        assertEquals(outContent.toString().trim(), TEST_STRING);
    }

    @Test
    public void IfInputIsStringAndSilent_LogIsEmpty() {
        Utils.log(TEST_STRING, true);
        assertTrue(outContent.toString().isEmpty());
    }

    @Test
    public void IfInputIsNullAndSilent_LogIsEmpty() {
        Utils.log(null, true);
        assertTrue(outContent.toString().isEmpty());
    }

    @Test
    public void IfInputIsNullAndNotSilent_LogIsEmpty() {
        Utils.log(null, false);
        assertTrue(outContent.toString().isEmpty());
    }
}
