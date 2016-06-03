package cz.muni.fi.pv239.androidpoll.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cz.muni.fi.pv239.androidpoll.R;

/**
 * Created by Filip on 26.5.2016.
 */
public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    public void onProceedRegistrationClick(View v){
        Intent intent = new Intent(RegistrationActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}