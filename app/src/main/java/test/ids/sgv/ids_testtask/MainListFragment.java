package test.ids.sgv.ids_testtask;

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

import com.actionbarsherlock.app.SherlockFragment;

import java.util.List;


public class MainListFragment extends SherlockFragment implements LoaderManager.LoaderCallbacks<List<ResultWrapper>> {

    private ListView mListView;
    private int currentFirstVisibleItem;
    private int currentVisibleItemCount;
    private int currentScrollState;
    private boolean isLoading;
    private LoaderHandler mHandler;
    private SearchEngine searchEngine;
    private MainAdapter mainAdapter;
    private static final int LIST_LOADER_CONST = 1;
    private LayoutInflater inflater;
    private View footer;
    private static final String TAG = MainListFragment.class.getSimpleName();

    static MainListFragment newInstance(int page) {
        MainListFragment pageFragment = new MainListFragment();
        return pageFragment;
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
        footer = view.findViewById(R.id.progress);
        mHandler = new LoaderHandler();
        mListView = (ListView) view.findViewById(R.id.list);
        mainAdapter = new MainAdapter(getActivity());
        mListView.setAdapter(mainAdapter);
        searchEngine = new SearchEngine("pig");
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                currentScrollState = scrollState;
                isScrollCompleted();
            }

            @Override
            public void onScroll(AbsListView absListView, int first, int visibleItemCount, int totalItemCount) {
                currentFirstVisibleItem = first;
                currentVisibleItemCount = visibleItemCount;
            }
        });

        getActivity().getSupportLoaderManager().initLoader(LIST_LOADER_CONST,null,this);
        return view;
    }


//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public void onPrepareOptionsMenu(Menu menu) {
//        super.onPrepareOptionsMenu(menu);
//    }

    @Override
    public Loader<List<ResultWrapper>> onCreateLoader(int id, Bundle args) {
//        if(id == LIST_LOADER_CONST){
            Log.d(MainListFragment.class.getSimpleName(), "OnCreateLoader");
            return new DataLoader(getActivity(),searchEngine);
    }

    @Override
    public void onLoadFinished(Loader<List<ResultWrapper>> loader, List<ResultWrapper> data) {
        mainAdapter.setResultWrapperList(data);
        Log.d(MainListFragment.class.getSimpleName(), "OnLoaderFinished");
    }

    @Override
    public void onLoaderReset(Loader<List<ResultWrapper>> loader) {
        mainAdapter.setResultWrapperList(null);
        Log.d(MainListFragment.class.getSimpleName(), "OnLoaderReset");
    }

    private void isScrollCompleted() {
        if (this.currentVisibleItemCount > 0 && this.currentScrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && !isLoading) {
            Log.d(TAG, "End Of List");
            isLoading = true;
//            mListView.addFooterView(footer);
            //load data here
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
    }

    private class LoaderHandler extends Handler {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {
                Log.d(TAG,"Load data");
                List<ResultWrapper> wrapperList = (List<ResultWrapper>) msg.obj;
                MainAdapter  mainAdapter1 = (MainAdapter) mListView.getAdapter();
                mainAdapter1.addResults(wrapperList);
                isLoading = false;
//                mListView.removeFooterView(footer);
            }
        }
    }

}
