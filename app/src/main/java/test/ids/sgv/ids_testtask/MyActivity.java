package test.ids.sgv.ids_testtask;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

import java.util.List;
import java.util.Vector;


public class MyActivity extends SherlockFragmentActivity {

    static final String TAG = "myLogs";
    ViewPager pager;
    MyFragmentPagerAdapter pagerAdapter;
    List<Fragment> fragments;
//    MyFragmentPagerAdapter pagerAdapter2;
    String q;
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
        super.onCreateOptionsMenu(menu);
//        getSupportMenuInflater().inflate(R.menu.menu, menu);
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
//        if (null != searchView) {
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//            searchView.setIconifiedByDefault(false);
//        }
//        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
//            public boolean onQueryTextChange(String newText) {
////                adapter.getFilter().filter(newText);
//                Log.d(TAG, newText);
//                return true;
//            }
//
//            public boolean onQueryTextSubmit(String query) {
////
//                Log.d(TAG, query);
//                q = query;
////                pagerAdapter.replaceFragment(0,MainListFragment.newInstance(query));
//                return true;
//            }
//        };
//        searchView.setOnQueryTextListener(queryTextListener);
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

        fragments = new Vector<Fragment>();
        fragments.add(MainListFragment.newInstance(null));
        fragments.add(FavoriteFragment.newInstance(0));

        this.pagerAdapter = new MyFragmentPagerAdapter(super.getSupportFragmentManager(), fragments);
        this.pager.setAdapter(pagerAdapter);
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;
        static final int NUM_ITEMS = 2;
        private  FragmentManager mFragmentManager;
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
                    return FavoriteFragment.newInstance(0);

//            return fragments.get(position);
                default: return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        public void replaceFragment(int i, SherlockFragment f){
//            int containerViewId = getResources().getIdentifier("pager", "id", getPackageName());
            getSupportFragmentManager().beginTransaction().replace(R.id.pager,f).commit();
        }
    }

}
