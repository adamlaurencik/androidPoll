package cz.muni.fi.pv239.androidpoll.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.List;

import cz.muni.fi.pv239.androidpoll.Entities.Option;
import cz.muni.fi.pv239.androidpoll.Entities.Question;
import cz.muni.fi.pv239.androidpoll.Managers.impl.QuestionManagerImpl;
import cz.muni.fi.pv239.androidpoll.Managers.interfaces.QuestionManager;
import cz.muni.fi.pv239.androidpoll.R;
import cz.muni.fi.pv239.androidpoll.ServerConnection.ServerResponse;
import rx.Observer;

/**
 * Created by Filip on 28.5.2016.
 */
public class AnswerActivity extends AppCompatActivity {

    private Question question;
    private List<Option> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        QuestionManager manager= new QuestionManagerImpl();
        Observer<ServerResponse<Question>> observer= new Observer<ServerResponse<Question>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ServerResponse<Question> questionServerResponse) {

            }
        };
       // manager.getRandomQuestion(observer,);
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
