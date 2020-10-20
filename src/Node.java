// Chase Grainger, Analysis of Algorithms
// 
// 'Node.java'
//
// This file contains the class declaration for a node,
// used within queue for this implementation of Lee's Algorithm.

public class Node {
	int x, y, dist, neighborIndex;

	Node(int x, int y, int dist, int neighborIndex) {
		this.x = x;
		this.y = y;
		this.dist = dist;
		this.neighborIndex = neighborIndex;
	}
}
