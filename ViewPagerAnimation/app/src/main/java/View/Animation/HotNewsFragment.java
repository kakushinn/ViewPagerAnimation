package View.Animation;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guochen.viewpageranimation.R;

import java.util.List;

import Entities.News;
import Logic.TopNewsService;

/**
 * Created by guochen on 2016/10/27.
 */
public class HotNewsFragment extends android.support.v4.app.Fragment {

    public TextView tv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("hot","This is hot news");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.text_test, null);
        tv = (TextView)view.findViewById(R.id.test);
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
        protected void onPostExecute(List<News> newses) {
            super.onPostExecute(newses);
            tv.setText(newses.get(0).getTitle());
        }
    }

}
