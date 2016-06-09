package cz.muni.fi.pv239.androidpoll.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;

import java.net.UnknownHostException;
import java.util.List;

import cz.muni.fi.pv239.androidpoll.Adapters.AnswerAdapter;
import cz.muni.fi.pv239.androidpoll.Entities.Answer;
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
    private Category category = new Category();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.category.setName(this.getIntent().getStringExtra("Category.name"));
        this.category.setId(this.getIntent().getLongExtra("Category.id", 0));
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
                    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                    toolbar.setTitle(category.getName()); // CATEGORY NAME

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

        rl.setOnTouchListener(new View.OnTouchListener() {
            private int min_distance = 100;
            private float downX, downY, upX, upY;
            View v;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                this.v = v;
                switch (event.getAction()) { // Check vertical and horizontal touches
                    case MotionEvent.ACTION_DOWN: {
                        downX = event.getX();
                        downY = event.getY();
                        return true;
                    }
                    case MotionEvent.ACTION_UP: {
                        upX = event.getX();
                        upY = event.getY();

                        float deltaX = downX - upX;
                        float deltaY = downY - upY;

                        //HORIZONTAL SCROLL
                        if (Math.abs(deltaX) > Math.abs(deltaY)) {
                            if (Math.abs(deltaX) > min_distance) {
                                // left or right
                                if (deltaX < 0) {
                                    this.onLeftToRightSwipe();
                                    return true;
                                }
                                if (deltaX > 0) {
                                    this.onRightToLeftSwipe();
                                    return true;
                                }
                            } else {
                                //not long enough swipe...
                                return false;
                            }
                        }
                        //VERTICAL SCROLL
                        else {
                            if (Math.abs(deltaY) > min_distance) {
                                // top or down
                                if (deltaY < 0) {
                                    this.onTopToBottomSwipe();
                                    return true;
                                }
                                if (deltaY > 0) {
                                    this.onBottomToTopSwipe();
                                    return true;
                                }
                            } else {
                                //not long enough swipe...
                                return false;
                            }
                        }
                        return false;
                    }
                }
                return false;
            }

            public void onLeftToRightSwipe() {
            }

            public void onRightToLeftSwipe() {
                onSkipClick(null);
            }

            public void onTopToBottomSwipe() {
            }

            public void onBottomToTopSwipe() {
            }
        });
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
        manager.skipQuestion(observer, question, SharedPrefsContainer.getSharedPreferences(that).getString("username", null), SharedPrefsContainer.getSharedPreferences(that).getString("password", null));
        Intent intent = new Intent(AnswerActivity.this, AnswerActivity.class);
        intent.putExtra("Category.name",this.category.getName());
        intent.putExtra("Category.id", this.category.getId());
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
