package com.puzzle.view;

import java.util.Set;
/*Class to have State and Path of a single move
 * 
 */
public class Path {
	private boolean isValid=false;
	private Set<Point> path=null;
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	public Set<Point> getPath() {
		return path;
	}
	public void setPath(Set<Point> path) {
		this.path = path;
	}
	
}
