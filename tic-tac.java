/******************************************************************************

Welcome to GDB Online.
GDB online is an online compiler and debugger tool for C, C++, Python, Java, PHP, Ruby, Perl,
C#, OCaml, VB, Swift, Pascal, Fortran, Haskell, Objective-C, Assembly, HTML, CSS, JS, SQLite, Prolog.
Code, Compile, Run and Debug online from anywhere in world.

*******************************************************************************/
import java.util.Scanner;

public class TicTacToe {

    static final int SIZE = 3;
    static final char HUMAN = 'X';
    static final char AI = 'O';
    static char[][] board = new char[SIZE][SIZE];

    public static void main(String[] args) {
        initializeBoard();
        printBoard();

        while (true) {
            humanMove();
            if (checkWin(HUMAN)) {
                System.out.println("Human wins!");
                break;
            }
            if (isBoardFull()) {
                System.out.println("It's a draw!");
                break;
            }

            aiMove();
            printBoard();
            if (checkWin(AI)) {
                System.out.println("AI wins!");
                break;
            }
            if (isBoardFull()) {
                System.out.println("It's a draw!");
                break;
            }
        }
    }

    static void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = '_';
            }
        }
    }

    static void printBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    static void humanMove() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Your move. Enter row and column (0-2): ");
        int row = scanner.nextInt();
        int col = scanner.nextInt();
        if (isValidMove(row, col)) {
            board[row][col] = HUMAN;
        } else {
            System.out.println("Invalid move! Try again.");
            humanMove();
        }
    }

    static boolean isValidMove(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE && board[row][col] == '_';
    }

    static boolean isBoardFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == '_') {
                    return false;
                }
            }
        }
        return true;
    }

    static boolean checkWin(char player) {
        for (int i = 0; i < SIZE; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }
        return false;
    }

    static int evaluate() {
        if (checkWin(AI)) {
            return 1;
        }
        if (checkWin(HUMAN)) {
            return -1;
        }
        return 0;
    }

    static int minimax(int depth, boolean isMaximizing) {
        int score = evaluate();

        if (score == 1 || score == -1) {
            return score;
        }

        if (isBoardFull()) {
            return 0;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (board[i][j] == '_') {
                        board[i][j] = AI;
                        int currentScore = minimax(depth + 1, false);
                        board[i][j] = '_';
                        bestScore = Math.max(bestScore, currentScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (board[i][j] == '_') {
                        board[i][j] = HUMAN;
                        int currentScore = minimax(depth + 1, true);
                        board[i][j] = '_';
                        bestScore = Math.min(bestScore, currentScore);
                    }
                }
            }
            return bestScore;
        }
    }

    static void aiMove() {
        int bestScore = Integer.MIN_VALUE;
        int row = -1;
        int col = -1;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == '_') {
                    board[i][j] = AI;
                    int currentScore = minimax(0, false);
                    board[i][j] = '_';
                    if (currentScore > bestScore) {
                        bestScore = currentScore;
                        row = i;
                        col = j;
                    }
                }
            }
        }
        board[row][col] = AI;
        System.out.println("AI's move: ");
        printBoard();
    }
}

