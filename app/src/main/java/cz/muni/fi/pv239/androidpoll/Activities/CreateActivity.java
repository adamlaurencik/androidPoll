package cz.muni.fi.pv239.androidpoll.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.securepreferences.SecurePreferences;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.muni.fi.pv239.androidpoll.Adapters.CategoryAdapter;
import cz.muni.fi.pv239.androidpoll.Adapters.CreateOptionAdapter;
import cz.muni.fi.pv239.androidpoll.Entities.Category;
import cz.muni.fi.pv239.androidpoll.Entities.Option;
import cz.muni.fi.pv239.androidpoll.Entities.Question;
import cz.muni.fi.pv239.androidpoll.Managers.impl.CategoryManagerImpl;
import cz.muni.fi.pv239.androidpoll.Managers.impl.QuestionManagerImpl;
import cz.muni.fi.pv239.androidpoll.Managers.interfaces.CategoryManager;
import cz.muni.fi.pv239.androidpoll.Managers.interfaces.QuestionManager;
import cz.muni.fi.pv239.androidpoll.R;
import cz.muni.fi.pv239.androidpoll.ServerConnection.ServerResponse;
import cz.muni.fi.pv239.androidpoll.SharedPrefsContainer;
import rx.Observer;

/**
 * Created by Filip on 28.5.2016.
 */
public class CreateActivity extends AppCompatActivity {

    private Context that=this;
    private Map<String,Long> categoryMap = new HashMap<>();
    private long selectedCategoryId= -1;
    CreateOptionAdapter adapter;
    ListView optionsListView;
    List<Option> optionsList = new ArrayList<>();
    EditText questionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("New Poll");
        questionEditText = (EditText) findViewById(R.id.create_question_question_edit_text);
        questionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                questionEditText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Observer<ServerResponse<List<Category>>> observer = new Observer<ServerResponse<List<Category>>>() {
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
            public void onNext(ServerResponse<List<Category>> response) {
                if(response.isSuccessful()){
                    Spinner categories= (Spinner) findViewById(R.id.categorySpinner);
                    List<String> catNames= new ArrayList<String>();

                    for(Category category: response.getData()){
                            categoryMap.put(category.getName(),category.getId());
                            catNames.add(category.getName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(that,android.R.layout.simple_spinner_item,catNames);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    categories.setAdapter(adapter);
                    categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedCategoryId=categoryMap.get(parent.getItemAtPosition(position));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
            }
        };
        CategoryManager manager= new CategoryManagerImpl();
        manager.getAllCategories(observer);
        optionsListView = (ListView) findViewById(R.id.create_question_options_list);
        adapter = new CreateOptionAdapter(this,optionsList);
        optionsListView.setAdapter(adapter);
        addNewOption(optionsListView);
        addNewOption(optionsListView);

    }

    public void addNewOption(View v){
        Option option = new Option();
        option.setId(((long) optionsList.size()));
        optionsList.add(option);
        adapter.notifyDataSetChanged();
    }

    public void onProceedCreateClick(View v){
        boolean sucess=true;
        v.requestFocus();
        boolean optionsSuccess=true;
        String questionText=questionEditText.getText().toString();
        if(questionText.length()==0){
            Toast.makeText(this,"Question text must be filled!",Toast.LENGTH_SHORT).show();
            Animation shake= AnimationUtils.loadAnimation(that, R.anim.shake);
            questionEditText.startAnimation(shake);
            sucess=false;
        }

        List<String> stringOptions = new ArrayList<>();
        for(Option option : optionsList){
            if(option.getText()==null || option.getText().length()==0){
                optionsSuccess=false;
                Animation shake= AnimationUtils.loadAnimation(that, R.anim.shake);
                optionsListView.getChildAt(option.getId().intValue()).startAnimation(shake);
            }
            stringOptions.add(option.getText());
        }
        if(!optionsSuccess){
            Toast.makeText(this, "All options must be filled!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!sucess) return;
        SecurePreferences preferences = SharedPrefsContainer.getSharedPreferences(that);
        Observer<ServerResponse<Question>> observer = new Observer<ServerResponse<Question>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ServerResponse<Question> questionServerResponse) {
                if(questionServerResponse.isSuccessful()){
                    Intent intent = new Intent(CreateActivity.this, OwnPollsActivity.class);
                    startActivity(intent);
                }
            }
        };
        QuestionManager manager = new QuestionManagerImpl();
        manager.createQuestionWithOptions(observer, preferences.getString("username", ""), preferences.getString("password", ""), selectedCategoryId, questionText, stringOptions);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
