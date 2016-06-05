package cz.muni.fi.pv239.androidpoll.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.List;

import cz.muni.fi.pv239.androidpoll.Activities.AnswerActivity;
import cz.muni.fi.pv239.androidpoll.Activities.MenuActivity;
import cz.muni.fi.pv239.androidpoll.Entities.Category;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by Guest on 5.6.2016.
 */
public class CategoryAdapter extends BaseAdapter{
    private Context context;
    private View.OnClickListener onClickListener;
    List<Category> categories;

    public CategoryAdapter(Context context, List<Category> categories){
        this.context = context;
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final Button button;
        if(convertView == null){
            button = new Button(context);
            Category category = (Category) getItem(position);
            button.setTag("#" + category.getName());
            button.setText("#" + category.getName());
            button.setOnClickListener(onClickListener);
        } else {
            button = (Button) convertView;
        }
        return button;
    }
}
