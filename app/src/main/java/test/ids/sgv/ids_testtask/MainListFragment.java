package test.ids.sgv.ids_testtask;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;


public class MainListFragment extends Fragment implements LoaderCallbacks<List<ResultWrapper>> {

    private ListView mListView;
//    private MyHandler mHandler;
    private int currentFirstVisibleItem;
    private int currentVisibleItemCount;
    private int currentScrollState;
    private boolean isLoading;
    private SearchEngine searchEngine;
    private MainAdapter mainAdapter;
    private static final int LIST_LOADER_CONST = 1;

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

        mListView = (ListView) view.findViewById(R.id.list);
        mainAdapter = new MainAdapter(getActivity());
        mListView.setAdapter(mainAdapter);

//        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
//                currentScrollState = scrollState;
////                isScrollCompleted();
//            }
//
//            @Override
//            public void onScroll(AbsListView absListView, int first, int visibleItemCount, int totalItemCount) {
//                currentFirstVisibleItem = first;
//                currentVisibleItemCount = visibleItemCount;
//            }
//        });
        getActivity().getSupportLoaderManager().initLoader(LIST_LOADER_CONST,null,this).forceLoad();
        return view;
    }


    @Override
    public Loader<List<ResultWrapper>> onCreateLoader(int id, Bundle args) {
//        if(id == LIST_LOADER_CONST){
            Log.d(MainListFragment.class.getSimpleName(), "OnCreateLoader");
            return new DataLoader(getActivity(),"dog");

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

}
