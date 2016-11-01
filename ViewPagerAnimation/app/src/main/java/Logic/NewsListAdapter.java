package Logic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guochen.viewpageranimation.R;

import java.util.List;

import Entities.News;

/**
 * Created by guochen on 2016/11/01.
 */
public class NewsListAdapter extends BaseAdapter {
    private Context context;
    private List<News> list;
    private LayoutInflater inflater;
    private ImageLoader imageLoader;
    public NewsListAdapter(Context context, List<News> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder;

        if(convertView == null){
            convertView = inflater.from(context).inflate(R.layout.news_list_item,null);
            viewHolder = new MyViewHolder();
            viewHolder.newsImageView = (ImageView) convertView.findViewById(R.id.news_image);
            viewHolder.newsTitleText = (TextView) convertView.findViewById(R.id.news_title);
            viewHolder.newsDateText = (TextView) convertView.findViewById(R.id.news_date);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (MyViewHolder) convertView.getTag();
        }
        String url = list.get(position).getThumbnail_pic();
        viewHolder.newsImageView.setTag(url);
        viewHolder.newsImageView.setImageResource(R.mipmap.ic_launcher);
        new ImageLoader().loadImage(viewHolder.newsImageView, url);
        viewHolder.newsTitleText.setText(list.get(position).getTitle());
        viewHolder.newsDateText.setText(list.get(position).getDate().substring(0,10));
        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    class MyViewHolder{
        public ImageView newsImageView;
        public TextView newsTitleText;
        public TextView newsDateText;
    }
}
