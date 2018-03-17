package ConnectFour;

public class libBoard {

    public static Board makeMove(Board b,int column,char player){
        Board toRet = new Board(b);
        if (toRet.makeMove(column,player))
            return toRet;
        else return null;
    }



}
