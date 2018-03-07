package me.xiaocao.news.model;

import java.util.List;

/**
 * description: ZhihuResult
 * author: lijun
 * date: 18/1/3 20:40
 */

public class ZhihuResult<T> {
    /**
     * date : 20180103
     * stories : []
     * top_stories : []
     */

    private String date;
    private T stories;
    private T top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public T getStories() {
        return stories;
    }

    public void setStories(T stories) {
        this.stories = stories;
    }

    public T getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(T top_stories) {
        this.top_stories = top_stories;
    }


}
