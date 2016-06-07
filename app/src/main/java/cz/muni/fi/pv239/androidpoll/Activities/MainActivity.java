package cz.muni.fi.pv239.androidpoll.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.securepreferences.SecurePreferences;

import cz.muni.fi.pv239.androidpoll.R;
import cz.muni.fi.pv239.androidpoll.SharedPrefsContainer;

/**
 * Created by Filip on 26.5.2016.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SecurePreferences preferences = SharedPrefsContainer.getSharedPreferences(this);
        if(preferences.contains("username") && preferences.contains("password")){
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Strawberry Polls");
    }

    public void onLogInClick(View v){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void onRegisterClick(View v){
        Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);
    }
}
