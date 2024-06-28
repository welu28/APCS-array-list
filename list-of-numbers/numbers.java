import java.util.*;

public class numbers
{
    private List<Integer> numbers;
    private HashMap<Integer, Integer> occurences = new HashMap<>(); // to find the mode
    private int min;
    private int max;
    private Random random = new Random();

    public void createList(int size) {
        numbers = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            int curr = random.nextInt(1, 20) + 1;
            min = Math.min(curr, min);
            max = Math.max(curr, max);
            occurences.put(curr, occurences.getOrDefault(curr, 0) + 1);
            numbers.add(curr);
        }
    }

    public ArrayList<Integer> mode() { // most frequent occuring number, returns arraylist b/c could be multiple
        ArrayList<Integer> modes = new ArrayList<>();
        int maxOccurences = 0;
        for(Map.Entry<Integer, Integer> entry: occurences.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            if(value > maxOccurences) {
                modes.clear();
                modes.add(key);
            }
            else if(value == maxOccurences) modes.add(key);
        }   

        return modes;
    }

    public int min() {
        return min;
    }

    public int max() {
        return max;
    }
}
