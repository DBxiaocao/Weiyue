package me.xiaocao.news.model.request;

import me.xiaocao.news.app.Api;
import x.lib.http.request.get.GetRequest;

/**
 * description: HomeListRequest
 * author: lijun
 * date: 18/1/4 19:55
 */

public class HomeListRequest extends GetRequest {

    private int page;
    private String date;
    private boolean isRefresh;


    public HomeListRequest setPage(int page) {
        this.page = page;
        return this;
    }


    public HomeListRequest setDate(String date) {
        this.date = date;
        return this;
    }

    public HomeListRequest setIsRefresh(boolean refresh) {
        this.isRefresh = refresh;
        return this;
    }

    @Override
    public String url() {
        if (isRefresh) {
            return new StringBuffer()
                    .append(Api.JIEMIAN_HOST)
                    .append(Api.JIEMIAN_HEAD)
                    .append(Api.JIEMIAN_HOME_HEAD)
                    .append(Api.URL_HOME_REFRESH)
                    .append(Api.JIEMIAN_END)
                    .toString();
        } else {
            return new StringBuffer()
                    .append(Api.JIEMIAN_HOST)
                    .append(Api.JIEMIAN_HEAD)
                    .append(Api.JIEMIAN_HOME_HEAD)
                    .append(date)
                    .append("/0/")
                    .append(page)
                    .append("/36/")
                    .append(Api.JIEMIAN_END).toString();
        }
    }
}
