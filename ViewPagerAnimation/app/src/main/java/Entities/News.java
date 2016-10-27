package Entities;

/**
 * Created by guochen on 2016/10/27.
 */
public class News {

    public News(String title, String date, String thumbnail_pic, String url) {
        this.title = title;
        this.date = date;
        this.thumbnail_pic = thumbnail_pic;
        this.url = url;
    }

    private String title ;
    private String date;
    private String thumbnail_pic;
    private String url;

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
}
