public class MoveFunctions
{
  /*
Justin Hughes and Yacine Belaifa
COMP 3721
Robot Turtles Milestone 4
MoveFunctions.java
*/

  public MoveFunctions()// constructor 
  {

  }
  /* left turn */
  public static void turn_left(Player tm)
  {
    if (tm.getDirection().equals("EAST")) 
    {
        tm.setDirection("NORTH");
    }
    else if(tm.getDirection().equals("WEST")) 
    {
        tm.setDirection("SOUTH");
    }
    else if(tm.getDirection().equals("NORTH"))
    {
          tm.setDirection("WEST");
    }
    else
    {
        tm.setDirection("EAST");
    }
        
      Deck playerDeck = tm.getDeck();
      playerDeck.setRemainingLeftMoves(playerDeck.getRemainingLeftMoves() -1);//Reduce number of left turn cards in players deck by 1
      tm.getSequenceOfMoves().push("TURN_LEFT");  //Adding this move to the Stack of existing moves
  }
     
  /* right turn */
    public static void turn_right(Player tm)
    {
        if (tm.getDirection().equals("EAST")) 
        {
            tm.setDirection("SOUTH");
        }
        else if(tm.getDirection().equals("WEST")) 
        {
            tm.setDirection("NORTH");
        }
        else if(tm.getDirection().equals("NORTH"))
        {
            tm.setDirection("EAST");
        }
        else
        {
            tm.setDirection("WEST");
        }
        Deck playerDeck = tm.getDeck();
        playerDeck.setRemainingRightMoves(playerDeck.getRemainingRightMoves() -1); //Reduce number of left turn cards in players deck by 1
        tm.getSequenceOfMoves().push("TURN_RIGHT"); //Adding this move to the Stack of existing moves
    }
    
    /* forward move */
  
    public static void forward(Player tm)
    {
      int[] l = tm.getLocation();
      if (tm.getDirection().equals("EAST"))
      {
        l[0] = l[0] + 1;
            
      }
      else if(tm.getDirection().equals("WEST"))
      {
        l[0] = l[0] - 1; 
            
      }
      else if(tm.getDirection().equals("NORTH"))
      {
        l[1] = l[1] - 1; 
            
      }
      else
      {
        l[1] = l[1] + 1;
           
      }
      tm.setLocation(l);
      Deck playerDeck = tm.getDeck();
      playerDeck.setRemainingForwardMoves(playerDeck.getRemainingForwardMoves() -1);//Reduce number of left turn cards in players deck by 1
      tm.getSequenceOfMoves().push("GO_FORWARD"); //Adding this move to the Stack of existing moves
     
    }

}