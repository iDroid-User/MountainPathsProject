import java.util.*;
import java.io.*;
import java.awt.*;

public class MapDataDrawer
{

  private int[][] grid;
  private int minValue, maxValue;

  public MapDataDrawer(String filename, int rows, int cols){
      // initialize grid
      grid = new int[rows][cols];
      //read the data from the file into the grid
      Scanner scan = null;
      try {
          scan = new Scanner(new File("Colorado_840x480.dat"));
      } catch (FileNotFoundException e) {
          throw new RuntimeException(e);
      }
      // Read data in row-major order, accounting for the lack of line breaks
      for (int row = 0; row < rows; row++) {
        for (int col = 0; col < cols; col++) {
            if (scan.hasNextInt())
                grid[row][col] = scan.nextInt();
        }
      }
  }
  
  /**
   * @return the min value in the entire grid
   */
  public int findMinValue(){
      int min = grid[0][0];
      for (int row = 0; row < grid.length; row++) {
          for (int col = 0; col < grid[0].length; col++) {
              if (grid[row][col] < min) min = grid[row][col];
          }
      }
      return min;
  }
  /**
   * @return the max value in the entire grid
   */
  public int findMaxValue(){
      int max = grid[0][0];
      for (int row = 0; row < grid.length; row++) {
          for (int col = 0; col < grid[0].length; col++) {
              if (grid[row][col] > max) max = grid[row][col];
          }
      }
      return max;
  }
  
  /**
   * @param col the column of the grid to check
   * @return the index of the row with the lowest value in the given col for the grid
   */
  public  int indexOfMinInCol(int col){

      return -1;
  }
  
  /**
   * Draws the grid using the given Graphics object.
   * Colors should be grayscale values 0-255, scaled based on min/max values in grid
   */
  public void drawMap(Graphics g){




  }

   /**
   * Find a path from West-to-East starting at given row.
   * Choose a foward step out of 3 possible forward locations, using greedy method described in assignment.
   * @return the total change in elevation traveled from West-to-East
   */
  public int drawLowestElevPath(Graphics g, int row){
    return -1;
  }
  
  /**
   * @return the index of the starting row for the lowest-elevation-change path in the entire grid.
   */
  public int indexOfLowestElevPath(Graphics g){
     return -1;
  
  }
  
  
}