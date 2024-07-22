

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

        String[] arr = words.toArray(new String[0]);
        sort(arr); // compare the sorting times, but don't actually sort yet (keep unordered for search)
        

        Scanner io = new Scanner(System.in);
        // user input
        System.out.println("Enter a word: ");
        String word = io.next();
        System.out.println("_______________________________________________");
        search(arr, word);

        long startTime = System.currentTimeMillis();
        System.out.println("hashmap search started at: " + new Date(startTime));

        if(wordCounts.containsKey(word)) {
            System.out.println("Word count: " + wordCounts.get(word));
        }
        else {
            System.out.println("WORD NOT IN BOOK");
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("hashmap search ended at: " + new Date(endTime));
        System.out.println("hashmap search duration: " + (endTime - startTime) + " milliseconds");
        System.out.println("_______________________________________________");
        System.out.print("check count.txt for more information about word count");
        io.close();
    }

    public static void search(String[] words, String word) {
        // make a copy so we don't change the original array
        String[] copyA = Arrays.copyOf(words, words.length);
        String[] copyB = Arrays.copyOf(words, words.length);

        Arrays.sort(copyA);
        System.out.println("binary search:");
        long start = System.currentTimeMillis();
        binarySearch(copyA, word);
        long end = System.currentTimeMillis();
        System.out.println("binary search time: " + (end - start) + " milliseconds");
        System.out.println("_______________________________________________");

        System.out.println("sequential search:");
        start = System.currentTimeMillis();
        // O(n logn)
        sequentialSearch(copyB, word);
        end = System.currentTimeMillis();
        System.out.println("sequential search time: " + (end - start) + " milliseconds");
        System.out.println("_______________________________________________");
    }

    public static void binarySearch(String[] a, String word) {
        int low = 0;
        int high = a.length - 1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2; // midpoint
            int comparison = a[mid].compareTo(word);
            
            if (comparison == 0) { // found the word
                int count = 0;
                while (mid >= 0 && a[mid].equals(word)) {
                    count++;
                    mid--;
                }
                mid = low + (high - low) / 2 + 1; // reset mid to 1 greater than the initial position
                while (mid < a.length && a[mid].equals(word)) {
                    count++;
                    mid++;
                }
                System.out.println("Word count: " + count);
                return;
            } else if (comparison < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        
        System.out.println("WORD NOT IN BOOK");
    }

    public static void sequentialSearch(String[] a, String word) {
        int count = 0;
        for(int i = 0; i < a.length; i++) {
            count += a[i].equals(word) ? 1 : 0;
        }
        if(count == 0) {
            System.out.println("WORD NOT IN BOOK");
        }
        else System.out.println("Word count: " + count);
    }

    public static void sort(String[] words) { // compare bubble sort with default sort (merge sort)
        // make a copy so we don't change the original array
        String[] copyA = Arrays.copyOf(words, words.length);
        String[] copyB = Arrays.copyOf(words, words.length);

        long start = System.currentTimeMillis();
        // O(n^2)
        bubbleSort(copyA);
        long end = System.currentTimeMillis();
        System.out.println("bubble sort time: " + (end - start) + " milliseconds");
        System.out.println("_______________________________________________");

        start = System.currentTimeMillis();
        // O(n logn)
        Arrays.sort(copyB);
        end = System.currentTimeMillis();
        System.out.println("java arrays sort time: " + (end - start) + " milliseconds");
        System.out.println("_______________________________________________");
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
