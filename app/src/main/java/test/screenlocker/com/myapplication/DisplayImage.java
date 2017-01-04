package test.screenlocker.com.myapplication;

/**
 * Created by syeda on 1/2/2017.
 */


import static test.screenlocker.com.myapplication.HeavyLifter.FAIL;
import static test.screenlocker.com.myapplication.HeavyLifter.SUCCESS;
import static test.screenlocker.com.myapplication.R.id.imageView;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import test.screenlocker.com.myapplication.R;
import test.screenlocker.com.myapplication.HeavyLifter;
import test.screenlocker.com.myapplication.utils.PreferencesConstants;
import test.screenlocker.com.myapplication.utils.PreferencesHandler;

public class DisplayImage extends Activity {
    private static final List<Integer> backgrounds = new ArrayList<Integer>();
    int[] imageId = {
            R.drawable.thumb1,
            R.drawable.thumb2,
            R.drawable.thumb3,
            R.drawable.thumb4,
            R.drawable.thumb5,
            R.drawable.thumb6,
            R.drawable.thumb7,
            R.drawable.thumb8,
    };
    int position;
    protected static final String EXTRA_RES_ID = "POS";

    private int currentPosition = 0;
    private ImageView backgroundPreview;
    private HeavyLifter chuckNorris;
    PreferencesHandler prefs;
    Button display;
    Intent intent;
    int index;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_image);

        backgroundPreview = (ImageView) findViewById(R.id.backgroundPreview);
        display=(Button) findViewById(R.id.background);
      //  changePreviewImage(currentPosition);
        chuckNorris = new HeavyLifter(this, chuckFinishedHandler);
        Bundle bdl=getIntent().getExtras();
        index=bdl.getInt("Index");
        backgroundPreview.setImageResource(PreferencesConstants.imageId[index]);
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAsWallpaper(v);
            }
        });
    }

  /*  public void gotoPreviousImage(View v) {
        int positionToMoveTo = currentPosition;
        positionToMoveTo--;
        if(positionToMoveTo < 0){
            positionToMoveTo = TOTAL_IMAGES;
        }
        changePreviewImage(positionToMoveTo);
    }*/

    public void setAsWallpaper(View v) {
        int resourceId = backgrounds.get(PreferencesConstants.imageId[index]);
        chuckNorris.setResourceAsWallpaper(resourceId);

    }

  /*  public void gotoNextImage(View v) {
        int positionToMoveTo = currentPosition;
        positionToMoveTo++;
        if(currentPosition == TOTAL_IMAGES){
            positionToMoveTo = 0;
        }

        changePreviewImage(positionToMoveTo);
    }
*/

    public void changePreviewImage(int pos) {
        currentPosition = pos;
        backgroundPreview.setImageResource(backgrounds.get(pos));
        Log.d("Main", "Current position: "+pos);
    }
    private Handler chuckFinishedHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case SUCCESS:
                    Toast.makeText(DisplayImage.this, "Wallpaper set", Toast.LENGTH_SHORT).show();
                    break;
                case FAIL:
                    Toast.makeText(DisplayImage.this, "Uh oh, can't do that right now", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };
}
