package com.puzzle;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ShikakuGame extends Activity implements OnClickListener{
	private static final String TAG = "Shikaku";
	public static final String KEY_DIFFICULTY = 
			"org.shikaku.difficulty";
	public static final int DIFFICULTY_NOVICE1 = 0;
	public static final int DIFFICULTY_NOVICE2 = 1;
	public static final int DIFFICULTY_NOVICE3 = 2;


	public static final int DIFFICULTY_MEDIUM1 = 3;
	public static final int DIFFICULTY_MEDIUM2 = 4;
	public static final int DIFFICULTY_MEDIUM3 = 5;

	public static final int DIFFICULTY_EXPERT1 = 6;
	public static final int DIFFICULTY_EXPERT2 = 7;
	public static final int DIFFICULTY_EXPERT3 = 8;
	public static int diff;

	
	ImageView puzzleView1=null;
	ImageView puzzleView2=null;
	ImageView puzzleView3=null;

	private final int[] ids_bg_puzzle=new int[]{R.drawable.npuz_1, R.drawable.npuz_2, R.drawable.npuz_3,
			R.drawable.mpuz_1, R.drawable.mpuz_2, R.drawable.mpuz_3,
			R.drawable.exprt_1, R.drawable.exprt_2, R.drawable.exprt_3};
	
	private final String[] ARR_TITLE_PUZZLE={"Novice", "Medium", "Expert"};
	private static int puzzle1[]=null;
	private static int puzzle2[]=null;
	private static int puzzle3[]=null;
	private final String novicePuzzle1 = "20040" +
					"00004" +
					"02200" +
					"03000" +
					"23003";
	private final String novicePuzzle2 = "04000" +
					"04020" +
					"30422" +
					"20000" +
					"00002";
	private final String novicePuzzle3 = "00300" +
					"02030" +
					"30222" +
					"00200" +
					"20040";
	private final String mediumPuzzle1 = "0040000" +
					"0040220" +
					"0000000" +
					"0060064" +
					"0003020" +
					"0408000" +
					"2000002";
	private final String mediumPuzzle2 = "0200000" +
					"4200800" +
					"0000030" +
					"0060040" +
					"0030200" +
					"0200222" +
					"0223000";
	private final String mediumPuzzle3 = "2002020" +
					"0000000" +
					"4306000" +
					"0030305" +
					"0040040" +
					"0000304" +
					"2200000";
	private final String expertPuzzle1 = "7000200300" +
					"0200000000" +
					"0000040000" +
					"4200000000" +
					"0000000200" +
					"2003203022" +
					"0003002000" +
					"0000070000" +
					"00000000<0" +
					"4000000000";
	private final String expertPuzzle2 = "6003000000" +
					"0000000040" +
					"0000000000" +
					"0408080040" +
					"0000002030" +
					"220000200:" +
					"0000032000" +
					"0206900400" +
					"0008000080" +
					"0000000000";
	private final String expertPuzzle3 = "0300202000" +
					"0200006052" +
					"0020400405" +
					"6000300000" +
					"0000330000" +
					"0000000070" +
					"5200020020" +
					"0000900300" +
					"0000006204" +
					"0300300000";

	@Override

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		Log.d(TAG, "onCreate");
		diff = getIntent().getIntExtra(KEY_DIFFICULTY, DIFFICULTY_NOVICE1);
		setContentView(R.layout.puzzle_layout);
		puzzleView1 = (ImageView)findViewById(R.id.puz_img1);
		puzzleView2 = (ImageView)findViewById(R.id.puz_img2);
		puzzleView3 = (ImageView)findViewById(R.id.puz_img3);
		puzzleView1.setOnClickListener(this);
		puzzleView2.setOnClickListener(this);
		puzzleView3.setOnClickListener(this);
		TextView txtPuzzleTitle=(TextView) findViewById(R.id.txtPuzzleTitle);
		
		
		puzzle1 = getPuzzle(diff*3);
		puzzle2 = getPuzzle(diff*3+1);
		puzzle3 = getPuzzle(diff*3+2);

		
		txtPuzzleTitle.setText(ARR_TITLE_PUZZLE[diff]);
		puzzleView1.setBackgroundResource(ids_bg_puzzle[3*diff]);
		puzzleView2.setBackgroundResource(ids_bg_puzzle[3*diff+1]);
		puzzleView3.setBackgroundResource(ids_bg_puzzle[3*diff+2]);
		puzzleView1.setTag(3*diff);
		puzzleView2.setTag(3*diff+1);
		puzzleView3.setTag(3*diff+2);


	}

	/**Gets the values for the puzzle*/
	private int[] getPuzzle(int diff) {
		String stringValue;
		switch (diff) {

		case DIFFICULTY_NOVICE1:
			stringValue = novicePuzzle1;
			break;
		case DIFFICULTY_MEDIUM1:
			stringValue = mediumPuzzle1;
			break;
		case DIFFICULTY_EXPERT1:
			stringValue= expertPuzzle1;
			break;
		case DIFFICULTY_NOVICE2:
			stringValue = novicePuzzle2;
			break;
		case DIFFICULTY_MEDIUM2:
			stringValue = mediumPuzzle2;
			break;
		case DIFFICULTY_EXPERT2:
			stringValue= expertPuzzle2;
			break;
		case DIFFICULTY_NOVICE3:
			stringValue = novicePuzzle3;
			break;
		case DIFFICULTY_MEDIUM3:
			stringValue = mediumPuzzle3;
			break;
		case DIFFICULTY_EXPERT3:
			stringValue= expertPuzzle3;
			break;
		default:
			stringValue = novicePuzzle1;
			break;

		}
		return fromPuzzleString(stringValue);
	}


	/** String into an array conversion */
	static protected int[] fromPuzzleString(String string) {
		int[] stringValue = new int[string.length()];
		for (int i = 0; i < stringValue.length; i++) {
			stringValue[i] = string.charAt(i) - '0';
		}
		return stringValue;
	}


	/** Return a string for the tile at the given coordinates */
	public static String retrieveNumeric(int x, int y) {
		int tileValue=0;
		if(diff == DIFFICULTY_NOVICE1)
		{
			tileValue = puzzle1[y * 5 + x];
		}
		else if(diff == DIFFICULTY_NOVICE2)
		{
			tileValue = puzzle2[y * 5 + x];
		}
		else if(diff == DIFFICULTY_NOVICE3)
		{
			tileValue = puzzle3[y * 5 + x];
		}
		else if(diff == DIFFICULTY_MEDIUM1)
		{
			tileValue = puzzle1[y * 7 + x];
		}
		else if(diff == DIFFICULTY_MEDIUM2)
		{
			tileValue = puzzle2[y * 7 + x];
		}
		else if(diff == DIFFICULTY_MEDIUM3)
		{
			tileValue = puzzle3[y * 7 + x];
		}

		else if(diff == DIFFICULTY_EXPERT1)
		{
			tileValue = puzzle1[y * 10 + x];
		}
		else if(diff == DIFFICULTY_EXPERT2)
		{
			tileValue = puzzle2[y * 10 + x];
		}
		else if(diff == DIFFICULTY_EXPERT3)
		{
			tileValue = puzzle3[y * 10 + x];
		}
		if (tileValue == 0)
			return "";
		else
			return String.valueOf(tileValue);
	}

	/** Return a value for the tile at the given coordinates */
	public static int retrieveCellval(int x, int y) {
		int value=0;
		if(diff == DIFFICULTY_NOVICE1)
		{
			value= puzzle1[x * 5 + y];
		}
		else if(diff == DIFFICULTY_NOVICE2)
		{
			value = puzzle2[x * 5 + y];
		}
		else if(diff == DIFFICULTY_NOVICE3)
		{
			value = puzzle3[x * 5 + y];
		}
		else if(diff == DIFFICULTY_MEDIUM1)
		{
			value = puzzle1[x * 7 + y];
		}
		else if(diff == DIFFICULTY_MEDIUM2)
		{
			value = puzzle2[x * 7 + y];
		}
		else if(diff == DIFFICULTY_MEDIUM3)
		{
			value = puzzle3[x * 7 + y];
		}

		else if(diff == DIFFICULTY_EXPERT1)
		{
			value = puzzle1[x * 10 + y];
		}
		else if(diff == DIFFICULTY_EXPERT2)
		{
			value = puzzle2[x * 10 + y];
		}
		else if(diff == DIFFICULTY_EXPERT3)
		{
			value = puzzle3[x * 10 + y];
		}
		return value;
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		diff=(Integer) v.getTag();

		Intent intent = new Intent(ShikakuGame.this, MainPuzzleview.class);
		startActivity(intent);

	}

}

