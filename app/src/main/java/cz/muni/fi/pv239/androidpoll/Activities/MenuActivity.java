package cz.muni.fi.pv239.androidpoll.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cz.muni.fi.pv239.androidpoll.R;

/**
 * Created by Filip on 28.5.2016.
 */
public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void onCategoryClick(View v){
        Intent intent = new Intent(MenuActivity.this, AnswerActivity.class);
        startActivity(intent);
    }

    public void onCreateClick(View v){
        Intent intent = new Intent(MenuActivity.this, CreateActivity.class);
        startActivity(intent);
    }

    public void onMyPollsClick(View v){
        Intent intent = new Intent(MenuActivity.this,OwnPollsActivity.class);
        startActivity(intent);
    }
}