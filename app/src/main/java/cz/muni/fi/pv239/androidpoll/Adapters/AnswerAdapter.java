package cz.muni.fi.pv239.androidpoll.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv239.androidpoll.Entities.Option;
import cz.muni.fi.pv239.androidpoll.R;

/**
 * Created by Adam on 07.06.2016.
 */
public class AnswerAdapter extends BaseAdapter {
    private Context context;
    List<Option> optionList = new ArrayList<>();
    private LayoutInflater inflater;

    public AnswerAdapter(Context context, List<Option> optionList){
        this.context=context;
        this.optionList=optionList;
        inflater= LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return optionList.size();
    }

    @Override
    public Option getItem(int position) {
        return optionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AnswerHolder answerHolder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.answer_button_item,parent,false);
            answerHolder =new AnswerHolder(convertView,context,position);
            convertView.setTag(answerHolder);
        }
        answerHolder = (AnswerHolder) convertView.getTag();
        answerHolder.optionButton.setText(getItem(position).getText());
        return convertView;
    }
}

class AnswerHolder{
    Button optionButton;

    public AnswerHolder(View view, final Context context,final int position) {
        optionButton = (Button) view.findViewById(R.id.list_answer_button);
    }
}
