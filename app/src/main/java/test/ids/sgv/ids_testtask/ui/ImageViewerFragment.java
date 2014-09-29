package test.ids.sgv.ids_testtask.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;

import test.ids.sgv.ids_testtask.R;

/**
 * Created by sgv on 29.09.2014.
 */
public class ImageViewerFragment extends SherlockFragment {

    private ImageView mImageView;

    public static ImageViewerFragment newInstance(String query) {
        ImageViewerFragment mainListFragment = new ImageViewerFragment();
        Bundle data = new Bundle();
        data.putString("url", query);
        mainListFragment.setArguments(data);
        return mainListFragment;
    }

    public ImageViewerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.image_viewer_fragment, null);
        String url = getArguments().getString("url");
        Log.d(ImageViewerFragment.class.getSimpleName(), url);

        mImageView = (ImageView) view.findViewById(R.id.image);
//        Picasso.with(getActivity())
//                .load(url)
//                .resize(75,75)
//                .skipMemoryCache()
//                .centerCrop()
//                .into(mImageView);

        return view;
    }


}
