package test.ids.sgv.ids_testtask.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import test.ids.sgv.ids_testtask.R;
import test.ids.sgv.ids_testtask.Utils;
import test.ids.sgv.ids_testtask.ui.ImageViewerFragment;
import test.ids.sgv.ids_testtask.ui.MyActivity;

/**
 * Created by sgv on 21.09.2014.
 */
public class MainAdapter extends BaseAdapter {

    private List<ResultWrapper> resultWrapperList;
    private Context context;
    private LayoutInflater lInflater;
    private CheckBoxListener checkBoxListener;
    private static final String TAG = MainAdapter.class.getSimpleName();
    private static String pathToImages;

    public MainAdapter(Context context) {
        this.context = context;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        checkBoxListener = new CheckBoxListener();
        pathToImages = Environment.getExternalStorageDirectory() +
                "/" + this.context.getApplicationContext().getPackageName() + "/images/";
    }

    @Override
    public int getCount() {
        int result = 0;
        if (resultWrapperList != null) {
            result = resultWrapperList.size();
        }
        return result;
    }

    @Override
    public Object getItem(int i) {
        return resultWrapperList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.list_item, parent, false);
        }
        ImageView mImageView = (ImageView) view.findViewById(R.id.img);
        TextView title = (TextView) view.findViewById(R.id.title);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(checkBoxListener);
        checkBox.setTag(position);
        final ResultWrapper resultWrapper = resultWrapperList.get(position);
        checkBox.setChecked(resultWrapper.isChecked());
        Picasso.with(context)
                .load(resultWrapper.getUrl())
                .resize(70, 70)
                .centerCrop()
                .into(mImageView);
        title.setText(resultWrapper.getTitle());

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageViewerFragment newFragment =  ImageViewerFragment.newInstance(resultWrapper.getUrl());
                FragmentTransaction transaction = ((MyActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.pager, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }

    public void setResultWrapperList(List<ResultWrapper> list) {
        this.resultWrapperList = list;
        this.notifyDataSetChanged();
    }

    public void addResults(List<ResultWrapper> list) {
        if (list != null) {
            resultWrapperList.addAll(list);
            this.notifyDataSetChanged();
        }
    }

    public List<ResultWrapper> getChosenItems() {
        List<ResultWrapper> resultWrapperListCopy = new ArrayList<ResultWrapper>();
        Log.d(TAG, resultWrapperList.size() + " - size of original list");
        for (ResultWrapper resultWrapper : resultWrapperList) {
            if (resultWrapper.isChecked()) {
                Log.d(TAG, resultWrapper.isChecked() + "");
                resultWrapperListCopy.add(resultWrapper);
            }
        }
        return resultWrapperListCopy;
    }

    public void deselect() {
        for (ResultWrapper resultWrapper : resultWrapperList) {
            if (resultWrapper.isChecked()) {
                resultWrapper.setChecked(false);
            }
        }
    }

    public void saveImagesToSDCard(final List<ResultWrapper> chosenItems) {
        Thread saveImages = new Thread(new Runnable() {
            @Override
            public void run() {
                for (ResultWrapper resultWrapper : chosenItems) {
                    storeImage(resultWrapper.getUrl(), Utils.getNameFromUrl(resultWrapper.getUrl()));
                }
            }
        });
       saveImages.start();
    }

    public static Bitmap getImageByUrl(String url){

        Bitmap result = null;
        File imgFile = new File(url);
        if (imgFile.exists()) {
            result = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        }
        return result;
    }

    private boolean storeImage(String url, String filename) {

        URL url1 = null;
        Bitmap bmp = null;
        try {
            url1 = new URL(url);
            bmp = BitmapFactory.decodeStream(url1.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        //get path to external storage (SD card)
        String iconsStoragePath = pathToImages;
        File sdIconStorageDir = new File(iconsStoragePath);
        //create storage directories, if they don't exist
        sdIconStorageDir.mkdirs();

        try {
            String filePath = sdIconStorageDir.toString() + filename;
            Log.d(TAG, "save image to :" + filePath);
            File img = new File(sdIconStorageDir, filename);
            img.createNewFile();
//            filename = new File(path + "/folder/subfolder/image.jpg");
            FileOutputStream fileOutputStream = new FileOutputStream(img);
            BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
            //choose another format if JPG doesn't suit you
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);

            bos.flush();
            bos.close();

        } catch (FileNotFoundException e) {
            Log.e(TAG, "Error saving image file: " + e.getMessage());
            return false;
        } catch (IOException e) {
            Log.e(TAG, "Error saving image file: " + e.getMessage());
            return false;
        }

        return true;
    }

    private class CheckBoxListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            resultWrapperList.get((Integer) compoundButton.getTag()).setChecked(b);
            Log.d(TAG, compoundButton.getTag() + " - number, isChecked - " + b);
        }
    }

}
