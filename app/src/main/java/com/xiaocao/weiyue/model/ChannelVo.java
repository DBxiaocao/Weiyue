package com.xiaocao.weiyue.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * description: ChannelVo
 * author: xiaocao
 * date: 17/7/22 11:10
 */
@Entity
public class ChannelVo {

    public static final int TYPE_TOP = 1;
    public static final int TYPE_BOTTOM = 2;
    @Id(autoincrement = true)
    private long id;
    private String newsId;
    private String title;
    private int type;
    @Generated(hash = 1970588381)
    public ChannelVo(long id, String newsId, String title, int type) {
        this.id = id;
        this.newsId = newsId;
        this.title = title;
        this.type = type;
    }

    @Generated(hash = 1129819947)
    public ChannelVo() {
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
