package com.charboard.www.charboard;

/**
 * Created by Steve on 12/17/2014.
 */

public class Board {
    public int score=0;
    public int highestScore=0;
    protected int[][] board;
    static int na = 0;
    static int a= 1;

    public Board(){
        board= new int[5][5];
    }

    public Board(int colour){
        board= new int[5][5];
        board[2][2]= 1;
        placeCharm(2,2,colour);
    }

    public void set(int num1, int num2, int colour){
        board[num1][num2] = colour;
    }

    public boolean dropAvailable(int num1, int num2){
        if(board[num1][num2]== a)
            return true;
        else
            return false;
    }

    public boolean dropNotAvailable(int num1, int num2){
        if(board[num1][num2]== na)
            return true;
        else
            return false;
    }

    public void addPoint(){
        score++;
    }

    public void restore(int x, int y, int count){
        if(count<2){
            if( 1<=x && x<=3&& 1 <=y && y <=3){
                if(board[x+1][y]>a||board[x-1][y]>a||board[x][y+1]>a||board[x][y-1]>a)
                    board[x][y]=a;
            }
            if(x==0){
                if(board[x+1][y]>a||board[x][y+1]>a||board[x][y-1]>a)
                    board[x][y]=a;
            }
            if(x==4){
                if(board[x-1][y]>a||board[x][y+1]>a||board[x][y-1]>a)
                    board[x][y]=a;
            }
            if(y==0){
                if(board[x+1][y]>a||board[x-1][y]>a||board[x][y+1]>a)
                    board[x][y]=a;
            }
            if(y==4){
                if(board[x+1][y]>a||board[x-1][y]>a||board[x][y-1]>a)
                    board[x][y]=a;
            }
            if(count==0){
                if(x<4){
                    board[x+1][y]=na;
                    restore(x+1,y,count+1);
                }
                if(x>0){
                    board[x-1][y]=na;
                    restore(x-1,y,count+1);
                }
                if(y<4){
                    board[x][y+1]=na;
                    restore(x,y+1,count+1);
                }
                if(y>0){
                    board[x][y-1]=na;
                    restore(x,y-1,count+1);
                }
            }
        }
    }

    public void checker(int x, int y) {
        //TODO horizontal check and clear
        boolean c1,c2,c3,c4;
        for(int i=0;i<2;i++){
            c1=false;
            c2=false;
            c3=false;
            c4=false;
            for(int sub=0;sub <4;sub++){
                if(board[i+sub][y]==2)
                    c1=true;
                else if(board[i+sub][y]==3)
                    c2=true;
                else if(board[i+sub][y]==4)
                    c3=true;
                else if(board[i+sub][y]==5)
                    c4=true;
            }
            if(c1&&c2&&c3&&c4){
                addPoint();
                //clear
                for(int sub=0;sub <4;sub++){
                    board[i+sub][y]=na;
                }
                //restore available area
                for(int sub=0;sub <4;sub++){
                    restore(i+sub,y,0);
                }
            }
        }

        //TODO vertical
        for(int j=0;j<2;j++){
            c1=false;
            c2=false;
            c3=false;
            c4=false;
            for(int sub=0;sub <4;sub++){
                if(board[x][j+sub]==2)
                    c1=true;
                else if(board[x][j+sub]==3)
                    c2=true;
                else if(board[x][j+sub]==4)
                    c3=true;
                else if(board[x][j+sub]==5)
                    c4=true;
            }
            if(c1&&c2&&c3&&c4){
                addPoint();
                for(int sub=0;sub <4;sub++){
                    board[x][j+sub]=na;
                }
                //restore available area
                for(int sub=0;sub <4;sub++){
                    restore(x,j+sub,0);
                }
            }
        }

    }

    public int get(int num1, int num2){
        return board[num1][num2];
    }

    public void placeCharm(int num1, int num2,int colour){
        if (dropAvailable(num1,num2)){
            set(num1,num2,colour);
            //change availability
            if( 0<=(num1+1)&&(num1+1)<=4)
                if(dropNotAvailable((num1+1),num2))
                    set((num1+1),num2, a);
            if( 0<=(num1-1)&&(num1-1)<=4)
                if(dropNotAvailable((num1-1),num2))
                    set((num1-1),num2, a);
            if( 0<=(num2+1)&&(num2+1)<=4)
                if(dropNotAvailable(num1,(num2+1)))
                    set(num1,(num2+1), a);
            if( 0<=(num2-1)&&(num2-1)<=4)
                if(dropNotAvailable(num1,(num2-1)))
                    set(num1,(num2-1), a);
        }
        checker(num1,num2);
    }

    public void showBoard(){
        for(int i=0;i<5;i++)
            for(int j=0;j<5;j++)
                if(j!=4)
                    System.out.print(get(j, i));
                else
                    System.out.println(get(j, i));
    }

}
