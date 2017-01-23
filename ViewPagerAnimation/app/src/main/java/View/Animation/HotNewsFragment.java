package View.Animation;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.guochen.viewpageranimation.NewsImageViewPager;
import com.example.guochen.viewpageranimation.R;

import java.util.List;

import Entities.News;
import Logic.ImageLoader;
import Logic.NewsListAdapter;
import Logic.TopNewsService;

/**
 * Created by guochen on 2016/10/27.
 */
public class HotNewsFragment extends android.support.v4.app.Fragment implements NewsListAdapter.MyRecyclerViewItemClickListener{

    public RecyclerView newsListView;
    public NewsImageViewPager viewpager;
    public List<News> _list;
    public NewsListAdapter.MyRecyclerViewItemClickListener myRecyclerViewItemClickListener = null;
    public SwipeRefreshLayout swipeRefreshLayout = null;
    public LinearLayout imageLinearLayout;
    public ImageView[] dots = new ImageView[3];
    public int currentIndex = 0;
    public NewsImageHandler mNewsImageHandler = new NewsImageHandler();

    class NewsImageHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 0:
                    int preIndex = viewpager.getCurrentItem();
                    currentIndex = (currentIndex+1)%3;
                    viewpager.setCurrentItem(currentIndex);
                    dots[currentIndex].setImageResource(R.mipmap.white_dot);
                    dots[preIndex].setImageResource(R.mipmap.dark_dot);
                    sendEmptyMessageDelayed(0,3000);
                    break;
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("hot", "This is hot news");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myRecyclerViewItemClickListener = this;
        View view = inflater.from(getActivity()).inflate(R.layout.news_screen, null);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshLayout);
        newsListView = (RecyclerView)view.findViewById(R.id.news_list);
        viewpager = (NewsImageViewPager)view.findViewById(R.id.newsImageViewPager);
        imageLinearLayout = (LinearLayout)view.findViewById(R.id.topImageLayout);
        newsListView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        newsListView.setLayoutManager(mLayoutManager);
        newsListView.setItemAnimator(new DefaultItemAnimator());
        initTopImageDots();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new HotNewsAsyncTask().execute("http://v.juhe.cn/toutiao/index?type=tiyu&key=b69cc2e92edc5b582eba0a94c51173c8");

            }
        });
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                dots[position].setImageResource(R.mipmap.white_dot);
                dots[currentIndex].setImageResource(R.mipmap.dark_dot);
                currentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mNewsImageHandler.sendEmptyMessageDelayed(0,3000);
        new HotNewsAsyncTask().execute("http://v.juhe.cn/toutiao/index?type=tiyu&key=b69cc2e92edc5b582eba0a94c51173c8");
        return view;
    }


    class HotNewsAsyncTask extends AsyncTask<String,Void,List<News>>{
        @Override
        protected List<News> doInBackground(String... params) {
            TopNewsService topNewsService = new TopNewsService();
            List<News> list = topNewsService.getListFromJsonData(params[0]);
            return list;
        }

        @Override
        protected void onPostExecute(final List<News> newses) {
            super.onPostExecute(newses);
            if(newses != null){
                NewsListAdapter adapter = new NewsListAdapter(getActivity(), newses);
                _list = newses;
                adapter.setOnItemClickListener(myRecyclerViewItemClickListener);
                newsListView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);

                viewpager.setAdapter(new PagerAdapter() {
                    @Override
                    public int getCount() {
                        if(newses == null || newses.size() == 0 || newses.size()>=3)
                            return 3;
                        else
                            return newses.size();
                    }

                    @Override
                    public boolean isViewFromObject(View view, Object object) {
                        return view == object;
                    }

                    @Override
                    public Object instantiateItem(ViewGroup container, final int position) {
                        ImageView imageView = new ImageView(getContext());
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        if(position == 0)
                            imageView.setImageResource(R.mipmap.guide_image1);
                        else if(position == 1)
                            imageView.setImageResource(R.mipmap.guide_image2);
                        else
                            imageView.setImageResource(R.mipmap.guide_image3);
                        if(newses != null && newses.size() != 0){
                            ImageLoader imageLoader = new ImageLoader();
                            String imageUrl = newses.get(position).getThumbnail_pic_large();
                            imageView.setTag(imageUrl);
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.setAction("android.intent.action.VIEW");
                                    Uri url = Uri.parse(newses.get(position).getUrl());
                                    intent.setData(url);
                                    startActivity(intent);
                                }
                            });
                            imageLoader.loadImage(imageView, imageUrl);
                        }
                        container.addView(imageView);
                        return imageView;
                    }

                    @Override
                    public void destroyItem(ViewGroup container, int position, Object object) {
                        container.removeView((ImageView)object);
                    }
                });
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri url = Uri.parse(_list.get(position).getUrl());
        intent.setData(url);
        startActivity(intent);
    }

    public void initTopImageDots()
    {
        for(int i = 0;i<imageLinearLayout.getChildCount();i++)
        {
            dots[i] = (ImageView)(imageLinearLayout.getChildAt(i));
        }
        currentIndex = 0;
        dots[0].setImageResource(R.mipmap.white_dot);
    }
}
