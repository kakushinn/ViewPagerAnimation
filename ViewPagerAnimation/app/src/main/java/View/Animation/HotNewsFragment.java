package View.Animation;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.guochen.viewpageranimation.NewsImageViewPager;
import com.example.guochen.viewpageranimation.R;

import java.util.List;

import Entities.News;
import Logic.NewsListAdapter;
import Logic.TopNewsService;

/**
 * Created by guochen on 2016/10/27.
 */
public class HotNewsFragment extends android.support.v4.app.Fragment {

    public ListView newsListView;
    public NewsImageViewPager viewpager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("hot", "This is hot news");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.news_screen, null);
        newsListView = (ListView) view.findViewById(R.id.news_list);
        viewpager = (NewsImageViewPager) view.findViewById(R.id.newsImageViewPager);
        new HotNewsAsyncTask().execute("http://v.juhe.cn/toutiao/index?type=top&key=b69cc2e92edc5b582eba0a94c51173c8");
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
            newsListView.setAdapter(new NewsListAdapter(getActivity(), newses));
            newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri url = Uri.parse(newses.get(position).getUrl());
                    intent.setData(url);
                    startActivity(intent);
                }
            });
        }
    }

}
