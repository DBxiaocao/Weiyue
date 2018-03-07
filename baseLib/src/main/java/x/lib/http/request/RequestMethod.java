package x.lib.http.request;

/**
 * description: RequestMethod
 * author: lijun
 * date: 17/12/27 16:07
 */

public enum RequestMethod {
    GET("GET"),

    POST("POST"),

    PUT("PUT"),

    MOVE("MOVE"),

    COPY("COPY"),

    DELETE("DELETE"),

    HEAD("HEAD"),

    PATCH("PATCH"),

    OPTIONS("OPTIONS"),

    TRACE("TRACE"),

    CONNECT("CONNECT");


    private final String value;

    RequestMethod(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public boolean allowRequestBody() {
        switch (this) {
            case POST:
            case PUT:
            case PATCH:
            case DELETE:
                return true;
            default:
                return false;
        }
    }
}
