package lk.ijse.controller;

/**
 * @author Amil Srinath
 */
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class PlagiarismChecker {

    public static void main(String[] args) {
        List<File> pdfFiles = getListOfPDFs("/Users/apple/Downloads/drive-download-20240403T102139Z-001");

        int count = 0;
        for (int i = 0; i < pdfFiles.size(); i++) {
            for (int j = i + 1; j < pdfFiles.size(); j++) {
                try {
                    File file1 = pdfFiles.get(i);
                    File file2 = pdfFiles.get(j);

                    String text1 = extractTextFromPDF(file1);
                    String text2 = extractTextFromPDF(file2);

                    double similarity = calculateSimilarity(text1, text2);

                    if (similarity > 0.8) {
                        System.out.println("Similarity between >>> " + file1.getName() + " < and > " + file2.getName() + ": " + similarity);
                    }
                } catch (IOException e) {

                }
            }
            System.out.println(++count);
        }
        System.out.println("Process End!!");
    }

    private static List<File> getListOfPDFs(String directoryPath) {
        List<File> pdfFiles = new ArrayList<>();
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".pdf")) {
                    pdfFiles.add(file);
                }
            }
        }
        return pdfFiles;
    }

    private static String extractTextFromPDF(File file) throws IOException {
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    private static double calculateSimilarity(String text1, String text2) {
        Set<String> tokens1 = tokenize(text1);
        Set<String> tokens2 = tokenize(text2);

        Map<String, Integer> termFrequency1 = calculateTermFrequency(tokens1);
        Map<String, Integer> termFrequency2 = calculateTermFrequency(tokens2);

        double dotProduct = calculateDotProduct(termFrequency1, termFrequency2);
        double magnitude1 = calculateMagnitude(termFrequency1);
        double magnitude2 = calculateMagnitude(termFrequency2);

        return dotProduct / (magnitude1 * magnitude2);
    }

    private static Set<String> tokenize(String text) {
        String[] words = text.split("\\W+");
        Set<String> tokens = new HashSet<>();
        for (String word : words) {
            tokens.add(word.toLowerCase());
        }
        return tokens;
    }

    private static Map<String, Integer> calculateTermFrequency(Set<String> tokens) {
        Map<String, Integer> termFrequency = new HashMap<>();
        for (String token : tokens) {
            termFrequency.put(token, termFrequency.getOrDefault(token, 0) + 1);
        }
        return termFrequency;
    }

    private static double calculateDotProduct(Map<String, Integer> termFrequency1, Map<String, Integer> termFrequency2) {
        double dotProduct = 0.0;
        for (String term : termFrequency1.keySet()) {
            if (termFrequency2.containsKey(term)) {
                dotProduct += termFrequency1.get(term) * termFrequency2.get(term);
            }
        }
        return dotProduct;
    }

    private static double calculateMagnitude(Map<String, Integer> termFrequency) {
        double magnitude = 0.0;
        for (int frequency : termFrequency.values()) {
            magnitude += Math.pow(frequency, 2);
        }
        return Math.sqrt(magnitude);
    }
}



