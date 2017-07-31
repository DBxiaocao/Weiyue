package com.xiaocao.weiyue.model.event;

import x.lib.ui.BaseEvent;

/**
 * className: NewsDetailEvent
 * author: lijun
 * date: 17/7/7 12:12
 */

public class NewsDetailEvent extends BaseEvent {
    public NewsDetailEvent(int code, Object data, String id) {
        super(code, data, id);
    }
}
