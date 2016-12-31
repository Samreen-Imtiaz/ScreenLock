package test.screenlocker.com.myapplication.PatternLock;

import group.pals.android.lib.ui.pattern.LockPatternActivity;
import test.screenlocker.com.myapplication.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import static test.screenlocker.com.myapplication.PatternFragment._ResultFailed;

public class LockPattern extends Activity {
	private static final int _ReqCreatePattern = 1;
	private static final int _ReqSignIn = 2;
	SharedPreferences invisibleSetting;
	private static final String _ClassName = LockPatternActivity.class
			.getName();


	public static final String _ActionCreatePattern = _ClassName
			+ ".create_pattern";
	public static final String _ActionComparePattern = _ClassName
			+ ".compare_pattern";
	public static final String _Pattern = _ClassName + ".pattern";
	public static final String _ExtraRetryCount = _ClassName + ".retry_count";
	private Intent intent;
	private static String pattern;
	Bundle b;
	 SharedPreferences settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock);
        invisibleSetting = getSharedPreferences(
				"mySetting", 0);
        settings = getSharedPreferences("First", 0);
        boolean dialogShown = settings.getBoolean("dialogShown", false);

        if (!dialogShown) {
          // First create and save pattern
        	intent = new Intent(_ActionCreatePattern, null,
					this, LockPatternActivity.class);
			startActivityForResult(intent, _ReqCreatePattern);
			
             
        }
		 else {
			// for comparing the second drawn pattern with the first one
			intent = new Intent(_ActionComparePattern,
					null, this, LockPatternActivity.class);
			pattern = invisibleSetting.getString("myPattern", pattern);
			intent.putExtra(_Pattern, pattern);
			startActivityForResult(intent, _ReqSignIn);
			
			
		}
    }

    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case _ReqCreatePattern:
			if (resultCode == RESULT_OK) {
				// ////////////////////////////////////////////////////////////////////////////////////////////////////////////
				// for saving pattern in our shared preferences
				pattern = data.getStringExtra(_Pattern);
				
				SharedPreferences.Editor editor = invisibleSetting.edit();
				editor.putString("myPattern", pattern);
				editor.commit();
				pattern = data.getStringExtra(_Pattern);
				SharedPreferences.Editor edit = settings.edit();
		          edit.putBoolean("dialogShown", true);
		          edit.commit(); 
				Toast.makeText(this, "Pattern Saved", Toast.LENGTH_SHORT)
						.show();
				Log.d(this.toString(), pattern);
				//Your Activity after saving patterns
				Intent i = new Intent(this, MainActivity.class);
				startActivity(i);
				finish();
				//onCreate(b);
			}
			break;

		// ////////////////////////////////////////////////////////////////////////////////////////////////////////////

		case _ReqSignIn: {
			/*
			 * NOTE that there are 3 possible result codes!!!
			 */
			switch (resultCode) {
			// when everything goes f9
			case RESULT_OK:

				Toast.makeText(this, "Successfull", Toast.LENGTH_LONG).show();
				//Your activity after frawing right pattern
				Intent i = new Intent(this, MainActivity.class);
				startActivity(i);
				finish();
				break;

			// ////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// wehen pattern drawn process is canclled
			case RESULT_CANCELED:
				Toast.makeText(this, "Canceled", Toast.LENGTH_LONG).show();
				break;

			// ////////////////////////////////////////////////////////////////////////////////////////////////////////////

			// when match failed
			case _ResultFailed:
				Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show();
				break;
			}

			/*
			 * In any case, there's always a key _ExtraRetryCount, which holds
			 * the number of tries that the user did.
			 */

			break;
		}
		// ////////////////////////////////////////////////////////////////////////////////////////////////////////////

		}
		@SuppressWarnings("unused")
		int retryCount = data.getIntExtra(_ExtraRetryCount,
				0);
	}
   

}
