package test.screenlocker.com.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import test.screenlocker.com.myapplication.FAQ;
import test.screenlocker.com.myapplication.Preferences;
import test.screenlocker.com.myapplication.R;
import test.screenlocker.com.myapplication.Settings;
import test.screenlocker.com.myapplication.WelcomeActivity;

public class SlideTabFragment extends Fragment {
    View rootView;
    int position;
    TextView t1,t2, t3, t4;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile_tab, container, false);
          t1=(TextView) rootView.findViewById(R.id.set);
        t2=(TextView) rootView.findViewById(R.id.man);
        t3=(TextView) rootView.findViewById(R.id.pref);
        t4=(TextView) rootView.findViewById(R.id.FAQ);
        onSliderItemSelected(position);
        return rootView;
    }
    public void onSliderItemSelected(int position) {

        switch (position) {
            case 0:
                t1.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(getActivity(), Settings.class);
                        startActivity(intent);
                    }
                });
            case 1:
                t2.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                        startActivity(intent);
                    }
                });
            case 2:
                t3.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(getActivity(), Preferences.class);
                        startActivity(intent);
                    }
                });
            case 3:
                t4.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(getActivity(), FAQ.class);
                        startActivity(intent);
                    }
                });
            default:
                break;
        }


    }

}
