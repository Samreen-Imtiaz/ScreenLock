package test.screenlocker.com.myapplication;

/**
 * Created by syeda on 1/2/2017.
 */


import static test.screenlocker.com.myapplication.HeavyLifter.FAIL;
import static test.screenlocker.com.myapplication.HeavyLifter.SUCCESS;


import java.util.ArrayList;
        import java.util.List;

        import android.app.Activity;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Message;
        import android.util.Log;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.Toast;

        import test.screenlocker.com.myapplication.R;
        import test.screenlocker.com.myapplication.HeavyLifter;

public class DisplayImage extends Activity {
    private static final List<Integer> backgrounds = new ArrayList<Integer>();
  /*  private static final int TOTAL_IMAGES;
    static {
        backgrounds.add(R.drawable.background1);
        backgrounds.add(R.drawable.background2);
        backgrounds.add(R.drawable.background3);
        TOTAL_IMAGES = (backgrounds.size() - 1);
    }*/



    private int currentPosition = 0;
    private ImageView backgroundPreview;
    private HeavyLifter chuckNorris;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_image);

        backgroundPreview = (ImageView) findViewById(R.id.backgroundPreview);
      //  changePreviewImage(currentPosition);
        chuckNorris = new HeavyLifter(this, chuckFinishedHandler);
        backgroundPreview.setOnClickListener(new View.OnClickListener() {
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
        int resourceId = backgrounds.get(currentPosition);
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
