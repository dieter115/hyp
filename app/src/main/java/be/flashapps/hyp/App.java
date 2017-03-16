package be.flashapps.hyp;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.multidex.MultiDex;

import com.orhanobut.logger.Logger;
import com.pixplicity.easyprefs.library.Prefs;

import net.danlew.android.joda.JodaTimeAndroid;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by dietervaesen on 9/11/16.
 */

public class App extends Application {

    private static App instance;
    private static Context mContext;

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    /*private static final String TWITTER_KEY = "zz8pOkrwAppQmAPhu720XJRhF";
    private static final String TWITTER_SECRET = "Bf3AvptHL30JWENqSljtLZgWfIkhk02jYgPG0GrMRw3Ex6nmfY";*/

    @Override
    public void onCreate() {
        super.onCreate();

        Logger.init("App")                 // default PRETTYLOGGER or use just init()
                .methodCount(3)                 // default 2
                .methodOffset(2);                // default 0
        //initialize here otherwise it doesn't know  all android time zones
        JodaTimeAndroid.init(this);


       /* LeakCanary.install(this);*/
        /*if (!BuildConfig.DEBUG) {
        CrashlyticsCore core = new CrashlyticsCore.Builder().build();
        Fabric.with(this, new Crashlytics.Builder().core(core).build(), new Crashlytics());
        }*/
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);


    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);//getlanguage is from app and getdisplaylanguage is from device
        MultiDex.install(this);
        mContext = this;
    }


    public static Context getContext() {
        return mContext;
    }

    public static App getInstance() {
        return instance;
    }
}
