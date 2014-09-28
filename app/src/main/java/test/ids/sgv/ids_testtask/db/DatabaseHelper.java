package test.ids.sgv.ids_testtask.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by sgv on 28.09.2014.
 */
public class DatabaseHelper extends SQLiteOpenHelper implements BaseColumns {

    private static final String DATABASE_NAME = "ids.db";
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_TABLE_MAIN = "list";
    public static final String DATABASE_TABLE_FAVORITE = "favoriteList";

    public static final String URL_COLUMN = "URL";
    public static final String TITLE_COLUMN = "TITLE";

    private static final String CREATE_MAIN_SCRIPT = "create table "
            + DATABASE_TABLE_MAIN + " (" + BaseColumns._ID+ " integer primary key autoincrement, "
            + URL_COLUMN + " text not null, "
            + TITLE_COLUMN + " text not null "
            + " );";

    private static final String CREATE_FAVORITE_SCRIPT = "create table "
            + DATABASE_TABLE_FAVORITE + " (" + BaseColumns._ID+ " integer primary key autoincrement, "
            + URL_COLUMN + " text not null, "
            + TITLE_COLUMN + " text not null "
            + " );";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL(CREATE_MAIN_SCRIPT);
        sqLiteDatabase.execSQL(CREATE_FAVORITE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
