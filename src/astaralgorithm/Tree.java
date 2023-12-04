/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astaralgorithm;

import java.util.ArrayList;
import java.util.Arrays;

public class Tree
{

  //declaring attributes
  public Node root = null;
  public ArrayList<Node> pathsVisited = new ArrayList<>();
  public ArrayList<Node> leavesList = new ArrayList<>();

  // blank constructor
  public Tree()
  {
    this.root = null;
  }

  // swap the list
  public void swapList(ArrayList<Node> objCandidates, int best, int replace)
  {
    Node tempNode = objCandidates.get(best);
    objCandidates.set(best, objCandidates.get(replace));
    objCandidates.set(replace, tempNode);
  }

  // overload the constructor
  public Tree(int[][] state)
  {
    int rowLength = state.length;
    Node objTreeNode = new Node(rowLength, null, 0, state, -1, -1);
    this.root = objTreeNode;
  }

  // initialize the tree
  public void initializeTree()
  {
    // declares initial variables
    Node selectedState = new Node();
    Node goalNode = new Node();
    boolean goalFound = false;

    // if the root value itself the goal, state that it is found
    if (this.root.h == 0)
    {
      System.out.println("solution has been found");
      goalFound = true;
    }
    // else for adding the next node
    else 
    {
      pathsVisited.add(root);
      selectedState = root;
    }
    //until the goal node is reached
    while (goalFound == false)
    {
      //declare all necessary data structures
      ArrayList<Node> objCandidates = new ArrayList<>();
      ArrayList<Node> trueCandidates = new ArrayList<>();
      objCandidates = selectedState.generateNodes();
      trueCandidates = optimizeCandidates(objCandidates);
      leavesList.addAll(trueCandidates);
      // sorts the leaves before adding new ones for optimization with selection sort
      leavesList = root.sortNodes(leavesList);
      // chooses the first node as the selected state
      selectedState = leavesList.get(0);
      // once the manhattan distance is zero then the solution node has been found
      if (selectedState.h == 0)
      {
        goalFound = true;
        goalNode = selectedState;
        System.out.println("Reached the goal state!");
      }
      // moves the current node from the leaves list and makes it path visited
      else 
      {
        leavesList.remove(0);
        pathsVisited.add(selectedState);
      }
    }
    // print path and number of moves
    findPath(goalNode, "");

  }

  //recursion to find the path and keep on adding to the string until it reaches the root (null)
  public void findPath(Node node, String moves)
  {
    if (node.parentNode == null)
    {
      int counter = 0;
      System.out.println(moves);
      // print the number of moves for each time there is a comma
      for (int i = 0; i < moves.length(); i++)
      {
        if (moves.charAt(i) == ',')
        {
          counter++;
        }
      }
      System.out.println("Number of moves: " + counter);
    }
    else
    {
      findPath(node.parentNode, node.associatedMove + ", " + moves);
    }
  }

  // optimize the candidates
  public ArrayList<Node> optimizeCandidates(ArrayList<Node> objCandidates)
  {
    // the final candidates that haven't been visited
    ArrayList<Node> trueCandidates = new ArrayList<>();

    // compares values between available tree nodes and path visited
    for (int i = 0; i < objCandidates.size(); i++)
    {
      boolean inVisited = false;

      for (int j = 0; j < pathsVisited.size(); j++)
      {
        //if state is already in paths visited --> doesn't use that state as a candidate
        if ((Arrays.deepEquals(objCandidates.get(i).state, pathsVisited.get(j).state)))
        {
          inVisited = true;
        }
      }
      //adds state as a candidate if it is a new path
      if (inVisited == false)
      {
        trueCandidates.add(objCandidates.get(i));
      }
    }
    
    return trueCandidates;
  }
}
