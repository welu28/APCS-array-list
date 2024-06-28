import java.util.*;
import java.io.*;

public class poe {
    public static final String[] punctuation = {
        ".", ",", ";", ":", "?", "!", "—", "'", "\"", "(", ")", "..."
    };

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new File("poemodified.txt"));
        PrintWriter pw = new PrintWriter(new File("count.txt"));

        HashMap<String, Integer> wordCounts = new HashMap<>();
        
        int totalWords = 0; // total number of words, doesn't have to be unique

        while(scanner.hasNext()) {
            String curr = scanner.next().toLowerCase();
            for (String punct : punctuation) {
                if (curr.contains(punct)) {
                    // Remove punctuation from the word
                    curr = curr.replaceAll("\\" + punct, "");
                }
            }
            wordCounts.put(curr, wordCounts.getOrDefault(curr, 0) + 1);
            totalWords++;
        }

        pw.println("Total words: " + totalWords);
        pw.println("Total unique words: " + wordCounts.size());
        pw.print("Most occuring words: ");
        ArrayList<String> modes = modes(wordCounts);
        for(String elem: modes) {
            pw.print(elem + " ");
        }
        pw.println();
        pw.println("Occurences of each word:");
        pw.println("--------------------------------");

        for(Map.Entry<String, Integer> entry: wordCounts.entrySet()) {
            pw.println(entry.getKey() + ": " + entry.getValue());
        }
        scanner.close();
        pw.close();


        Scanner io = new Scanner(System.in);
        // user input
        System.out.println("Enter a word: ");
        String word = io.next();
        if(wordCounts.containsKey(word)) {
            System.out.println("Number of occurences: " + wordCounts.get(word));
        }
        else {
            System.out.println("WORD NOT IN BOOK");
        }
        io.close();
    }

    public static ArrayList<String> modes(HashMap<String, Integer> occurences) {
        ArrayList<String> modes = new ArrayList<>();
        int maxOccurences = 0;
        for(Map.Entry<String, Integer> entry: occurences.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            if(value > maxOccurences) {
                maxOccurences = value;
                modes.clear();
                modes.add(key);
            }
            else if(value == maxOccurences) modes.add(key);
        }

        return modes;
    }
}
