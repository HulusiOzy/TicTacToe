//Note to reader
//Will be commenting out redundant code instead of removing them, just incase it is needed in the future

import java.util.*;


class AIplayer { 
    //List<Point> availablePoints;
    List<PointsAndScores> rootsChildrenScores;
    Board b = new Board();
    
    //Default
    public AIplayer() {
    }
    
    //Method to return the best move to be made according to calculations
    public Point returnBestMove() {
        int MAX = -100000;
        int best = -1;

        //Just go through the list of moves and select one with the higest score
        for (int i = 0; i < rootsChildrenScores.size(); ++i) { 
            if (MAX < rootsChildrenScores.get(i).score) {
                MAX = rootsChildrenScores.get(i).score;
                best = i;
            }
        }
        return rootsChildrenScores.get(best).point;
    }

    /*
    //Returning the minimum value in the list
    //Might be redundant after changes
    public int returnMin(List<Integer> list) {
        int min = Integer.MAX_VALUE;
        int index = -1;
        
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) < min) {
                min = list.get(i);
                index = i;
            }
        }
        return list.get(index);
    }
    */

    /*
    //Returning the maximum value in the list
    //Might be redundant after changes
    public int returnMax(List<Integer> list) {
        int max = Integer.MIN_VALUE;
        int index = -1;
        
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) > max) {
                max = list.get(i);
                index = i;
            }
        }
        return list.get(index);
    } 
    */
 
    //Calling the minimax process
    public void callMinimax(int depth, int turn, Board b){
        //Store the scores and the corresponding moves for the roots children of the game
        rootsChildrenScores = new ArrayList<>();
        //Start the minimax with initial alpha/beta values
        minimax(depth, turn, b, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    //minimax with beta pruning
    public int minimax(int depth, int turn, Board b, int alpha, int beta) {
        int maxDepth = 10; //Max Depth
    
        //Return Heuristic if max depth is reached
        if (depth == maxDepth) {
            return evaluateHeuristic(b);
        }
    
        //Board win loose check
        if (b.hasXWon()) return 1;
        if (b.hasOWon()) return -1;
    
        //Get the list of avalible points
        List<Point> pointsAvailable = b.getAvailablePoints();
        //Draw check
        if (pointsAvailable.isEmpty()) return 0;
    
        // X's turn (maximizing player)
        if (turn == 1) {
            int maxScore = Integer.MIN_VALUE;
            //Go through all avalible points
            for (int i = 0; i < pointsAvailable.size(); ++i) {
                Point point = pointsAvailable.get(i);
                //Place X's move on the board
                b.placeAMove(point, 1);

                //Keep on calling minimax for opponents turn
                int currentScore = minimax(depth + 1, 2, b, alpha, beta);
                //Undo the move
                b.placeAMove(point, 0);
                //Update the max score
                maxScore = Math.max(maxScore, currentScore);
                //Update alpha
                alpha = Math.max(alpha, maxScore);
                //If at the root level, add the score and move to the list
                if (depth == 0) {
                    rootsChildrenScores.add(new PointsAndScores(currentScore, point));
                }
                //If beta is less than or equal to alpha, break the loop. (Alpha Beta Pruning)
                if (beta <= alpha) {
                    break;
                }
            }
            return maxScore;
        } 
        //minimizing player
        //Could of done this with else instead of else if. But this will do for now
        else {
            int minScore = Integer.MAX_VALUE;
            //Go through all points
            for (int i = 0; i < pointsAvailable.size(); ++i) {
                Point point = pointsAvailable.get(i);
                //Place O's move
                b.placeAMove(point, 2);
                // Recursively call minimax again
                int currentScore = minimax(depth + 1, 1, b, alpha, beta);
                //Undo the move when done
                b.placeAMove(point, 0);
                //Update the min score
                minScore = Math.min(minScore, currentScore);
                //Update the beta value
                beta = Math.min(beta, minScore);
                //If beta is less than or equal to alpha, break the loop. (Alpha Beta Pruning)
                if (beta <= alpha) {
                    break;
                }
            }
            return minScore;
        }
    }

    //Heuristic evaluation function
    //Probably can be improved but this will do for now
    public int evaluateHeuristic(Board b) {
        int score = 0;
        
        //Evaluate rows
        for (int i = 0; i < 5; ++i) {
            int countX = 0;
            int countO = 0;
            //Counting the num of X and O in each row
            for (int j = 0; j < 5; ++j) {
                if (b.board[i][j] == 1) {
                    countX++;
                } else if (b.board[i][j] == 2) {
                    countO++;
                }
            }
            //Add score based on eval
            score += evaluateCount(countX, countO);
        }
        
        //Evaluate columns
        for (int j = 0; j < 5; ++j) {
            int countX = 0;
            int countO = 0;
            //Counting the num of X and O in each colunm
            for (int i = 0; i < 5; ++i) {
                if (b.board[i][j] == 1) {
                    countX++;
                } else if (b.board[i][j] == 2) {
                    countO++;
                }
            }
            //Add score based on eval
            score += evaluateCount(countX, countO);
        }
        
        //DID DIAGONALS DIFFERENTLY SEEING HOW WE ONLY CAN GET 2 DIAGS

        //Evaluate first diag(top left to bottom right)
        int countX = 0;
        int countO = 0;
        //Counting the num of X and O in each diag
        for (int i = 0; i < 5; ++i) {
            if (b.board[i][i] == 1) {
                countX++;
            } else if (b.board[i][i] == 2) {
                countO++;
            }
        }
        //Add score based on eval
        score += evaluateCount(countX, countO);
        
        //Eval second diag(top right to bottom left)
        countX = 0;
        countO = 0;
        for (int i = 0; i < 5; ++i) {
            if (b.board[i][4-i] == 1) {
                countX++;
            } else if (b.board[i][4-i] == 2) {
                countO++;
            }
        }
        //Add score based on eval
        score += evaluateCount(countX, countO);
        
        return score;
    }
    
    private int evaluateCount(int countX, int countO) {
        int score = 0;
    
        //Eval the count of X's
        if (countX == 4) {
            score += 1000;
        } else if (countX == 3 && countO == 0) {
            score += 100;
        } else if (countX == 2 && countO == 0) {
            score += 10;
        } else if (countX == 1 && countO == 0) {
            score += 1;
        }
    
        //Eval the count of O's
        if (countO == 4) {
            score -= 1000;
        } else if (countO == 3 && countX == 0) {
            score -= 100;
        } else if (countO == 2 && countX == 0) {
            score -= 10;
        } else if (countO == 1 && countX == 0) {
            score -= 1;
        }
    
        //Where there are no X's or O's in a line
        if (countX == 0 && countO == 0) {
            score += 1;
        }
    
        //Return score
        return score;
    }
}
