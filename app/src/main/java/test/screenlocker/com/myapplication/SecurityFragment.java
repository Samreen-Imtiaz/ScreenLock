package test.screenlocker.com.myapplication;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import java.util.ArrayList;

import test.screenlocker.com.myapplication.listener.SliderListener;

public class SecurityFragment extends Fragment
{

    private TabHost mTabHost;
    private ViewPager mViewPager;
    private TabsAdapter mTabsAdapter;
    TextView heading;
    SlidingPaneLayout mSlidingLayout;
    ImageButton slidingPaneButton;

    public SecurityFragment() {
    }

    public static SecurityFragment newInstance() {
        Bundle args = new Bundle();
        SecurityFragment sampleFragment = new SecurityFragment();
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
        View v = inflater.inflate(R.layout.fragment_security, container, false);
        heading = (TextView) v.findViewById(R.id.textview);
        heading.setText("Security");
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


        mTabHost = (TabHost) v.findViewById(android.R.id.tabhost);
        mTabHost.setup();

        mViewPager = (ViewPager) v.findViewById(R.id.pager);
        mTabsAdapter = new TabsAdapter(getActivity(), mTabHost, mViewPager);

        // Here we load the content for each tab.
        mTabsAdapter.addTab(mTabHost.newTabSpec("pin").setIndicator("Pin"), PinFragment.class, null);
        mTabsAdapter.addTab(mTabHost.newTabSpec("pattern").setIndicator("Pattern"), PatternFragment.class, null);

        return v;
    }

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
    }
}
