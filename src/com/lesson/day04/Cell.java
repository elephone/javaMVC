package com.lesson.day04;

//代表蛇身上的 结点， 和食物
public class Cell {
    private int x=0;
    private int y=0;
    public Cell() {
	x=0;
	y=0;
    }
    
    public Cell(int x, int y) {
	
	this.x = x;
	this.y = y;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    

}
