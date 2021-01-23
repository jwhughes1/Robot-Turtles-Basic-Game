/*
Justin Hughes and Yacine Belaifa
COMP 3721
Robot Turtles Milestone 4
Tile.java
*/
public class Tile
{
    private int[] location ;// array of size 2 where location[0] = x_cord location[1] = y_cord
    private boolean occupied;
    private String type;
    //Not sure if Tile actually needs a location - these are referred through in the board
    
    public Tile(int[]location,String type,boolean occupied)
    {
        this.location = location;
        this.type = type;
        this.occupied = false;
    }

    public int[] getLocation()
    {
        return this.location;

    }
    public void setLocation(int[] loc)
    {
        this.location = loc;
    }
    public void setType(String t)
    {
         this.type = t;
    }
    public String getType()
    {
        return this.type ;
    }
    public boolean isOccupied()
    {
        return this.occupied;

    }
    public void setOccupiedStatus(boolean status)
    {
        this.occupied = status;
    }
    
}