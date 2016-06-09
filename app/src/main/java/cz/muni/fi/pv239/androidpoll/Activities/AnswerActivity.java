package cz.muni.fi.pv239.androidpoll.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.data.Entry;

import java.net.UnknownHostException;
import java.util.List;

import cz.muni.fi.pv239.androidpoll.Adapters.AnswerAdapter;
import cz.muni.fi.pv239.androidpoll.Entities.Category;
import cz.muni.fi.pv239.androidpoll.Entities.Option;
import cz.muni.fi.pv239.androidpoll.Entities.Question;
import cz.muni.fi.pv239.androidpoll.Managers.impl.OptionManagerImpl;
import cz.muni.fi.pv239.androidpoll.Managers.impl.QuestionManagerImpl;
import cz.muni.fi.pv239.androidpoll.Managers.interfaces.OptionManager;
import cz.muni.fi.pv239.androidpoll.Managers.interfaces.QuestionManager;
import cz.muni.fi.pv239.androidpoll.R;
import cz.muni.fi.pv239.androidpoll.ServerConnection.ServerResponse;
import cz.muni.fi.pv239.androidpoll.SharedPrefsContainer;
import rx.Observer;

/**
 * Created by Filip on 28.5.2016.
 */
public class AnswerActivity extends AppCompatActivity {
    private Context that=this;

    private Question question;
    private List<Option> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final Category category = new Category();
        category.setName(this.getIntent().getStringExtra("Category.name"));
        category.setId(this.getIntent().getLongExtra("Category.id", 0));
        toolbar.setTitle(category.getName()); // CATEGORY NAME
        QuestionManager manager= new QuestionManagerImpl();
        final Observer<ServerResponse<Question>> observer= new Observer<ServerResponse<Question>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if(e instanceof UnknownHostException) {
                    new AlertDialog.Builder(that)
                            .setTitle("Connection not found")
                            .setMessage("Connection to the internet was not found")
                            .setCancelable(false)

                            .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    recreate();
                                }
                            })

                            .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                    Intent intent = new Intent(Intent.ACTION_MAIN);
                                    intent.addCategory(Intent.CATEGORY_HOME);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            })

                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }

            @Override
            public void onNext(final ServerResponse<Question> questionServerResponse) {
                if(!questionServerResponse.isSuccessful()){
                    AnswerActivity.this.setContentView(R.layout.content_answer_empty);
                }else {

                    OptionManager optionManger = new OptionManagerImpl();
                    Observer<ServerResponse<List<Option>>> optionObserver = new Observer<ServerResponse<List<Option>>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(ServerResponse<List<Option>> optionList) {
                            AnswerAdapter answerAdapter = new AnswerAdapter(that, optionList.getData(), questionServerResponse.getData(), category);
                            ListView listView = (ListView) findViewById(R.id.answer_list_view);
                            listView.setAdapter(answerAdapter);
                        }
                    };
                    optionManger.getQuestionOptions(optionObserver, questionServerResponse.getData().getId());
                    TextView question2 = (TextView) findViewById(R.id.questionTextView);
                    question2.setText(questionServerResponse.getData().getQuestion());
                    question = questionServerResponse.getData();
                }
            }
        };
        manager.getRandomQuestion(observer, category,
                SharedPrefsContainer.getSharedPreferences(that).getString("username", ""),
                SharedPrefsContainer.getSharedPreferences(that).getString("password", ""));
    }

    public void onSkipClick(View v){
        QuestionManagerImpl manager = new QuestionManagerImpl();
        Observer<ServerResponse<Question>> observer = new Observer<ServerResponse<Question>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if(e instanceof UnknownHostException) {
                    new AlertDialog.Builder(that)
                            .setTitle("Connection not found")
                            .setMessage("Connection to the internet was not found")
                            .setCancelable(false)

                            .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    recreate();
                                }
                            })

                            .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                    Intent intent = new Intent(Intent.ACTION_MAIN);
                                    intent.addCategory(Intent.CATEGORY_HOME);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    System.exit(0);
                                }
                            })

                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }

            @Override
            public void onNext(ServerResponse<Question> listServerResponse) {

            }
        };
        manager.skipQuestion(observer,question,SharedPrefsContainer.getSharedPreferences(that).getString("username",null),SharedPrefsContainer.getSharedPreferences(that).getString("password",null));
        Intent intent = new Intent(AnswerActivity.this, AnswerActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(AnswerActivity.this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
