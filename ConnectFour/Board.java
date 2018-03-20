package ConnectFour;

import java.util.LinkedList;

import static ConnectFour.ConnectFour.W;
import static ConnectFour.ConnectFour.H;
import static ConnectFour.ConnectFour.maxPiecesPerType;
import static ConnectFour.ConnectFour.human;
import static ConnectFour.ConnectFour.computer;

public class Board {
    char pieces[][];
    int piecesPerColumn[];
    int countComputer;
    int countHuman;
    static int size=3;

    Board(){
        countComputer=0;
        countHuman =0;
        pieces = new char[H][W];
        for(int i=0;i<H;i++){
            for(int j=0;j<W;j++){
                pieces[i][j]='-';
            }
        }
        piecesPerColumn = new int[W];
    }
    Board(Board b){
        pieces = new char[H][W];
        for(int i=0;i<H;i++){
            for(int j=0;j<W;j++){
                pieces[i][j]=b.pieces[i][j];
            }
        }
        piecesPerColumn = new int[W];
        for(int i=0;i<W;i++){
            piecesPerColumn[i]= b.piecesPerColumn[i];
        }
        countComputer = b.countComputer;
        countHuman = b.countHuman;
    }

    void print(){
        for(int i=0;i<H;i++){
            for(int j=0;j<W;j++){
                System.out.print(pieces[i][j]+"\t");
            }
            System.out.println();
        }
        for(int i=1;i<=W;i++){
            System.out.print(i+"\t");
        }
        System.out.println();
    }
    boolean checkMovePossible(int column,char player){
        if (column>=0 && column<W) {
            if (player == human && countHuman < maxPiecesPerType) {
                if (piecesPerColumn[column] < (H)) {
                    return true;
                }
            }
            if (player == computer && countComputer < maxPiecesPerType) {
                if (piecesPerColumn[column] < (H)) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean makeMove(int column,char player){
        column--;
        if (checkMovePossible(column,player)){
            pieces[(H-1)-piecesPerColumn[column]][column]=player;
            piecesPerColumn[column]++;
            if (player==human)
                countHuman++;
            if (player==computer)
                countComputer++;
            return true;
        }
        return false;
    }


    int checkVictory(char player){
        return -getUtilityValue2(player);
//        return getUtilityValue(player);
    }

    private int getUtilityValue2(char player) {
        int aux, result = 0;

        if (player == human) result = 16;
        else if (player == computer) result = -16;

        aux = checkHorizontal();
//        System.out.println("Aux horizontal: " + aux);
        if (aux == 512 && player == human) return aux;
        else if (aux == -512 && player == computer) return aux;
        else result += aux;

        aux = checkVertical();
//        System.out.println("Aux vertical: " + aux);
        if (aux == 512 && player == human) return aux;
        else if (aux == -512 && player == computer) return aux;
        else result += aux;

        aux = checkDiagonalTRCtoLLC();
//        System.out.println("Aux diagonal 1: " + aux);
        if (aux == 512 && player == human) return aux;
        else if (aux == -512 && player == computer) return aux;
        else result += aux;

        aux = checkDiagonalTLCtoLRC();
//        System.out.println("Aux diagonal 2: " + aux);
        if (aux == 512 && player == human) return aux;
        else if (aux == -512 && player == computer) return aux;
        else result += aux;

//        System.out.println("Resultado: " + result);

        return result;
    }

    private int checkHorizontal() {
        int numberOfOs = 0, numberOfXs = 0, result = 0;
        for (int i = 0; i < H; i++) {
            for (int j = 0; j + size < W; j++) {
                for (int k = j; k <= j + size; k++) {
                    if (pieces[i][k] == computer) {
                        numberOfOs++;
                    } else if (pieces[i][k] == human) {
                        numberOfXs++;
                    }
                }
                if (numberOfOs == 4 && numberOfXs == 0) {
                    return -512;
                } else if (numberOfXs == 4 && numberOfOs == 0) {
                    return 512;
                } else if (numberOfOs == 3 && numberOfXs == 0) {
                    result -= 50;
                } else if (numberOfOs == 2 && numberOfXs == 0) {
                    result -= 10;
                } else if (numberOfOs == 1 && numberOfXs == 0) {
                    result -= 1;
                } else if (numberOfOs == 0 && numberOfXs == 1) {
                    result += 1;
                } else if (numberOfOs == 0 && numberOfXs == 2) {
                    result += 10;
                } else if (numberOfOs == 0 && numberOfXs == 3) {
                    result += 50;
                }
                numberOfOs = 0;
                numberOfXs = 0;
            }
        }
        return result;
    }

    private int checkVertical () {
        int numberOfOs = 0, numberOfXs = 0, result = 0;
        for (int j = 0; j < W; j++) {
            for (int i = 0; i + size < H; i++) {
                for (int k = i; k <= i + size; k++) {
                    if (pieces[k][j] == computer) {
                        numberOfOs++;
                    } else if (pieces[k][j] == human) {
                        numberOfXs++;
                    }
                }
                if (numberOfOs == 4 && numberOfXs == 0) {
                    return -512;
                } else if (numberOfXs == 4 && numberOfOs == 0) {
                    return 512;
                } else if (numberOfOs == 3 && numberOfXs == 0) {
                    result -= 50;
                } else if (numberOfOs == 2 && numberOfXs == 0) {
                    result -= 10;
                } else if (numberOfOs == 1 && numberOfXs == 0) {
                    result -= 1;
                } else if (numberOfOs == 0 && numberOfXs == 1) {
                    result += 1;
                } else if (numberOfOs == 0 && numberOfXs == 2) {
                    result += 10;
                } else if (numberOfOs == 0 && numberOfXs == 3) {
                    result += 50;
                }
                numberOfOs = 0;
                numberOfXs = 0;
            }
        }
        return result;
    }

    private int checkDiagonalTRCtoLLC () {
        // top right corner -> lower left corner
        int numberOfOs = 0, numberOfXs = 0, result = 0;
        for (int i = 0; i+size < H; i++) {
            for (int j = W-1; j-size>=0; j--) {
                for (int k=i, l=j; k<=i+size && l>=j-size; k++, l--) {
                    if (pieces[k][l] == computer) {
                        numberOfOs++;
                    } else if (pieces[k][l] == human) {
                        numberOfXs++;
                    }
                }
                if (numberOfOs == 4 && numberOfXs == 0) {
                    return -512;
                } else if (numberOfXs == 4 && numberOfOs == 0) {
                    return 512;
                } else if (numberOfOs == 3 && numberOfXs == 0) {
                    result -= 50;
                } else if (numberOfOs == 2 && numberOfXs == 0) {
                    result -= 10;
                } else if (numberOfOs == 1 && numberOfXs == 0) {
                    result -= 1;
                } else if (numberOfOs == 0 && numberOfXs == 1) {
                    result += 1;
                } else if (numberOfOs == 0 && numberOfXs == 2) {
                    result += 10;
                } else if (numberOfOs == 0 && numberOfXs == 3) {
                    result += 50;
                }
                numberOfOs = 0;
                numberOfXs = 0;
            }
        }
        return result;
    }

    private int checkDiagonalTLCtoLRC () {
        // top left corner -> lower right corner
        int numberOfOs = 0, numberOfXs = 0, result = 0;
        for (int i=0; i+size < H; i++) {
            for (int j=0; j+size < W; j++) {
                for (int k=i, l=j; k<=i+size && l<=j+size; k++, l++) {
                    if (pieces[k][l] == computer) {
                        numberOfOs++;
                    } else if (pieces[k][l] == human) {
                        numberOfXs++;
                    }
                }
                if (numberOfOs == 4 && numberOfXs == 0) {
                    return -512;
                } else if (numberOfXs == 4 && numberOfOs == 0) {
                    return 512;
                }  else if (numberOfOs == 3 && numberOfXs == 0) {
                    result -= 50;
                } else if (numberOfOs == 2 && numberOfXs == 0) {
                    result -= 10;
                } else if (numberOfOs == 1 && numberOfXs == 0) {
                    result -= 1;
                } else if (numberOfOs == 0 && numberOfXs == 1) {
                    result += 1;
                } else if (numberOfOs == 0 && numberOfXs == 2) {
                    result += 10;
                } else if (numberOfOs == 0 && numberOfXs == 3) {
                    result += 50;
                }
                numberOfOs = 0;
                numberOfXs = 0;
            }
        }
        return result;
    }

    private int getUtilityValue(char player) {
        boolean isFull = true;
        for(int i=0;i<W;i++){
            if(piecesPerColumn[i]<H){
                isFull=false;
                break;
            }
        }
        if (!isFull){
            int totalUtility = (player==human)?(-16):(16);
            int uVert =utilityVertical();
            if (Math.abs(uVert)==512)
                return uVert;
            else totalUtility+=uVert;

            int uHori = utilityHorizontal();
            if(Math.abs(uHori)==512)
                return uHori;
            else totalUtility+=uHori;

            int uDiag1 =utilityDiagonal1();
            if (Math.abs(uDiag1)==512)
                return uDiag1;
            else totalUtility+=uDiag1;

            int uDiag2 =utilityDiagonal2();
            if (Math.abs(uDiag2)==512)
                return uDiag2;
            else totalUtility+=uDiag2;

            return totalUtility;
        }
        return 0;
    }

    private int utilityVertical(){
        int totalUtility=0;
        for(int j=0;j<W;j++){
            for(int i=0;i<H;i++){
                int nHumans=0;
                int nComputer =0;
                if (i+3<H) {
                    for (int k = i; k - i < 4; k++) {
                        if (pieces[k][j] == human)
                            nHumans++;
                        if (pieces[k][j] == computer)
                            nComputer++;
                    }

                    if (nHumans == 3 && nComputer == 0)
                        totalUtility += -50;
                    else if (nHumans == 2 && nComputer == 0)
                        totalUtility += -10;
                    else if (nHumans == 1 && nComputer == 0)
                        totalUtility += -1;
                    else if (nComputer == 1 && nHumans == 0)
                        totalUtility += 1;
                    else if (nComputer == 2 && nHumans == 0)
                        totalUtility += 10;
                    else if (nComputer == 3 && nHumans == 0)
                        totalUtility += 50;
                    else if (nComputer == 4 && nHumans == 0)
                        return 512;
                    else if (nComputer == 0 && nHumans == 4)
                        return -512;
                    else totalUtility += 0;
                }
            }
        }
        return totalUtility;
    }

    private int utilityHorizontal(){
        int totalUtility=0;
        for(int i=0;i<H;i++){
            for(int j=0;j<W;j++){
                int nHumans=0;
                int nComputer =0;
                if (j+3<W) {
                    for (int k = j; k < W && k - j < 4; k++) {
                        if (pieces[i][k] == human)
                            nHumans++;
                        if (pieces[i][k] == computer)
                            nComputer++;
                    }

                    if (nHumans == 3 && nComputer == 0)
                        totalUtility += -50;
                    else if (nHumans == 2 && nComputer == 0)
                        totalUtility += -10;
                    else if (nHumans == 1 && nComputer == 0)
                        totalUtility += -1;
                    else if (nComputer == 1 && nHumans == 0)
                        totalUtility += 1;
                    else if (nComputer == 2 && nHumans == 0)
                        totalUtility += 10;
                    else if (nComputer == 3 && nHumans == 0)
                        totalUtility += 50;
                    else if (nComputer == 4 && nHumans == 0)
                        return 512;
                    else if (nComputer == 0 && nHumans == 4)
                        return -512;
                    else totalUtility += 0;
                }
            }

        }
        return totalUtility;
    }

    private int utilityDiagonal1(){
        int totalUtility=0;
        for(int i=0;i<H;i++){
            int p_i = i;
            int p_j = 0;
            while(p_i<H && p_j<W){
                int nHumans=0;
                int nComputer =0;
                if (p_i+3<H && p_j+3<W) {
                    for (int k = 0; k < 4; k++) {
                        if (pieces[p_i + k][p_j + k] == human) {
                            nHumans++;
                        } else if (pieces[p_i + k][p_j + k] == computer) {
                            nComputer++;
                        }
                    }
                    if (nHumans == 3 && nComputer == 0)
                        totalUtility += -50;
                    else if (nHumans == 2 && nComputer == 0)
                        totalUtility += -10;
                    else if (nHumans == 1 && nComputer == 0)
                        totalUtility += -1;
                    else if (nComputer == 1 && nHumans == 0)
                        totalUtility += 1;
                    else if (nComputer == 2 && nHumans == 0)
                        totalUtility += 10;
                    else if (nComputer == 3 && nHumans == 0)
                        totalUtility += 50;
                    else if (nComputer == 4 && nHumans == 0)
                        return 512;
                    else if (nComputer == 0 && nHumans == 4)
                        return -512;
                    else totalUtility += 0;
                }else break;
                p_i+=1;
                p_j+=1;
            }


        }

        for(int j=0;j<W;j++){
            int p_i = 0;
            int p_j = j;
            while(p_i<H && p_j<W){
                int nHumans=0;
                int nComputer =0;
                if (p_i+3<H && p_j+3<W) {
                    for (int k = 0; k < 4; k++) {
                        if (pieces[p_i + k][p_j + k] == human) {
                            nHumans++;
                        } else if (pieces[p_i + k][p_j + k] == computer) {
                            nComputer++;
                        }
                    }
                    if (nHumans == 3 && nComputer == 0)
                        totalUtility += -50;
                    else if (nHumans == 2 && nComputer == 0)
                        totalUtility += -10;
                    else if (nHumans == 1 && nComputer == 0)
                        totalUtility += -1;
                    else if (nComputer == 1 && nHumans == 0)
                        totalUtility += 1;
                    else if (nComputer == 2 && nHumans == 0)
                        totalUtility += 10;
                    else if (nComputer == 3 && nHumans == 0)
                        totalUtility += 50;
                    else if (nComputer == 4 && nHumans == 0)
                        return 512;
                    else if (nComputer == 0 && nHumans == 4)
                        return -512;
                    else totalUtility += 0;
                }else break;
                p_i+=1;
                p_j+=1;
            }


        }


        return totalUtility;
}

    private int utilityDiagonal2(){
        int totalUtility=0;
        for (int i=0;i<H;i++){
            int pos_i = i;
            int pos_j = W-1;
            while(pos_i<H && pos_j>=0){
                int nHumans=0;
                int nComputer =0;
                if (pos_i+3<H && pos_j-3>=0){
                    for(int k=0;k<4;k++){
                        if (pieces[pos_i+k][pos_j-k]==human)
                            nHumans++;
                        else if (pieces[pos_i+k][pos_j-k]==computer){
                            nComputer++;
                        }
                    }

                    if (nHumans == 3 && nComputer == 0)
                        totalUtility += -50;
                    else if (nHumans == 2 && nComputer == 0)
                        totalUtility += -10;
                    else if (nHumans == 1 && nComputer == 0)
                        totalUtility += -1;
                    else if (nComputer == 1 && nHumans == 0)
                        totalUtility += 1;
                    else if (nComputer == 2 && nHumans == 0)
                        totalUtility += 10;
                    else if (nComputer == 3 && nHumans == 0)
                        totalUtility += 50;
                    else if (nComputer == 4 && nHumans == 0)
                        return 512;
                    else if (nComputer == 0 && nHumans == 4)
                        return -512;
                    else totalUtility += 0;
                }else break;
                pos_i+=1;
                pos_j-=1;
            }
        }

        for(int j=W-2;j>=0;j--){
            int pos_i=0;
            int pos_j=j;
            while(pos_i<H && pos_j>=0){
                int nHumans=0;
                int nComputer =0;
                if (pos_i+3<H && pos_j-3>=0){
                    for(int k=0;k<4;k++){
                        if (pieces[pos_i+k][pos_j-k]==human)
                            nHumans++;
                        else if (pieces[pos_i+k][pos_j-k]==computer){
                            nComputer++;
                        }
                    }

                    if (nHumans == 3 && nComputer == 0)
                        totalUtility += -50;
                    else if (nHumans == 2 && nComputer == 0)
                        totalUtility += -10;
                    else if (nHumans == 1 && nComputer == 0)
                        totalUtility += -1;
                    else if (nComputer == 1 && nHumans == 0)
                        totalUtility += 1;
                    else if (nComputer == 2 && nHumans == 0)
                        totalUtility += 10;
                    else if (nComputer == 3 && nHumans == 0)
                        totalUtility += 50;
                    else if (nComputer == 4 && nHumans == 0)
                        return 512;
                    else if (nComputer == 0 && nHumans == 4)
                        return -512;
                    else totalUtility += 0;
                }else break;
                pos_i+=1;
                pos_j-=1;

            }
        }
        return totalUtility;
    }



}

