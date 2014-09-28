package test.ids.sgv.ids_testtask.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import java.util.List;
import java.util.Vector;

import test.ids.sgv.ids_testtask.R;


public class MyActivity extends SherlockFragmentActivity {

    static final String TAG = MyActivity.class.getSimpleName();
    private ViewPager pager;
    private MyFragmentPagerAdapter pagerAdapter;
    private List<Fragment> fragments;
    private String q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        pager = (ViewPager) findViewById(R.id.pager);
        initialiseViewPager();
    }

    @Override
    public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
//        super.onCreateOptionsMenu(menu);
        return  super.onCreateOptionsMenu(menu);
    }

    private void initialiseViewPager() {

        fragments = new Vector<Fragment>();
        fragments.add(MainListFragment.newInstance(null));
        fragments.add(FavoriteFragment.newInstance());

        this.pagerAdapter = new MyFragmentPagerAdapter(super.getSupportFragmentManager(), fragments);
        this.pager.setAdapter(pagerAdapter);
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;
        static final int NUM_ITEMS = 2;
        private FragmentManager mFragmentManager;
        private Fragment mFragmentAtPos0;

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.fragments = list;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return MainListFragment.newInstance(q);
                case 1:
                    return FavoriteFragment.newInstance();

                default: return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        public void replaceFragment(int i, SherlockFragment f){
            getSupportFragmentManager().beginTransaction().replace(R.id.pager,f).commit();
        }
    }

}
