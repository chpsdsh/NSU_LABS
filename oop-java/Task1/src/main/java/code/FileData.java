package code;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public final class FileData {
    private int wordCount = 0;
    private Map<String, Integer> wordMap = new TreeMap<>();

    public int getWordCount() {
        return wordCount;
    }

    public Map<String, Integer> getWordMap() {
        return wordMap;
    }

    public void createMap(String inputFileName) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(inputFileName);
             Reader reader = new InputStreamReader(inputStream)) {
            if (inputStream == null) {
                throw new IOException("Resource file not found: " + inputFileName);
            }
            readData(reader);
        } catch (IOException e) {
            System.err.println("Error while reading resource file: " + e.getLocalizedMessage());
        }
    }

    public void readData(Reader reader) {
        int character;
        StringBuilder word = new StringBuilder();
        try {
            while ((character = reader.read()) != -1) {
                char c = (char) character;
                if (Character.isLetterOrDigit(c)) {
                    word.append(c);
                } else {
                    if (!word.isEmpty()) {
                        wordCount++;
                        String key = word.toString();
                        wordMap.merge(key, 1, Integer::sum);
                        word.setLength(0);
                    }
                }
            }
            if (!word.isEmpty()) {
                wordCount++;
                String key = word.toString();
                wordMap.merge(key, 1, Integer::sum);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sortMap() {
        wordMap = wordMap.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}
