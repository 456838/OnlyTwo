package com.duowan.onlyone;

import android.content.Context;
import android.content.res.Configuration;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.qihoo360.replugin.RePlugin;
import com.salton123.base.ApplicationBase;

import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

/**
 * User: 巫金生(newSalton@163.com)
 * Date: 2017/5/25 22:27
 * Description:
 * Update:
 */
public class SaltonApplication extends ApplicationBase {

    @Override
    public void onCreate() {
        super.onCreate();
        // 栈视图功能，大大降低Fragment的开发难度，建议在Application里初始化
        Fragmentation.builder()
                // 设置 栈视图 模式为 悬浮球模式   SHAKE: 摇一摇唤出   NONE：隐藏
                .stackViewMode(Fragmentation.BUBBLE)
                // ture时，遇到异常："Can not perform this action after onSaveInstanceState!"时，会抛出
                // false时，不会抛出，会捕获，可以在handleException()里监听到
                .debug(BuildConfig.DEBUG)
                // 线上环境时，可能会遇到上述异常，此时debug=false，不会抛出该异常（避免crash），会捕获
                // 建议在回调处上传至我们的Crash检测服务器
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
//                         以Bugtags为例子: 手动把捕获到的 Exception 传到 Bugtags 后台。
//                         Bugtags.sendException(e);
                    }
                })
                .install();
        // ======= REPLUGIN =======
        RePlugin.App.onCreate();
        // Open the debug function
        RePlugin.enableDebugger(this,true);
        // ========================

        TypefaceProvider.registerDefaultIconSets();
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        // ======= REPLUGIN =======
        RePlugin.App.attachBaseContext(this);
        // ========================
    }



    @Override
    public void onLowMemory() {
        super.onLowMemory();

        // ======= REPLUGIN =======
        RePlugin.App.onLowMemory();
        // ========================
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        // ======= REPLUGIN =======
        RePlugin.App.onTrimMemory(level);
        // ========================
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // ======= REPLUGIN =======
        RePlugin.App.onConfigurationChanged(newConfig);
        // ========================
    }

}
