package me.xiaocao.news.model;

import java.io.Serializable;

/**
 * description: Guokr
 * author: lijun
 * date: 17/9/8 22:11
 */

public class Guokr implements Serializable{
    private String title_hide;
    private int id;
    private String title;
    private String small_image;
    private String summary;
    private String resource_url;

    public String getResource_url() {
        return resource_url;
    }

    public void setResource_url(String resource_url) {
        this.resource_url = resource_url;
    }


    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle_hide() {
        return title_hide;
    }

    public void setTitle_hide(String title_hide) {
        this.title_hide = title_hide;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSmall_image() {
        return small_image;
    }

    public void setSmall_image(String small_image) {
        this.small_image = small_image;
    }

}
