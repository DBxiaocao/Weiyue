package x.lib.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 线程、Handler工具
 * Created by Rico on 2015/4/19.
 */
public class ThreadHelperFactory {

    private static final ThreadHelperFactory instance = new ThreadHelperFactory();

    private ThreadHelperFactory(){}

    public static ThreadHelperFactory getInstance(){
        return instance;
    }

    public  ThreadHelper createUIThreadHelper(Context context, Handler.Callback callback){
        return new ThreadHelper(context, callback);
    }
    public  ThreadHelper createUIThreadHelper(Context context){
        return new ThreadHelper(context);
    }

    public class ThreadHelper {

        private Handler handler;
        private Set<Runnable> threads = new HashSet<>();

        private ThreadHelper(Context context, Handler.Callback callback) {
            handler = new Handler(context.getMainLooper(), callback);
        }
        private ThreadHelper(Context context)
        {
            handler = new Handler(context.getMainLooper());
        }

        public void execute(Runnable runnable) {
            if (handler != null) {
//                if (!threads.contains(runnable)) {
                    threads.add(runnable);
//                }
                LogUtil.i("post thread");
                handler.post(runnable);
            }
        }

        public void executeDelay(Runnable runnable, long delay) {
            if (handler != null) {
//                if (!threads.contains(runnable)) {
                    threads.add(runnable);
//                }
                handler.postDelayed(runnable, delay);
            }
        }

        public void sendMessage(Message msg) {
            if (handler != null) {
                handler.sendMessage(msg);
            }
        }

        public void sendEmptyMessage(int what) {
            if (handler != null) {
                handler.sendEmptyMessage(what);
            }
        }

        public void sendMessageDelay(Message msg, long delay) {
            if (handler != null) {
                handler.sendMessageDelayed(msg, delay);
            }
        }

        public void sendEmptyMessageDelay(int what, long delay) {
            if (handler != null) {
                handler.sendEmptyMessageDelayed(what, delay);
            }
        }

        public void shutdown() {
            if (handler != null) {
                if (!threads.isEmpty()) {
                    for(Iterator<Runnable> it = threads.iterator(); it.hasNext();)
                    {
                        handler.removeCallbacks(it.next());
                    }
//                    for (int i = 0; i < threads.size(); i++) {
//                        handler.removeCallbacks(threads.get(i));
//                    }
                    threads.clear();
                }
                handler = null;
            }
        }
    }
}
