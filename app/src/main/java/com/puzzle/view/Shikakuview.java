package com.puzzle.view;




import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.puzzle.MainPuzzleview;
import com.puzzle.R;
import com.puzzle.ShikakuGame;

public class Shikakuview extends View {

	private boolean Readonly = false;
	private int level;
	private int LastTouchX =0;
	private int LastTouchY =0;
	private int FirstTouchX =0;
	private int FirstTouchY =0;
	private static final int INVALID_POINTER_ID = -1;
	// The �active pointer� is the one currently moving our object.
	private int ActivePointerId = INVALID_POINTER_ID;
	private int PosX = 0;
	private int PosY = 0;
	private boolean blocked = false;
	private float CellWidth;
	private float CellHeight;
	private String cellvalue;
	private int cval;

	/*
	 * Used to restrict puzzle to draw selection in rectangle or Square Shape
	 * */
	private int minX=Integer.MAX_VALUE;//Initialize with Max Value, so All Possible Integer value would be less then this value.
	private int maxX=-1;
	private int minY=Integer.MAX_VALUE;//Initialize with Max Value, so All Possible Integer value would be less then this value.
	private int maxY=-1;

	private ArrayList<Path> gamePaths=new ArrayList<Path>();//To contain all the existing paths
	private Set<Point> path=new LinkedHashSet<Point>();//To contain Path in the current move.

	Activity mActivity=null;
	/*
	 * Constructor changed to have an additional parameter level
	 * To reduce the need of Three Classes For three levels.
	 */
	public Shikakuview(Context context, Activity mActivity, int level) {
		this(context,null);
		setFocusable(true);
		setFocusableInTouchMode(true);
		this.level=level;
		this.mActivity=mActivity;
		// TODO Auto-generated constructor stub

	}

	public Shikakuview(Context context, AttributeSet attr) {
		super(context,attr);
		// TODO Auto-generated constructor stub
	}


	private float gridwidth;
	private float gridheight;
	private int SelectedX;
	private int SelectedY;
	private final Rect selectedRectangle = new Rect();


	@Override
	protected void onSizeChanged(int widthof, int heightof, int oldwidthof, int oldheightof)
	{
		gridwidth = widthof / (float)level;
		gridheight = heightof / (float)level;
		getRect(SelectedX, SelectedY, selectedRectangle);
		super.onSizeChanged(widthof, heightof, oldwidthof, oldheightof);
	}

	private void getRect(int selectedX2, int selectedY2, Rect selectedRectangle2) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		int width = -1, height = -1;
		if (widthMode == MeasureSpec.EXACTLY) {
			width = widthSize;
		} else {
			//width = DEFAULT_BOARD_SIZE;
			if (widthMode == MeasureSpec.AT_MOST && width > widthSize ) {
				width = widthSize;
			}
		}
		if (heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		} else {
			//height = DEFAULT_BOARD_SIZE;
			if (heightMode == MeasureSpec.AT_MOST && height > heightSize ) {
				height = heightSize;
			}
		}

		if (widthMode != MeasureSpec.EXACTLY) {
			width = height;
		} 

		if (heightMode != MeasureSpec.EXACTLY) {
			height = width;
		}

		if (widthMode == MeasureSpec.AT_MOST && width > widthSize ) {
			width = widthSize;
		}
		if (heightMode == MeasureSpec.AT_MOST && height > heightSize ) {
			height = heightSize;
		}

		CellWidth = (width - getPaddingLeft() - getPaddingRight()) / (float)level;
		CellHeight = (height - getPaddingTop() - getPaddingBottom()) / (float)level;

		setMeasuredDimension(width, height);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		Paint backdrop = new Paint();
		backdrop.setColor(getResources().getColor(R.color.puzzle_backdrop));
		canvas.drawRect(0, 0, getWidth(), getHeight(), backdrop);

		Paint linescolor = new Paint();
		linescolor.setColor(getResources().getColor(R.color.puzzle_lines));


		for(int i = 0; i<level; i++)
		{

			canvas.drawLine(0, i * gridheight, getWidth(), i * gridheight, linescolor);
			canvas.drawLine(0, i * gridheight + 1, getWidth(), i * gridheight + 1, linescolor);
			canvas.drawLine(i * gridwidth, 0, i * gridwidth, getHeight(), linescolor);
			canvas.drawLine(i * gridwidth + 1, 0, i * gridwidth + 1, getHeight(), linescolor);
		}

		// Drawing the numbers & Defining color and style for numbers
		Paint numericcolor = new Paint(Paint.ANTI_ALIAS_FLAG);
		numericcolor.setColor(getResources().getColor(
				R.color.puzzle_numericcolor));
		numericcolor.setStyle(Style.FILL);
		numericcolor.setTextAlign(Paint.Align.CENTER);
		numericcolor.setTextScaleX(gridwidth / gridheight);
		numericcolor.setTextSize(gridheight * 0.75f);

		// To Draw the number in the center of the tile
		FontMetrics fm = numericcolor.getFontMetrics();

		//Draw all the Paths.
		for(int i=0; i<gamePaths.size(); i++)
		{
			Path p=gamePaths.get(i);
			if(p.isValid())
				backdrop.setColor(getResources().getColor(R.color.blue));//If path is Valid Set Backdrop Color to blue
			else
				backdrop.setColor(getResources().getColor(R.color.false_color));//If path is not valid set Backdrop color to false color
			Set<Point> item=p.getPath();
			Point[] arr=new Point[item.size()];
			item.toArray(arr);//Get array from set so we can iterate through set with indexes
			for(int j=0; j< arr.length; j++)
			{
				float left=arr[j].getX()*gridwidth;
				float right=arr[j].getX()*gridwidth+gridwidth;
				float top=arr[j].getY()*gridheight;
				float bottom=arr[j].getY()*gridheight+gridheight;

				//Add Margins to Cells so Path can be distinguished.
				if(!item.contains(new Point(arr[j].getX()-1, arr[j].getY())))
					left+=gridwidth/8;//If path don't have any point to left to any point, add left margin.
				if(!item.contains(new Point(arr[j].getX()+1, arr[j].getY())))
					right-=gridwidth/8;//If path don't have any point to right to any point, add right margin.
				if(!item.contains(new Point(arr[j].getX(), arr[j].getY()-1)))
					top+=gridwidth/8;//If path don't have any point to top to any point, add top margin.
				if(!item.contains(new Point(arr[j].getX(), arr[j].getY()+1)))
					bottom-=gridwidth/8;//If path don't have any point to bottom to any point, add bottom margin.
				canvas.drawRect(left, top, right, bottom , backdrop);
			}
		}
		{
			//Draw Current move as other paths has been.
			Point[] arr=new Point[path.size()];
			path.toArray(arr);
			for(int j=0; j< arr.length; j++)
			{
				backdrop.setColor(getResources().getColor(R.color.blue));
				float left=arr[j].getX()*gridwidth;
				float right=arr[j].getX()*gridwidth+gridwidth;
				float top=arr[j].getY()*gridheight;
				float bottom=arr[j].getY()*gridheight+gridheight;
				if(!path.contains(new Point(arr[j].getX()-1, arr[j].getY())))
					left+=gridwidth/8;
				if(!path.contains(new Point(arr[j].getX()+1, arr[j].getY())))
					right-=gridwidth/8;
				if(!path.contains(new Point(arr[j].getX(), arr[j].getY()-1)))
					top+=gridwidth/8;
				if(!path.contains(new Point(arr[j].getX(), arr[j].getY()+1)))
					bottom-=gridwidth/8;
				canvas.drawRect(left, top, right, bottom , backdrop);
			}
		}

		// Centering in X
		float x = gridwidth / 2;
		// Centering in Y
		float y = gridheight / 2 - (fm.ascent + fm.descent) / 2;
		for (int i = 0; i < level; i++) {
			for (int j = 0; j < level; j++) {
				canvas.drawText(ShikakuGame.retrieveNumeric(i, j), i
						* gridwidth + x, j * gridheight + y, numericcolor);
			}
		}
	}

	/*
	 * Method to return touched Point.
	 */
	private Point getCellAtPoint(int x, int y) {
		// take into account padding
		int lx = x - getPaddingLeft();
		int ly = y - getPaddingTop();

		int row = (int) (ly / CellHeight);
		int col = (int) (lx / CellWidth);
		return new Point(col, row);
	}

	/*
	 * method to calculate cells which need to be filled or deleted in puzzle
	 * according to the touched point.
	 */
	private void fillPuzzle(Point point)
	{
		if(point.getY()<minY)
			minY=point.getY();
		if(point.getY()>maxY)
			maxY=point.getY();
		if(point.getX()<minX)
			minX=point.getX();
		if(point.getX()>maxX)
			maxX=point.getX();
		if(point.getX() >= 0 && point.getX() < level 
				&& point.getY() >= 0 && point.getY() < level) {
			cval = ShikakuGame.retrieveCellval(point.getY(),point.getX()); 
			/*
			 * If current move has a touched point already,
			 * delete all the points on top of this point 
			 * from the path.
			 */
			if(path.contains(point))
			{
				ArrayList<Point> pts=new ArrayList<Point>();
				pts.addAll(path);
				if(pts.get(0).getX()==minX)
					maxX=point.getX();
				else if(pts.get(0).getX()==maxX)
					minX=point.getX();

				if(pts.get(0).getY()==minY)
					maxY=point.getY();
				else if(pts.get(0).getY()==maxY)
					minY=point.getY();

				int i=pts.size()-1;
				while(i>=0)
				{
					Point pt=pts.get(i);
					if(pt.getX()<minX)
						path.remove(pt);
					else if(pt.getX()>maxX)
						path.remove(pt);
					else if(pt.getY()< minY)
						path.remove(pt);
					else if(pt.getY()>maxY)
						path.remove(pt);
					i--;
				}

			}
			//If Point don't exist in path, add the point to the path.
			else 
				for(int i=minY; i<=maxY; i++)
					for(int j=minX; j<=maxX; j++)
					{
						Point rectPt=new Point(j, i);
						if(!path.contains(rectPt))
						{
							Point pt=new Point(j, i);
							if(manageConflicts(pt))//If Point is a single Point in the path and intersect with another path, the point is not required to be added to the path.
								path.add(new Point(j, i));
						}
					}
		}
	}

	/*
	 * Calculates if Point is Conflicting with another paths.
	 * Delete the Paths with Conflicts.
	 */
	public boolean manageConflicts(Point point)
	{
		for(int i=0; i< gamePaths.size(); i++)
		{
			Path oldPath=gamePaths.get(i);
			Set<Point> p=oldPath.getPath();

			if(p.contains(point))
			{

				gamePaths.remove(i);
				if(path.size()==0)
					return false;
				break;
			}
		}
		return true;
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (!Readonly) {

			int pointerCount = event.getPointerCount();
			for(int count=0; count<pointerCount; count++)
			{
				int x = (int)event.getX(count);
				int y = (int)event.getY(count);


				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:

					// Remember where we started

					FirstTouchX = x;
					FirstTouchY = y;
					fillPuzzle(getCellAtPoint(x,y));
					// Save the ID of this pointer
					ActivePointerId = event.getPointerId(count);
					break;
				case MotionEvent.ACTION_MOVE:


					LastTouchX = x;
					LastTouchY = y;
					blocked = true;
					fillPuzzle(getCellAtPoint(x,y));

					break;
				case MotionEvent.ACTION_UP:
					ActivePointerId = INVALID_POINTER_ID;
					LinkedHashSet<Point> temp= new LinkedHashSet<Point>();
					temp.addAll(path);
					boolean state=false;
					Iterator<Point> pts= temp.iterator();
					int c=0;
					int number=0;
					//While Loop to calculate how much points the current path have with numbers
					while(pts.hasNext())
					{
						Point pt=pts.next();
						int value=ShikakuGame.retrieveCellval(pt.getY(),pt.getX());
						if(value!=0)
						{
							c++;
							number=value;//number in cell
						}
					}
					Path p=new Path();
					if(c==1 && temp.size()==number)
						p.setValid(true);//If path has one and only one cell with number in this cell which is equals to the number of cells selected, path is valid.
					else
						p.setValid(false);
					p.setPath(temp);
					gamePaths.add(p);
					path.clear();
						

					/*
					 * Reset Min and Max Value, to enable app
					 * to calculate max and min of next path.
					 */
					minX=Integer.MAX_VALUE;
					maxX=-1;
					minY=Integer.MAX_VALUE;
					maxY=-1;
					
					
					/** 
					 * Code to calculate if problem has been solved
					 **/
					int countCells=0;//Count to calculate filled cells in the puzzle
					state= true;//state if all the points are in valid paths or not.
					
					for(int i=0; i<gamePaths.size(); i++)
					{
						Path path1=gamePaths.get(i);
						if(path1.getPath().size()==0)
							continue;
						countCells+=path1.getPath().size();
						state = path1.isValid();
						if(!state)
							break;
					}
					
					if(countCells==level*level && state)
					{
						MainPuzzleview mainPuzzleView=(MainPuzzleview) mActivity;
						mainPuzzleView.finishGame();
					}

					break;
				case MotionEvent.ACTION_CANCEL:
					ActivePointerId = INVALID_POINTER_ID;
					break;
				}
				postInvalidate();
			}

		}

		return !Readonly;
	}

}
