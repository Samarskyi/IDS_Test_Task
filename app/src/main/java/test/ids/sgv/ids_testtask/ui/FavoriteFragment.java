package test.ids.sgv.ids_testtask.ui;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

import test.ids.sgv.ids_testtask.R;
import test.ids.sgv.ids_testtask.db.DAO;
import test.ids.sgv.ids_testtask.model.FavoriteAdapter;

/**
 * Created by sgv on 21.09.2014.
 */
public class FavoriteFragment extends SherlockFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private DAO dao;
    private ListView mListView;
    private FavoriteAdapter mFavoriteAdapter;
    private static final int FAVORITE_LOADER_CONST = 2;

    private static final String TAG = FavoriteFragment.class.getSimpleName();

    static FavoriteFragment newInstance() {
        FavoriteFragment pageFragment = new FavoriteFragment();
        return pageFragment;
    }

    public FavoriteFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, null);

        dao = new DAO(getActivity());
        mListView = (ListView) view.findViewById(R.id.list);
        mFavoriteAdapter = new FavoriteAdapter(getActivity(),null);
        mListView.setAdapter(mFavoriteAdapter);

        Log.d(TAG,"On CREATE FAVORITE ");
        getActivity().getSupportLoaderManager().initLoader(FAVORITE_LOADER_CONST, null, this).forceLoad();
        return view;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(FavoriteFragment.class.getSimpleName(), "OnCreateLoader favorite");
        switch (id) {
            case FAVORITE_LOADER_CONST:
                Log.d(FavoriteFragment.class.getSimpleName(), "return FavoriteDataLoader");
                return new FavoriteDataLoader(getActivity());
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        try {
            if (cursor != null && cursor.getCount() > 0) {
                mFavoriteAdapter.swapCursor(cursor);
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mFavoriteAdapter.changeCursor(null);
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }
}
