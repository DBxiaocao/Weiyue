package x.lib.http.exception;

/**
 * description: ServerException
 * author: lijun
 * date: 17/12/27 11:15
 */

public class ServerException extends RuntimeException {
    private int code;
    private String msg;

    public ServerException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
