

import java.util.*;
import java.io.*;

public class poe {
    public static final String[] punctuation = {
        ".", ",", ";", ":", "?", "!", "—", "'", "\"", "(", ")", "..."
    };

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("poeModified.txt"));
        PrintWriter pw = new PrintWriter(new File("count.txt"));

        HashMap<String, Integer> wordCounts = new HashMap<>();
        List<String> words = new ArrayList<>();
        
        int totalWords = 0; // total number of words, doesn't have to be unique

        while(scanner.hasNext()) {
            String curr = scanner.next().toLowerCase();
            for (String punct : punctuation) {
                if (curr.contains(punct)) {
                    //remove punctuation from the word
                    curr = curr.replaceAll("\\" + punct, "");
                }
            }
            wordCounts.put(curr, wordCounts.getOrDefault(curr, 0) + 1);
            words.add(curr);
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

        sort(words.toArray(new String[0]));

        Scanner io = new Scanner(System.in);
        // user input
        System.out.println("Enter a word: ");
        String word = io.next();
        long startTime = System.currentTimeMillis();
        System.out.println("HashMap search started at: " + new Date(startTime));

        if(wordCounts.containsKey(word)) {
            System.out.println("Number of occurences: " + wordCounts.get(word));
        }
        else {
            System.out.println("WORD NOT IN BOOK");
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("HashMap ended at: " + new Date(endTime));
        System.out.println("HashMap search duration: " + (endTime - startTime) + " milliseconds");
        io.close();
    }

    public static void sort(String[] words) { // compare bubble sort with default sort (merge sort)
        // make a copy so we don't change the original array
        String[] copyA = Arrays.copyOf(words, words.length);
        String[] copyB = Arrays.copyOf(words, words.length);

        long start = System.currentTimeMillis();
        bubbleSort(copyA);
        long end = System.currentTimeMillis();
        System.out.println("bubble sort time: " + (end - start) + " milliseconds");

        start = System.currentTimeMillis();
        Arrays.sort(copyB);
        end = System.currentTimeMillis();
        System.out.println("java arrays sort time: " + (end - start) + " milliseconds");
    }

    /*
     * Bubble sort iterates over the list, compares adjacent items, and swaps them if they are in the wrong order. 
     * This continues until the list is sorted. 
     * The algorithm’s name comes from the fact that smaller elements "bubble" to the top of the list. 
     * Despite its simplicity, bubble sort is inefficient and is O(n^2).
     */
    public static void bubbleSort(String[] arr) {
        int n = arr.length;
        for(int i = 0; i < n-1; i++) {
            for(int j = 0; j < n-(i+1); j++) { // so that we only compare up to i
                if(arr[j].compareTo(arr[j+1]) > 0) {
                    String temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
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
