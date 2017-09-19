package pickit.com.pickit.Models;

import android.app.Application;
import android.content.Context;

/**
 * Created by Tal on 13/08/2017.
 */

public class PIMyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getMyContext(){
        return context;
    }
}