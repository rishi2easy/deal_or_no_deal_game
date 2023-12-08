import java.util.*;

/**
* The BoxList class represents a list of Box objects with monetary amounts
* It provides methods to access and add values the boxes in the list.
* @author Rishabh Patel
*/
public class BoxList {
    
    /** Array of Box objects */
    private Box[] boxes;
    
    /**
     * Constructs a BoxList with the given array of monetary amounts.
     *
     * @param monetaryAmounts An array of monetary amounts for initializing the BoxList.
     * @throws IllegalArgumentException if monetaryAmounts is null
     * @throws IllegalArgumentException if monetaryAmounts has less than two elements.
     */
    public BoxList(double[] monetaryAmounts) {
        if (monetaryAmounts == null) {
            throw new IllegalArgumentException("Null monetary amounts");
        }
        if (monetaryAmounts.length < 2) {
            throw new IllegalArgumentException("Invalid monetary amounts");
        }
        
        boxes = new Box[monetaryAmounts.length];
        for (int i = 0; i < monetaryAmounts.length; i++) {
            boxes[i] = new Box(monetaryAmounts[i]);
        }
    }
    
    /**
     * Gets the monetary value of the Box at the specified index.
     *
     * @param index The index of the Box to get.
     * @return The monetary value of the Box at the specified index.
     * @throws IllegalArgumentException if the index is out of bounds.
     */
    public double getValue(int index) {
        if (index < 0 || index >= boxes.length) {
            throw new IllegalArgumentException("Invalid index");
        }
        return boxes[index].getValue();
    }
    
    /**
     * Checks if the box at the specified index is open.
     *
     * @param index The index of the box.
     * @return true if the box is open, false otherwise.
     * @throws IllegalArgumentException if the index is out of bounds.
     */
    public boolean isOpen(int index) {
        if (index < 0 || index >= boxes.length) {
            throw new IllegalArgumentException("Invalid index");
        }
        if (boxes[index].isOpen()) {
            return true;
        }
        return false;
    }
    
    /**
     * Opens the box at the specified index.
     *
     * @param index The index of the box to be opened.
     * @throws IllegalArgumentException if the index is out of bounds.
     */
    public void open(int index) {
        if (index < 0 || index >= boxes.length) {
            throw new IllegalArgumentException("Invalid index");
        }
        boxes[index].open();
    }
    
    /**
     * Calculates the average monetary value of unopened boxes.
     *
     * @return The average value of unopened boxes, or 0 if all boxes are open.
     */
    public double averageValueOfUnopenedBoxes() {
        double sum = 0;
        int count = 0;
        
        for (int i = 0; i < boxes.length; i++) {
            if (!(isOpen(i))) {
                sum += getValue(i);
                count++;
            }
        }
        if (count == 0) {
            return 0;
        }
        return sum / count;
    }
    
    /**
     * Shuffles the boxes in the list by performing a specified number of swaps.
     *
     * @param numberOfSwaps The number of swaps to be performed.
     * @throws IllegalArgumentException if numberOfSwaps is negative.
     */
    public void shuffle(int numberOfSwaps) {
        if (numberOfSwaps < 0) {
            throw new IllegalArgumentException("Invalid number of swaps");
        }
        
        Random random = new Random();
        for (int i = 0; i < numberOfSwaps; i++) {
            int index1 = random.nextInt(boxes.length);
            int index2 = random.nextInt(boxes.length);
            
            while (index1 == index2) {
                index2 = random.nextInt(boxes.length);
            }
            
            Box temp = boxes[index1];
            boxes[index1] = boxes[index2];
            boxes[index2] = temp;
        }
    }
    
    /**
     * Checks if this BoxList is equal to another object.
     *
     * @param o The object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BoxList) || o == null) {
            return false;
        }
        BoxList other = (BoxList) o;
        return Arrays.deepEquals(this.boxes, other.boxes);
    }
    
    /**
     * Returns a string format of the BoxList.
     *
     * @return A string format of the BoxList.
     */
    public String toString() {
        String s = "";
        for (int i = 0; i < boxes.length; i++) {
            s += i + ": " + boxes[i].toString() + "\n";
        }
        return s;
    }
}