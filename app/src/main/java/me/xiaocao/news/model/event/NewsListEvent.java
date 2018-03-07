package me.xiaocao.news.model.event;

import x.lib.ui.BaseEvent;

/**
 * description: NewsListEvent
 * author: lijun
 * date: 17/8/27 15:08
 */

public class NewsListEvent extends BaseEvent{
    public NewsListEvent(int code, Object data, String id) {
        super(code, data, id);
    }
}
