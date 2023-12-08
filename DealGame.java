import java.util.*;
import java.io.*;
import java.nio.file.*;

/**
 * The DealGame class represents a simulation of the Deal or No Deal game.
 * Players can choose boxes, open boxes, and make decisions based on the offers.
 * The game consists of multiple rounds, each with a set number of boxes to be opened.
 * The player's goal is to maximize their winnings.
 * @author Rishabh Patel
 */
public class DealGame {
  
    /** Number of boxes in the game */
    public static final int NUM_BOXES = 26;
  
  
    /** Monetary values that will be used in the game */
    public static final double[] BOX_VALUES = {0.01, 1, 5, 10, 25, 50, 75, 
                                               100, 200, 300, 400, 500, 
                                               750, 1000, 5000, 10000, 
                                               25000, 50000, 75000, 
                                               100000, 200000, 300000, 
                                               400000, 500000, 750000, 
                                               1000000};
  
    /** Number of boxes to be opened in each round */
    public static final int[] BOXES_IN_ROUND = {0, 6, 5, 4, 3, 2, 
                                                1, 1, 1, 1, 1};
                                                
    /** Number of rounds in the game */
    public static final int NUM_ROUNDS = 10;
  
    /** Number of times boxes are swapped during the set up */
    public static final int BOX_SWAPS = 500;
  
    /** Name of the file that contains the high score */
    public static final String HIGH_SCORE_FILE = "highscore.txt";
    
    /** An instance of the BoxList class */
    private BoxList boxList;
    
    /** An integer which stores the index of the player’s box in the BoxList */
    private int playerBoxIndex;
    
    /** A boolean variable that knows whether or not the player has chosen a box */
    private boolean hasPlayerChosenBox;
    
    /** An integer that stores the current round number */
    private int round;
    
    /** An integer that keeps track of the number of boxes 
    that have been opened in the current round */
    private int boxesOpenedThisRound;
    
    /** An integer that keeps track of the total number of 
    boxes that have been opened in the game */
    private int boxesOpenedTotal;
    
    /** A double that stores the highest score */
    private double highScore;
    
    /**
     * Constructs a DealGame object.
     *
     * @param testing True if the game is in testing mode, false otherwise.
     */
    public DealGame (boolean testing) {
        boxList = new BoxList(BOX_VALUES);
        if (!testing) {
            boxList.shuffle(BOX_SWAPS);
        }
        
        File file = new File(HIGH_SCORE_FILE);
        if (file.exists()) {
            try {
                Scanner scan = new Scanner(file);
                highScore = Double.parseDouble(scan.nextLine());
                scan.close();
            } catch (IOException e) {
                highScore = 0;
            }
        }
        else {
            highScore = 0;
        }
        round = 1;
        boxesOpenedThisRound = 0;
        boxesOpenedTotal = 0;
    }
    
    /**
     * Checks if the player has chosen a box.
     *
     * @return True if the player has chosen a box, false otherwise.
     */
    public boolean hasPlayerChosenBox() {
        return hasPlayerChosenBox;
    }
    
    /**
     * Selects a box with the given index.
     *
     * @param index The index of the box to be selected.
     * @throws IllegalArgumentException if the index is invalid.
     */
    public void selectBox(int index) {
        if (index < 0 || index >= BOX_VALUES.length) {
            throw new IllegalArgumentException("Invalid index");
        }
        if (!hasPlayerChosenBox()) {
            this.playerBoxIndex = index;
            this.hasPlayerChosenBox = true;
        }
        else if (hasPlayerChosenBox()) {
            boxList.open(index);
            boxesOpenedThisRound++;
            boxesOpenedTotal++;
        }
    }
    
    /**
     * Gets the number of boxes remaining to be opened in the current round.
     *
     * @return The number of boxes remaining to be opened.
     */
    public int getBoxesRemainingToOpenThisRound() {
        return BOXES_IN_ROUND[round] - boxesOpenedThisRound;
    }
    
    /**
     * Gets the number of boxes opened in the current round.
     *
     * @return The number of boxes opened in the current round.
     */
    public int getBoxesOpenedThisRound() {
        return boxesOpenedThisRound;
    }
    
    /**
     * Gets the current round number.
     *
     * @return The current round number.
     */
    public int getRound() {
        return round;
    }
    
    /**
     * Starts the next round of the game.
     */
    public void startNextRound() {
        round++;
        boxesOpenedThisRound = 0;
    }
    
    /**
     * Checks if it is the end of the current round.
     *
     * @return True if it is the end of the round, false otherwise.
     */
    public boolean isEndOfRound() {
        if (boxesOpenedThisRound == BOXES_IN_ROUND[round]) {
            return true;
        }
        return false;
    }
    
    /**
     * Gets the monetary value of the player's chosen box.
     *
     * @return The value of the player's chosen box.
     */
    public double getPlayerBoxValue() {
        return boxList.getValue(playerBoxIndex);
    }
    
    /**
     * Checks if a box with the given index is open.
     *
     * @param index The index of the box to check.
     * @return True if the box is open, false otherwise.
     * @throws IllegalArgumentException if the index is invalid.
     * @throws IllegalArgumentException if the index is invalid.

     */
    public boolean isBoxOpen(int index) {
        if (index < 0 || index >= BOX_VALUES.length) {
            throw new IllegalArgumentException("Invalid index");
        }
        if (boxList.isOpen(index)) {
            return true;
        }
        return false;
    }
    
    /**
     * Gets the monetary value inside a box with the given index.
     *
     * @param index The index of the box.
     * @return The value inside the box.
     * @throws IllegalArgumentException if the index is invalid.
     */
    public double getValueInBox(int index) {
        if (index < 0 || index >= BOX_VALUES.length) {
            throw new IllegalArgumentException("Invalid index");
        }
        return boxList.getValue(index);
    }
    
    /**
     * Gets the current offer from the banker based on the average value of unopened boxes.
     *
     * @return The current offer from the banker.
     */
    public double getCurrentOffer() {
        return boxList.averageValueOfUnopenedBoxes() * round / 10;
    }
    
    /**
     * Gets the current high score.
     *
     * @return The current high score.
     */
    public double getHighScore() {
        return highScore;
    }

    /**
     * Checks if a given value is a new high score and updates the high score if necessary.
     *
     * @param value The value to be checked against the high score.
     * @return True if the value is a new high score, false otherwise
     */
    public boolean isNewHighScore(double value) {
        if (value > highScore) {
            highScore = value;
            Path path = Path.of(HIGH_SCORE_FILE);
            PrintWriter out = null;
            try {
                out = new PrintWriter(new FileOutputStream(HIGH_SCORE_FILE));
            }
            catch (IOException e) {
                System.exit(1);
            }
            out.printf("%.2f", value);
            out.close();
            return true;
        }
        return false;
    }
    
    
        
        
        
       
        
  
}
