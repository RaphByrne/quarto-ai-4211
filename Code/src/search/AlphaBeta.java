package search;


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
	  li = start.getState().getActions().listIterator();
	  bestAction = (Action)start.getState().getActions().get(0);
	  bestValue = Double.NEGATIVE_INFINITY;
	  
	  Actions equalActionsPool = new Actions();
	  
	  while(li.hasNext()) {
		  
		  Node nodeclone = (Node)start.clone();
		  Action nextAction = (Action)li.next();
		  nodeclone.update(nextAction);
		  double maxVal;

		  maxVal = minValue(nodeclone, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);

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
      int count = 0;
      while (li.hasNext()) {

    	  arc = (Action)li.next();
    	  child = (Node)visit.clone();
    	  child.update(arc);

    	  		
	    	  alpha = Math.max(alpha, minValue(child, alpha, beta));
	    	  
	    	  if(alpha >= beta) return beta;

    	  count++;
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
      int count = 0;
      while (li.hasNext()) {

    	  arc = (Action)li.next();
    	  child = (Node)visit.clone();
    	  child.update(arc);

    	  		
	    	  beta = Math.min(beta, maxValue(child, alpha, beta));
	    	  if(beta <= alpha) return alpha;

    	  count++;
      }
      return beta;
    }
  }
}