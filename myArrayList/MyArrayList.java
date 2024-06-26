// Simulates ARRAYLIST, have students implement this
// to better understand how the java api works
import java.util.Arrays;

public class MyArrayList
{
    private Object [] list;
    private int numElements;       // number of students currently in the
									// list

    // Constructs the list, initially empty,
    // but can hold up to the capacity
    public MyArrayList (int capacity)
    {
    	list = new Object[capacity];
    	numElements = 0;
    }


    // Adds t to the end of the list
    public void add (Object t)
    {
        if(numElements < list.length) {
            list[numElements] = t;
        }
        // handle case where the list is full
        else {
            list = Arrays.copyOf(list, list.length * 2);
        }
        numElements++;
    }


    // Returns the number of active elements on the list
    public int size ()
    {
        return numElements;
    }


    // Returns the student in the i'th location (counting from 0)
    // Precondition: 0 <= i < size()
    public Object get (int i)
    {
        if(i >= 0 && i < numElements) {
            return list[i];
        }
        else {
            throw new IndexOutOfBoundsException();
        }
    }


    // Sets the i'th location in the list to t;
    // returns the previous i'th element.
    public Object set (int i, Object t)
    {
        if(i >= 0 && i < numElements) {

            Object temp = list[i];
            list[i] = t;
            return temp;
        }
        else {
            throw new IndexOutOfBoundsException();
        }
    }


    // Removes the i'th element, sliding all items beyond i up by one spot.
    // Returns the element removed
    public Object remove (int i)
    {
        if(i >= 0 && i < numElements) {
            Object temp = list[i];
            for(int j = i; j < numElements - 1; j++) {
                list[j] = list[j + 1];
            }
            list[numElements - 1] = null;
            numElements--;
            return temp;
        }
        else {
            throw new IndexOutOfBoundsException();
        }
    }
}
