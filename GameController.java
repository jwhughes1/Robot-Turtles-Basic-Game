import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashSet;
import java.awt.event.*;
/*
Justin Hughes and Yacine Belaifa
COMP 3721
Robot Turtles Milestone 4
GameController.java
*/

public class GameController
{
    private GameModel model; 
    private CommandLineView CLI;
    private GUIView GUI;
    private Scanner in;
    
    public GameController()
    {
        CLI = new CommandLineView(this);
        model = new GameModel();
    }

    public void updateGUIView() 
    {
        GUI.closeFrame();
        GUI.validate();
        GUI.repaint();
        GUI.showExistingBoard();

    }
   
    /* user input */
    public String getUserInput()    //To avoid duplicate code code smell, this method is used to process the move, but also to retrieve 
    {
        in = new Scanner(System.in);  
        String input = in.nextLine();
        return input;
    }
   
    public void checkTurnFormat(String move)
    {
        boolean wrong_move = !(move.equals("forward")|| move.equals("left turn")||move.equals("right turn"));   
        while(wrong_move) // loop until a valid move is entered 
        {
            System.out.println("enter valid move");
            move = getUserInput();
            if (move.equals("forward"))
            {
                System.out.println("Entered forward move. This is now valid. ");
                wrong_move = false;
            }
            else if(move.equals("left turn"))
            {
                System.out.println("Entered left turn. This is now valid.");
                wrong_move = false;
            }
            else if(move.equals("right turn"))
            {
                System.out.println("Entered right turn. This is now valid.");
                wrong_move = false;
            }
        }
    }
   
    /*  manage order of player turns */
    public void manageTurnFlow()
    {
        ArrayList<Player> players = model.getPlayers();
        while (players.size() >= 1)
        {
            for (int i =0; i < players.size();i++)
            {
                Player p = players.get(i);
                if(p.getDeck().isEmpty()==false)   //If the player's deck has no cards left 
                {
                    CLI.printTurnStatement(p.getColor());//fix argument
                    String move = this.getUserInput();   
                    this.checkTurnFormat(move);
                    int status = model.takePlayerTurn(p,move);
                    this.updateGUIView();
                    while (status ==0)
                    {
                        CLI.invalidInputMessage();
                        CLI.printTurnStatement(p.getColor());//fix argument
                        move = this.getUserInput();   
                        this.checkTurnFormat(move);
                        status = model.takePlayerTurn(p,move);
                        this.updateGUIView();
                    }
                    if (model.hasWon == true)
                    {
                        updateWinMessage(p.getColor());
                        return;
                    }
                    String bugStatus = this.bugInformation();
                    model.bugProcessing(bugStatus, p);
                    this.updateGUIView();
                    if (bugStatus.equals("yes"))
                    {
                        i = i -1;   //do-over of previous loop
                    }
                    else
                    {
                        this.CLI.showEndOfTurnMessage(p.getColor());
                    }
                }
                else
                {
                    System.out.println("Sorry, you have no more cards remaining :(");
                    players.remove(p);
                }
            }
        }
    }

    //returns true if the Bug undid a move, otherwise it does not
    public String bugInformation()  
    {
        this.CLI.getBugMessage();
        String bugStatus = getUserInput();
        boolean validBug = bugStatus.equals("yes") || bugStatus.equals("no");
        while(validBug == false) 
        {
            CLI.invalidInputMessage();
            bugStatus = getUserInput();
            validBug = bugStatus.equals("yes") || bugStatus.equals("no");

        }
        return bugStatus;
    }

    public void updateWinMessage(String color)
    {
        this.CLI.showCurrentPlayerHasWon(color);
        if(model.getPlayers().size() > 1)
        {
            this.CLI.showEndOfGameMessage();
        }
    }
    public void launchGame()
    {
        this.CLI = new CommandLineView(this); // initialize CLIView 
        System.out.println("Welcome to RobotTurtles");
        System.out.println("would you like to review game rules ? yes/no");
        Scanner in = new Scanner(System.in);
        String r = in.nextLine();
        if(r.equals("yes"))
        {
            CLI.gameRules();
        }
           
        System.out.println("Enter number of players:");
        in = new Scanner(System.in);    
        int numPlayers = in.nextInt();
        while(numPlayers< 1 ||  numPlayers > 4 )
        {
            System.out.println("ERROR: Entered invalid number of players. Please re-enter the number of players (between 1 and 4).");
            numPlayers = in.nextInt();
        }
        model.setNumPlayers(numPlayers);
        HashSet<String> colors = this.colorFormat(); 
        model.setUpPlayersList(colors);
        System.out.println("Setting up jewels");
        HashSet<String> jewels = this.colorFormat(); 
        model.setUpJewelsList(jewels);
        
        this.setUpGUI();
        this.manageTurnFlow();
    }

    public void setUpGUI()
    {
        this.GUI = new GUIView(this);//fix constructor of GUIView
        this.GUI.showBoard();
    }
    
    //A hashSet is a different data structure than an arrayList
    public HashSet<String> colorFormat()
    {
        //Could not completely forgo the ArrayList - the user needs to know what colors they have to choose from 
        ArrayList<String> colorsList = new ArrayList<String>();
        colorsList.add("green");
        colorsList.add("red");
        colorsList.add("magenta");
        colorsList.add("blue");
        HashSet<String> chosenColors = new HashSet<String>();//-------------
        for (int i =0; i <model.getNumPlayers();i++ )
        {
            CLI.showColorMessage(colorsList);
            String color = getUserInput();  
            if (colorsList.contains(color)== false)
            {
                while (colorsList.contains(color)==false)
                {
                    CLI.invalidInputMessage();
                    CLI.showColorMessage(colorsList);
                    color = getUserInput();
                }
            }
            colorsList.remove(color);
            chosenColors.add(color);
        }
        return chosenColors;
    }
}
