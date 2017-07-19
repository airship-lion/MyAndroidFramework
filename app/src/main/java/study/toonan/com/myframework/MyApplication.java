package study.toonan.com.myframework;

import android.app.Activity;
import android.app.Application;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/8/26.
 */
public class MyApplication extends Application {
    private static MyApplication application;
    public static ArrayList<Activity> activityList = new ArrayList<Activity>();
    //获取到主线程的handler
    private static Handler mMainThreadHanler;
    //获取到主线程
    private static Thread mMainThread;
    //获取到主线程的id
    private static int mMainThreadId;
    protected Toast mToast;

    public static MyApplication getInstance() {
        return application;
    }

    public static Handler getMainThreadHandler() {
        return mMainThreadHanler;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        initThread();
    }



    private void initThread() {
        this.mMainThreadHanler = new Handler();
        this.mMainThread = Thread.currentThread();
        //获取到调用线程的id
        this.mMainThreadId = android.os.Process.myTid();
    }

    /**
     * 获取dbutil，务必用这个方法获取，不要用其他的方法（否则更新数据库时可能会清空用户数据）
     *
     * @return
     */
//    public DbUtils getDbUtils() {
////        return DbUtils.create(this,getApplicationContext().getDatabasePath(MyAppConfig.DIR_APP_DATABASE_NAME).getAbsolutePath(), MyAppConfig.DIR_APP_DATABASE_NAME,
////                MyAppConfig.dbVersion, this);
//        return DbUtils.create(this, MyAppConfig.DIR_APP_DATA, MyAppConfig.DIR_APP_DATABASE_NAME,
//                MyAppConfig.dbVersion, this);
//    }

    /**
     * 结束指定的Activity
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            this.activityList.remove(activity);
            activity.finish();
        }
    }

    /**
     * 应用退出，结束所有的activity
     */
    public void exit() {
        for (Activity activity : activityList) {
            if (activity != null) {
                activity.finish();
            }
        }
        System.exit(0);
    }


//    /**
//     * 回到首页
//     */
//    public void goHome() {
//        for (Activity activity : activityList) {
//            if (activity != null) {
//                if (!(activity instanceof HomeActivity)) {
//                    activity.finish();
//                }
//            }
//        }
//    }


    public void showToast(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    public void showLongToast(String msg) {
        showToast(msg, Toast.LENGTH_LONG);
    }

    private void showToast(String msg, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(this, msg, duration);
        } else {
            mToast.setText(msg);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

    public View inflate(int resId) {
        return View.inflate(this, resId, null);
    }

    public static long getMainThreadId() {
        return mMainThreadId;
    }



}
