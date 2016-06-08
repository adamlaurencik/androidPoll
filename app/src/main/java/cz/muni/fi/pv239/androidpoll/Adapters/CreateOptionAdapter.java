package cz.muni.fi.pv239.androidpoll.Adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv239.androidpoll.Entities.Option;
import cz.muni.fi.pv239.androidpoll.R;

/**
 * Created by Adam on 06.06.2016.
 */
public class CreateOptionAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    List<Option> options= new ArrayList<>();

    public CreateOptionAdapter(Context context, List<Option> options){
        inflater= LayoutInflater.from(context);
        this.context=context;
        this.options=options;
    }

    public List<Option> getOptions(){ return options;}

    @Override
    public int getCount() {
        return options.size();
    }

    @Override
    public Option getItem(int position) {
        return options.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null || convertView.getTag()==null){
            OptionViewHolder optionViewHolder;
            convertView=inflater.inflate(R.layout.create_option_item,parent,false);
            optionViewHolder =new OptionViewHolder(convertView,context,position);
            convertView.setTag(optionViewHolder);
        }
        final OptionViewHolder optionViewHolder;
        optionViewHolder = (OptionViewHolder) convertView.getTag();
        optionViewHolder.optionTextView.setText(position+":");
        optionViewHolder.optionEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    EditText editTxt = (EditText) v;
                    options.get(position).setText(editTxt.getText().toString());
                }
            }
        });
        optionViewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getOptions().size() > 2) {
                    optionViewHolder.optionEditText.setOnClickListener(null);
                    getOptions().remove(position);
                    notifyDataSetChanged();
                }else{
                    Toast.makeText(context,"You have to create at least 2 answers!",Toast.LENGTH_LONG).show();
                }
            }
        });
        return convertView;
    }
}
class OptionViewHolder{
    int optionNumber;
    TextView optionTextView;
    EditText optionEditText;
    ImageButton deleteButton;

    public OptionViewHolder(View view, final Context context,final int position) {
        optionTextView = (TextView) view.findViewById(R.id.option_item_answer_text);
        optionEditText = (EditText) view.findViewById(R.id.option_item_answer_edit_text);
        deleteButton = (ImageButton) view.findViewById(R.id.option_item_answer_delete);
    }
}
