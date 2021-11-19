/*
Justin Hughes and Yacine Belaifa
COMP 3721
Robot Turtles Milestone 4
Bug.java
*/
public class Bug 
{
    
    public Bug()    
    {
        
    }
    
    public void undoMove(Player p)
    {
        String lastMove = p.getSequenceOfMoves().pop();
        if (lastMove.equals("TURN_LEFT"))
        {
            MoveFunctions.turn_right(p);  
            int currentLeftMoves = p.getDeck().getRemainingLeftMoves();
            p.getDeck().setRemainingLeftMoves(currentLeftMoves +1);
        }
        else if(lastMove.equals("TURN_RIGHT"))
        {
            MoveFunctions.turn_left(p);  
            int currentRightMoves = p.getDeck().getRemainingRightMoves();
            p.getDeck().setRemainingRightMoves(currentRightMoves +1);
        }
        else
        {
            int[] currPosition = p.getLocation();
            if (p.getDirection().equals("EAST")) // east is right 
            {
                currPosition[0] = currPosition[0] - 1;
            }
            else if(p.getDirection().equals("WEST")) // west is left 
            {
                currPosition[0] = currPosition[0] + 1; 
            }
            else if(p.getDirection().equals("NORTH"))
            {
                currPosition[1] = currPosition[1] + 1;
            }
            else
            {
                currPosition[1] = currPosition[1] - 1; 
            }
            int currentForwardMoves = p.getDeck().getRemainingForwardMoves();
            p.getDeck().setRemainingForwardMoves(currentForwardMoves +1);
            p.setLocation(currPosition);
        }
            
    }
}