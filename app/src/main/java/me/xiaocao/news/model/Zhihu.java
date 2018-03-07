package me.xiaocao.news.model;

import java.io.Serializable;
import java.util.List;

/**
 * description: Zhihu
 * author: lijun
 * date: 17/9/8 21:48
 */

public class Zhihu implements Serializable{

    /**
     * images : ["https://pic2.zhimg.com/v2-ac0eb60d599b39da74c83f958f9fed81.jpg"]
     * type : 0
     * id : 9603119
     * ga_prefix : 090820
     * title : 「也不懂体谅我，净给我添麻烦」愧疚吧？那你就被套路了
     */

    private int type;
    private int id;
    private String ga_prefix;
    private String title;
    private List<String> images;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
