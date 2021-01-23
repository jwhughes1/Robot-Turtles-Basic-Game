/*
Justin Hughes and Yacine Belaifa
COMP 3721
Robot Turtles Milestone 4
Player.java
*/
import java.util.Stack;

public class Player 
{
    //Player has a Deck to keep track of the number of left turn, right turn, and move forward cards 
    private String color_ID;
    private Deck playerDeck;
    private Bug bugCard;
    private String turtDirection;
    
    private int[] location; /*an int[] array of size 2 defines the player's location. 
                            Element 0 is their horizontal position, element 1 is their vertical position.*/
    private Stack<String> moves;  /*This is a collection of all the moves the current player has made so far.
                                //Each time a player takes a turn, the move they made is pushed to this stack.
                                When the Bug card is invoked, the move the player wishes to undo will be popped 
                                off the top of the Stack.
                                It contains string representations of every move that has been made so far.*/
    
    public Player(String color)
    {
        color_ID = color;
        location = new int[2];
        //location and direction depend on color
        if(color_ID.equals("red"))
        {
            location[0] = 7;
            location[1] = 7;
            turtDirection = "NORTH";
        }
        else if (color_ID.equals("blue"))
        {
            location[0] = 0;
            location[1] = 7;
            turtDirection = "WEST";
        }
        else if(color_ID.equals("green"))
        {
            location[0] = 0;
            location[1] = 0;
            turtDirection = "SOUTH";
        }
        else    //color is magenta
        {
            location[0] = 7;
            location[1] = 0;
            turtDirection = "EAST";
        }
        playerDeck = new Deck(8,8,18);
        bugCard = new Bug();
        moves = new Stack<String>();
    }
    
    public int[] getLocation()
    {
        return this.location;
    }
    
    public void setLocation(int[] position)
    {
        this.location[0] = position[0];
        this.location[1] = position[1];
    }
    
    public String getColor()
    {
        return this.color_ID;
    }
    
    public Bug getBugCard()
    {
        return this.bugCard;
    }
    
    public Deck getDeck()
    {
        return this.playerDeck;
    }
    
    public Stack<String> getSequenceOfMoves() //removed getLastMove, and return stack
    {
        return moves;
    }
    public String getDirection()
    {
        return this.turtDirection;
    }
    public void setDirection(String dir)
    {
        this.turtDirection = dir;
    }
}