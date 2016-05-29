package cz.muni.fi.pv239.androidpoll.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cz.muni.fi.pv239.androidpoll.R;

/**
 * Created by Filip on 28.5.2016.
 */
public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_results);
    }
    public void onMenuClick(View v){
        Intent intent = new Intent(ResultsActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    public void onNextClick(View v){
        Intent intent = new Intent(ResultsActivity.this, AnswerActivity.class);
        startActivity(intent);
    }
}