package ConnectFour;


import static ConnectFour.ConnectFour.*;
import static ConnectFour.libBoard.makeMove;

public class AlphaBetaCut {


    public static int alphaBetaCut(Board b) {
        int val[] = new int[W+1];
        int v = Integer.MIN_VALUE;
        int maxId=1;
        for(int i=1;i<=W;i++){
            Board descendant = makeMove(b,i,computer);
            if (descendant!=null) {
                int prevV = v;
                val[i]=minimizeValue(descendant, computer, 1,Integer.MIN_VALUE,Integer.MAX_VALUE);
                v = Math.max(v, val[i]);
                if (v!=prevV){
                    maxId = i;
                }
            }
        }
        return maxId;
    }
    private static int maximizeValue(Board b,char player,int depth,int alpha,int beta){
        int utility = b.checkVictory(player);
        if (Math.abs(utility)==512 || utility==0 || depth>=maxDepth)
            return utility;
        int v = Integer.MIN_VALUE;
        for(int i=1;i<=W;i++){
            Board descendant = makeMove(b,i,(player==computer)?(human):computer);
            if (descendant!=null) {
                v = Math.max(v, minimizeValue(descendant, (player == computer) ? (human) : computer, depth + 1, alpha, beta));
                if (v>= beta)
                    return v;
                beta = Math.max(alpha,v);
            }
        }
        return v;
    }
    private static int minimizeValue(Board b,char player,int depth,int alpha,int beta){
        int utility = b.checkVictory(player);
        if (Math.abs(utility)==512 || utility==0 || depth>=maxDepth){
            return utility;
        }
        int v = Integer.MAX_VALUE;
        for(int i=1;i<=W;i++){
            Board descendant = makeMove(b,i,(player==computer)?(human):computer);
            if (descendant !=null){
                v = Math.min(v,maximizeValue(descendant,(player==computer)?(human):computer,depth+1,alpha,beta));
                if (v<=alpha){
                    return v;
                }
                beta = Math.min(beta,v);
            }
        }
        return v;
    }

}