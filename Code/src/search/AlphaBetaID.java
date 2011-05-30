package search;

import java.lang.System;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Timer;

import agent.*;

public class AlphaBetaID extends Thread {

  NodeInfo nodeInfo;
  Node start;
  public Action best;
  long startTime;
  long timeToRun;
  int plyCount = 0;
  ArrayList<Action> badActs;
  
  public AlphaBetaID (NodeInfo nodeInfo, Node start, long timeToRun) {
    this.nodeInfo = nodeInfo;
    this.start = start;
    this.best = null;
    startTime = System.currentTimeMillis();
    this.timeToRun = timeToRun;
    badActs = new ArrayList<Action>();
  }
  

  @Override
  /**
   * Runs the test for the given time setting the value of "best" to 
   * the best action found in the last completed search from decision
   */
  public void run() {
	  nodeInfo.setDepthLimit(2);
	  while((nodeInfo.getDepthLimit() <= 16)) {
		  Action nextBest;
		  plyCount = 0;
		  nextBest = decision(start);
		  //System.out.println("Maximum depth of this search " + plyCount);
		  if(System.currentTimeMillis() - startTime > timeToRun) break;
		  best = nextBest;
		  Node clone = (Node)start.clone();
		  clone.update(best);
		  if(nodeInfo.utility(clone) < -0.5) best = null;
		  nodeInfo.setDepthLimit(nodeInfo.getDepthLimit() + 1);
	  }
  }
  
  /**
   * Returns the best action to be made based on a minimax search
   * Links the utilities found back to a usable action
   * @param start - the root node
   * @return the best action found
   */
  public Action decision(Node start) {
	  Action bestAction;
	  double bestValue;
	  ListIterator li;
	  Actions acts = start.getState().getActions();
	  if(acts.removeAll(badActs)) System.out.println("Removed some bad actions");
	  
	  li = start.getState().getActions().listIterator();
	  bestAction = (Action)start.getState().getActions().get(0);
	  bestValue = Double.NEGATIVE_INFINITY;
	  
	  Actions equalActionsPool = new Actions();
	  
	  while(li.hasNext()) {
		  if(System.currentTimeMillis() - startTime > timeToRun) break;
		  Node nodeclone = (Node)start.clone();
		  Action nextAction = (Action)li.next();
		  nodeclone.update(nextAction);
		  double maxVal;
		  //plyCount = 0;
		  maxVal = minValue(nodeclone, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		  if(maxVal < -0.5) badActs.add(nextAction);
		  if(maxVal > bestValue) {
			  equalActionsPool = new Actions();
			  bestValue = maxVal;
			  bestAction = nextAction;
		  } else if(maxVal == bestValue)
			  equalActionsPool.add(nextAction);
	  }
	  if(equalActionsPool.size() > 1)
		  return (Action)equalActionsPool.get((int)(Math.random()*equalActionsPool.size()));
	  else
		  return bestAction;
  }
  
  /**
   * @return the highest value Max can achieve at this node with optimal play
   */
  public double maxValue (Node visit, double alpha, double beta) {
    ListIterator li;
    Action arc;
    Node child;
    if (nodeInfo.isTerminal(visit)) {
    	return nodeInfo.utility(visit);
    }
    else {
    	li = visit.getState().getActions().listIterator();
      while (li.hasNext()) {
    	  if(System.currentTimeMillis() - startTime > timeToRun) break;
    	  arc = (Action)li.next();
    	  child = (Node)visit.clone();
    	  child.update(arc);
    	  if(child.getPath().size() > plyCount) plyCount = child.getPath().size();
    	  		
	    	  alpha = Math.max(alpha, minValue(child, alpha, beta));
	    	  
	    	  if(alpha >= beta) return beta;

      }
      return alpha;
    }
  }

  
  /**
   * @return the lowest value Min can achieve at this node with optimal play
   */
  public double minValue (Node visit, double alpha, double beta) {
    ListIterator li;
    Action arc;
    Node child;
    if (nodeInfo.isTerminal(visit)) {
    	return nodeInfo.utility(visit);
    }
    else {
    	li = visit.getState().getActions().listIterator();
      while (li.hasNext()) {
    	  if(System.currentTimeMillis() - startTime > timeToRun) break;
    	  arc = (Action)li.next();
    	  child = (Node)visit.clone();
    	  child.update(arc);
    	  if(child.getPath().size() > plyCount) plyCount = child.getPath().size();
	    	  beta = Math.min(beta, maxValue(child, alpha, beta));
	    	  if(beta <= alpha) return alpha;
      }
      return beta;
    }
  }


}