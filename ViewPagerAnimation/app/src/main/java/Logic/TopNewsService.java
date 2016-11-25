package Logic;

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import Entities.News;

/**
 * Created by guochen on 2016/10/28.
 */
public class TopNewsService {

    private String readStream(InputStream is){
        String result = "";
        try {
            String line = "";
            InputStreamReader inputStreamReader = new InputStreamReader(is,"utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            try {
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<News> getListFromJsonData(String url){
        List<News> newsList = new ArrayList<News>();
        try {
            String jsonString = readStream(new URL(url).openStream());
            JSONObject jsonObject;
            News newsBean;
            try {
                jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("data");

                for(int i=0;i<jsonArray.length();i++){
                    jsonObject = jsonArray.getJSONObject(i);
                    newsBean = new News();
                    newsBean.setTitle(jsonObject.getString("title"));
                    newsBean.setDate(jsonObject.getString("date"));
                    newsBean.setThumbnail_pic(jsonObject.getString("thumbnail_pic_s"));
                    newsBean.setUrl(jsonObject.getString("url"));
                    newsList.add(newsBean);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newsList;
    }

}
