/*
Justin Hughes and Yacine Belaifa
COMP 3721
Robot Turtles Milestone 4
Board.java
*/
import java.util.ArrayList;

public class Board 
{
    private Tile[][] grid;
    public Board()
    {
        
        grid = new Tile[8][8];// initilize grid
        for(int i = 0;i<grid.length; i++)
        {
            for(int j = 0 ; j< grid[i].length ; j++)
            {
                int[] pos = {i,j};
                grid[i][j] = new Tile(pos,"None",false);//Update Tile constructor
            }
        }
    }
    public Tile[][] getGrid()
    {
        return this.grid;
    }
}