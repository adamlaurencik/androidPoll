package cz.muni.fi.pv239.androidpoll.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import cz.muni.fi.pv239.androidpoll.R;

/**
 * Created by Filip on 28.5.2016.
 */
public class CreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Spinner categories= (Spinner) findViewById(R.id.categorySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        setContentView(R.layout.activity_create);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categories.setAdapter(adapter);
    }
    public void onProceedCreateClick(View v){
        Intent intent = new Intent(CreateActivity.this, OwnPollsActivity.class);
        startActivity(intent);
    }
}
