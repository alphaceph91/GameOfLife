package com.example.gameoflife;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {

    public static final Random RANDOM = new Random();
    public int width, height;
    private Cell[][] board;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        board = new Cell[width][height];
        init();
    }

    //width, height is the data I received
    //this.width, this.height is the width and height of the board

    public void setData(int width, int height, boolean[] data) {
        //Clean the board before putting new data
        for (int w = 0; w < this.width; w++) {
            for  (int h = 0; h < this.height; h++) {
                board[w][h].die();
            }
        }

        //to define how many cell we have to write
        //we have to define the width and height
        // we select minimum between width and this.width because
        // if this.width < width we have more data than can show, and we will show only this.width
        // if this.width > width we have less data than we can show, we will show all width
        int tmpWidth = this.width > width ? width : this.width;
        int tmpHeight = this.height > height ? height : this.height;

        //create the cell from the data
        for (int w = 0; w < tmpWidth; w++) {
            for (int h = 0; h < tmpHeight; h++) {
                board[w][h] = new Cell(w, h, data[h * width + w]);
            }
        }
    }

    private void init() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                board[i][j] = new Cell(i, j, RANDOM.nextBoolean());
            }
        }
    }

    public Cell get(int i, int j) {
        return board[i][j];
    }

    public int nbNeighboursOf(int i, int j) {
        int nb = 0;

        for (int k = i - 1; k <= i + 1; k++) {
            for (int l = j - 1; l <= j + 1; l++) {
                if ((k != 1 || l != j) && k >= 0 && k < width && l >= 0 && l < height) {
                    Cell cell = board[k][l];

                    if (cell.alive) {
                        nb++;
                    }
                }
            }
        }

        return nb;
    }


    public void nextGeneration() {
        List<Cell> liveCells = new ArrayList<>();
        List<Cell> deadCells = new ArrayList<>();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Cell cell = board[i][j];

                int nbNeighbours = nbNeighboursOf(cell.x, cell.y);

                //rule 1 and rule 3
                if (cell.alive && (nbNeighbours < 2 || nbNeighbours > 3)) {
                    deadCells.add(cell);
                }

                //rule 2 and rule 4
                if ((cell.alive && (nbNeighbours == 3 || nbNeighbours == 2)) || (!cell.alive && nbNeighbours == 3)) {
                    liveCells.add(cell);
                }
            }
        }

        for (Cell cell : liveCells) {
            cell.reborn();
        }

        for (Cell cell : deadCells) {
            cell.die();
        }
    }
}