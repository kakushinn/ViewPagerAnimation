package Logic;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.guochen.viewpageranimation.NewsImageViewPager;
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
    public int currentIndex = 0;
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
    public int getItemViewType(int position) {
        if(position == 0){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if(i == 0){
            FirstItemViewHolder firstViewHolder;
            View view = inflater.from(context).inflate(R.layout.first_news_list_item,null);
            firstViewHolder = new FirstItemViewHolder(view);
            return firstViewHolder;
        }else{
            ItemViewHolder viewHolder;
            View view = inflater.from(context).inflate(R.layout.news_list_item,null);
            viewHolder = new ItemViewHolder(view, mListener);
            return viewHolder;
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int i) {
        if(viewHolder instanceof ItemViewHolder){
            String url = list.get(i).getThumbnail_pic();
            ((ItemViewHolder)viewHolder).newsImageView.setTag(url);
            ((ItemViewHolder)viewHolder).newsImageView.setImageResource(R.mipmap.ic_launcher);
            imageLoader.loadImage(((ItemViewHolder) viewHolder).newsImageView, url);
            ((ItemViewHolder)viewHolder).newsTitleText.setText(list.get(i).getTitle());
            ((ItemViewHolder)viewHolder).newsDateText.setText(list.get(i).getDate().substring(0,10));
        }else if(viewHolder instanceof FirstItemViewHolder){
            ((FirstItemViewHolder)viewHolder).initTopImageDots();
            ((FirstItemViewHolder)viewHolder).newsImageViewPager.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    if (list == null || list.size() == 0 || list.size() >= 3)
                        return 3;
                    else
                        return list.size();
                }

                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;
                }

                @Override
                public Object instantiateItem(ViewGroup container, final int position) {
                    ImageView imageView = new ImageView(context);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    if (position == 0)
                        imageView.setImageResource(R.mipmap.guide_image1);
                    else if (position == 1)
                        imageView.setImageResource(R.mipmap.guide_image2);
                    else
                        imageView.setImageResource(R.mipmap.guide_image3);
                    if (list != null && list.size() != 0) {
                        ImageLoader imageLoader = new ImageLoader();
                        String imageUrl = list.get(position).getThumbnail_pic_large();
                        imageView.setTag(imageUrl);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.VIEW");
                                Uri url = Uri.parse(list.get(position).getUrl());
                                intent.setData(url);
                                context.startActivity(intent);
                            }
                        });
                        imageLoader.loadImage(imageView, imageUrl);
                    }
                    container.addView(imageView);
                    return imageView;
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView((ImageView) object);
                }
            });

            ((FirstItemViewHolder)viewHolder).newsImageViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(final int position) {
                    ((FirstItemViewHolder)viewHolder).dots[position].setImageResource(R.mipmap.white_dot);
                    ((FirstItemViewHolder)viewHolder).dots[currentIndex].setImageResource(R.mipmap.dark_dot);
                    currentIndex = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            ((FirstItemViewHolder)viewHolder).mNewsImageHandler.sendEmptyMessageDelayed(0, 3000);
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

    class FirstItemViewHolder extends RecyclerView.ViewHolder
    {
        public NewsImageViewPager newsImageViewPager;
        public LinearLayout dotsLinearLayout;
        public ImageView[] dots = new ImageView[3];
        public NewsImageHandler mNewsImageHandler = new NewsImageHandler();

        public FirstItemViewHolder(View itemView) {
            super(itemView);
            newsImageViewPager = (NewsImageViewPager)itemView.findViewById(R.id.newsImageViewPager);
            dotsLinearLayout = (LinearLayout)itemView.findViewById(R.id.topImageLayout);
        }

        public void initTopImageDots()
        {
            for(int i = 0;i<dotsLinearLayout.getChildCount();i++)
            {
                dots[i] = (ImageView)(dotsLinearLayout.getChildAt(i));
            }
            currentIndex = 0;
            dots[0].setImageResource(R.mipmap.white_dot);
        }

        class NewsImageHandler extends Handler
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what)
                {
                    case 0:
                        int preIndex = newsImageViewPager.getCurrentItem();
                        currentIndex = (currentIndex+1)%3;
                        newsImageViewPager.setCurrentItem(currentIndex);
                        dots[currentIndex].setImageResource(R.mipmap.white_dot);
                        dots[preIndex].setImageResource(R.mipmap.dark_dot);
                        sendEmptyMessageDelayed(0,3000);
                        break;
                }
            }
        }
    }
}
