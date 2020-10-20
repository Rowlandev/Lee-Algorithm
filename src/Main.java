// Chase Grainger, Analysis of Algorithms
// 
// 'Main.java'
//
// This file contains the main starting point for this implementation
// of Lee's Algorithm.

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.abs;

class Main {
	private static final int n = 3;
	static int[][] maze = getMaze(n);
	static int startX = 0;
	static int startY = 0;
	static int destinationX = 1;
	static int destinationY = 2;
	static int minDistance;

	// Declare possible direction movements for a cell [left down, up, right]
	private static final int[] row = { -1, 0, 0, 1 }; // Can move left or right
	private static final int[] col = { 0, -1, 1, 0 }; // Can move down or up
	
	/* For given value of n, return corresponding maze containing no obstacles */
	private static int[][] getMaze(int n) {
		int[][] arr = new int[n][n];
		Arrays.stream(arr).forEach(cell -> Arrays.fill(cell, 1));
		return arr;
	}

	/* Return true if possible to go to position from current cell */
	private static boolean isValid(int maze[][], boolean visited[][], int row, int col) {
		return (row >= 0) && (row < n) && (col >= 0) && (col < n)
						&& maze[row][col] == 1 && !visited[row][col];
	}
	
	/* Find shortest path in matrix from start to destination cell */
	private static ArrayList<Node> getVisitedCells(int maze[][], int i, int j, int x, int y) {
		
		// Array list to track visited cells for backtracking
		ArrayList<Node> visitedCells = new ArrayList<Node>();
		
		// Create matrix to track visited cells
		boolean[][] visited = new boolean[n][n];
		
		// Mark start cell as visited
		visited[i][j] = true;

		// Create empty queue and enqueue starting cell
		Queue<Node> q = new ArrayDeque<>();
		q.add(new Node(i, j, 0, 0));

		// Initialize to store shortest distance from start to destination cell
		int min_dist = Integer.MAX_VALUE;

		// Continue while queue still has items
		while (!q.isEmpty()) {
			// Remove and return node in front of queue
			Node node = q.poll();

			// Get information from front of queue
			i = node.x;
			j = node.y;
			int dist = node.dist;

			// If destination node is found
			if (i == x && j == y) {
				// Update minimum distance from start to destination node
				min_dist = dist;
				break;
			}


			// Enqueue each valid cell from current position
			for (int k = 0; k < 4; k++) {
				if (isValid(maze, visited, i + row[k], j + col[k])) {
					// Mark next, valid cell as visited and enqueue it
					int newX = i + row[k];
					int newY = j + col[k];
					int newDistance = dist + 1;
					int neighborIndex = getNeighborIndex(newX, newY);
					visited[newX][newY] = true;
					Node newNode = new Node(newX, newY, newDistance, neighborIndex);
					visitedCells.add(newNode);
					q.add(newNode);
				}
			}
		}
		
		// Update class property for minDistance if route was found
		if (min_dist != Integer.MAX_VALUE) { minDistance = min_dist; }
		
		// Return all visited cells from start to destination cell or NULL if path couldn't be found
		return visitedCells;
	}
	
	/* Returns neighbor index of given cell */
	private static int getNeighborIndex(int x, int y) {
		return abs((x + y) - (startX + startY));
	}

	/* Return shortest path from start to destination cell */
	private static ArrayList<Node> backtrack(ArrayList<Node> visitedCells) {
		
		// Add start cell to shortest path
		ArrayList<Node> shortestPath = new ArrayList<Node>();
		shortestPath.add(new Node(startX, startY, 0, 0));
		
		// Add cells in route to destination cell
		int index = 0;
		for (int i = 0; i < visitedCells.size(); i++) {
			Node node = visitedCells.get(i);
			// If destination cell was found
			if (node.x == destinationX && node.y == destinationY) {
				break;
			} else if (node.neighborIndex == index + 1) {
				shortestPath.add(node);
				index += 1;
			}
		}
		
		shortestPath.add(new Node(destinationX, destinationY, 0, 0));
		return shortestPath;
	}
	
	/* Main starting point of program */
	public static void main(String[] args) {
		ArrayList<Node> visitedCells = getVisitedCells(maze, startX, startY, destinationX, destinationY);
		ArrayList<Node> shortestPath = backtrack(visitedCells);
		
		// Print information for shortest path
		System.out.println("The distance from (" + startX + ", " + startY + ") to (" + destinationX + ", " + destinationY + ") is " + minDistance + ".");
		System.out.print("The shortest path is: ");
		for (int i = 0; i < shortestPath.size(); i++) {
			Node node = shortestPath.get(i);
			System.out.print("(" + node.x + ", " + node.y + ")");
			if (i != shortestPath.size() - 1) { System.out.print(" -> "); }
		}
	}
}