The project is set up very similarly to the sample code from the labs for developing
cheX AI and similar. We were always running out of Eclipse so you'll have to link in
the agent-3.4.jar from the labs to get it to work.

It implements the search package where you can find our 3 searches:
AlphaBeta - the "perfect" search
AlphaBetaID - iterative deepening with move ordering
AlphaBetaIDwithSymmetry - iterative deepening with move ordering and symmetry elimination

The players implementing these searches are (respectively):
AlphaBetaPlayer
AlphaBetaIDPlayer
SymmetryPlayer

There is also a one-ply agent called NoMistakeOnePly that was used for testing and
development as well as a random agent (RandomRob)

The file QPlay.java behaves similarly to Play.java from Lab6
You can play any two AIs together by running them through testGame. The first and
second player must be different instances and must have their "isOne" argument in their
constructors set to the appropriate value for their invocation in testGame. There is no
rigerous validation of values through this so you'll have to be careful sometimes :)

If you want to play as a human player it's text based input/output through stdout (sorry).
Just add the player "HumanPlayer" to the game. It will prompt for a move for which the format
is:
x,y piece
where x and y are grid coordinates described in the output and piece is a string representation
of a piece where upper and lower case represents opposite values of the same attribute.
B,b for black, white
R,r for round, square
S,s for solid, hollow
T,t for tall, short

eg a white, round, solid, short piece would be bRSt

The output will display which pieces are left unplayed

Once again, no rigerous error checking so make sure you're typing in a valid piece
Sorry if it's a bit confusing to look at