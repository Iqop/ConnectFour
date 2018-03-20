package ConnectFour;

import static ConnectFour.ConnectFour.*;
import static ConnectFour.libBoard.makeMove;

public class MiniMax {

    /*
        Calls the minimax algorithm
        Must return the column to play [1-7]
    */

    public static int minimaxAlg(Board b) {
        int GreatestUtility=Integer.MIN_VALUE;
        int maxId=0;
        for(int i=1;i<=W;i++){
            Board descendant = makeMove(b,i,computer);
            if (descendant!=null) {

                int act=minimizeValue(descendant, computer, 1);
                if (act > GreatestUtility){
                    GreatestUtility=act;
                    maxId=i;
                }
            }
        }
        return maxId;
    }
    public static int maximizeValue(Board b,char player,int depth){
        int utility = b.checkVictory(player);
        if (Math.abs(utility)==512 || utility==0 || depth>=maxDepth)
            return utility;
        int v = Integer.MIN_VALUE;
        for(int i=1;i<=W;i++){
            Board descendant = makeMove(b,i,(player==computer)?(human):computer);
            if (descendant!=null)
                v = Math.max(v,minimizeValue(descendant,(player==computer)?(human):computer,depth+1));
        }
        return v;
    }
    public static int minimizeValue(Board b,char player,int depth){
        int utility = b.checkVictory(player);
        if (Math.abs(utility)==512 || utility==0 || depth>=maxDepth){
            return utility;
        }
        int v = Integer.MAX_VALUE;
        for(int i=1;i<=W;i++){
            Board descendant = makeMove(b,i,(player==computer)?(human):computer);
            if (descendant !=null){
                v = Math.min(v,maximizeValue(descendant,(player==computer)?(human):computer,depth+1));
            }
        }
        return v;
    }

}
