package test.screenlocker.com.myapplication;


import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarFragment;
import com.roughike.bottombar.OnTabSelectedListener;


public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {
    private CoordinatorLayout coordinatorLayout;
    private static String TAG = MainActivity.class.getSimpleName();

    private BottomBar bottomBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // display the first navigation drawer view on app launch
        displayView(0);


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

        // Make a Badge for the first tab, with red background color and a value of "4".
      //  BottomBarBadge unreadMessages = bottomBar.makeBadgeForTabAt(1, "#E91E63", 4);
      //  unreadMessages.show();
      //  unreadMessages.setAnimationDuration(200);


        ////////////////////////////////////////////////////////////////////////////////////////

    }

    ///////////////////////////////////////////////////////////////////////////////////////////
  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_help) {
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
/////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        UserManual activity= null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new Settings();
                title = getString(R.string.settings);
                break;
            case 1:
                activity = new UserManual();
                title = getString(R.string.slides);
                break;
            case 2:
                fragment = new Preferences();
                title = getString(R.string.pref);
                break;
            case 3:
                fragment = new FAQ();
                title = getString(R.string.faq);
                break;
            default:
                break;
        }


    }

}