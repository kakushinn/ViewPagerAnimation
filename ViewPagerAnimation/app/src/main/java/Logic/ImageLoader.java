package Logic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by guochen on 2016/11/01.
 */
public class ImageLoader {

    private ImageView mImageView;
    private String mUrl;
    private  LruCache<String,Bitmap> lruCache;

    public ImageLoader(){
        //获取最大的可用内存
        int maxMemory = (int)Runtime.getRuntime().maxMemory();

        //设置缓存的Size
        int cacheSize = maxMemory/4;

        lruCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    public Bitmap getBitmapFromCache(String url){
        return lruCache.get(url);
    }

    public void addBitmapToCache(String url, Bitmap bitmap){
        if(getBitmapFromCache(url) == null)
            lruCache.put(url, bitmap);
    }

    public void loadImage(ImageView newsImageView, final String url) {
        mImageView = newsImageView;
        mUrl = url;
        new Thread(){
            @Override
            public void run() {
                super.run();
                Bitmap bitmap = getBitmapFromCache(url);
                if(bitmap == null){
                    bitmap = getBitmapFromUrl(url);
                    if(bitmap != null)
                        addBitmapToCache(url, bitmap);
                }
                Message msg = Message.obtain();
                msg.obj = bitmap;
                handler.sendMessage(msg);
            }
        }.start();
    }

    private Bitmap getBitmapFromUrl(String url) {
        Bitmap bitmap;
        InputStream inputStream = null;

        try {
            URL imageURL = new URL(url);
            try {
                HttpURLConnection connection = (HttpURLConnection) imageURL.openConnection();
                inputStream = new BufferedInputStream(connection.getInputStream());
                bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }catch (MalformedURLException e){
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(mImageView.getTag() != null && mImageView.getTag().equals(mUrl)){
                Bitmap bitmap = (Bitmap) msg.obj;
                mImageView.setImageBitmap(bitmap);
            }
        }
    };
}
