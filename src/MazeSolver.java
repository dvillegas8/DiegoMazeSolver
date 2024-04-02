/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam
 * @version 03/10/2023
 */
// Diego Villegas
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     * @return An arraylist of MazeCells to visit in order
     */
    public ArrayList<MazeCell> getSolution() {
        // TODO: Get the solution from the maze
        // Should be from start to end cells
        ArrayList<MazeCell> solution = new ArrayList<MazeCell>();
        Stack<MazeCell> cells = new Stack<MazeCell>();
        cells.push(maze.getEndCell());
        // Continue adding parents until Start cell is the parent
        while(!cells.peek().getParent().equals(maze.getStartCell())){
            cells.push(cells.peek().getParent());
        }
        // Add start cell
        cells.push(maze.getStartCell());
        // Creates solution starting from the start cell
        while(!cells.empty()){
            solution.add(cells.pop());
        }
        return solution;
    }

    /**
     * Performs a Depth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS() {
        Stack <MazeCell> cells = new Stack<MazeCell>();
        ArrayList<MazeCell> solution = new ArrayList<MazeCell>();
        MazeCell current = maze.getStartCell();
        // Keep going through the maze until we reach the end cell
        while(!current.equals(maze.getEndCell())){
            // Check North
            checkNeighborStack(cells, current, current.getRow() - 1, current.getCol());
            // Check East
            checkNeighborStack(cells, current, current.getRow(), current.getCol() + 1);
            // Check South
            checkNeighborStack(cells, current, current.getRow() + 1, current.getCol());
            // Check West
            checkNeighborStack(cells, current, current.getRow(), current.getCol() - 1);
            // Update current cell from Stack
            current = cells.pop();
        }
        return getSolution();
    }

    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS() {
        // TODO: Use BFS to solve the maze
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        Queue<MazeCell> cells = new LinkedList<MazeCell>();
        MazeCell current = maze.getStartCell();
        // Keep going through the maze until we reach the end cell
        while(!current.equals(maze.getEndCell())) {
            // Check North
            checkNeighborQueue(cells, current, current.getRow() - 1, current.getCol());
            // Check East
            checkNeighborQueue(cells, current, current.getRow(), current.getCol() + 1);
            // Check South
            checkNeighborQueue(cells, current, current.getRow() + 1, current.getCol());
            // Check West
            checkNeighborQueue(cells, current, current.getRow(), current.getCol() - 1);
            // Update current cell from Queue
            current = cells.remove();

        }
        return this.getSolution();
    }
    // Checks the neighbor tile for BFS, if it is valid, then update information and add to queue
    public void checkNeighborQueue(Queue<MazeCell> cells, MazeCell current, int row, int col){
        if (maze.isValidCell(row, col)) {
            cells.add(maze.getCell(row, col));
            maze.getCell(row, col).setExplored(true);
            maze.getCell(row, col).setParent(current);
        }
    }
    // Checks the neighbor tile for DFS, if it is valid, then update information and add to Stack
    public void checkNeighborStack(Stack<MazeCell> cells, MazeCell current, int row, int col){
        if (maze.isValidCell(row, col)) {
            cells.add(maze.getCell(row, col));
            maze.getCell(row, col).setExplored(true);
            maze.getCell(row, col).setParent(current);
        }
    }

    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);

        // Reset the maze
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}
