package test.screenlocker.com.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import test.screenlocker.com.myapplication.LocKAppApplicaton;

import java.util.ArrayList;
import java.util.List;

import test.screenlocker.com.myapplication.listener.SliderListener;
import test.screenlocker.com.myapplication.model.Category;

public class ThemeFregment extends Fragment
{

    private TabHost mTabHost;
    private ViewPager mViewPager;
    //private TabsAdapter mTabsAdapter;
    TextView heading;
    SlidingPaneLayout mSlidingLayout;
    ImageButton slidingPaneButton;


    private static final String TAG_FEED = "feed", TAG_ENTRY = "entry",
            TAG_GPHOTO_ID = "gphoto$id", TAG_T = "$t",
            TAG_ALBUM_TITLE = "title";
    private static final String TAG = ThemeFregment.class.getSimpleName();

    public ThemeFregment() {
    }



    public static ThemeFregment newInstance() {
        Bundle args = new Bundle();
        ThemeFregment sampleFragment = new ThemeFregment();
        sampleFragment.setArguments(args);
        return sampleFragment;
    }

    @Override
    public void onCreate(Bundle instance)
    {
        super.onCreate(instance);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fregment_theme, container, false);
      /*  heading = (TextView) v.findViewById(R.id.textview);
        heading.setText("Theme");

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
*/
     /*   mTabHost = (TabHost) v.findViewById(android.R.id.tabhost);
        mTabHost.setup();

        mViewPager = (ViewPager) v.findViewById(R.id.pager);
        mTabsAdapter = new TabsAdapter(getActivity(), mTabHost, mViewPager);

        // Here we load the content for each tab.
        mTabsAdapter.addTab(mTabHost.newTabSpec("theme").setIndicator("Theme"), StyleFragmet.class, null);
        mTabsAdapter.addTab(mTabHost.newTabSpec("wallpaper").setIndicator("Wallpaper"), WallpaperFragment.class, null);
*/

/*
    public static class TabsAdapter extends FragmentPagerAdapter implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener
    {
        private final Context mContext;
        private final TabHost mTabHost;
        private final ViewPager mViewPager;
        private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

        static final class TabInfo
        {
            private final String tag;
            private final Class<?> clss;
            private final Bundle args;

            TabInfo(String _tag, Class<?> _class, Bundle _args)
            {
                tag = _tag;
                clss = _class;
                args = _args;
            }
        }

        static class DummyTabFactory implements TabHost.TabContentFactory
        {
            private final Context mContext;

            public DummyTabFactory(Context context)
            {
                mContext = context;
            }

            public View createTabContent(String tag)
            {
                View v = new View(mContext);
                v.setMinimumWidth(0);
                v.setMinimumHeight(0);
                return v;
            }
        }

        public TabsAdapter(FragmentActivity activity, TabHost tabHost, ViewPager pager)
        {
            super(activity.getSupportFragmentManager());
            mContext = activity;
            mTabHost = tabHost;
            mViewPager = pager;
            mTabHost.setOnTabChangedListener(this);
            mViewPager.setAdapter(this);
            mViewPager.setOnPageChangeListener(this);
        }

        public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args)
        {
            tabSpec.setContent(new DummyTabFactory(mContext));
            String tag = tabSpec.getTag();

            TabInfo info = new TabInfo(tag, clss, args);
            mTabs.add(info);
            mTabHost.addTab(tabSpec);
            notifyDataSetChanged();
        }

        @Override
        public int getCount()
        {
            return mTabs.size();
        }

        @Override
        public Fragment getItem(int position)
        {
            TabInfo info = mTabs.get(position);

            return Fragment.instantiate(mContext, info.clss.getName(), info.args);

        }

        public void onTabChanged(String tabId)
        {
            int position = mTabHost.getCurrentTab();
            mViewPager.setCurrentItem(position);
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {
        }

        public void onPageSelected(int position)
        {
            // Unfortunately when TabHost changes the current tab, it kindly
            // also takes care of putting focus on it when not in touch mode.
            // The jerk.
            // This hack tries to prevent this from pulling focus out of our
            // ViewPager.
            TabWidget widget = mTabHost.getTabWidget();
            int oldFocusability = widget.getDescendantFocusability();
            widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            mTabHost.setCurrentTab(position);
            widget.setDescendantFocusability(oldFocusability);
        }

        public void onPageScrollStateChanged(int state)
        {
        }
    }*/

    // Picasa request to get list of albums
    String url = AppConst.URL_PICASA_ALBUMS
            .replace("_PICASA_USER_", LocKAppApplicaton.getInstance()
                    .getPrefManger().getGoogleUserName());

    Log.d(TAG, "Albums request url: " + url);




    // Preparing volley's json object request
    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url,
            null, new Response.Listener<JSONObject>() {

        @Override
        public void onResponse(JSONObject response) {
            Log.d(TAG, "Albums Response: " + response.toString());

            List<Category> albums=new ArrayList<Category>();
            try {
                // Parsing the json response
                JSONArray entry = response.getJSONObject(TAG_FEED)
                        .getJSONArray(TAG_ENTRY);

                // loop through albums nodes and add them to album
                // list
                for (int i = 0; i < entry.length(); i++) {
                    JSONObject albumObj = (JSONObject) entry.get(i);
                    // album id
                    String albumId = albumObj.getJSONObject(
                            TAG_GPHOTO_ID).getString(TAG_T);

                    // album title
                    String albumTitle = albumObj.getJSONObject(
                            TAG_ALBUM_TITLE).getString(TAG_T);


                    Category album= new Category();
                    album.setId(albumId);
                    album.setTitle(albumTitle);

                    // add album to list
                    albums.add(album);

                    Log.d(TAG, "Album Id: " + albumId
                            + ", Album Title: " + albumTitle);

                }

                // Store albums in shared pref
                LocKAppApplicaton.getInstance().getPrefManger().storeCategories(albums);

                // String the main activity
                Intent intent = new Intent(getActivity().getApplicationContext(),
                        MainActivity.class);
                startActivity(intent);
                // closing spalsh activity
               // finish();

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getActivity().getApplicationContext(),
                        getString(R.string.msg_unknown_error),
                        Toast.LENGTH_LONG).show();
            }

        }
    }, new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e(TAG, "Volley Error: " + error.getMessage());

            // show error toast
            Toast.makeText(getActivity().getApplicationContext(),
                    getString(R.string.splash_error),
                    Toast.LENGTH_LONG).show();

            // Unable to fetch albums
            // check for existing Albums data in Shared Preferences
            if (LocKAppApplicaton.getInstance().getPrefManger()
                    .getCategories() != null && LocKAppApplicaton.getInstance().getPrefManger()
                    .getCategories().size() > 0)
            {
                // String the main activity
                Intent intent = new Intent(getActivity().getApplicationContext(),
                        MainActivity.class);
                startActivity(intent);
                // closing spalsh activity
              //  finish();
            } else {
                // Albums data not present in the shared preferences
                // Launch settings activity, so that user can modify
                // the settings

             //   Intent i = new Intent(this,
             //           SettingsActivity.class);
                // clear all the activities
            //    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
            //            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            //    startActivity(i);
            }

        }
    });

    // disable the cache for this request, so that it always fetches updated
    // json
  //  jsonObjReq.setShouldCache(false);

  //  LocKAppApplicaton.getInstance().addToRequestQueue(jsonObjReq);


        return v;
    }

}
