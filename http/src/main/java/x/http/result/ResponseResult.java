package x.http.result;


/**
 * 网络请求返回数据类
 */
public class ResponseResult {
    public boolean success;
    public String result;
    public String msg = "";
    public boolean isLastPage;
    public String totalPrice;


    public ResponseResult() {
    }

    public ResponseResult setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public ResponseResult setResult(String result) {
        this.result = result;
        return this;
    }

    public ResponseResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "success=" + success +
                ", result='" + result + '\'' +
                ", msg=" + msg +
                '}';
    }
}