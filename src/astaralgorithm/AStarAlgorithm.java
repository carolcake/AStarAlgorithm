/*
Caroline Hsu - 01/17/23
nodes class
 - manhattan distance 
class for doing the moves and left right up down
based on the position of the number you need to select the move and you need
to have a method to add a node - make the child

absolute value of 3-3 + 3-2 = manhattan distance for 8
go through whole array and cacluate for each element
total manhattan distance = summation of each element

main class - goal array, initial array
create manhattan distance with int array
find coordinates of goal array

move left and move right and move up and move down

 */
package astaralgorithm;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.PriorityQueue;

public class AStarAlgorithm
{

  // declare initial and goal states
  final static int[][] initialState =
  {
    {
      8, 3, 2
    },
    {
      4, 7, 1
    },
    {
      0, 5, 6
    }
  };

  final static int[][] goalState =
  {
    {
      1, 2, 3
    },
    {
      4, 5, 6
    },
    {
      7, 8, 0
    }
  };

  public static void main(String[] args)
  {
    boolean solvable;
    solvable = isSolvable(initialState);
    int moves = 0; 
    if (solvable == true)
    {
      //System.out.print(calculateAnotherManhattanDistance(state));
      System.out.println("Your initial state is: ");
      printTilePositions();
      System.out.println("Your puzzle is solvable.");
      Tree solutionTree = new Tree(initialState);
      solutionTree.initializeTree();
    }
    else
    {
      System.out.println("Your puzzle is not solvable.");
    }
  }

  public static boolean isSolvable(int[][] initialState)
  {
    // declare variables to find whether or not initial state is solvable
    int n = 3;
    int puzzleSize = n * n;
    int[] oneD = new int[n];
    int inversionCounter = 0;
    oneD = arrayConversion(initialState);
    boolean solvable;
    inversionCounter = countInversions(initialState);
    puzzleSize = inversionCounter + puzzleSize;
    if (inversionCounter % 2 == 0)
    {
      solvable = true;
    }
    else
    {
      solvable = false;
    }
    return solvable;
  }

  // converts a 2d array to a 1d array by double looping it 
  public static int[] arrayConversion(int[][] puzzle)
  {
    int[] oneDArray = new int[puzzle.length * puzzle.length];
    for (int i = 0; i < puzzle.length; i++)
    {
      for (int j = 0; j < puzzle.length; j++)
      {
        oneDArray[(i * puzzle.length) + j] = puzzle[i][j];
      }
    }
    return oneDArray;
  }

  // count the inversions of a puzzle
  public static int countInversions(int[][] puzzle)
  {
    int inversionCounter = 0;
    // copy the puzzle over
    ArrayList<Integer> copyPuzzle = new ArrayList<Integer>();
    for (int i = 0; i < puzzle.length; i++)
    {
      for (int j = 0; j < puzzle.length; j++)
      {
        copyPuzzle.add(puzzle[i][j]);
      }
    }
    Integer[] secondCopy = new Integer[copyPuzzle.size()];
    copyPuzzle.toArray(secondCopy);
    // do it for the second copy so the first isn't messed with and count inversions
    for (int i = 0; i < secondCopy.length - 1; i++)
    {
      for (int j = i + 1; j < secondCopy.length; j++)
      {
        if (secondCopy[i] != 0 && secondCopy[j] != 0 && secondCopy[i] > secondCopy[j])
        {
          inversionCounter++;
        }
      }
    }
    return inversionCounter;
  }

  // method for printing the positions of the blocks/tiles
  public static void printTilePositions()
  {
    for (int i = 0; i < initialState.length; i++)
    {
      for (int j = 0; j < initialState.length; j++)
      {
        System.out.print(initialState[i][j] + " ");
      }
      System.out.println();
    }
  }

}
