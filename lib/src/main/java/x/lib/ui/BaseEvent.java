package x.lib.ui;

/**
 * className: BaseEvent
 * author: lijun
 * date: 17/6/30 18:20
 */

public class BaseEvent {
    // 消息 Code
    public int code;
    // 消息对象
    public Object data;

    public String id;
    public static final int code_success = 200;
    public static final int code_refresh = 201;
    public static final int code_load = 202;
    public static final int code_load_err=203;
    public static final int code_err = 400;

    public BaseEvent(int code, Object data, String id) {
        this.code = code;
        this.data = data;
        this.id = id;
    }
}
