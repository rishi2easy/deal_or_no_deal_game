/**
 * The Box class represents a box with a numeric value and an open/close state.
 * The box cannot have a value less than a small positive constant DELTA.
 *
 * @author Rishabh Patel
 */
 
public class Box {
    
    /** A small positive constant used for value comparison */
    public static final double DELTA = 0.01;
    
    /** A double that represents the monetary value contained in the box */
    private double value;
    
    /** A boolean that knows whether or not the box is open */
    private boolean isOpen;
    
    /**
     * Constructs a Box object with the specified value.
     *
     * @param value the numeric value of the box
     * @throws IllegalArgumentException if the specified value is less than DELTA
     */
    public Box (double value) {
        if (value < DELTA) {
            throw new IllegalArgumentException ("Invalid value");
        }
        
        this.isOpen = false;
        this.value = value;
    }
    
    /**
     * Gets the numeric value of the box.
     *
     * @return the value of the box
     */
    public double getValue() {
        return value;
    }
    
    /**
     * Checks if the box is open.
     *
     * @return true if the box is open, false otherwise
     */
    public boolean isOpen() {
        return isOpen;
    }
    
    /**
     * Opens the boc
     */
    public void open() {
        this.isOpen = true;
    }
    
    /**
     * Compares this box to another object for equality.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    public boolean equals (Object o) {
        
        if (!(o instanceof Box)) {
            return false;
        }
        else {
            Box other = (Box) o;
            if (other.isOpen == isOpen && Math.abs(value - other.value) < DELTA) {
                return true;
            }
            else {
                return false;
            }
        }
        
        
    }
    
    /**
     * Returns a string representation of the box.
     *
     * @return a string representation of the box
     */
    public String toString() {
        return "Open: " + isOpen + " Value: " + value;
    }
        
}
