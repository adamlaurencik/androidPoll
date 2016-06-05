package cz.muni.fi.pv239.androidpoll.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.securepreferences.SecurePreferences;

import java.util.List;

import cz.muni.fi.pv239.androidpoll.Activities.OwnPollsActivity;
import cz.muni.fi.pv239.androidpoll.Activities.OwnResultsActivity;
import cz.muni.fi.pv239.androidpoll.Entities.Question;
import cz.muni.fi.pv239.androidpoll.Managers.interfaces.QuestionManager;
import cz.muni.fi.pv239.androidpoll.R;
import cz.muni.fi.pv239.androidpoll.ServerConnection.ServerResponse;
import rx.Observer;

/**
 * Created by Adam on 05.06.2016.
 */
public class OwnQuestionAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
     List<Question> ownQuestions;
    private QuestionManager manager;

    public OwnQuestionAdapter(Context context, List<Question> ownQuestions, QuestionManager manager){
        inflater=LayoutInflater.from(context);
        this.context=context;
        this.manager=manager;
        this.ownQuestions=ownQuestions;
    }

    @Override
    public int getCount() {
        return ownQuestions.size();
    }


    @Override
    public Question getItem(int position) {
        return ownQuestions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ownQuestions.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.own_poll_item,parent,false);
            viewHolder=new ViewHolder(convertView,context,getItem(position),manager, position);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        Question question = getItem(position);
        viewHolder.questionTextView.setText(question.getQuestion());
        return convertView;
    }
}

class ViewHolder{
    Question question;
    TextView questionTextView;
    ImageView deleteButton;

    public ViewHolder(View view, final Context context, final Question question, final QuestionManager manager, final int position){
        questionTextView= (TextView) view.findViewById(R.id.own_poll_item_question);
        deleteButton=(ImageView) view.findViewById(R.id.own_poll_item_delete);
        this.question=question;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context,OwnResultsActivity.class);
                intent.putExtra("questionId",question.getId());
                intent.putExtra("questionText",question.getQuestion());
                context.startActivity(intent);

            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog dialog= new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Do you want to delete this question?")
                        .setIcon(android.R.drawable.ic_delete)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SecurePreferences preferences = new SecurePreferences(context);
                                Observer<ServerResponse<Question>> observer= new Observer<ServerResponse<Question>>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(ServerResponse<Question> questionServerResponse) {
                                        if(questionServerResponse.isSuccessful()){
                                            ListView listView = (ListView) v.getParent().getParent();
                                            OwnQuestionAdapter adapter = (OwnQuestionAdapter) listView.getAdapter();
                                            adapter.ownQuestions.remove(position);
                                            adapter.notifyDataSetChanged();
                                        }
                                    }
                                };
                                manager.deleteQuestion(observer,question,preferences.getString("username",""),preferences.getString("password",""));
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                        dialog.show();
            }

        });
    }
}
