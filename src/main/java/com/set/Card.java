package com.set;

import java.util.ArrayList;

class Card {
    private int number;
    private int style;
    private int color;
    private int shape;
    private int shapeWidth = 15;
    public static ArrayList<Integer> selected = new ArrayList<Integer>(15);
    public static int score = 0;

    Card(int number, int style, int color, int shape) {
        this.number = number;
        this.style = style;
        this.color = color;
        this.shape = shape;
    }

    public int getNumber() {
        return this.number;
    }

    public int getStyle() {
        return this.style;
    }

    public int getColor() {
        return this.color;
    }

    public int getShape() {
        return this.shape;
    }

    public void displayCard(int x, int y, int tablePosition, ShapeLibrary board) {
        board.drawCard(x, y, tablePosition);
        //if 1, x
        //if 2, x - (width*2) then x + width*2
        //if 3, x - (width*3) then x then x + width*3
        int modifier = 0;
        if(this.number == 3) {
            modifier = this.shapeWidth*3;
        }
        for(int i=0; i<this.number; i++) {
            if(this.number != 2) {
                if(this.shape == 1) {
                    board.paintOval(x+(i-1)*modifier, y, this.style, this.color);
                } else if (this.shape == 2) {
                    board.paintDiamond(x+(i-1)*modifier, y, this.style, this.color);
                } else {
                    board.paintSquiggle(x+(i-1)*modifier, y, this.style, this.color);
                }
            } else {
                if(this.shape == 1) {
                    board.paintOval(x+i*this.shapeWidth*4-this.shapeWidth*2, y, this.style, this.color);
                } else if (this.shape == 2) {
                    board.paintDiamond(x+i*this.shapeWidth*4-this.shapeWidth*2, y, this.style, this.color);
                } else {
                    board.paintSquiggle(x+i*this.shapeWidth*4-this.shapeWidth*2, y, this.style, this.color);
                }
            }
        }
    }

    public String toString() {
        return Integer.toString(this.number) + Integer.toString(this.style) + Integer.toString(this.color) + Integer.toString(this.shape) + "\n";
    }
}