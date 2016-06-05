package cz.muni.fi.pv239.androidpoll.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv239.androidpoll.Adapters.CategoryAdapter;
import cz.muni.fi.pv239.androidpoll.Entities.Category;
import cz.muni.fi.pv239.androidpoll.Managers.impl.CategoryManagerImpl;
import cz.muni.fi.pv239.androidpoll.Managers.interfaces.CategoryManager;
import cz.muni.fi.pv239.androidpoll.R;
import cz.muni.fi.pv239.androidpoll.ServerConnection.ServerResponse;
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
        Observer<ServerResponse<List<Category>>> observer = new Observer<ServerResponse<List<Category>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ServerResponse<List<Category>> response) {
                if(response.isSuccessful()){
                    gridCategories = (GridView) findViewById(R.id.categoriesGridView);
                    List<Category> categories = response.getData();

                    categoryAdapter = new CategoryAdapter(that, categories);
                    gridCategories.setAdapter(categoryAdapter);
                    gridCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            onCategoryClick((Category) categoryAdapter.getItem(position));
                        }
                    });
                    categoryAdapter.setOnItemClickListener(new AdapterView.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onCategoryClick(v);///get info about category to open right poll here
                        }
                    });
                }
            }
        };
        CategoryManager manager= new CategoryManagerImpl();
        manager.getAllCategories(observer);
    }
    public List<Category> getCategories(){
        List<Category> categories = new ArrayList<>();

        Category cat = new Category();
        cat.setName("Sports");
        categories.add(cat);

        Category cat2 = new Category();
        cat2.setName("Politics");
        categories.add(cat2);

        Category cat3 = new Category();
        cat3.setName("Fashion");
        categories.add(cat3);

        Category cat4 = new Category();
        cat4.setName("Science");
        categories.add(cat4);

        Category cat5 = new Category();
        cat5.setName("Nature");
        categories.add(cat5);

        Category cat6 = new Category();
        cat6.setName("Love");
        categories.add(cat6);

        Category cat7 = new Category();
        cat7.setName("Music");
        categories.add(cat7);

        Category cat8 = new Category();
        cat8.setName("Fun");
        categories.add(cat8);
        return categories;
    }
    public void onCategoryClick(Category category){
        // todooo
        Intent intent = new Intent(MenuActivity.this, AnswerActivity.class);
        startActivity(intent);
    }

    public void onCategoryClick(View v){
        Intent intent = new Intent(MenuActivity.this, AnswerActivity.class);
        startActivity(intent);
    }

    public void onCreateClick(View v){
        Intent intent = new Intent(MenuActivity.this, CreateActivity.class);
        startActivity(intent);
    }

    public void onMyPollsClick(View v){
        Intent intent = new Intent(MenuActivity.this,OwnPollsActivity.class);
        startActivity(intent);
    }
}