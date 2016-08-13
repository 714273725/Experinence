package ye.jian.ge;

import android.app.Application;

/**
 * Created by Administrator on 2016/5/30.
 */
public class BaseContext {
    static Application mApp;

    public static Application getInstance() {
        checkNull();
        return mApp;
    }

    public static void initialize(Application application) {
        mApp = application;
    }

    public static void checkNull() {
        if (mApp == null) {
            throw new NullPointerException("Stub,app not initialize!");
        }
    }
}