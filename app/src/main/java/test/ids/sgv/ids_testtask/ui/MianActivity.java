package test.ids.sgv.ids_testtask.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import java.util.List;
import java.util.Vector;

import test.ids.sgv.ids_testtask.R;


public class MianActivity extends SherlockFragmentActivity {

    static final String TAG = MianActivity.class.getSimpleName();
    private ViewPager mPager;
    private MyFragmentPagerAdapter mAdapter;
    private List<Fragment> fragments;
    private String q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mPager = (ViewPager) findViewById(R.id.pager);
        initialiseViewPager();
    }

    @Override
    public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void initialiseViewPager() {

        fragments = new Vector<Fragment>();
        fragments.add(MainListFragment.newInstance(null));
        fragments.add(FavoriteFragment.newInstance());

        this.mAdapter = new MyFragmentPagerAdapter(super.getSupportFragmentManager(), fragments);
        this.mPager.setAdapter(mAdapter);

    }

    private class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {

        public List<Fragment> fragments;
        static final int NUM_ITEMS = 2;
        public FragmentManager mFragmentManager;
        private Fragment mFragmentAtPos0;

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.fragments = list;
            mFragmentManager = getSupportFragmentManager();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

    }
}
