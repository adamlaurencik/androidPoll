package cz.muni.fi.pv239.androidpoll.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.securepreferences.SecurePreferences;

import java.net.UnknownHostException;
import java.util.List;

import cz.muni.fi.pv239.androidpoll.Adapters.OwnQuestionAdapter;
import cz.muni.fi.pv239.androidpoll.Entities.Question;
import cz.muni.fi.pv239.androidpoll.Managers.impl.QuestionManagerImpl;
import cz.muni.fi.pv239.androidpoll.Managers.interfaces.QuestionManager;
import cz.muni.fi.pv239.androidpoll.R;
import cz.muni.fi.pv239.androidpoll.ServerConnection.ServerResponse;
import cz.muni.fi.pv239.androidpoll.SharedPrefsContainer;
import rx.Observer;

/**
 * Created by Filip on 28.5.2016.
 */
public class OwnPollsActivity extends AppCompatActivity {
    Context that = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_polls);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(SharedPrefsContainer.getSharedPreferences(that).getString("username", "Your") + "´s Polls");

        SecurePreferences preferences = SharedPrefsContainer.getSharedPreferences(this);
        final QuestionManager manager=new QuestionManagerImpl();
        final ListView listView = (ListView) findViewById(R.id.own_poll_list_view);
        Observer<ServerResponse<List<Question>>> observer = new Observer<ServerResponse<List<Question>>>() {
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
            public void onNext(ServerResponse<List<Question>> listServerResponse) {
                if(listServerResponse.getData().isEmpty()){
                    OwnPollsActivity.this.setContentView(R.layout.content_own_polls_empty);
                }else {
                    OwnQuestionAdapter adapter = new OwnQuestionAdapter(that, listServerResponse.getData(), manager);
                    listView.setAdapter(adapter);
                }
            }
        };
        manager.getUsersQuestion(observer, preferences.getString("username", ""), preferences.getString("password", ""));
    }

    @Override
    public void onBackPressed(){
        finish();
        Intent intent = new Intent(OwnPollsActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}

