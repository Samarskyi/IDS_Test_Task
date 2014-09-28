package test.ids.sgv.ids_testtask.model;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

import test.ids.sgv.ids_testtask.R;

/**
 * Created by sgv on 21.09.2014.
 */
public class MainAdapter extends BaseAdapter {

    private List<ResultWrapper> resultWrapperList ;
    private Context context;
    private LayoutInflater lInflater;
    private CheckBoxListener checkBoxListener;
    private static final String TAG = MainAdapter.class.getSimpleName();

    public MainAdapter(Context context){
        this.context = context;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        checkBoxListener = new CheckBoxListener();
    }

    @Override
    public int getCount() {
        int result = 0;
        if(resultWrapperList != null){
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
//        if (view == null) {
            view = lInflater.inflate(R.layout.list_item, parent, false);
//        }
        ImageView mImageView = (ImageView) view.findViewById(R.id.img);
        TextView title = (TextView) view.findViewById(R.id.title);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(checkBoxListener);
        checkBox.setTag(position);
        ResultWrapper resultWrapper = resultWrapperList.get(position);
        Picasso.with(context)
                .load(resultWrapper.getUrl())
                .resize(50, 50)
                .centerCrop()
                .into(mImageView);
        Picasso.with(context).load(resultWrapper.getUrl()).into(mImageView);
        title.setText(resultWrapper.getTitle());
        return view;
    }

    public void setResultWrapperList(List<ResultWrapper> list){
        this.resultWrapperList = list;
        this.notifyDataSetChanged();
    }

    public void addResults(List<ResultWrapper> list){
        if(list != null){
            resultWrapperList.addAll(list);
            this.notifyDataSetChanged();
//            this.notifyDataSetInvalidated();
        }
//        check();
    }

    public void check(){
        for (ResultWrapper  resultWrapper: resultWrapperList){
            for (ResultWrapper  resultWrapper2: resultWrapperList){
               if(resultWrapper.getUrl().equals(resultWrapper2.getUrl())){
                   Log.e(TAG, resultWrapper.getUrl() + " ---  ");
               }
            }
        }
    }

    public List<ResultWrapper> getChosenItems(){
        List<ResultWrapper> resultWrapperListCopy = new ArrayList<ResultWrapper>();
        Log.d(TAG,resultWrapperList.size()+" - size of original list");
        for(ResultWrapper resultWrapper : resultWrapperList){
            if(resultWrapper.isChecked()){
                Log.d(TAG,resultWrapper.isChecked()+"");
                resultWrapperListCopy.add(resultWrapper);
            }
        }
        return resultWrapperListCopy;
    }

    public void deselect(){
        for(ResultWrapper resultWrapper : resultWrapperList){
            if(resultWrapper.isChecked()){
                resultWrapper.setChecked(false);
            }
        }
    }

    private class CheckBoxListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            resultWrapperList.get((Integer) compoundButton.getTag()).setChecked(b);
            Log.d(TAG, compoundButton.getTag() + " - number, isChecked - "+ b);
        }
    }

}
