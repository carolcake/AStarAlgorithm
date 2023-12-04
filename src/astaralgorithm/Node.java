/*
Caroline Hsu - 2/13/2023 Node class
 */
package astaralgorithm;

import java.util.ArrayList;
import java.util.Comparator;

public class Node
{

  // declare attributes
  public int columnNo;
  public int f;
  public int g;
  public int h;
  public int[][] state = new int[3][3];
  public ArrayList<Node> children;
  public Node parentNode;
  public int previousRow;
  public int previousColumn;
  public String associatedMove;

  public Node()
  {
    // node values 
    this.columnNo = 3;
    this.g = 0;
    this.h = Integer.MAX_VALUE;
    this.f = Integer.MAX_VALUE;
    this.state = new int[columnNo][columnNo];
  }

  // overload node
  public Node(int columnNo, Node parentNode, int parentG, int[][] state, int previousRow, int previousColumn)
  {
    this.columnNo = columnNo;
    this.state = state;
    this.h = this.manhattanDistance(state);
    this.g = parentG + 1;
    this.f = h + g;
    this.parentNode = parentNode;
    this.previousRow = previousRow;
    this.previousColumn = previousColumn;
  }

  //uses selection sort to sort all nodes based on f measure
  public ArrayList<Node> sortNodes(ArrayList<Node> objCandidates)
  {
    int best;

    for (int k = 0; k < objCandidates.size(); k++)
    {
      best = k;

      for (int q = k + 1; q < objCandidates.size() - 1; q++)//if the fValue at q is less than the fValue at best--> sorts the nodes
      {
        if (objCandidates.get(q).f < objCandidates.get(best).f)
        {
          best = q;
        }
      }
      swapList(objCandidates, best, k); // swaps nodes

    }

    return objCandidates;
  }

  // swaps two nodes using a temp
  public void swapList(ArrayList<Node> candidatesObj, int best, int replace)
  {
    Node tempNode = candidatesObj.get(best);
    candidatesObj.set(best, candidatesObj.get(replace));
    candidatesObj.set(replace, tempNode);
  }

  // copy array to another new array
  public int[][] copyArray(int[][] state)
  {
    // double for loop to iterate through and copy to new array
    int[][] copyState = new int[state.length][state.length];
    for (int i = 0; i < copyState.length; i++)
    {
      for (int j = 0; j < copyState.length; j++)
      {
        copyState[i][j] = state[i][j];
      }

    }
    return copyState;
  }

  // calculate manhattan distance given state
  public static int manhattanDistance(int[][] state)
  {
    int distance = 0;
    for (int i = 0; i < 3; i++)
    {
      for (int j = 0; j < 3; j++)
      {
        // set x and y coordinates equal to loop vars
        int x1 = i;
        int y1 = j;
        // create new array with method of coordinates called 
        int[] array = coordinates(state[i][j]);
        int x2 = array[0];
        int y2 = array[1];
        // find the distance with the abs val of the x and y coordinates subtracted
        distance += Math.abs(x2 - x1) + Math.abs(y2 - y1);
      }
    }
    return distance;
  }

  // calculate coordinates 
  public static int[] coordinates(int x)
  {
    int[] array = new int[2];
    // iterate through to return the array at the end
    for (int i = 0; i < 3; i++)
    {
      for (int j = 0; j < 3; j++)
      {
        if (AStarAlgorithm.goalState[i][j] == x)
        {
          array[0] = i;
          array[1] = j;
        }
      }
    }
    return array;
  }

  //generate children nodes
  public ArrayList<Node> generateNodes()
  {
    // new arraylist for candidates
    ArrayList<Node> objCandidates = new ArrayList<>();

    // go through each member of the state until the blank is found
    for (int i = 0; i < state.length; i++)
    {
      //find blank in specific area
      for (int j = 0; j < state.length; j++)
      {
        // if the state is 0, find the possible candidates
        if (state[i][j] == 0)
        {
          objCandidates = findCandidates(state, previousRow, previousColumn, i, j);
          j = state.length;
        }
      }
    }

    // sort the candidates before returning the arraylist
    objCandidates = sortNodes(objCandidates);

    return objCandidates;
  }

  // expands the childrenNodes to find all the possible candidates for the next move
  public ArrayList<Node> findCandidates(int[][] state, int previousRow, int previousColumn, int currentRow, int currentColumn)
  {
    ArrayList<Node> objCandidates = new ArrayList<>();
    // sets up possible movements for if statements
    int leftColumn = currentColumn - 1;
    int rightColumn = currentColumn + 1;
    int upRow = currentRow - 1;
    int downRow = currentRow + 1;
    int[][] leftState = new int[state.length][state.length];
    int[][] rightState = new int[state.length][state.length];
    int[][] upState = new int[state.length][state.length];
    int[][] downState = new int[state.length][state.length];

    // left is a possible move
    if (leftColumn >= 0)
    {
      //creating a new state where the left column is switched
      leftState = copyArray(this.state);
      swapColumns(leftState, currentColumn, currentRow, leftColumn);
      Node objLeftNode = new Node(this.columnNo, this, this.g, leftState, currentRow, currentColumn);
      objLeftNode.associatedMove = "Left";

      //add new candidate w left node
      objCandidates.add(objLeftNode);
    }
    // up is a possible move
    if (upRow >= 0)
    {
      //creating a new state where the up row is switched
      upState = copyArray(this.state);
      swapRowCells(upState, upRow, currentRow, currentColumn);
      Node objUpNode = new Node(this.columnNo, this, this.g, upState, currentRow, currentColumn);
      objUpNode.associatedMove = "Up";

      //add to candidates
      objCandidates.add(objUpNode);
    }
    // right is a possible move
    if (rightColumn < this.columnNo)
    {
      //creating a new state where the right column is switched
      rightState = copyArray(this.state);
      swapColumns(rightState, currentColumn, currentRow, rightColumn);
      Node objRightNode = new Node(this.columnNo, this, this.g, rightState, currentRow, currentColumn);
      objRightNode.associatedMove = "Right";

      //add new candidate w right node
      objCandidates.add(objRightNode);
    }
    // down is a possible move
    if (downRow < this.columnNo)
    {
      downState = copyArray(this.state);
      swapRowCells(downState, downRow, currentRow, currentColumn);
      Node objDownNode = new Node(this.columnNo, this, this.g, downState, currentRow, currentColumn);
      objDownNode.associatedMove = "Down";

      //add to candidates
      objCandidates.add(objDownNode);
    }
    //return possible candidates
    return objCandidates;
  }

  //swaps cells of separate rows at specific indices
  private void swapRowCells(int[][] twoD, int rowIndexOne, int rowIndexTwo, int c)
  {
    //uses a temp value to swap corresponding indices of two rows
    int temp = twoD[rowIndexOne][c];
    twoD[rowIndexOne][c] = twoD[rowIndexTwo][c];
    twoD[rowIndexTwo][c] = temp;
  }

  //swaps columns
  public void swapColumns(int[][] newState, int currentColumn, int currentRow, int newColumn)
  {
    int temp = newState[currentRow][currentColumn];
    newState[currentRow][currentColumn] = newState[currentRow][newColumn];
    newState[currentRow][newColumn] = temp;
  }

}
