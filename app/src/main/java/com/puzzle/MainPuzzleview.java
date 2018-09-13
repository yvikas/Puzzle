package com.puzzle;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.puzzle.Util.StopWatch;
import com.puzzle.view.Shikakuview;


public class MainPuzzleview extends Activity{

	private Shikakuview shikakuview;
	public static final String KEY_SELECTION ="org.shikaku.Puzzle";
	public static final int NPUZ_1 = 0;
	public static final int MPUZ_1 = 1;
	public static final int EPUZ_1 = 2;
	public static final int NPUZ_2 = 3;
	public static final int MPUZ_2 = 4;
	public static final int EPUZ_2 = 5;
	public static final int NPUZ_3 = 6;
	public static final int MPUZ_3 = 7;
	public static final int EPUZ_3 = 8;


	final int MSG_START_TIMER = 0;
	final int MSG_STOP_TIMER = 1;
	final int MSG_UPDATE_TIMER = 2;

	TextView txtTimer=null;
	StopWatch timer = new StopWatch();
	final int REFRESH_RATE = 1000;

	//private final String[] ARR_IN_GAME_MENU={"Main Menu", "Solve", "Hint", "Save", "Exit"};
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);

		int level=0;
		if(ShikakuGame.diff>=0 && ShikakuGame.diff<3)
			level=5;
		else if(ShikakuGame.diff>=3 && ShikakuGame.diff<6)
			level=7;
		else if(ShikakuGame.diff>=6 && ShikakuGame.diff<9)
			level=10;
		if(level>0)
			shikakuview=new Shikakuview(this, this, level);
		setContentView(shikakuview);

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);
		View v = (View) menu.findItem(R.id.Timer).getActionView();
		txtTimer=(TextView) v.findViewById(R.id.txt_title);
		mHandler.sendEmptyMessage(MSG_START_TIMER);
		
		View spinnerLayout= menu.findItem(R.id.menu).getActionView();
		Spinner spinner=(Spinner) spinnerLayout.findViewById(R.id.menu_spinner);
		spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item));
		return super.onCreateOptionsMenu(menu);
	}


	Handler mHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MSG_START_TIMER:
				timer.starttimer(); //start timer
				mHandler.sendEmptyMessage(MSG_UPDATE_TIMER);
				break;

			case MSG_UPDATE_TIMER:
				txtTimer.setText(""+ timer.getElapsedTime()/1000);
				mHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIMER,REFRESH_RATE); //text view is updated every second, 
				break;                                  //though the timer is still running
			case MSG_STOP_TIMER:
				mHandler.removeMessages(MSG_UPDATE_TIMER); // no more updates.
				timer.stoptimer();//stop timer
				txtTimer.setText(""+ timer.getElapsedTime()/1000);
				break;

			default:
				break;
			}
		}
	};


	public void finishGame()
	{
		{
			mHandler.sendEmptyMessage(MSG_STOP_TIMER);
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Shikaku..");
			alertDialog.setMessage("Congratulations! You have solved the puzzle in: "+timer.getElapsedTime()/1000+" Secs.");
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			});
			alertDialog.setIcon(R.drawable.icon);
			alertDialog.show();
		}
	}

}
