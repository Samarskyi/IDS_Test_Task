package test.ids.sgv.ids_testtask.ui;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SearchView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import java.util.List;

import test.ids.sgv.ids_testtask.R;
import test.ids.sgv.ids_testtask.db.DAO;
import test.ids.sgv.ids_testtask.model.MainAdapter;
import test.ids.sgv.ids_testtask.model.ResultWrapper;
import test.ids.sgv.ids_testtask.model.SearchEngine;


public class MainListFragment extends SherlockFragment implements LoaderManager.LoaderCallbacks<List<ResultWrapper>> {

    private ListView mListView;
    private int currentFirstVisibleItem;
    private int currentVisibleItemCount;
    private int currentScrollState;
    private boolean isLoading;
    private LoaderHandler mHandler;
    private SearchEngine searchEngine;
    private MainAdapter mainAdapter;
    private DAO dao;
    private static final int LIST_LOADER_CONST = 1;
    private static final String TAG = MainListFragment.class.getSimpleName();

    static MainListFragment newInstance(String query) {
        MainListFragment mainListFragment = new MainListFragment();
        Bundle data = new Bundle();
        data.putString("query", query);
        mainListFragment.setArguments(data);
        return mainListFragment;
    }

    public MainListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, null);
        mHandler = new LoaderHandler();
        dao = new DAO(getActivity());
        mListView = (ListView) view.findViewById(R.id.list);
        mainAdapter = new MainAdapter(getActivity());
        mListView.setAdapter(mainAdapter);
        String query = getArguments().getString("query");
        if (query != null) {
            searchEngine = new SearchEngine(query);
        }
        searchEngine = null;
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
//                currentScrollState = scrollState;
//                isScrollCompleted();
            }

            @Override
            public void onScroll(AbsListView absListView, int first, int visibleItemCount, int totalItemCount) {
//                currentFirstVisibleItem = first;
//                currentVisibleItemCount = visibleItemCount;
                if (first + visibleItemCount >= totalItemCount - 1) {
                    isScrollCompleted();
                }
            }
        });
        this.setHasOptionsMenu(true);
        getActivity().getSupportLoaderManager().initLoader(LIST_LOADER_CONST, null, this);
        return view;
    }

    @Override
    public Loader<List<ResultWrapper>> onCreateLoader(int id, Bundle args) {
        Log.d(MainListFragment.class.getSimpleName(), "OnCreateLoader mainList");

        switch (id) {
            case LIST_LOADER_CONST:
                return new DataLoader(getActivity(), searchEngine);

            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<List<ResultWrapper>> loader, List<ResultWrapper> data) {
        if (data != null) {
            mainAdapter.setResultWrapperList(data);
        }
        Log.d(TAG, "OnLoaderFinished");
    }

    @Override
    public void onLoaderReset(Loader<List<ResultWrapper>> loader) {
        mainAdapter.setResultWrapperList(null);
        Log.d(TAG, "OnLoaderReset");
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        MyActivity activity = (MyActivity) getActivity();
        activity.getSupportMenuInflater().inflate(R.menu.menu, menu);
        SearchManager searchManager = (SearchManager) activity.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        if (null != searchView) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(activity.getComponentName()));
            searchView.setIconifiedByDefault(false);
        }
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, newText);
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, query);
                searchEngine = new SearchEngine(query);
                MainListFragment.this.getLoaderManager().restartLoader(LIST_LOADER_CONST, null, MainListFragment.this);
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save:
                Log.d(TAG, "SAVE");
                dao.createDatabase().openWritable();
                dao.insertResultWrapper(mainAdapter.getChosenItems());
                mainAdapter.saveImagesToSDCard(mainAdapter.getChosenItems());
                mainAdapter.deselect();
                mainAdapter.notifyDataSetChanged();
                return true;

            default:
                return false;
        }
    }

    private void isScrollCompleted() {

        Log.d(TAG, "End Of List");
        //load data here
        if (searchEngine != null) {
            isLoading = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<ResultWrapper> wrapperList = searchEngine.getResult();
                    Message message = mHandler.obtainMessage();
                    message.obj = wrapperList;
                    message.what = 1;
                    mHandler.sendMessage(message);
                }
            }).start();
        }
//        }
    }

    private class LoaderHandler extends Handler {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {
                Log.d(TAG, "Load data");
                List<ResultWrapper> wrapperList = (List<ResultWrapper>) msg.obj;
                MainAdapter mainAdapter1 = (MainAdapter) mListView.getAdapter();
                mainAdapter1.addResults(wrapperList);
                isLoading = false;
//                mListView.removeFooterView(footer);
            }
        }
    }

}
