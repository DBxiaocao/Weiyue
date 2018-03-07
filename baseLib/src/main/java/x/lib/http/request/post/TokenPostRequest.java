package x.lib.http.request.post;


/**
 * className: PostRequest
 * author: lijun
 * date: 17/6/29 15:39
 */

public abstract class TokenPostRequest extends PostRequest {
    public String token;

    public TokenPostRequest(String token) {
        this.token = token;
    }

}
