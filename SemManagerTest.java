import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import student.TestCase;

/**
 * @author Solomon Manamel
 * @version 1
 */
public class SemManagerTest extends TestCase {
    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        // Nothing here
    }


    /**
     * Read contents of a file into a string
     * 
     * @param path
     *            File name
     * @return the string
     * @throws IOException
     */
    static String readFile(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded);
    }


    /**
     * This method is simply to get coverage of the class declaration.
     */
    public void testMInitx() {
        SemManager sem = new SemManager();
        assertNotNull(sem);
        try {
            SemManager.main(null);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * Full parser test
     * 
     * @throws Exception
     */
    public void testparserfull() throws Exception {
        String[] args = new String[3];
        args[0] = "512";
        args[1] = "4";
        args[2] = "P1Sample_inputX.txt";

        SemManager.main(args);
        String output = systemOut().getHistory();
        String referenceOutput = readFile("P1Sample_outputX.txt");
        assertFuzzyEquals(referenceOutput, output);
    }


    /**
     * Full parser test 2
     * 
     * @throws Exception
     */
    public void testparserfullTwo() throws Exception {
        String[] args = new String[3];
        args[0] = "512";
        args[1] = "4";
        args[2] = "P2Sample_inputX.txt";

        SemManager.main(args);
        String output = systemOut().getHistory();
        String referenceOutput = readFile("P2Sample_outputX.txt");
        assertFuzzyEquals(referenceOutput, output);
    }


    /**
     * Simple parser test (input only)
     * 
     * @throws Exception
     */
    public void testparserinput() throws Exception {
        String[] args = new String[3];
        args[0] = "2048";
        args[1] = "16";
        args[2] = "P1SimpSample_inputX.txt";

        SemManager.main(args);
        String output = systemOut().getHistory();
        String referenceOutput = readFile("P1SimpSample_outputX.txt");
        assertFuzzyEquals(referenceOutput, output);
    }
}
