package code;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class CsvWriter {
    public static void inputDataToCsv(String outputFileName, FileData data) {
        int wordCount = data.getWordCount();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            writer.write("Word,Frequency,Frequency(%)");
            writer.newLine();
            for (Map.Entry<String, Integer> entry : data.getWordMap().entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue() + "," + (double) entry.getValue() / (double) wordCount * 100);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}