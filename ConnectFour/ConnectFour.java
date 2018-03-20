package ConnectFour;

import java.util.Scanner;

import static ConnectFour.AlphaBetaCut.alphaBetaCut;
import static ConnectFour.MiniMax.minimaxAlg;

public class ConnectFour {
    public static final int W = 7;
    public static final int H = 6;
    public static  int maxDepth;
    public static final int maxPiecesPerType=21;
    public static final char computer = 'O';
    public static final char human = 'X';

    static String alg = "";

    public static Scanner scan = new Scanner(System.in);

    public static void main(String args[]){
        System.out.println();
        int corrVal=0;
        boolean invalid = true;
        //Making a new Board for the game
        Board b  = new Board();
        /*
            The computer starts, remember humans are X's
         */


        /*
            Displaying the menu
         */

        System.out.print("Choose the \"algorithm\" you want to use:\n1) Simple Minimax\n2) Minimax with Alpha Beta Cut\n");
        System.out.print("\nInsert option: \t");
        int option = scan.nextInt();
        while (invalid) {
            if (option == 1 || option == 2) {
                if (option==1)
                    alg="MiniMax";
                else alg="AlphaBeta";
                invalid = false;
            }
            else {
                System.out.println("Invalid option. Try again.");
                System.out.print("Insert option: \t");
                option = scan.nextInt();
            }
        }
        System.out.print("Now select the maximum depth for the algorithm: ");
        maxDepth=scan.nextInt();
        System.out.println();
        if (option == 1) {
            System.out.println("******************** \tCONNECT FOUR (with Minimax)\t ********************\n");
            System.out.println("\t\t\t** Using Minimax with maximum depth of: " + maxDepth  + " **\n\n");
        }
        else {
            System.out.println("******************** \tCONNECT FOUR (with Alpha Beta)\t ********************\n");
            System.out.println("\t     ** Using Minimax with Alpha Beta Cut with maximum depth of: " + maxDepth + " **\n\n");
        }


        /*
            Game loop, for every move from either of the players it checks if there's a win
         */

        while(Math.abs(corrVal)!=512 ||!b.isFull()) {

            drawGameInterface(b, computer);
            corrVal = b.checkVictory(computer);
            if (Math.abs(corrVal)!=512 || !b.isFull()){
                drawGameInterface(b,human);
                corrVal=b.checkVictory(human);
            }
        }
        drawFinalScreen(b,corrVal);


    }
    /*
        Shows the final result of the game, win or draw
     */

    private static void drawFinalScreen(Board b, int victoryResult){
        System.out.println("\n\n====================The Game as ended=======================");
        b.print();
        if (b.isFull() && Math.abs(victoryResult)!=512) {
            System.out.println("The winner was: " + ((victoryResult == 512) ? computer : human));
        }else System.out.println("It's a draw, shame on you!!!");

    }

    /*
        Shows the game environment, for the human it asks for a column to play
        If is the computer turn it runs minimax or minimax with alpha beta cut
     */

    private static void drawGameInterface(Board b,char  player){
        if (player==human){
            b.print();
            System.out.println("\n\nIt's now "+player+"'s turn");
            System.out.print("Make a move by selecting the column to play [1-"+W+"]: ");
            int column = scan.nextInt();
            while (!b.makeMove(column,player)){
                System.out.println("Bad move, try again");
                System.out.print("Make a move by selecting the column to play [1-"+W+"]: ");
                column = scan.nextInt();
            }

        }else{
            b.print();
            System.out.println("\n\nIt's now "+player+"'s turn");
            if (alg.compareTo("MiniMax")==0) {
                b.makeMove(minimaxAlg(b), player);
            }else{
                b.makeMove(alphaBetaCut(b), player);
            }

        }

    }

}
