package cz.muni.fi.pv239.androidpoll.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cz.muni.fi.pv239.androidpoll.Activities.AnswerActivity;
import cz.muni.fi.pv239.androidpoll.Activities.MenuActivity;
import cz.muni.fi.pv239.androidpoll.Activities.OwnPollsActivity;
import cz.muni.fi.pv239.androidpoll.Entities.Category;

import static android.support.v4.app.ActivityCompat.startActivity;
import static android.support.v4.content.ContextCompat.startActivities;

/**
 * Created by Guest on 5.6.2016.
 */
public class CategoryAdapter extends BaseAdapter{
    private Context context;
    private View.OnClickListener onClickListener;
    List<Category> categories;
    private static int counter = -1;

    public CategoryAdapter(Context context, List<Category> categories){
        this.context = context;
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Category getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setOnItemClickListener(View.OnClickListener listener) {
        onClickListener = listener;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Button button;
        if(convertView == null){
            button = new Button(context);
        } else {
            button = (Button) convertView;
        }
        final Category category =  getItem(position);
        button.setTag("#" + category.getName());
        button.setText("#" + category.getName());
        button.setHeight(200 * (int)context.getResources().getDisplayMetrics().density);
        if(position == 0){
            button.setBackgroundColor(Color.WHITE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,OwnPollsActivity.class);
                    context.startActivity(intent);
                }
            });
        } else {
        button.setBackgroundColor(getColor(category.getId()));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AnswerActivity.class);
                    intent.putExtra("Category.id", category.getId());
                    intent.putExtra("Category.name", category.getName());
                    context.startActivity(intent);
                }
            });
        }
        return button;
    }
    private Integer getColor(Long id) {
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(193,113,113));
        colors.add(Color.RED);
        colors.add(Color.GRAY);
        colors.add(Color.CYAN);
        colors.add(Color.GREEN);
        colors.add(Color.MAGENTA);
        colors.add(Color.YELLOW);
        colors.add(Color.rgb(0,130,130));
        colors.add(Color.rgb(240, 64, 10));
        return colors.get(id.intValue() % colors.size());
    }
}
