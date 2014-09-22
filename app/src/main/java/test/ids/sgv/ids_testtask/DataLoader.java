package test.ids.sgv.ids_testtask;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

/**
 * Created by sgv on 22.09.2014.
 */
public class DataLoader extends AsyncTaskLoader<List<ResultWrapper>> {

    private SearchEngine searchEngine;
    private String query;

    public DataLoader(Context context,String query) {
        super(context);
        this.query = query;
        searchEngine = new SearchEngine(query);
        Log.d(DataLoader.class.getSimpleName(), "DATA LOADER IS CREATED");
    }

    @Override
    public List<ResultWrapper> loadInBackground() {
        Log.d(DataLoader.class.getSimpleName(), "LOAD IN BACKGROUND IS BEGINNING");
        List<ResultWrapper> wrapperList = searchEngine.getResult();
        Log.d(DataLoader.class.getSimpleName(), "LOAD IN BACKGROUND IS FINISHED");
        return wrapperList;
    }
}
