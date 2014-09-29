package test.ids.sgv.ids_testtask.ui;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

import test.ids.sgv.ids_testtask.model.ResultWrapper;
import test.ids.sgv.ids_testtask.model.SearchEngine;

/**
 * Created by sgv on 22.09.2014.
 */
public class DataLoader extends AsyncTaskLoader<List<ResultWrapper>> {

    private static final String TAG = DataLoader.class.getSimpleName();
    private SearchEngine searchEngine;
    private String query;
    private List<ResultWrapper> wrapperList;

    public DataLoader(Context context, SearchEngine searchEngine) {
        super(context);
//        this.query = query;
        this.searchEngine = searchEngine;
        Log.d(TAG, "DATA LOADER IS CREATED");
    }

    @Override
    public List<ResultWrapper> loadInBackground() {
        if (searchEngine != null) {
            List<ResultWrapper> wrapperList = searchEngine.getResult();
            this.wrapperList = wrapperList;
            return this.wrapperList;
        } else return null;
    }

    @Override
    public void deliverResult(List<ResultWrapper> listOfData) {
        if (isReset()) {
            // An async query came in while the loader is stopped.  We
            // don't need the result.
            if (listOfData != null) {
                onReleaseResources(listOfData);
            }
        }
        List<ResultWrapper> oldApps = listOfData;
        wrapperList = listOfData;

        if (isStarted()) {
            // If the Loader is currently started, we can immediately
            // deliver its results.
            super.deliverResult(listOfData);
        }

        // At this point we can release the resources associated with
        // 'oldApps' if needed; now that the new result is delivered we
        // know that it is no longer in use.
        if (oldApps != null) {
            onReleaseResources(oldApps);
        }
    }

    /**
     * Handles a request to start the Loader.
     */
    @Override
    protected void onStartLoading() {
        if (wrapperList != null) {
            // If we currently have a result available, deliver it
            // immediately.
            deliverResult(wrapperList);
        }


        if (takeContentChanged() || wrapperList == null) {
            // If the data has changed since the last time it was loaded
            // or is not currently available, start a load.
            forceLoad();
        }
    }

    /**
     * Handles a request to stop the Loader.
     */
    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    /**
     * Helper function to take care of releasing resources associated
     * with an actively loaded data set.
     */
    protected void onReleaseResources(List<ResultWrapper> apps) {
    }
}
