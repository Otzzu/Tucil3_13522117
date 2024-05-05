package src.Solver;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class ReadDict {
    public static HashSet<String> readDict(String filename) throws IOException {
        HashSet<String> dict = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dict.add(line.trim());
            }
        }

        return dict;
    }
}
