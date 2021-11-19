/*
Justin Hughes and Yacine Belaifa
COMP 3721
Robot Turtles Milestone 4
Jewel.java
*/
public class Jewel 
{
    private int[] location;
    private String color;
    
    public Jewel(String jewelColor)
    {
        this.color = jewelColor;
        this.location = new int[2];
        if(jewelColor.equals("red"))
        {
            this.location[0] = 4;
            this.location[1] = 3;
        }
        else if(jewelColor.equals("green"))
        {
            this.location[0] = 3;
            this.location[1] = 4;
        }
        else if(jewelColor.equals("blue"))
        {
            this.location[0] = 4;
            this.location[1] = 4;
        }
        else
        {
            this.location[0] = 3;
            this.location[1] = 3;
        }
    }
    public Jewel(int[] position, String jewelColor )// constructor 
    {
        this.color = jewelColor;
        this.location = new int[2];
        this.location[0] = position[0];
        this.location[1] = position[1];
    }
    public int[] getLocation()
    {
        return this.location;
    }
    public void setLocation(int[] loc)
    {
        this.location = loc;
    }
    public void setColor(String col)
    {
        this.color = col;
    }
    public String getColor()
    {
        return this.color;
    }
   
}