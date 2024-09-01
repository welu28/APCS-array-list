import java.util.*;
import java.io.*;

public class poe {
    public static final String[] punctuation = {
        ".", ",", ";", ":", "?", "!", "—", "'", "\"", "(", ")", "..."
    };

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("poe/poeModified.txt"));
        PrintWriter pw = new PrintWriter(new File("poe/count.txt"));

        HashMap<String, Word> wordCounts = new HashMap<>();
        List<Word> words = new ArrayList<>();
        
        int totalWords = 0;

        while(scanner.hasNext()) {
            String curr = scanner.next().toLowerCase();
            for (String punct : punctuation) {
                if (curr.contains(punct)) {
                    curr = curr.replaceAll("\\" + punct, "");
                }
            }
            if (wordCounts.containsKey(curr)) {
                wordCounts.get(curr).incrementCount();
            } else {
                Word newWord = new Word(curr, 1);
                wordCounts.put(curr, newWord);
                words.add(newWord);
            }
            totalWords++;
        }

        System.out.println("Total words: " + totalWords);
        System.out.println("Total unique words: " + wordCounts.size());
        System.out.println("Most occurring words: ");
        ArrayList<Word> modes = modes(wordCounts);
        for (Word elem : modes) {
            System.out.print(elem + ", ");
        }
        System.out.println();
        System.out.println("_______________________________________________");

        pw.println("Occurrences of each word:");
        pw.println("--------------------------------");

        for (Word word : words) {
            pw.println(word);
        }
        scanner.close();
        pw.close();

        Word[] arr = words.toArray(new Word[0]);
        sort(arr); // compare the sorting times, but don't actually sort yet

        Scanner io = new Scanner(System.in);
        System.out.println("Enter a word: ");
        String word = io.next();
        System.out.println("_______________________________________________");
        search(arr, word);

        long startTime = System.currentTimeMillis();
        System.out.println("HashMap search:");
        System.out.println("Start time: " + startTime);

        if (wordCounts.containsKey(word)) {
            System.out.println("Word count: " + wordCounts.get(word).getCount());
        } else {
            System.out.println("WORD NOT IN BOOK");
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("End time: " + endTime);
        System.out.println("HashMap search duration: " + (endTime - startTime) + " milliseconds");
        System.out.println("_______________________________________________");
        System.out.print("Check count.txt for more information about word count");
        io.close();
    }

    public static void search(Word[] words, String word) {
        Word[] copyA = Arrays.copyOf(words, words.length);
        Word[] copyB = Arrays.copyOf(words, words.length);

        Arrays.sort(copyA);
        System.out.println("Binary search:");
        long start = System.currentTimeMillis();
        System.out.println("Start time: " + start);
        binarySearch(copyA, word);
        long end = System.currentTimeMillis();
        System.out.println("End time: " + end);
        System.out.println("Binary search time: " + (end - start) + " milliseconds");
        System.out.println("_______________________________________________");

        System.out.println("Sequential search:");
        start = System.currentTimeMillis();
        System.out.println("Start time: " + start);
        sequentialSearch(copyB, word);
        end = System.currentTimeMillis();
        System.out.println("End time: " + end);
        System.out.println("Sequential search time: " + (end - start) + " milliseconds");
        System.out.println("_______________________________________________");
    }

    public static void binarySearch(Word[] a, String word) {
        int low = 0;
        int high = a.length - 1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int comparison = a[mid].getWord().compareTo(word);
            
            if (comparison == 0) {
                int count = a[mid].getCount();
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

    public static void sequentialSearch(Word[] a, String word) {
        int count = 0;
        for (Word w : a) {
            if (w.getWord().equals(word)) {
                count = w.getCount();
                break;
            }
        }
        if (count == 0) {
            System.out.println("WORD NOT IN BOOK");
        } else {
            System.out.println("Word count: " + count);
        }
    }

    public static void sort(Word[] words) {
        Word[] copyA = Arrays.copyOf(words, words.length);
        Word[] copyB = Arrays.copyOf(words, words.length);

        long start = System.currentTimeMillis();
        System.out.println("Bubble sort:");
        System.out.println("Start time: " + start);
        bubbleSort(copyA);
        long end = System.currentTimeMillis();
        System.out.println("End time: " + end);
        System.out.println("Bubble sort time: " + (end - start) + " milliseconds");
        System.out.println("_______________________________________________");

        System.out.println("Default Java Arrays sort:");
        start = System.currentTimeMillis();
        System.out.println("Start time: " + start);
        Arrays.sort(copyB);
        end = System.currentTimeMillis();
        System.out.println("End time: " + end);
        System.out.println("Java Arrays sort time: " + (end - start) + " milliseconds");
        System.out.println("_______________________________________________");
    }

    public static void bubbleSort(Word[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - (i + 1); j++) {
                if (arr[j].compareTo(arr[j + 1]) > 0) {
                    Word temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    public static ArrayList<Word> modes(HashMap<String, Word> occurrences) {
        ArrayList<Word> modes = new ArrayList<>();
        int maxOccurrences = 0;
        for (Word word : occurrences.values()) {
            int value = word.getCount();
            if (value > maxOccurrences) {
                maxOccurrences = value;
                modes.clear();
                modes.add(word);
            } else if (value == maxOccurrences) {
                modes.add(word);
            }
        }

        return modes;
    }
}
