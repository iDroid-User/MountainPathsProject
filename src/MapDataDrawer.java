import java.util.*;
import java.io.*;
import java.awt.*;

public class MapDataDrawer {

  private int[][] grid;

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
  public int findMinValue() {
      int min = grid[0][0];
      for (int row = 0; row < grid.length; row++) {
          for (int col = 0; col < grid[row].length; col++) {
              if (grid[row][col] < min) min = grid[row][col];
          }
      }
      return min;
  }
  /**
   * @return the max value in the entire grid
   */
  public int findMaxValue() {
      int max = grid[0][0];
      for (int row = 0; row < grid.length; row++) {
          for (int col = 0; col < grid[row].length; col++) {
              if (grid[row][col] > max) max = grid[row][col];
          }
      }
      return max;
  }
  
  /**
   * @param col the column of the grid to check
   * @return the index of the row with the lowest value in the given col for the grid
   */
  public  int indexOfMinInCol(int col) {
      int lowestRowIdx = 0, lowestValue = grid[0][col];
      for (int row = 0; row < grid.length; row++) {
          if (grid[row][col] < lowestValue) {
              lowestValue = grid[row][col];
              lowestRowIdx = row;
          }
      }
      return lowestRowIdx;
  }
  
  /**
   * Draws the grid using the given Graphics object.
   * Colors should be grayscale values 0-255, scaled based on min/max values in grid
   */
  public void drawMap(Graphics g) {
    int min = findMinValue(); // Darkest shade
    int max = findMaxValue(); // Brightest shade

    for (int row = 0; row < grid.length; row++) {
        for (int col = 0; col < grid[row].length; col++) {
            int c = (int) Math.round((double)(grid[row][col] - min) / (max - min) * 255); // Calculate grayscale value
            g.setColor(new Color(c,c,c));
            g.fillRect(col,row,1,1); // Graphics are drawn in column-major order as 1x1 filled rectangles
        }
    }
  }

   /**
   * Find a path from West-to-East starting at given row.
   * Choose a forward step out of 3 possible forward locations, using greedy method described in assignment.
   * @return the total change in elevation traveled from West-to-East
   */
  public int drawLowestElevPath(Graphics g, int startRow) {
      int row = startRow; // Keeps track of the current row
      int totalElevChange = 0;
      // Loops through every possible column across the map
      for (int col = 0; col < grid[row].length; col++) {
          g.fillRect(col,row,1,1);

          if (col == grid[row].length - 1) return totalElevChange; // Guard clause
          else {
              int step = 0, change, smallestChange;
              // Check the same row (the default) on the next column
              smallestChange = Math.abs(grid[row][col] - grid[row][col + 1]);
              // Check the previous row on the next column
              if (row > 0) {
                  change = Math.abs(grid[row][col] - grid[row - 1][col + 1]);
                  if (smallestChange == change) step = 0; // If tied with the adjacent step, go forward
                  else if (smallestChange > change) {
                      smallestChange = change;
                      step = -1; // Go forward-up
                  }
              }
              // Check the next row on the next column
              if (row < grid.length - 1) {
                  change = Math.abs(grid[row][col] - grid[row + 1][col + 1]);
                  if (smallestChange == change && step == 0) step = 0; // If tied with the adjacent step, go forward
                  // If side steps tie
                  else if (smallestChange == change) {
                      Random rand = new Random();
                      step = rand.nextBoolean() ? 1 : -1; // Choose randomly between fwd-up/fwd-down
                  }
                  else if (smallestChange > change) {
                      smallestChange = change;
                      step = 1; // Go forward-down
                  }
              }
              totalElevChange += smallestChange;
              row += step;
          }
      }
      return totalElevChange;
  }

  /**
   * @return the index of the starting row for the lowest-elevation-change path in the entire grid.
   */
  public int indexOfLowestElevPath(Graphics g) {
      int startRowIdx = 0;
      int lowestElevChange = drawLowestElevPath(g, 0);
      // Loop through every possible starting row and call drawLowestElevPath() to find the lowest route
      for (int row = 1; row < grid.length; row++) {
          int elevChange = drawLowestElevPath(g, row);
          if (lowestElevChange > elevChange) {
              lowestElevChange = elevChange;
              startRowIdx = row;
          }
      }
      return startRowIdx;
  }
}