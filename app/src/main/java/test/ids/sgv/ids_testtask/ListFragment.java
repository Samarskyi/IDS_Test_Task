package test.ids.sgv.ids_testtask;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.List;


public class ListFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<ResultWrapper>>{

    private ListView mListView;
    private MyHandler mHandler;
    private int currentFirstVisibleItem;
    private int currentVisibleItemCount;
    private int currentScrollState;
    private boolean isLoading;
    private SearchEngine searchEngine;
    private MainAdapter mainAdapter;
    private static final int LIST_LOADER_CONST = 1;
    static ListFragment newInstance(int page) {
        ListFragment pageFragment = new ListFragment();
        return pageFragment;
    }

    public ListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, null);
        mHandler = new MyHandler();
        isLoading = false;
        mListView = (ListView) view.findViewById(R.id.list);
        searchEngine = new SearchEngine("cat");

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

        return view;
    }

    private void isScrollCompleted() {
        if (this.currentVisibleItemCount > 0 && this.currentScrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
//            if(!isLoading){
//                isLoading = true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<ResultWrapper> wrapperList = searchEngine.getResult();
                        Message message = mHandler.obtainMessage();
                        message.obj = wrapperList;
                        message.what = 2;
                        mHandler.sendMessage(message);
                    }
                }).start();
//            }
        }
    }

    @Override
    public Loader<List<ResultWrapper>> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<ResultWrapper>> listLoader, List<ResultWrapper> resultWrappers) {

    }

    @Override
    public void onLoaderReset(Loader<List<ResultWrapper>> listLoader) {

    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {
                Log.d("11111111", "!!!!!!!!!!");
                List<ResultWrapper> wrapperList = (List<ResultWrapper>) msg.obj;
//                for (ResultWrapper resultWrapper : wrapperList) {
//                    Log.d(SearchEngine.class.getSimpleName(), resultWrapper.toString());
//                }
                mainAdapter = new MainAdapter(getActivity(), wrapperList);
                mListView.setAdapter(mainAdapter);
            }

            if (msg.what == 2) {
                List<ResultWrapper> wrapperList = (List<ResultWrapper>) msg.obj;
                mainAdapter.addResults(wrapperList);
            }
        }
    }

}
