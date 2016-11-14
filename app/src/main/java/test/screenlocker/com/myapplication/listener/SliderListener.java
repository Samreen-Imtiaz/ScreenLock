package test.screenlocker.com.myapplication.listener;

import android.content.Context;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;

/**
 * Created by Alee on 7/11/2016.
 */
public class SliderListener extends SlidingPaneLayout.SimplePanelSlideListener {

    private static SliderListener sliderListener;
    private Context context;

    private SliderListener(Context context) {
        this.context = context;
    }

    public static SliderListener getInstance(Context context) {

        if (sliderListener == null) {
            sliderListener = new SliderListener(context);
        }
        return sliderListener;
    }

    @Override
    public void onPanelOpened(View panel) {
//        Toast.makeText(panel.getContext(), "Opened", Toast.LENGTH_SHORT).show();

        panelOpened();


    }

    @Override
    public void onPanelClosed(View panel) {
//        Toast.makeText(panel.getContext(), "Closed", Toast.LENGTH_SHORT).show();
        panelClosed();
    }

    @Override
    public void onPanelSlide(View view, float v) {
    }


    private void panelClosed() {
    }


    private void panelOpened() {


//        if(context!=null)
//            LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(AppConstants.PROFILE_SLIDE_BROADCAST_RECEIVER));


    }
}
