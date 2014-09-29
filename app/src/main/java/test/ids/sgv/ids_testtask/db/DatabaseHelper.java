package test.ids.sgv.ids_testtask.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by sgv on 28.09.2014.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "main.sqlite";
    private static final int DATABASE_VERSION = 3;

//    public static final String DATABASE_TABLE_MAIN = "list";
    public static final String DATABASE_TABLE_FAVORITE = "favoriteList";

    private static String DB_PATH = "";
    private static String DB_NAME = "main.sqlite";

    public static final String ID_COLUMN    = "_ID";
    public static final String URL_COLUMN   = "URL";
    public static final String TITLE_COLUMN = "TITLE";
    public static final String PATH_COLUMN  = "PATH";

    private SQLiteDatabase mDataBase;
    private final Context mContext;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.mContext = context;
    }

    public void createDataBase() throws IOException {
        //If database not exists copy it from the assets

        boolean mDataBaseExist = checkDataBase();
        if (!mDataBaseExist) {
            this.getReadableDatabase();
            this.close();
            try {
                //Copy the database from assests
                copyDataBase();
                Log.e(TAG, "createDatabase database created");
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    //Check that the database exists here: /data/data/your package/databases/Da Name
    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    //Copy the database from assets
    private void copyDataBase() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public boolean openDataBase() throws SQLException {
        String mPath = DB_PATH + DB_NAME;
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDataBase != null;
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null && mDataBase.isOpen())
            mDataBase.close();
        super.close();
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, Context mContext) {
        super(context, name, factory, version);
        this.mContext = mContext;
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version, DatabaseErrorHandler errorHandler, Context mContext) {
        super(context, name, factory, version, errorHandler);
        this.mContext = mContext;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
  //    sqLiteDatabase.rawQuery(CREATE_FAVORITE_SCRIPT,null);
//        Log.d("CREATE SCRIPT",CREATE_FAVORITE_SCRIPT);
//        sqLiteDatabase.execSQL(CREATE_FAVORITE_SCRIPT);
//        sqLiteDatabase.rawQuery("insert into " + DATABASE_TABLE_FAVORITE + " values(1, 'taralal' , 'tytytyty')",null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

}
