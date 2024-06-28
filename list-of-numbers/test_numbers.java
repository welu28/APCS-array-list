public class test_numbers {
    public static void main(String[] args) {
        NumberList list = new NumberList(20);
        System.out.println("Mode: " + list.mode());
        System.out.println("Lowest value: " + list.min());
        System.out.println("Highest value: " + list.max());
        System.out.print("Full list: ");
        list.printList();
    }
}
