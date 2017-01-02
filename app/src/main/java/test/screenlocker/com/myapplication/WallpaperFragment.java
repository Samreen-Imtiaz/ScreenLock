package test.screenlocker.com.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class WallpaperFragment extends Fragment
{
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
    GridView grid;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v= inflater.inflate(R.layout.fragment_wallpaper, container, false);

        CustomGrid adapter = new CustomGrid(getActivity(), imageId);
        grid=(GridView) v.findViewById(R.id.grid);
        grid.setAdapter(adapter);
       grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
           public void onItemClick(AdapterView<?> parent, View view,
                                   int position, long id) {
               // Toast.makeText(MainActivity.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), DisplayImage.class);
                startActivity(intent);
            }
        });

        return v;
    } /////////////////////////////////////////////////////////////////////////////

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
