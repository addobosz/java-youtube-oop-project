import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomLinePicker {
    private final int mNumberOfLines;
    private List<String> textLines;
    public RandomLinePicker(String filePath) {
        try {

            // Read the file and store titles in a list
            this.textLines = readFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
            this.mNumberOfLines = textLines.size();

    }

    private List<String> readFile(String filePath) throws IOException {
        List<String> linesList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                linesList.add(line);
            }
        }
        return linesList;
    }

    public String getRandomLine() {
        Random random = new Random();
        int randomIndex = random.nextInt(this.mNumberOfLines);
        return this.textLines.get(randomIndex);
    }
}