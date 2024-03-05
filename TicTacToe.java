//Note to reader
//Will be commenting out redundant code instead of removing them, just incase it is needed in the future

import java.util.*;

public class TicTacToe {

    public static void main(String[] args) {
        //Creating/Calling Instances
        AIplayer AI = new AIplayer();
        Board b = new Board();
        //Point p = new Point(0, 0);
        //Random rand = new Random();

        //Display the initial board
        b.displayBoard();

        //Asking the user who makes the first move
        System.out.println("Who makes first move? (1)Computer (2)User: ");
        //Scan taken from Board.java
        int choice = b.scan.nextInt();

        //If the computer makes the first move
        if (choice == 1) {
            //Calling the minimax algorithm to determine the best move for the AI player
            AI.callMinimax(0, 1, b);

            //Only for debugging(Helped alot, but remove if needed)
            for (PointsAndScores pas : AI.rootsChildrenScores) {
                System.out.println("Point: " + pas.point + " Score: " + pas.score);
            }

            //Place the move and update the board
            b.placeAMove(AI.returnBestMove(), 1);
            b.displayBoard();
        }

        //Game loop starts now
        while (!b.isGameOver()) {
            //Asking and scanning for the move
            System.out.println("Your move: line (1-5) column (1-5)");
            Point userMove = new Point(b.scan.nextInt()-1, b.scan.nextInt()-1);

            //Move validation
            while (b.getState(userMove) != 0) {
                System.out.println("Invalid move. Make your move again: ");
                userMove.x = b.scan.nextInt()-1;
                userMove.y = b.scan.nextInt()-1;
            }
            
            //Placing and displaying
            b.placeAMove(userMove, 2);
            b.displayBoard();

            //Simple break
            if (b.isGameOver()) {
                break;
            }

            //Same thing as before for the AI
            AI.callMinimax(0, 1, b);
            for (PointsAndScores pas : AI.rootsChildrenScores) {
                System.out.println("Point: " + pas.point + " Score: " + pas.score);
            }
            b.placeAMove(AI.returnBestMove(), 1);
            b.displayBoard();
        }
        //After break checking the game results
        if (b.hasXWon()) {
            System.out.println("Unfortunately, you lost!");
        } else if (b.hasOWon()) {
            System.out.println("You win!");
        } else {
            System.out.println("It's a draw!");
        }
    }
}