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
    public List<News> _list;
    public NewsListAdapter.MyRecyclerViewItemClickListener myRecyclerViewItemClickListener = null;
    public SwipeRefreshLayout swipeRefreshLayout = null;
    public LinearLayout imageLinearLayout;

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
        imageLinearLayout = (LinearLayout)view.findViewById(R.id.topImageLayout);
        newsListView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        newsListView.setLayoutManager(mLayoutManager);
        newsListView.setItemAnimator(new DefaultItemAnimator());

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new HotNewsAsyncTask().execute("http://v.juhe.cn/toutiao/index?type=tiyu&key=b69cc2e92edc5b582eba0a94c51173c8");

            }
        });
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
}
