package search;

import java.lang.System;

import java.util.ArrayList;
import java.util.ListIterator;

import agent.*;

public class Minimax {

  NodeInfo nodeInfo;
  ArrayList<Node> visited;

  public Minimax (NodeInfo nodeInfo) {
    this.nodeInfo = nodeInfo;
    visited = new ArrayList<Node>();
  }

  /*
  public double minimaxValue(Node node, boolean maxToMove) {
	  if(nodeInfo.isTerminal(node)) return nodeInfo.utility(node);
	  else if(maxToMove) {
		  return
	  }
  }
 */
  //Assumes it is max's turn to move first
  public Action decision(Node start) {
	  //System.out.println("Starting MINIMAX");
	  Action bestAction;
	  double bestValue;
	  ListIterator li;
	  li = start.getState().getActions().listIterator();
	  bestAction = (Action)start.getState().getActions().get(0);
	  bestValue = Double.NEGATIVE_INFINITY;
	  
	  while(li.hasNext()) {
		  
		  Node nodeclone = (Node)start.clone();
		  Action nextAction = (Action)li.next();
		  nodeclone.update(nextAction);
		  double newBest;
		  double maxVal;
		  //System.out.println("Top level: Test Action");
		  maxVal = maxValue(nodeclone, 0);
		  //System.out.println("Top level: recieved max of " + maxVal);
		  if(maxVal > bestValue) {
			  bestValue = maxVal;
			  //System.out.println("Found better move. Value: " + newBest);
			  bestAction = nextAction;
		  }
	  }
	  return bestAction;
  }
  
  /**
   * @return the highest value Max can achieve at this node with optimal play
   */
  public double maxValue (Node visit, double depth) {
    double maxSoFar = Double.NEGATIVE_INFINITY;
    ListIterator li;
    Action arc;
    Node child;
    double childValue;
    if (nodeInfo.isTerminal(visit) || visit.getPath().size() > nodeInfo.getDepthLimit()) {
    	return nodeInfo.utility(visit);
    }
    else {
      li = visit.getState().getActions().listIterator();
      double maxValue = Double.NEGATIVE_INFINITY;
      int count = 0;
      while (li.hasNext()) {
    	  //System.out.println("Max: Testing Action " + count);
    	  arc = (Action)li.next();
    	  child = (Node)visit.clone();
    	  child.update(arc);
    	  if(!visited.contains(child)) {
	    	  childValue = minValue(child, depth+1);
	    	  if(childValue > maxSoFar) maxSoFar = childValue;
	    	  //System.out.println("Max: got child value: " + childValue);
	    	  visited.add(child);
    	  }
    	  count++;
      }
      return maxSoFar;
    }
  }

  /**
   * @return the lowest value Min can achieve at this node with optimal play
   */
  public double minValue (Node visit, double depth) {
    double minSoFar = Double.POSITIVE_INFINITY;
    ListIterator li;
    Action arc;
    Node child;
    double childValue;
    if (nodeInfo.isTerminal(visit) || visit.getPath().size() > nodeInfo.getDepthLimit()) {
    	return nodeInfo.utility(visit);
    }
    else {
      li = visit.getState().getActions().listIterator();
      int count = 0;
      while (li.hasNext()) {
    	  //System.out.println("        Min: Testing Action " + count);
    	  arc = (Action)li.next();
    	  child = (Node)visit.clone();
    	  child.update(arc);
    	  if(!visited.contains(child)) {
	    	  childValue = maxValue(child, depth+1);
	    	  if(minSoFar > childValue) minSoFar = childValue;
	    	  visited.add(child);
    	  }
    	  count++;
      }
      return minSoFar;
    }


  }

}