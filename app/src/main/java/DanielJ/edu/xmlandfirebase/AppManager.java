package DanielJ.edu.xmlandfirebase;

import android.app.Application;

import com.beardedhen.androidbootstrap.TypefaceProvider;

/**
 * Created by Jakars on 09/06/2017.
 */


//Registered in the manifest tag in the name attribute.
    //Init stuff once for an app.
public class AppManager extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Font Awesome fa_android
        //http://fontawsome.io/cheatsheet/
        TypefaceProvider.registerDefaultIconSets();
    }
}
