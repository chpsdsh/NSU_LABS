package code;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public class FileData {
    private int wordCount = 0;
    private Map<String, Integer> wordMap = new HashMap<>();

    public int getWordCount() {
        return wordCount;
    }

    public Map<String, Integer> getWordMap() {
        return wordMap;
    }

    public void createMap(String inputFileName) {
        Reader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(inputFileName));
            readData(reader);
        } catch (IOException e) {
            System.err.println("Error while reading file:"
                    + e.getLocalizedMessage());
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
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
                        wordMap.put(key, wordMap.getOrDefault(key, 0) + 1);
                        word.setLength(0);
                    }
                }
            }
            if (word.length() > 0) {
                wordCount++;
                String key = word.toString();
                wordMap.put(key, wordMap.getOrDefault(key, 0) + 1);
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
