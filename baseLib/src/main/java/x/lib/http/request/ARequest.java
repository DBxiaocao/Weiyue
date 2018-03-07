package x.lib.http.request;


import java.util.Map;


public abstract class ARequest {

//    public abstract RequestMethod method();
    public abstract Map<String,String> params();
    public abstract String url();

}
