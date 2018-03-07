package me.xiaocao.news.model.db;


import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * description: CollectionVo
 * author: lijun
 * date: 17/9/14 17:46
 */

@Entity
public class CollectionVo {
    public static final int sina=1;
    public static final int zhihu=2;
    public static final int guokr=3;


    @Id(autoincrement = true)
    private long id;
    private String title;
    private String src;
    private String url;
    private String imgUrl;
    private int type;//1,sina   2,zhihu    3,guokr


    @Generated(hash = 940671582)
    public CollectionVo(long id, String title, String src, String url,
            String imgUrl, int type) {
        this.id = id;
        this.title = title;
        this.src = src;
        this.url = url;
        this.imgUrl = imgUrl;
        this.type = type;
    }

    @Generated(hash = 2106617298)
    public CollectionVo() {
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
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
}
