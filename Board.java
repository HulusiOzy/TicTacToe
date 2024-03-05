//Note to reader
//Will be commenting out redundant code instead of removing them, just incase it is needed in the future

import java.util.*;

class Point {
    int x, y;

    //Constructor
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    //Displaying the currect point in a readable format
    public String toString() {
        return "[" + (x+1) + ", " + (y+1) + "]";
    }
}

//Class to relate a score and a point
class PointsAndScores {
    int score;
    Point point;

    PointsAndScores(int score, Point point) {
        this.score = score;
        this.point = point;
    }
}

//Class for the game board
class Board { 
    List<Point> availablePoints;
    Scanner scan = new Scanner(System.in);
    int[][] board = new int[5][5];

    //Default constructor for the board
    public Board() {
    }

    //Calling hasXwon / hasOwon / checking if points avalible with or's
    public boolean isGameOver() {
        return (hasXWon() || hasOWon() || getAvailablePoints().isEmpty());
    }


    //Checking if X won
    public boolean hasXWon() {
        //Tried doing it more efficently, idk if another way is possible

        //Check for horizontal wins
        for (int i = 0; i < 5; ++i) {
            if (board[i][0] == 1 && board[i][1] == 1 && board[i][2] == 1 && board[i][3] == 1 && board[i][4] == 1) {
                return true;
            }
        }
        
        //Check for vertical wins
        for (int i = 0; i < 5; ++i) {
            if (board[0][i] == 1 && board[1][i] == 1 && board[2][i] == 1 && board[3][i] == 1 && board[4][i] == 1) {
                return true;
            }
        }
        
        //Check for diagonal wins
        if ((board[0][0] == 1 && board[1][1] == 1 && board[2][2] == 1 && board[3][3] == 1 && board[4][4] == 1) ||
            (board[0][4] == 1 && board[1][3] == 1 && board[2][2] == 1 && board[3][1] == 1 && board[4][0] == 1)) {
            return true;
        }
        
        return false;
    }

    //Checking if Y won
    public boolean hasOWon() {
        //Tried doing it more efficently, idk if another way is possible

        //Check for horizontal wins
        for (int i = 0; i < 5; ++i) {
            if (board[i][0] == 2 && board[i][1] == 2 && board[i][2] == 2 && board[i][3] == 2 && board[i][4] == 2) {
                return true;
            }
        }
        
        //Check for vertical wins
        for (int i = 0; i < 5; ++i) {
            if (board[0][i] == 2 && board[1][i] == 2 && board[2][i] == 2 && board[3][i] == 2 && board[4][i] == 2) {
                return true;
            }
        }
        
        //Check for diagonal wins
        if ((board[0][0] == 2 && board[1][1] == 2 && board[2][2] == 2 && board[3][3] == 2 && board[4][4] == 2) ||
            (board[0][4] == 2 && board[1][3] == 2 && board[2][2] == 2 && board[3][1] == 2 && board[4][0] == 2)) {
            return true;
        }
        
        return false;
    }

    //Getting avalible points on the board
    public List<Point> getAvailablePoints() {
        availablePoints = new ArrayList<>();
        //All it does is iterate over the board to find empty spots
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                if (board[i][j] == 0) {
                    availablePoints.add(new Point(i, j));
                }
            }
        }
        return availablePoints;
    }
    
    //Method to get the state of a singular point
    public int getState(Point point){
    	return board[point.x][point.y];
    }

    //Method to place a move on the board for a player
    public void placeAMove(Point point, int player) {
        board[point.x][point.y] = player;   
    }

    //Board display
    public void displayBoard() {
        System.out.println();
    
        //Iterating over the boards rows and colunms
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                if (board[i][j] == 1)
                    System.out.print("X ");
                else if (board[i][j] == 2)
                    System.out.print("O ");
                else
                    System.out.print(". ");
            }
            System.out.println();
        }
    }
}
