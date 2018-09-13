package com.puzzle;





import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ShikakuActivity extends Activity implements OnClickListener{
	Button New_Game,Resume_Game,Scoreboard,About,Exit;
	String Difficulty[] = {"Novice","Medium","Expert"};
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        New_Game = (Button)findViewById(R.id.New_Game_label);
        Resume_Game = (Button)findViewById(R.id.Resume_label);
        Scoreboard = (Button)findViewById(R.id.Scoreboard_label);
        About = (Button)findViewById(R.id.About_label);
        Exit = (Button)findViewById(R.id.Exit_label);
        New_Game.setOnClickListener(this);
        Resume_Game.setOnClickListener(this);
        Scoreboard.setOnClickListener(this);
        About.setOnClickListener(this);
        Exit.setOnClickListener(this);
    }
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.New_Game_label:
			NewGameDialog();
			break;
		case R.id.Resume_label:
			break;
		case R.id.Scoreboard_label:
			break;
		case R.id.About_label:
			Intent i = new Intent(this, About.class);
			startActivity(i);
			break;
		case R.id.Exit_label:
			finish();
			break;
		}
	}
	
	private void NewGameDialog()  
	{
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this)
		.setTitle(R.string.New_Game_title)
		.setItems(Difficulty,
		new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialoginterface, int i) {
				// TODO Auto-generated method stub
				startGame(i);
			}
			})
			.show();
	}
	
	private void startGame(int i) {
		// TODO Auto-generated method stub
		
		Intent intent = new Intent(ShikakuActivity.this, ShikakuGame.class);
		intent.putExtra(ShikakuGame.KEY_DIFFICULTY, i);
		startActivity(intent);
		}
}