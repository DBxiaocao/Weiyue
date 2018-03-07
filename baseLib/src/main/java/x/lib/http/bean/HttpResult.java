package x.lib.http.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/** 
 * description: HttpResult
 * author: lijun
 * date: 17/12/10 下午8:45
*/
public class HttpResult<T> implements Serializable {
    @SerializedName("result")
    private T result;
    @SerializedName("message")
    private String description;
    @SerializedName("code")
    private int state;


    public void setResult(T result) {
        this.result = result;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setState(int state) {
        this.state = state;
    }

    public T getResult() {
        return result;
    }

    public String getDescription() {
        return description;
    }

    public int getState() {
        return state;
    }

    public boolean isSuccess() {
        return state == 0;
    }
}
