package test.screenlocker.com.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import test.screenlocker.com.myapplication.listener.SliderListener;

public class HomeFragment extends Fragment {
    private static final String STARTING_TEXT = "Four Buttons Bottom Navigation";
    TextView heading;
    SlidingPaneLayout mSlidingLayout;
    ImageButton slidingPaneButton;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment sampleFragment = new HomeFragment();
        sampleFragment.setArguments(args);
        return sampleFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        heading = (TextView) v.findViewById(R.id.textview);
        heading.setText("Home");
       // displayView();
        mSlidingLayout = (SlidingPaneLayout) getActivity().findViewById(R.id.sliding_pane_layout);
        mSlidingLayout.setPanelSlideListener(SliderListener.getInstance(getActivity()));
        slidingPaneButton = (ImageButton) v.findViewById(R.id.left_button_header);
        slidingPaneButton.setVisibility(View.VISIBLE);
        slidingPaneButton.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.top_left_menu_icon));
        slidingPaneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlidingLayout.openPane();
            }
        });
        return v;
    }

    public void displayView() {

    }
}