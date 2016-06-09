package cz.muni.fi.pv239.androidpoll.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.securepreferences.SecurePreferences;

import java.net.UnknownHostException;

import cz.muni.fi.pv239.androidpoll.Entities.User;
import cz.muni.fi.pv239.androidpoll.Managers.impl.UserManagerImpl;
import cz.muni.fi.pv239.androidpoll.Managers.interfaces.UserManager;
import cz.muni.fi.pv239.androidpoll.R;
import cz.muni.fi.pv239.androidpoll.ServerConnection.ServerResponse;
import cz.muni.fi.pv239.androidpoll.SharedPrefsContainer;
import rx.Observer;

public class LoginActivity extends AppCompatActivity {
    private Context that = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Log in");

        final EditText nameEdit= (EditText) findViewById(R.id.loginNameEditText);
        final EditText passwordEdit= (EditText) findViewById(R.id.loginPasswordEditText);
        passwordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordEdit.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        nameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nameEdit.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void onProceedLoginClick(View v){
        final EditText nameEditText= (EditText) findViewById(R.id.loginNameEditText);
        final EditText passwordEditText= (EditText) findViewById(R.id.loginPasswordEditText);
        final ProgressDialog dialog = ProgressDialog.show(this, "Logging in", "Please wait...", true);
        Observer<ServerResponse<User>> observer = new Observer<ServerResponse<User>>() {
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
            public void onNext(ServerResponse<User> userServerResponse) {
                if(userServerResponse.isSuccessful()){
                    SecurePreferences preferences = SharedPrefsContainer.getSharedPreferences(that);
                    SecurePreferences.Editor editor = preferences.edit();
                    editor.putString("username",userServerResponse.getData().getName());
                    editor.putString("password", passwordEditText.getText().toString());
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                    startActivity(intent);
                }else{
                    switch (userServerResponse.getError()){
                        case ServerResponse.INCORRECT_PASS_MESSAGE: showError(passwordEditText,"Incorrect password"); break;
                        case ServerResponse.INCORRECT_USERNAME_MESSAGE: showError(nameEditText, "User does not exist"); break;
                    }
                }
                dialog.dismiss();
            }
        };
        UserManager userManager= new UserManagerImpl();
        userManager.loginUser(observer, nameEditText.getText().toString(), passwordEditText.getText().toString());
    }

    public void showError(EditText edit, String errorText){
        Animation shake= AnimationUtils.loadAnimation(that, R.anim.shake);
        edit.startAnimation(shake);
        edit.setError(errorText);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
