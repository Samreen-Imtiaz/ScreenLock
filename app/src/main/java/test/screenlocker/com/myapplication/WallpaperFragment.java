package test.screenlocker.com.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static test.screenlocker.com.myapplication.HeavyLifter.FAIL;
import static test.screenlocker.com.myapplication.HeavyLifter.SUCCESS;

public class WallpaperFragment extends Fragment
{

    private static final List<Integer> backgrounds = new ArrayList<Integer>();
    private static final int TOTAL_IMAGES;
    static {
        backgrounds.add( R.drawable.thumb1);
        backgrounds.add(R.drawable.thumb2);
        backgrounds.add(R.drawable.thumb3);
        backgrounds.add( R.drawable.thumb4);
        backgrounds.add(R.drawable.thumb5);
        backgrounds.add(R.drawable.thumb6);
        backgrounds.add( R.drawable.thumb7);
        backgrounds.add(R.drawable.thumb8);
        TOTAL_IMAGES = (backgrounds.size() - 1);
    }

    private int currentPosition = 0;
    private ImageView backgroundPreview;
    private HeavyLifter chuckNorris;
            Button b1,b2,b3;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v= inflater.inflate(R.layout.fragment_wallpaper, container, false);

        backgroundPreview = (ImageView) v.findViewById(R.id.backgroundPreview);
        b1=(Button) v.findViewById(R.id.background);
        b2=(Button) v.findViewById(R.id.move);
        b3=(Button) v.findViewById(R.id.mov);

        changePreviewImage(currentPosition);
        chuckNorris = new HeavyLifter(getActivity(), chuckFinishedHandler);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoPreviousImage(v);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoPreviousImage(v);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAsWallpaper(v);
            }
        });

        return v;
    }



    public void gotoPreviousImage(View v) {
        int positionToMoveTo = currentPosition;
        positionToMoveTo--;
        if(positionToMoveTo < 0){
            positionToMoveTo = TOTAL_IMAGES;
        }
        changePreviewImage(positionToMoveTo);
    }
    public void setAsWallpaper(View v) {
        int resourceId = backgrounds.get(currentPosition);
        chuckNorris.setResourceAsWallpaper(resourceId);
    }
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
                    Toast.makeText(getActivity(), "Wallpaper set", Toast.LENGTH_SHORT).show();
                    break;
                case FAIL:
                    Toast.makeText(getActivity(), "Uh oh, can't do that right now", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };
    /////////////////////////////////////////////////////////////////////////////

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

}
