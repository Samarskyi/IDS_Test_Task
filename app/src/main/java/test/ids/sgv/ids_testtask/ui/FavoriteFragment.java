package test.ids.sgv.ids_testtask.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import test.ids.sgv.ids_testtask.R;
import test.ids.sgv.ids_testtask.db.DAO;

/**
 * Created by sgv on 21.09.2014.
 */
public class FavoriteFragment extends Fragment {

    private DAO dao;
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
        Log.d(TAG,"On CREATE FAVORITE ");
        Log.d(TAG, dao.getFavorite().size() + " favorite list size ");
//        Log.d(TAG, dao.getFavorite().toString());
        return view;
    }

}
