package ConnectFour;

public class libBoard {

    /*
        Returns a new board it the move if the move is possible, otherwise return null
     */

    public static Board makeMove(Board b,int column,char player){
        Board toRet = new Board(b);
        if (toRet.makeMove(column,player))
            return toRet;
        else return null;
    }



}
