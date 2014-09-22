package test.ids.sgv.ids_testtask;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import java.util.List;
import java.util.Vector;


public class MyActivity extends SherlockFragmentActivity {

    static final String TAG = "myLogs";
    ViewPager pager;
    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        pager = (ViewPager) findViewById(R.id.pager);
        initialiseViewPager();
    }


    private void initialiseViewPager() {

        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(ListFragment.newInstance(0));
        fragments.add(FavoriteFragment.newInstance(1));

        this.pagerAdapter = new MyFragmentPagerAdapter(super.getSupportFragmentManager(), fragments);
        this.pager.setAdapter(pagerAdapter);
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        static final int PAGE_COUNT = 2;
        private List<Fragment> fragments;
        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.fragments = list;
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
