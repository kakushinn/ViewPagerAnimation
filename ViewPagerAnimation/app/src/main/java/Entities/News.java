package Entities;

/**
 * Created by guochen on 2016/10/27.
 */
public class News {

    public News() {
    }

    public News(String title, String date, String thumbnail_pic, String url, String thumbnail_pic_large) {
        this.title = title;
        this.date = date;
        this.thumbnail_pic = thumbnail_pic;
        this.url = url;
        this.thumbnail_pic_large = thumbnail_pic_large;
    }

    private String title ;
    private String date;
    private String thumbnail_pic;
    private String url;
    private String thumbnail_pic_large;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getThumbnail_pic() {
        return thumbnail_pic;
    }

    public void setThumbnail_pic(String thumbnail_pic) {
        this.thumbnail_pic = thumbnail_pic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail_pic_large() {
        return thumbnail_pic_large;
    }

    public void setThumbnail_pic_large(String thumbnail_pic_large) {
        this.thumbnail_pic_large = thumbnail_pic_large;
    }
}
