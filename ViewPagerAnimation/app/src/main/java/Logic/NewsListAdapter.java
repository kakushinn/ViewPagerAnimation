package Logic;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<News> list;
    private LayoutInflater inflater;
    private MyRecyclerViewItemClickListener mListener;
    private ImageLoader imageLoader;
    public interface MyRecyclerViewItemClickListener{
        public void onItemClick(View view, int position);
    }

    public NewsListAdapter(Context context, List<News> list) {
        this.context = context;
        this.list = list;
        imageLoader = new ImageLoader();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ItemViewHolder viewHolder;
        View view = inflater.from(context).inflate(R.layout.news_list_item,null);
        viewHolder = new ItemViewHolder(view, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if(viewHolder instanceof ItemViewHolder){
            String url = list.get(i).getThumbnail_pic();
            ((ItemViewHolder)viewHolder).newsImageView.setTag(url);
            ((ItemViewHolder)viewHolder).newsImageView.setImageResource(R.mipmap.ic_launcher);
            imageLoader.loadImage(((ItemViewHolder) viewHolder).newsImageView, url);
            ((ItemViewHolder)viewHolder).newsTitleText.setText(list.get(i).getTitle());
            ((ItemViewHolder)viewHolder).newsDateText.setText(list.get(i).getDate().substring(0,10));
        }
    }

    public void setOnItemClickListener(MyRecyclerViewItemClickListener listener){
        this.mListener = listener;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView newsImageView;
        public TextView newsTitleText;
        public TextView newsDateText;
        public MyRecyclerViewItemClickListener itemListener;
        public ItemViewHolder(View itemView, MyRecyclerViewItemClickListener listener) {
            super(itemView);
            newsImageView = (ImageView)itemView.findViewById(R.id.news_image);
            newsTitleText = (TextView)itemView.findViewById(R.id.news_title);
            newsDateText = (TextView)itemView.findViewById(R.id.news_date);
            this.itemListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(itemListener != null){
                itemListener.onItemClick(v,getPosition());
            }
        }
    }
}
