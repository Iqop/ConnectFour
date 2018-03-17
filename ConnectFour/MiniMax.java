package ConnectFour;

import static ConnectFour.ConnectFour.*;
import static ConnectFour.libBoard.makeMove;

public class MiniMax {

    //Must return the column to play [1-7]
    public static int minimaxAlg(Board b) {
        int val[] = new int[W+1];
        int v = Integer.MIN_VALUE;
        int maxId=1;
        for(int i=1;i<=W;i++){
            Board descendant = makeMove(b,i,computer);
            if (descendant!=null) {
                int prevV = v;
                val[i]=minimizeValue(descendant, computer, 1);
                v = Math.max(v, val[i]);
                if (v!=prevV){
                    maxId = i;
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
