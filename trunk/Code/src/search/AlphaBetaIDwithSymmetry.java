package search;

import java.lang.System;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Timer;

import quarto.QBoard;

import agent.*;

public class AlphaBetaIDwithSymmetry extends Thread {

  NodeInfo nodeInfo;
  ArrayList<Node> visited;
  Node start;
  public Action best;
  long startTime;
  long timeToRun;
  int plyCount = 0;
  ArrayList<Action> badActs;
  
  public AlphaBetaIDwithSymmetry (NodeInfo nodeInfo, Node start, long timeToRun) {
    this.nodeInfo = nodeInfo;
    visited = new ArrayList<Node>();
    this.start = start;
    this.best = null;
    startTime = System.currentTimeMillis();
    this.timeToRun = timeToRun;
    badActs = new ArrayList<Action>();
  }
  
  @Override
  public void run() {
	  nodeInfo.setDepthLimit(2);
	  //Action best = null;
	  while((nodeInfo.getDepthLimit() <= 16)) {
		  Action nextBest;
		  plyCount = 0;
		  nextBest = Decision(start);
		  System.out.println("Maximum depth of this search " + plyCount);
		  if(System.currentTimeMillis() - startTime > timeToRun) break;
		  best = nextBest;
		  Node clone = (Node)start.clone();
		  clone.update(best);
		  if(nodeInfo.utility(clone) < -0.5) best = null;
		  //System.out.println("Utility of best: " + nodeInfo.utility(clone));
		  nodeInfo.setDepthLimit(nodeInfo.getDepthLimit() + 1);
	  }
  }
  
  
  //Assumes it is max's turn to move first
  public Action Decision(Node start) {
	  //System.out.println("Starting MINIMAX");
	  Action bestAction;
	  double bestValue;
	  ListIterator li;
	  //Actions orderedActs = ((PlayerNodeInfo)nodeInfo).orderActions(start);
      //li = orderedActs.listIterator();
	  Actions acts = start.getState().getActions();
	  if(acts.removeAll(badActs)) System.out.println("Removed some bad actions");
	  //System.out.println("Found " + acts.size() + " moves");
	  li = symmetryElimination(start).listIterator();
	  bestAction = (Action)start.getState().getActions().get(0);
	  bestValue = Double.NEGATIVE_INFINITY;
	  
	  Actions equalActionsPool = new Actions();
	  
	  while(li.hasNext()) {
		  if(System.currentTimeMillis() - startTime > timeToRun) break;
		  Node nodeclone = (Node)li.next();
		  double maxVal;
		  //System.out.println("Top level: Test Action");
		  //plyCount = 0;
		  maxVal = minValue(nodeclone, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		  Action nextAction = (Action)nodeclone.getPath().get(0);
		  if(maxVal < -0.5) badActs.add(nextAction);
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
	  Thread.yield();
    ListIterator li;
    Action arc;
    Node child;
    double childValue;
    if (nodeInfo.isTerminal(visit)) {
    	//System.out.println("Terminating at depth = " + visit.getPath().size());
    	return nodeInfo.utility(visit);
    }
    else {
    	//Actions orderedActs = ((PlayerNodeInfo)nodeInfo).orderActions(visit);
        //li = orderedActs.listIterator();
    	li = visit.getState().getActions().listIterator();
      int count = 0;
      while (li.hasNext()) {
    	  if(System.currentTimeMillis() - startTime > timeToRun) break;
    	  //System.out.println("Max: Testing Action " + count);
    	  arc = (Action)li.next();
    	  child = (Node)visit.clone();
    	  child.update(arc);
    	  if(child.getPath().size() > plyCount) plyCount = child.getPath().size();
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
	  Thread.yield();
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
    	  if(System.currentTimeMillis() - startTime > timeToRun) break;
    	  //System.out.println("        Min: Testing Action " + count);
    	  arc = (Action)li.next();
    	  child = (Node)visit.clone();
    	  child.update(arc);
    	  if(child.getPath().size() > plyCount) plyCount = child.getPath().size();
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
  
  public ArrayList<Node> symmetryElimination(Node visit) {
	  ListIterator li;
	    Action arc;
	    Node child;
	  Actions acts = visit.getState().getActions();
  	li = visit.getState().getActions().listIterator();
  	ArrayList<Node> nodes = new ArrayList<Node>();
  	while(li.hasNext()) {
  		arc = (Action)li.next();
    	  	child = (Node)visit.clone();
    	  	child.update(arc);
    	  	QBoard childBoard = (QBoard)child.getState();
    	  	boolean equivalent = false;
    	  	for(Node n: nodes) {
    	  		QBoard board = (QBoard)n.getState();
    	  		if(childBoard.equivalence(board)) {
    	  			equivalent = true;
    	  			break;
    	  		}
    	  	}
    	  	if(!equivalent) nodes.add(child);
  	}
  	return nodes;
  }


}