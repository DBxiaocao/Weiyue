package x.lib.http.request.get;

/**
 * className: GetRequest
 * author: lijun
 * date: 17/6/29 15:35
 */

public abstract class TokenGetRequest extends GetRequest{
    public String token;

    public TokenGetRequest(String token) {
        this.token = token;
    }

}
