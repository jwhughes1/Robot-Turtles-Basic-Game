import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

/*
Justin Hughes and Yacine Belaifa
COMP 3721
Robot Turtles Milestone 4
GameModel.java
*/

public class GameModel  
{
    private ArrayList<Player> players;  
    private ArrayList<Jewel> jewels;  
    private Board boardClass;// instance of the Board class 
    private Tile[][] gameBoard; //the 2d grid of tiles that is an instance of the Board class
    protected boolean hasWon; //fix
    private int numPlayers;

    public GameModel()// constructor 
    {
        this.boardClass = new Board(); // initialize board 
        this.gameBoard = boardClass.getGrid(); //get the grid from the boardClass
        this.players = new ArrayList<Player>();
        this.jewels = new ArrayList<Jewel>();
        this.numPlayers = 0;
        this.hasWon = false;
    } 
    //We were doing recursion on this method, but that no longer makes sense if we are thinking about doing a specific Move.
    //This method now returns an int status signifying if a player needs to redo their turn: 0 if they do, 1 if they do not

    public int takePlayerTurn(Player p, String move) // this method handles taking a turn for the current player
    {
            // once we exit the loop, the move is valid 
            // process move and update location 
            if (move.equals("forward"))                             //If player wants to move forward
            {
                if(isValidMove(p.getLocation(),p.getDirection())==true && this.canCaptureJewel(p) == true)  //If the current player is about to collect their jewel
                {
                    int[] position = p.getLocation();
                    if(p.getDeck().getRemainingForwardMoves() >0)
                    {
                        Tile prevTile = gameBoard[position[0]][position[1]];
                        MoveFunctions.forward(p); 
                        System.out.println("Cards remaining: " + p.getDeck() + "\n");//move forward - must do this after checking if going forward will capture the jewel
                        hasWon = true;
                        clearBoard(p,prevTile);
                        writePlayerDataToFile();
                        writeJewelDataToFile();
                        return 1;
                    }
                    else
                    {
                        System.out.println("ERROR: This player has no more forward cards");
                        if(p.getDeck().isEmpty())
                        {
                            clearPlayer(p,gameBoard[position[0]][position[1]]);
                            writePlayerDataToFile();
                        }
                        else
                        {
                            return 0;   //Code for "Redo this turn"
                        }
                    }
                }
                else if (this.canCaptureJewel(p) == true && isValidMove(p.getLocation(),p.getDirection())==false) //If the move the user wants to make is invalid.
                {
                    System.out.println("ERROR: Invalid Move\n");
                }
                else
                {
                    System.out.println("Current Location is: " + Arrays.toString(p.getLocation()));
                    if (isValidMove(p.getLocation(),p.getDirection()))
                    {
                        if(p.getDeck().getRemainingForwardMoves() >0)// check wether a player still has forward moves in deck
                        {
                            MoveFunctions.forward(p); 
                            writePlayerDataToFile();
                            System.out.println("Updated Location is: " + Arrays.toString(p.getLocation()) + "\n");
                            System.out.println("Cards remaining: " + p.getDeck() + "\n");// display player deck
                            return 1;
                        }
                        else
                        {
                            if(p.getDeck().isEmpty())
                            {
                                int[] position = p.getLocation();
                                clearPlayer(p,gameBoard[position[0]][position[1]]);
                                writePlayerDataToFile();
                                return 1;
                            }
                            else
                            {
                                return 0;
                            }
                        }
                    }
                    else
                    {
                        System.out.println("ERROR: Invalid Move\n");
                    }
                }
                return 1;
            }
                else if (move.equals("left turn"))
                {
                    if(p.getDeck().getRemainingLeftMoves() >0)
                    {
                        MoveFunctions.turn_left(p); 
                        writePlayerDataToFile();
                        System.out.println("Cards remaining: " + p.getDeck());;// display player deck deck 
                        System.out.println("Updated Location is: " + Arrays.toString(p.getLocation()));
                        return 1;
                    }
                    else
                    {
                        System.out.println("ERROR: This player has no more left turn cards\n");
                        if(p.getDeck().isEmpty())
                        {
                            int[] position = p.getLocation();
                            clearPlayer(p,gameBoard[position[0]][position[1]]);
                            writePlayerDataToFile();
                            return 1;
                        }
                        else
                        {
                            return 0;
                        }
                    }
                }
                else if (move.equals("right turn"))
                {
                    if(p.getDeck().getRemainingRightMoves() >0) //check if player has right move cards in deck 
                    {
                        MoveFunctions.turn_right(p); 
                        writePlayerDataToFile();
                        System.out.println("Cards remaining: " + p.getDeck());
                        System.out.println("Updated Location is: " + Arrays.toString(p.getLocation()));
                        return 1;
                    }
                    else
                    {
                        System.out.println("ERROR: This player has no more right turn cards\n");
                        if(p.getDeck().isEmpty())
                        {
                            int[] position = p.getLocation();
                            clearBoard(p,gameBoard[position[0]][position[1]]);
                            writePlayerDataToFile();
                            return 1;
                        }
                        else
                        {
                            return 0;
                        }
                    }
                }
                
            return 1;
        }
    public boolean isValidMove(int[] currposition, String dir) // checks if a move is valid
    {
        
        int x_cord = currposition[0];
        int y_cord = currposition[1];
        if (x_cord - 1 < 0 && dir.equals("WEST") || x_cord +1 >= gameBoard.length && dir.equals("EAST"))
        {
            return false;
        }
        else if(y_cord+1 >=gameBoard.length && dir.equals("SOUTH") || y_cord - 1 <0&&dir.equals("NORTH") )
        {
            return false;
        }
        return true ;
    }
    /* launch */    //Justin modified this
    
    /* bug processing */
    public void bugProcessing(String bugStatus, Player p)
    {
        if(bugStatus.equals("yes") && p.getSequenceOfMoves().size() > 0)
        {
            String prevMove= p.getSequenceOfMoves().peek();
            p.getBugCard().undoMove(p);//We are undoing the most recently played move
            //GUIView.showBoard();
            //This fragment just clears up the board if we specifically change the status
            int[] prevLocation = p.getLocation();
            int[] currLocation = prevLocation;
            if(prevMove.equals("FORWARD"))
            {
                currLocation = p.getLocation(); 
            }
            
            Tile prevTile = gameBoard[prevLocation[0]][prevLocation[1]];
            Tile currTile = gameBoard[currLocation[0]][currLocation[1]];
            writePlayerDataToFile();
            prevTile.setOccupiedStatus(false);
            currTile.setOccupiedStatus(true);
        }
        else if(bugStatus.equals("yes") && p.getSequenceOfMoves().size() == 0)
        {
            System.out.println("\n ERROR: We are trying to Bug when we have not written any code yet \n");
        }
    }
    //This function performs the logic of setting up a List of  for the program, given the Colors they represent
    public void setUpJewelsList(HashSet<String> colors)// sets up jewels for the game 
    {
        Iterator<String> i = colors.iterator();
        while(i.hasNext())
        {
            String color = i.next();
            Jewel newJewel = new Jewel(color);
            int[] position = newJewel.getLocation();
            gameBoard[position[0]][position[1]].setType("Jewel");
            jewels.add(newJewel); 
        }
        this.writeJewelDataToFile();
    }
    public boolean canCaptureJewel(Player p)
    {
        int[] pos = p.getLocation();
        Tile nextTileLocation = null;  //Tile that player is about to move to 
        //set location of next tile
        if (p.getDirection().equals("EAST") && pos[0] <gameBoard.length -1 ) // east is right 
        { 
            nextTileLocation = gameBoard[pos[0]+1][pos[1]];
        }
        else if(p.getDirection().equals("WEST")&& pos[0] >0) // west is left 
        {
            nextTileLocation = gameBoard[pos[0]-1][pos[1]];
        }             
        else if(p.getDirection().equals("NORTH")&& pos[1] >0)
        {
            nextTileLocation = gameBoard[pos[0]][pos[1]-1];
        }
        else if(p.getDirection().equals("SOUTH")&& pos[1]<gameBoard.length-1 )           
        {
           nextTileLocation = gameBoard[pos[0]][pos[1]+1]; 
        }
        else
        {
            nextTileLocation = gameBoard[pos[0]][pos[1]]; 
        }
        //verify type of tile is jewel
        if (nextTileLocation.getType().equals("Jewel"))
        {
            return true;
        }
        return false;
    }
    /*SET UP PLAYERS LIST */
    public void setUpPlayersList(HashSet<String> colors) // sets up players for the game . //THIS WILL PROBABLY GO INTO CONTROLLER s
    {
        Iterator<String> i = colors.iterator();
        while(i.hasNext())
        {
            String color = i.next();
            Player newPlayer = new Player(color);
            int[] position = newPlayer.getLocation();
            gameBoard[position[0]][position[1]].setType("Player");
            players.add(newPlayer); 
        }
        this.writePlayerDataToFile();
    }

    public void clearBoard(Player p, Tile prevTile)
    {
        int[] position = p.getLocation();
        for (int i =0; i < jewels.size();i++ )
        {
            Jewel j = jewels.get(i);
            int[] jPosition = j.getLocation();
            if (jPosition[0] == position[0] && jPosition[1] == position[1])
            {
               jewels.remove(j);
            }
        }
         Tile nextTile = gameBoard[position[0]][position[1]];
        prevTile.setOccupiedStatus(false);
        nextTile.setOccupiedStatus(true);
        players.remove(p);
    }
    public void clearPlayer(Player p, Tile prevTile)
    {
        int[] position = p.getLocation();
        Tile nextTile = gameBoard[position[0]][position[1]];
        prevTile.setOccupiedStatus(false);
        nextTile.setOccupiedStatus(true);
        players.remove(p);
    }

    public ArrayList<Player> getPlayers()
    {
        return players;
    }

    public ArrayList<Jewel> getJewels()
    {
        return jewels;
    }

    //This function creates a file, jewelData.txt, which records the specific Jewel information that is needed for the View
    //This ensures the Model and the View do not communicate directly and only do so using the contents of this file.
    public void writeJewelDataToFile()
    {
        try 
        {
            File jewelFile = new File("jewelData.txt");
            FileWriter writer = new FileWriter(jewelFile);
            for (int i =0; i < jewels.size(); i++)
            {
                Jewel j = jewels.get(i);
                int[] position = j.getLocation();
                writer.write(position[0] + " " + position[1] + " " + j.getColor() + "\n");
            }
            writer.close();
            jewelFile.deleteOnExit();
        } 
        catch (IOException e) 
        {
            System.err.println("ERROR WITH WRITING JEWEL DATA TO THE FILE");
        }
    }
    
    //This function creates a file, playerData.txt, which records the specific Player information that is needed for the View
    //This ensures the Model and the View do not communicate directly and only do so using the contents of this file.
    public void writePlayerDataToFile()
    {
        try 
        {
            File playerFile = new File("playerData.txt");
            FileWriter writer = new FileWriter(playerFile);
            for (int i =0; i < players.size(); i++)
            {
                Player p = players.get(i);
                int[] position = p.getLocation();
                writer.write(position[0] + " " + position[1] + " " + p.getColor() + " " + p.getDirection() + "\n");
            }
            
            writer.close();
            playerFile.deleteOnExit();
        } 
        catch (IOException e) 
        {
            System.err.println("ERROR WITH WRITING PLAYER DATA TO THE FILE");
        }
    }
    public void setNumPlayers(int players)
    {
        numPlayers = players;
    }
    public int getNumPlayers()
    {
        return numPlayers;
    }
}
