package test.ids.sgv.ids_testtask;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sgv on 21.09.2014.
 */
public class FavoriteFragment extends Fragment {

    static FavoriteFragment newInstance(int page) {
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
        return view;
    }

}
