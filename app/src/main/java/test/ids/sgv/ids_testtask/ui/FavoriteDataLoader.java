package test.ids.sgv.ids_testtask.ui;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import test.ids.sgv.ids_testtask.db.DAO;

/**
 * Created by sgv on 28.09.2014.
 */
class FavoriteDataLoader extends AsyncTaskLoader<Cursor> {

    private DAO mDAO;

    public FavoriteDataLoader(Context context) {
        super(context);
        mDAO = new DAO(context);
        mDAO.createDatabase().openReadable();
    }

    @Override
    public Cursor loadInBackground() {
        Log.d(FavoriteDataLoader.class.getSimpleName(), "loading in background");
        return mDAO.getFavoriteCursor();
    }
}
