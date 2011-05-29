package search;

import java.lang.System;

import java.util.ArrayList;
import java.util.ListIterator;
import agent.*;

public class AlphaBeta {

  NodeInfo nodeInfo;
  ArrayList<Node> visited;

  public AlphaBeta (NodeInfo nodeInfo) {
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
  public Action Decision(Node start) {
	  //System.out.println("Starting MINIMAX");
	  Action bestAction;
	  double bestValue;
	  ListIterator li;
	  //Actions orderedActs = ((PlayerNodeInfo)nodeInfo).orderActions(start);
      //li = orderedActs.listIterator();
	  Actions acts = start.getState().getActions();
	  //System.out.println("Found " + acts.size() + " moves");
	  li = start.getState().getActions().listIterator();
	  bestAction = (Action)start.getState().getActions().get(0);
	  bestValue = Double.NEGATIVE_INFINITY;
	  
	  Actions equalActionsPool = new Actions();
	  
	  while(li.hasNext()) {
		  
		  Node nodeclone = (Node)start.clone();
		  Action nextAction = (Action)li.next();
		  nodeclone.update(nextAction);
		  double maxVal;
		  //System.out.println("Top level: Test Action");
		  maxVal = minValue(nodeclone, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		  //System.out.println("Top level: recieved max of " + maxVal);
		  //System.out.println("Found move with utility: " + maxVal);
		  if(maxVal > bestValue) {
			  equalActionsPool = new Actions();
			  bestValue = maxVal;
			  //System.out.println("Found better move. Value: " + newBest);
			  bestAction = nextAction;
		  } else if(maxVal == bestValue)
			  equalActionsPool.add(nextAction);
	  }
	  //System.out.println("Best utility found is: " + bestValue);
	  if(equalActionsPool.size() > 1)
		  return (Action)equalActionsPool.get((int)(Math.random()*equalActionsPool.size()));
	  else
		  return bestAction;
  }
  
  /**
   * @return the highest value Max can achieve at this node with optimal play
   */
  public double maxValue (Node visit, double alpha, double beta) {
    //double maxSoFar = Double.NEGATIVE_INFINITY;
    ListIterator li;
    Action arc;
    Node child;
    double childValue;
    if (nodeInfo.isTerminal(visit)) {
    	return nodeInfo.utility(visit);
    }
    else {
    	//Actions orderedActs = ((PlayerNodeInfo)nodeInfo).orderActions(visit);
        //li = orderedActs.listIterator();
    	li = visit.getState().getActions().listIterator();
      int count = 0;
      while (li.hasNext()) {
    	  //System.out.println("Max: Testing Action " + count);
    	  arc = (Action)li.next();
    	  child = (Node)visit.clone();
    	  child.update(arc);
    	  //if(!visited.contains(child)) {
    	  		
	    	  alpha = Math.max(alpha, minValue(child, alpha, beta));
	    	  
	    	  if(alpha >= beta) return beta;
	    	  //if(alpha > maxSoFar) maxSoFar = alpha;
	    	  //System.out.println("Max: got child value: " + childValue);
	    	 // visited.add(child);
    	 // }
    	  count++;
      }
      return alpha;
    }
  }

  
  /**
   * @return the lowest value Min can achieve at this node with optimal play
   */
  public double minValue (Node visit, double alpha, double beta) {
    //double minSoFar = Double.POSITIVE_INFINITY;
    ListIterator li;
    Action arc;
    Node child;
    double childValue;
    if (nodeInfo.isTerminal(visit)) {
    	return nodeInfo.utility(visit);
    }
    else {
    	//Actions orderedActs = ((PlayerNodeInfo)nodeInfo).orderActions(visit);
      //li = orderedActs.listIterator();
    	li = visit.getState().getActions().listIterator();
      int count = 0;
      while (li.hasNext()) {
    	  //System.out.println("        Min: Testing Action " + count);
    	  arc = (Action)li.next();
    	  child = (Node)visit.clone();
    	  child.update(arc);
    	  //if(!visited.contains(child)) {
    	  		
	    	  beta = Math.min(beta, maxValue(child, alpha, beta));
	    	  if(beta <= alpha) return alpha;
	    	  //if(minSoFar > beta) minSoFar = beta;
	    	  //visited.add(child);
    	  //}
    	  count++;
      }
      return beta;
    }
  }
}