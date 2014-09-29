package test.ids.sgv.ids_testtask.model;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;

import test.ids.sgv.ids_testtask.R;
import test.ids.sgv.ids_testtask.db.DatabaseHelper;

/**
 * Created by sgv on 28.09.2014.
 */
public class FavoriteAdapter extends CursorAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    String path;

    public FavoriteAdapter(Context context, Cursor cursor) {
        super(context, cursor, FLAG_REGISTER_CONTENT_OBSERVER);
        mContext = context;

        path = Environment.getExternalStorageDirectory() +
                "/" + mContext.getApplicationContext().getPackageName() + "/images/";
    }

    public FavoriteAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        boolean isCursor = cursor == null;
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.favorite_list_item, viewGroup, false);
        return v;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView title = (TextView) view.findViewById(R.id.title);
        ImageView image = (ImageView) view.findViewById(R.id.img);
        title.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TITLE_COLUMN)));
        Log.d("Name", cursor.getString(3));
        Log.d("Path", path + cursor.getString(3));
        Picasso.with(context)
                .load(new File(path + cursor.getString(3)))
                .resize(70, 70)
                .centerCrop()
                .into(image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO show big image in new fragment
            }
        });

    }
}
