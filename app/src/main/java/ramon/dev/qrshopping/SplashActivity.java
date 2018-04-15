package ramon.dev.qrshopping;

/**
 * Created by ramondev on 4/14/18.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_TIME_OUT = 4000;
    private static final String FIRST_TIME = "firstTime";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final Intent i = new Intent(SplashActivity.this, MainActivity.class);

        SharedPreferences settings = getSharedPreferences(FIRST_TIME,0);

        if(settings.getString("first","").isEmpty()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences settings = getSharedPreferences(FIRST_TIME,0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("first","notFirst");
                    editor.commit();
                    startActivity(i);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(i);
                    finish();
                }
            }, 1000);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("SCAN","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("SCAN","onPause");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
