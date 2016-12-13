package test.screenlocker.com.myapplication;


import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarFragment;
import com.roughike.bottombar.OnTabSelectedListener;


public class MainActivity extends AppCompatActivity {
    private CoordinatorLayout coordinatorLayout;
    private static String TAG = MainActivity.class.getSimpleName();

    private BottomBar bottomBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // display the first navigation drawer view on app launch



        ////////////////////////////////////////////////////////////////////////////////////////

        bottomBar = BottomBar.attach(this, savedInstanceState);

        bottomBar.setFragmentItems(getSupportFragmentManager(), R.id.fragmentContainer,
                new BottomBarFragment(HomeFragment.newInstance(), R.drawable.ic_action_name, "Home"),
                new BottomBarFragment(UserFregment.newInstance(), R.drawable.ic_action_user, "User Profile"),
                new BottomBarFragment(SecurityFragment.newInstance(), R.drawable.ic_action_lock, "Security"),
                new BottomBarFragment(ThemeFregment.newInstance(), R.drawable.ic_theme, "Theme")
        );

      //  bottomBar.mapColorForTab(0, "#3B494C");
     //   bottomBar.mapColorForTab(1, "#00796B");
     //   bottomBar.mapColorForTab(2, "#7B1FA2");
     //   bottomBar.mapColorForTab(3, "#FF5252");

        bottomBar.setOnItemSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onItemSelected(int position) {
                switch (position) {
                    case 0:

                }
            }
        });

    }

}