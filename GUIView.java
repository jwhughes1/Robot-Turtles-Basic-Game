import java.util.Arrays;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.io.IOException;
import java.lang.reflect.Field;
import javax.imageio.ImageIO;

/*
Justin Hughes and Yacine Belaifa
COMP 3721
Robot Turtles Milestone 4
GUIView.java
*/
//Long story short, we MUST have a way for the GUI to be updating (and repaint to be called) WHILE we are calling TakePlayerTurn - this approach isnt working
public class GUIView extends JPanel 
{
    //These arrays each contain an integer index referring to a pixel (that is either an x-coordinate or a y-coordinate) of where each line is drawn
    private int[] xDimensions ;    
    private int[] yDimensions ;
    private static JFrame frame;
    private int squareSideLength;   //Refers to the length (in pixels) of each square we draw on the Grid.
    private GameController controller;
    

    public GUIView(GameController gController) // constructor 
    {
        this.xDimensions = new int [9];
        this.yDimensions = new int [9];
        this.squareSideLength = getHeight()/9;
        this.controller = gController;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);//Calls the paintComponent() method located in the Graphics class
        int barWidth = 6;  //This is the weight (in pixels) of the black bar
        squareSideLength = getHeight()/9;  //states the length of one square (one space where a Turtle could be located) in pixels 
        
        //Variables for  determining the propoprtions of the screen on which we want to make lines.
        int minLineStart = squareSideLength;    
        int maxLineStart = 8 * getHeight()/9 ;   //Its sufficent to choose 1 dimension, getHeight and getWeight supposed to be same size.
        loadBackgroundImage(minLineStart, maxLineStart, g);
        g.setColor(Color.BLACK);
        int rem = (getHeight())/ squareSideLength; 
        drawLines(minLineStart, maxLineStart, rem, barWidth, g);
        //access the info from controller -> not in view.
        //still need to put values in text files?
        processJewelData(g);
        processTurtleData(g);
    }
    
    //This function reads through the existing file created by the Model (JewelData.txt) to write the Model information that is needed to show the Jewels on the screen.
   //NOTE: Should we handle text files through the controller? Or focus more oN SOLID right now?
    public void processJewelData(Graphics g)
    {
        try {
            FileReader reader = new FileReader("jewelData.txt");
            BufferedReader bReader = new BufferedReader(reader);
            String line;
 
            while ((line = bReader.readLine()) != null) 
            {
                String[] pieces = line.split(" ");
                int xPosition = Integer.parseInt(pieces[0]);
                int yPosition = Integer.parseInt(pieces[1]);
                String color = pieces[2];
                int[] position = {xPosition,yPosition};
                drawJewel(position,color,g);
            }
            reader.close();
 
        } catch (IOException e) {
            System.err.println("ERROR READING jewelData.txt");
        } 
    }

    //This function reads through the existing file created by the Model (playerData.txt) to write the Model information that is needed to show the Players on the screen.
    //NOTE: Should we handle text files through the controller? Or focus more oN SOLID right now?
    public void processTurtleData(Graphics g)
    {
        try {
            FileReader reader = new FileReader("playerData.txt");
            BufferedReader bReader = new BufferedReader(reader);
            String line;
 
            while ((line = bReader.readLine()) != null) 
            {
                String[] pieces = line.split(" ");
                int xPosition = Integer.parseInt(pieces[0]);
                int yPosition = Integer.parseInt(pieces[1]);
                String color = pieces[2];
                String direction = pieces[3];
                int[] position = {xPosition,yPosition};
                drawTurtle(position,color,direction,g);
            }
            reader.close();
 
        } catch (IOException e) {
            System.err.println("ERROR READING turtleData.txt");
        } 
    }
    //This method loads the Robot Turtles Background Image
    public void loadBackgroundImage(int minLineStart, int maxLineStart, Graphics g)
    {
        try    
        {
            ImageIO.setUseCache(false);
            Image img = ImageIO.read(new File("rt_board_resized.jpg"));//Opening and Retrieving background image
            
            //Forcing the image to fit inside the Grid structure on the screen
            g.drawImage(img,minLineStart -10, minLineStart -10 , maxLineStart - minLineStart + 25, (maxLineStart - minLineStart + 25),null);    
        } 
        catch (IOException ex) 
        {
            System.out.println("There was an error retrieving the image file. ");
        }
    }
    
    //This method draws the required lines on the screen
    public void drawLines(int minLineStart, int maxLineStart, int rem, int barWidth,  Graphics g)
    {
        int xDimIndex =0; int yDimIndex = 0;
        for (int i =minLineStart; i <= maxLineStart; i = i+(squareSideLength - rem))
        {
            g.fillRect(i, squareSideLength,  barWidth,7 *squareSideLength );//drawing a horizontal line sit
            xDimensions[xDimIndex] = i;
            xDimIndex++;
        }
        
        for (int i =minLineStart; i <= maxLineStart; i = i+(squareSideLength - rem))//removed rem
        {
            g.fillRect(squareSideLength,i ,7* squareSideLength, barWidth); 
            yDimensions[yDimIndex] = i;
            yDimIndex++;
        }
    }
    //This method takes care of drawing a Jewe (represented by a Diamond (Polygon)of a given color)
    public void drawJewel(int[] position, String color, Graphics g)
    {
        //*WAY THAT DECREMENT FOR DRAWTURTLE - AND COLLECT JEWEL ALSO CHECK BUG CARD ENTERED VALID OPTION
        //computing the midpoint of the 2 adjacent x values referenced by the jewel's position. 
        //We add/subtract 10 in each direction to make the jewel easier to see on the screen.
        int midpointX = ((xDimensions[position[0]] +10) + xDimensions[position[0]+1] -10)/2;  
        
        //List of x and y coordinates that are used to draw the sides of the Jewel
        int[] xCoordinatesJewel = {xDimensions[position[0]] + 10, midpointX, xDimensions[position[0]+1] -10, midpointX};
        int[] yCoordinatesJewel = {yDimensions[position[1]] + 22, yDimensions[position[1]+1] - 10,yDimensions[position[1]] +22, yDimensions[position[1]] + 6};
        try 
        {
            Field field = Class.forName("java.awt.Color").getField(color);  //COnverting the Color (represented by a String) to a Color object, and capturing that color to draw the Jewel
            g.setColor((Color)field.get(null)); //Will retrieve the color we want
        }
         catch (Exception e) 
        {
            g.setColor(Color.BLACK); // default
        }
        g.fillPolygon(xCoordinatesJewel, yCoordinatesJewel,4);//drawing the jewel itself 
        
    }
    //This method takes care of drawing the turtle (which is represented by a Circle of a given color).The circle also indicates the direction of the Turtle (see returnDirectionArrow for this)
    public void drawTurtle(int[] position, String color, String direction, Graphics g)
    {
        squareSideLength = getHeight()/9;
        int xStartPosition = xDimensions[position[0]];  
        int yStartPosition = yDimensions[position[1]];
        Point center = new Point((xStartPosition + xDimensions[position[0]+1]) / 2, (yStartPosition + yDimensions[position[1]+1]) / 2);   //Calculation of the center point in the square
        try 
        {
            Field field = Class.forName("java.awt.Color").getField(color);
            g.setColor((Color)field.get(null)); //Will retrieve the color we want
        }
         
        catch (Exception e) 
        {
            g.setColor(Color.BLACK); // default case
       }
        
        g.fillOval(center.x - (squareSideLength/4),center.y - (squareSideLength/4),squareSideLength/2,squareSideLength/2);//Drawing the circle itself
        g.setColor(Color.BLACK);
        String dirArrow = returnDirectionArrow(direction);  
        g.setFont(new Font("default", Font.BOLD, 20));
        g.drawString(dirArrow,center.x - 6,center.y + 6 ); //Drawing the direction arrow in the centre of the circle
        
    }
    
    //Returns a character resembling an arrow for the given direction.
    public String returnDirectionArrow(String direction)
    {
        if (direction.equals("NORTH"))
        {
            return "^";
        }
        else if (direction.equals("SOUTH"))
        {
            return "V";
        }
        else if (direction.equals("EAST"))
        {
            return ">";
        }
        else
        {
            return "<";
        }
    }
    
    public void showBoard () 
    {
        GUIView panel = new GUIView(this.controller); //Not sure here 
        frame = new JFrame("Robot Turtles GUI");
        frame.setSize(700,700);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
        
    }
    public void showExistingBoard()
    {
        frame.setVisible(true);
    }
    public void closeFrame()
    {
        frame.setVisible(false);
    }
    
    
}
  
