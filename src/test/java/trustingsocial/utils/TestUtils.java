package trustingsocial.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNull;

public class TestUtils {
    public static void assertReaders(String expectedFile, String actualFile) {

        String expectedLine;
        try {
            BufferedReader expected = new BufferedReader(new FileReader(new File(expectedFile)));
            BufferedReader actual = new BufferedReader(new FileReader(new File(actualFile)));

            while ((expectedLine = expected.readLine()) != null) {
                String actualLine = actual.readLine();
                assertNotNull("Expected had more lines then the actual.", actualLine);
                assertEquals(expectedLine, actualLine);
            }
            assertNull("Actual had more lines then the expected.", actual.readLine());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
