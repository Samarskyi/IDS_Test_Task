package test.ids.sgv.ids_testtask.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import test.ids.sgv.ids_testtask.model.ResultWrapper;

/**
 * Created by sgv on 28.09.2014.
 */
public class DAO  {

    private static final String TAG = DAO.class.getSimpleName();
    private Context mContext;
    private SQLiteDatabase mSqLiteDatabase, mSqLiteDatabaseWrite;

    private DatabaseHelper mDbHelper;


    public DAO (Context context){
        this.mContext = context;
        mDbHelper = new DatabaseHelper(mContext);
        mSqLiteDatabase = mDbHelper.getReadableDatabase();
    }

    public void insertResultWrapper(ResultWrapper resultWrapper){
        ContentValues newValues = new ContentValues();

        newValues.put(DatabaseHelper.URL_COLUMN, resultWrapper.getUrl());
        newValues.put(DatabaseHelper.TITLE_COLUMN, resultWrapper.getTitle());

        mSqLiteDatabaseWrite = mDbHelper.getWritableDatabase();
        mSqLiteDatabaseWrite.insert(DatabaseHelper.DATABASE_TABLE_FAVORITE, null, newValues);
        mSqLiteDatabaseWrite.close();
    }

    public void insertResultWrapper(List<ResultWrapper> resultWrapperList){
        mSqLiteDatabaseWrite = mDbHelper.getWritableDatabase();

        for(ResultWrapper wrapper: resultWrapperList ){

            ContentValues newValues = new ContentValues();
            newValues.put(DatabaseHelper.URL_COLUMN, wrapper.getUrl());
            newValues.put(DatabaseHelper.TITLE_COLUMN, wrapper.getTitle());
            mSqLiteDatabaseWrite.insert(DatabaseHelper.DATABASE_TABLE_FAVORITE, null, newValues);

        }
        mSqLiteDatabaseWrite.close();
    }

    public List<ResultWrapper> getFavorite(){
        Log.d(TAG, "getFavorite");
        ArrayList<ResultWrapper> list = new ArrayList<ResultWrapper>();

        Cursor cursor =  mSqLiteDatabase.query(DatabaseHelper.DATABASE_TABLE_FAVORITE,
                new String[]{DatabaseHelper.URL_COLUMN, DatabaseHelper.TITLE_COLUMN},
                null, null,
                null, null, null) ;

        while (cursor.moveToNext()){
            String url = cursor.getString(cursor.getColumnIndex(DatabaseHelper.URL_COLUMN));
            String title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TITLE_COLUMN));

            list.add(new ResultWrapper(url, title));
        }
        cursor.close();
        return list;
    }

    public void close(){
        mSqLiteDatabase.close();
        mSqLiteDatabaseWrite.close();
        mDbHelper.close();
    }

}
