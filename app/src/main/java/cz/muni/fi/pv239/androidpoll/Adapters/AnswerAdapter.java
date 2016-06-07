package cz.muni.fi.pv239.androidpoll.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.securepreferences.SecurePreferences;

import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv239.androidpoll.Activities.ResultsActivity;
import cz.muni.fi.pv239.androidpoll.Entities.Answer;
import cz.muni.fi.pv239.androidpoll.Entities.Option;
import cz.muni.fi.pv239.androidpoll.Entities.Question;
import cz.muni.fi.pv239.androidpoll.Managers.impl.AnswerManagerImpl;
import cz.muni.fi.pv239.androidpoll.Managers.interfaces.AnswerManager;
import cz.muni.fi.pv239.androidpoll.R;
import cz.muni.fi.pv239.androidpoll.ServerConnection.ServerResponse;
import cz.muni.fi.pv239.androidpoll.SharedPrefsContainer;
import rx.Observer;

/**
 * Created by Adam on 07.06.2016.
 */
public class AnswerAdapter extends BaseAdapter {
    private Context context;
    Question question;
    List<Option> optionList = new ArrayList<>();
    private LayoutInflater inflater;

    public AnswerAdapter(Context context, List<Option> optionList, Question question){
        this.context=context;
        this.optionList=optionList;
        inflater= LayoutInflater.from(context);
        this.question=question;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        AnswerHolder answerHolder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.answer_button_item,parent,false);
            answerHolder =new AnswerHolder(convertView,context,position);
            convertView.setTag(answerHolder);
        }
        answerHolder = (AnswerHolder) convertView.getTag();
        answerHolder.optionButton.setText(getItem(position).getText());
        answerHolder.optionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnswerManager manager = new AnswerManagerImpl();
                Observer<ServerResponse<Answer>> observer = new Observer<ServerResponse<Answer>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ServerResponse<Answer> answerServerResponse) {
                        Intent intent = new Intent(context, ResultsActivity.class);
                        intent.putExtra("question.Id",question.getId());
                        intent.putExtra("question.name",question.getQuestion());
                        context.startActivity(intent);
                    }
                };
                SecurePreferences prefs = SharedPrefsContainer.getSharedPreferences(context);
                manager.answerQuestion(observer,prefs.getString("username",""),question.getId(),getItem(position),prefs.getString("password", ""));
            }
        });
        return convertView;
    }
}

class AnswerHolder{
    Button optionButton;

    public AnswerHolder(View view, final Context context,final int position) {
        optionButton = (Button) view.findViewById(R.id.list_answer_button);
    }
}
