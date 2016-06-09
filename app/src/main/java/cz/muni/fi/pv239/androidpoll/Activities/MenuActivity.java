package cz.muni.fi.pv239.androidpoll.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv239.androidpoll.Adapters.CategoryAdapter;
import cz.muni.fi.pv239.androidpoll.Entities.Category;
import cz.muni.fi.pv239.androidpoll.Managers.impl.CategoryManagerImpl;
import cz.muni.fi.pv239.androidpoll.Managers.interfaces.CategoryManager;
import cz.muni.fi.pv239.androidpoll.R;
import cz.muni.fi.pv239.androidpoll.ServerConnection.ServerResponse;
import cz.muni.fi.pv239.androidpoll.SharedPrefsContainer;
import rx.Observer;

/**
 * Created by Filip on 28.5.2016.
 */
public class MenuActivity extends AppCompatActivity {

    private GridView gridCategories;
    private CategoryAdapter categoryAdapter;
    private Context that=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

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
                    gridCategories = (GridView) findViewById(R.id.categoriesGridView);
                    List<Category> categories = response.getData();

                    categoryAdapter = new CategoryAdapter(that, categories);
                    gridCategories.setAdapter(categoryAdapter);
                }
            }
        };
        CategoryManager manager= new CategoryManagerImpl();
        manager.getAllCategories(observer);
    }

    public void onCreateClick(View v){
        Intent intent = new Intent(MenuActivity.this, CreateActivity.class);
        startActivity(intent);
    }
    public void onLogoutClick(){

        SharedPrefsContainer.getSharedPreferences(that).edit().remove("username").commit();
        SharedPrefsContainer.getSharedPreferences(that).edit().remove("password").commit();

        Intent intent = new Intent(MenuActivity.this, MainActivity.class);
        startActivity(intent);

    }

    public void onMyPollsClick(View v){
        Intent intent = new Intent(MenuActivity.this,OwnPollsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logoutButton) {
            onLogoutClick();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}