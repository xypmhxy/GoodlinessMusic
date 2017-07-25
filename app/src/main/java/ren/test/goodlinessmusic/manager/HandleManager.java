package ren.test.goodlinessmusic.manager;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Administrator on 2017/07/25 0025
 */

public class HandleManager extends Handler {
    private static HandleManager handler;
    public static final int CURRENT_POSITION = 0;

    private HandleManager() {
    }

    public synchronized static HandleManager getInstance() {
        if (handler == null)
            handler = new HandleManager();
        return handler;
    }

    @Override
    public void handleMessage(Message msg) {
        if (listener != null)
            listener.onHandleMessage(msg);
        super.handleMessage(msg);
    }

    public interface onHandleMessageListener {
        void onHandleMessage(Message msg);
    }

    public onHandleMessageListener listener;

    public void setOnHandleMessageListener(onHandleMessageListener listener) {
        this.listener = listener;
    }
}
