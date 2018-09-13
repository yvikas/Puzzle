package com.puzzle.view;

public class Point implements Comparable<Point>{
	private int x;
	private int y;

	
	public Point(int x, int y) {
		super();
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
	public int compareTo(Point another) {
		// TODO Auto-generated method stub
		if(x==another.getX() && y== another.getY())
			return 0;
		else if(y<another.getY() || (y==another.getY()&& x<another.getX()))
			return -1;
		else
			return 1;
		
	}
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if(!(o instanceof Point))
			return false;
		Point another=(Point)o;
		return x==another.getX() && y== another.getY();
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return x*10+y;
	}
}
