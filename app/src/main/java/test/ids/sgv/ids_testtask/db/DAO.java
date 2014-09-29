package test.ids.sgv.ids_testtask.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import test.ids.sgv.ids_testtask.Utils;
import test.ids.sgv.ids_testtask.model.ResultWrapper;

/**
 * Created by sgv on 28.09.2014.
 */
public class DAO {

    private static final String TAG = DAO.class.getSimpleName();
    private Context mContext;
    private SQLiteDatabase mSqLiteDatabase, mSqLiteDatabaseWrite;

    private DatabaseHelper mDbHelper;
    private static String pathToImages;

    public DAO(Context context) {
        this.mContext = context;
        mDbHelper = new DatabaseHelper(mContext);
//        mSqLiteDatabase = mDbHelper.getReadableDatabase();
        pathToImages = Environment.getExternalStorageDirectory() +
                "/" + mContext.getApplicationContext().getPackageName() + "/images/";
    }

    public DAO createDatabase() throws SQLException {
        try {
            mDbHelper.createDataBase();
        } catch (IOException mIOException) {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public DAO openReadable() {
        try {
            mDbHelper.openDataBase();
            mSqLiteDatabase = mDbHelper.getReadableDatabase();
        } catch (SQLException mSQLException) {
            Log.e(TAG, "openReadable readable >>" + mSQLException.toString());

        }
        return this;
    }

    public DAO openWritable() {
        try {
            mDbHelper.openDataBase();
            mSqLiteDatabase = mDbHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            Log.e(TAG, "openReadable writable >>" + mSQLException.toString());

        }
        return this;
    }

    public void insertResultWrapper(List<ResultWrapper> resultWrapperList) {

        for (ResultWrapper wrapper : resultWrapperList) {

            ContentValues newValues = new ContentValues();
            newValues.put(DatabaseHelper.URL_COLUMN, wrapper.getUrl());
            newValues.put(DatabaseHelper.TITLE_COLUMN, Utils.getFileName(wrapper.getUrl()));
            newValues.put(DatabaseHelper.PATH_COLUMN,  Utils.getNameFromUrl(wrapper.getUrl()));
            mSqLiteDatabase.insert(DatabaseHelper.DATABASE_TABLE_FAVORITE, null, newValues);
        }
        Log.d(TAG, "data write");
        mSqLiteDatabase.close();
    }

    public Cursor getFavoriteCursor() {
        Log.d(TAG, "getFavorite cursor");

        Cursor cursor = mSqLiteDatabase.query(DatabaseHelper.DATABASE_TABLE_FAVORITE,
                new String[]{DatabaseHelper.ID_COLUMN, DatabaseHelper.URL_COLUMN, DatabaseHelper.TITLE_COLUMN, DatabaseHelper.PATH_COLUMN},
                null, null,
                null, null, null);
        return cursor;
    }

    public void close() {
        mSqLiteDatabase.close();
        mSqLiteDatabaseWrite.close();
        mDbHelper.close();
    }

}
