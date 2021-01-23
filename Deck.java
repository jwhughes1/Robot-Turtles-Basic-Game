/*
Justin Hughes and Yacine Belaifa
COMP 3721
Robot Turtles Milestone 4
Deck.java
*/
public class Deck 
{
    private int numLeftTurnCards;
    private int numRightTurnCards;
    private int numForwardMoveCards;
    
    public Deck(int leftTurnCards, int rightTurnCards, int numForwardMoves)
    {
        numLeftTurnCards = leftTurnCards;
        numRightTurnCards = rightTurnCards;
        numForwardMoveCards = numForwardMoves;
    }
    
    public int getRemainingLeftMoves()
    {
        return numLeftTurnCards;
    }
    
    public int getRemainingRightMoves()
    {
        return numRightTurnCards;
    }
    
    public int getRemainingForwardMoves()
    {
        return numForwardMoveCards;
    }
    
    public void setRemainingLeftMoves(int leftTurnCards)
    {
        numLeftTurnCards = leftTurnCards;
    }
    
    public void setRemainingRightMoves(int rightTurnCards)
    {
        numRightTurnCards = rightTurnCards;
    }

    public void setRemainingForwardMoves(int forwardMoveCards)
    {
        numForwardMoveCards = forwardMoveCards;
    }
    
    public boolean isEmpty()
    {
        if ((numRightTurnCards ==0) && (numLeftTurnCards ==0) &&(numForwardMoveCards == 0))
        {
           return true;
        }
        return false;
    }
    
    @Override
    public String toString()
    {
        return "Number of forward move cards: " + numForwardMoveCards + " number of left turn cards: "+ numLeftTurnCards + " number of right turn cards "+ numRightTurnCards;
    }
}
