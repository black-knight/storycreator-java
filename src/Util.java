import java.io.*;
import java.util.Scanner;

/**
 * Created by daniel on 09/02/14.
 */
public class Util {
    public static void outputTextToFile(String text, String filename) {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
            out.println(text);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readTextFile(String filename) {
        try {
            return new Scanner(new File(filename)).useDelimiter("\\A").next();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String characters(char ch, int count) {
        String str = "";
        for (int i = 0; i < count; i++) {
            str += ch;
        }
        return str;
    }
}
