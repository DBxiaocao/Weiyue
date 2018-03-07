package me.xiaocao.news.app;

/**
 * description: Api
 * author: lijun
 * date: 17/8/27 14:33
 */

public class Api {
    //新闻源 sina
    public static final String NEWS_HOST = "https://way.jd.com";
    public static final String NEWS_CHANNEL = "/jisuapi/channel";
    public static final String NEWS_LIST = "/jisuapi/get";
    public static final String NEWS_SEARCH = "/jisuapi/newSearch";
    public static final String NEWS_KEY = "40ccd6db5739a4ad7759e65e502ce336";
    //网易新闻
    public static final String WANGYI_HOST = "http://c.m.163.com/";
    public static final String WANGYI_URL = "/full.html";
    public static final String HTTP_NEWS_HEAD = "nc/article/list/";
    public static final String NEWS_DETAIL = "nc/article/";
    //果壳
    // http://www.guokr.com/apis/minisite/article.json?retrieve_type=by_channel&channel_key=techb&offset=0  频道
    // http://www.guokr.com/apis/minisite/article.json?retrieve_type=by_subject&limit=10&offset=0&subject_key=physics  栏目
    public static final String GUOKR_HOST = "http://www.guokr.com/";
    public static final String GUOKR_LIST = "/apis/minisite/article.json";
    //详情  http://www.guokr.com/apis/minisite/article/442387.json
    public static final String GUOKR_DETAIL = "/apis/minisite/article";
    public static final String GUOKR_JSON = ".json";



    //知乎日报  https://news-at.zhihu.com/api/4/news/before/20170909
    public static final String ZHIHU_HOST = "https://news-at.zhihu.com";
    public static final String ZHIHU_NEWS_LIST = "/api/4/news/latest";//最新列表
    public static final String ZHIHU_LAST_LIST = "/api/4/news/before/";//之前列表 20170909
    public static final String ZHIHU_MSG_DETAIL = "/api/4/news/";//详情  9606157  id


    //网易视频
    public static final String VIDEO_HOST = "http://c.m.163.com/";
    public static final String END_URL = "-20.html";
    public static final String HTTP_VIDEO_HEAD = "nc/video/list/";


    //图片源
    public static final String PIC_URL = "http://gank.io/api/data/福利/10/";


    //界面新闻
    public static final String JIEMIAN_HOST="https://appapi.jiemian.com";
    public static final String JIEMIAN_HOST_DETAIL="http://www.jiemian.com/article/";
    public static final String DETAIL_END=".html";
    public static final String JIEMIAN_HEAD="/v4/5.3.0/10001/";
    public static final String JIEMIAN_HOME_HEAD="cate/main/";
    public static final String JIEMIAN_END="3609.json ";
    public static final String URL_HOME_REFRESH="0/0/1/36/";
}
