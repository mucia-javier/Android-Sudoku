package com.example.javcoz.sudoku;
import java.io.Serializable;

public class Game implements Serializable {
    public static final int UNASSIGNED = 0;
    private  final int N = 9;
    public int board[][]=
            {{0,0,0, 0,0,0, 0,0,0},
                    {0,0,0, 0,0,0, 0,0,0},
                    {0,0,0, 0,0,0, 0,0,0},

                    {0,0,0, 0,0,0, 0,0,0},
                    {0,0,0, 0,0,0, 0,0,0},
                    {0,0,0, 0,0,0, 0,0,0},

                    {0,0,0, 0,0,0, 0,0,0},
                    {0,0,0, 0,0,0, 0,0,0},
                    {0,0,0, 0,0,0, 0,0,0}};
    public int original[][]=
                   {{0,0,0, 0,0,0, 0,0,0},
                    {0,0,0, 0,0,0, 0,0,0},
                    {0,0,0, 0,0,0, 0,0,0},

                    {0,0,0, 0,0,0, 0,0,0},
                    {0,0,0, 0,0,0, 0,0,0},
                    {0,0,0, 0,0,0, 0,0,0},

                    {0,0,0, 0,0,0, 0,0,0},
                    {0,0,0, 0,0,0, 0,0,0},
                    {0,0,0, 0,0,0, 0,0,0}};

    public class Cell{
        public int theRow;
        public int theCol;
        public int theValue;
        Cell(){
            this.theCol = 0;
            this.theRow = 0;
            this.theValue = UNASSIGNED;
        }
    }
    public boolean SolveSudoku(int grid[][]) {
        Cell theCell = new Cell();

        if (!FindUnassignedLocation(grid, theCell))
            return true;

        for (int num = 1; num <= 9; num++) {
            if (isSafe(grid, theCell.theRow, theCell.theCol, num)) {
                grid[theCell.theRow][theCell.theCol] = num;

                if (SolveSudoku(grid))
                    return true;

                grid[theCell.theRow][theCell.theCol] = UNASSIGNED;
            }
        }
        return false;
    }

    boolean FindUnassignedLocation(int grid[][], Cell aCell){
        for (aCell.theRow = 0; aCell.theRow < N; ++aCell.theRow)
            for (aCell.theCol = 0; aCell.theCol < N; ++aCell.theCol)
                if (grid[aCell.theRow][aCell.theCol] == UNASSIGNED)
                    return true;
        return false;
    }

    public boolean UsedInRow(int grid[][], int row, int num) {
        for (int col = 0; col < N; col++)
            if (grid[row][col] == num)
                return true;
        return false;
    }

    public boolean UsedInCol(int grid[][], int col, int num) {
        for (int row = 0; row < N; row++)
            if (grid[row][col] == num)
                return true;
        return false;
    }


    public boolean UsedInBox(int grid[][], int boxStartRow, int boxStartCol, int num) {
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 3; col++)
                if (grid[row+boxStartRow][col+boxStartCol] == num)
                    return true;
        return false;
    }

    public boolean isSafe(int grid[][], int row, int col, int num) {
        return !UsedInRow(grid, row, num) &&
                !UsedInCol(grid, col, num) &&
                !UsedInBox(grid, row - row%3 , col - col%3, num);
    }

    void printGrid(int grid[][]) {
        for (int row = 0; row < N; row++) {
            if(row%3 == 0)
                System.out.println();
            for (int col = 0; col < N; col++){
                if(col%3 ==0)
                    System.out.print("   ");;
                System.out.print(" "+grid[row][col]+" ");
            }
            System.out.println();;
        }
    }
    public boolean fillBoardFromString(String aPuzzle){
        if(aPuzzle == null ) //|| aPuzzle.length()!=81
            return false;
        for(int i=0; i<9; ++i)
            for(int j=0; j<9; ++j){
                int x = Character.getNumericValue(aPuzzle.charAt( (9*i)+j ) );
                this.original[i][j]= x;
                this.board[i][j]=x;
            }
        return true;
    }

}