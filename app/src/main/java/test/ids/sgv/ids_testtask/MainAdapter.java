package test.ids.sgv.ids_testtask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sgv on 21.09.2014.
 */
public class MainAdapter extends BaseAdapter {

    private List<ResultWrapper> resultWrapperList ;
    private Context context;
    private LayoutInflater lInflater;

    public MainAdapter(Context context){
        this.context = context;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public MainAdapter(Context context, List<ResultWrapper> list){
        this.context = context;
        this.resultWrapperList = list;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        if (view == null) {
            view = lInflater.inflate(R.layout.list_item, parent, false);
        }
        ImageView mImageView = (ImageView) view.findViewById(R.id.img);
        TextView title = (TextView) view.findViewById(R.id.title);

        ResultWrapper resultWrapper = resultWrapperList.get(position);

        Picasso.with(context)
                .load(resultWrapper.getUrl())
                .resize(100, 100)
                .centerCrop()
                .into(mImageView);
//        Picasso.with(context).load(resultWrapper.getUrl()).into(mImageView);
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
    }

}
