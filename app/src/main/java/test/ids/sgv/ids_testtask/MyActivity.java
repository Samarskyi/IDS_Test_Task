package test.ids.sgv.ids_testtask;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.SearchView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

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
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        pager = (ViewPager) findViewById(R.id.pager);
//        initialiseViewPager();
    }

    @Override
    public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
        super.onCreateOptionsMenu(menu);
        getSupportMenuInflater().inflate(R.menu.menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        if (null != searchView) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
        }
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
//                adapter.getFilter().filter(newText);
                Log.d(TAG, newText);
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
//                adapter.getFilter().filter(query);
                Log.d(TAG, query);
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        return true;

    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save:
                Log.d(TAG, "SAVE");
                return true;

            default: return super.onMenuItemSelected(featureId, item);
        }

    }

    private void initialiseViewPager() {

        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(MainListFragment.newInstance(0));
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
