package com.xiaocao.weiyue.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * description: Collection
 * author: xiaocao
 * date: 17/7/25 16:27
 */
@Entity
public class CollectionVo implements MultiItemEntity{
    public static final int TYPE_News = 1;
    public static final int TYPE_Video = 2;
    @Id(autoincrement = true)
    private long id;
    private String title;
    private String url;
    private String imgUrl;
    private int type;
    private String newsId;
    private String avatar;
    private String user;





    @Generated(hash = 1549094604)
    public CollectionVo(long id, String title, String url, String imgUrl, int type,
            String newsId, String avatar, String user) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.imgUrl = imgUrl;
        this.type = type;
        this.newsId = newsId;
        this.avatar = avatar;
        this.user = user;
    }

    @Generated(hash = 2106617298)
    public CollectionVo() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    @Override
    public int getItemType() {
        return type;
    }
}
