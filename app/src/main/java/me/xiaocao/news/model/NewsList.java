package me.xiaocao.news.model;

import java.io.Serializable;

/**
 * description: NewsList
 * author: lijun
 * date: 17/8/27 16:04
 */

public class NewsList implements Serializable{

    /**
     * src : 环球网
     * weburl : http://news.sina.com.cn/c/nd/2017-08-27/doc-ifykiqfe1966426.shtml
     * time : 2017-08-27 13:14
     * pic : http://api.jisuapi.com/news/upload/201708/27160004_28022.jpg
     * title : 世界级一代宗师去世 领导人致电哀悼
     * category : news
     * content :
     * url : http://news.sina.cn/gn/2017-08-27/detail-ifykiqfe1966426.d.html?vt=4&pos=108
     */

    private String src;
    private String weburl;
    private String time;
    private String pic;
    private String title;
    private String category;
    private String content;
    private String url;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
