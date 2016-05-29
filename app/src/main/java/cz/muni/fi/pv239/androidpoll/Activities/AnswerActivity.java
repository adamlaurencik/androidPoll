package cz.muni.fi.pv239.androidpoll.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cz.muni.fi.pv239.androidpoll.R;

/**
 * Created by Filip on 28.5.2016.
 */
public class AnswerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_answer);
    }
    public void onProceedAnswerClick(View v){
        Intent intent = new Intent(AnswerActivity.this, ResultsActivity.class);
        startActivity(intent);
    }
    public void onSkipClick(View v){
        Intent intent = new Intent(AnswerActivity.this, AnswerActivity.class);
        startActivity(intent);
    }
}
