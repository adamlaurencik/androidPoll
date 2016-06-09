package cz.muni.fi.pv239.androidpoll.Activities;

import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.securepreferences.SecurePreferences;

import java.net.UnknownHostException;

import cz.muni.fi.pv239.androidpoll.Entities.Gender;
import cz.muni.fi.pv239.androidpoll.Entities.User;
import cz.muni.fi.pv239.androidpoll.Managers.impl.UserManagerImpl;
import cz.muni.fi.pv239.androidpoll.Managers.interfaces.UserManager;
import cz.muni.fi.pv239.androidpoll.R;
import cz.muni.fi.pv239.androidpoll.ServerConnection.ServerResponse;
import cz.muni.fi.pv239.androidpoll.SharedPrefsContainer;
import rx.Observer;

/**
 * Created by Filip on 26.5.2016.
 */
public class RegistrationActivity extends AppCompatActivity {

    private Context that=this;
    private NumberPicker agePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Registration");

        NumberPicker agePicker = (NumberPicker) findViewById(R.id.registrationAgeNumberPicker);
        agePicker.setMaxValue(100);
        agePicker.setMinValue(5);


        final EditText nameEdit= (EditText) findViewById(R.id.registrationNameEditText);
        final EditText passwordEdit= (EditText) findViewById(R.id.registrationPasswordEditText);
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

    public void onProceedRegistrationClick(View v){
        Intent intent = new Intent(RegistrationActivity.this, MenuActivity.class);
        final EditText nameEdit= (EditText) findViewById(R.id.registrationNameEditText);
        final EditText passwordEdit= (EditText) findViewById(R.id.registrationPasswordEditText);
        NumberPicker agePick = (NumberPicker) findViewById(R.id.registrationAgeNumberPicker);
        RadioGroup genderPick = (RadioGroup) findViewById(R.id.radioGroup);
        int genderId = genderPick.getCheckedRadioButtonId();
        Gender gender = null;
        if(genderId == R.id.registrationMaleRadioButton) gender = Gender.MALE;
        if(genderId == R.id.registrationFemaleRadioButton) gender = Gender.FEMALE;
        if(nameEdit.getText().toString().length()<4){
            showError(nameEdit,"Name must have at least 4 letters");
            return;
        }
        if(passwordEdit.getText().toString().length()<5){
            showError(passwordEdit,"Password must have at least 5 letters");
            return;
        }
        final ProgressDialog dialog = ProgressDialog.show(this, "I am registering you", "Please wait...", true);
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
                    editor.putString("password", passwordEdit.getText().toString());
                    editor.commit();
                    Intent intent = new Intent(RegistrationActivity.this, MenuActivity.class);
                    startActivity(intent);
                    Toast.makeText(that, "Sucessful", Toast.LENGTH_LONG).show();
                }else{
                    switch(userServerResponse.getError()){
                        case ServerResponse.USER_ALREADY_EXIST: showError(nameEdit, "Username already exist!"); break;
                    }
                }
                dialog.dismiss();
            }
        };
        UserManager manager=new UserManagerImpl();
        manager.registerNewUser(observer, nameEdit.getText().toString(), passwordEdit.getText().toString(), agePick.getValue(), gender);
    }

    public void showError(EditText edit, String errorText){
        Animation shake = AnimationUtils.loadAnimation(that,R.anim.shake);
        edit.startAnimation(shake);
        edit.setError(errorText);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}