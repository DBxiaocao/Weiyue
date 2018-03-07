package me.xiaocao.news.model.event;

import x.lib.ui.BaseEvent;

/**
 * description: ReadDetailEvent
 * author: lijun
 * date: 17/9/8 22:58
 */

public class ReadDetailEvent extends BaseEvent{
    public ReadDetailEvent(int code, Object data, String id) {
        super(code, data, id);
    }
}
