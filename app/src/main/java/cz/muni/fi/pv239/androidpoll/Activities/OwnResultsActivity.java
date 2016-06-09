package cz.muni.fi.pv239.androidpoll.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv239.androidpoll.Entities.Option;
import cz.muni.fi.pv239.androidpoll.Entities.Question;
import cz.muni.fi.pv239.androidpoll.Managers.impl.OptionManagerImpl;
import cz.muni.fi.pv239.androidpoll.Managers.impl.QuestionManagerImpl;
import cz.muni.fi.pv239.androidpoll.Managers.interfaces.OptionManager;
import cz.muni.fi.pv239.androidpoll.Managers.interfaces.QuestionManager;
import cz.muni.fi.pv239.androidpoll.R;
import cz.muni.fi.pv239.androidpoll.ServerConnection.ServerResponse;
import cz.muni.fi.pv239.androidpoll.SharedPrefsContainer;
import rx.Observer;

/**
 * Created by Guest on 4.6.2016.
 */
public class OwnResultsActivity extends AppCompatActivity {

    private RelativeLayout relativeLayout;
    private PieChart statsChart;
    private List<Entry> data=new ArrayList<>();
    private List<String> names=new ArrayList<>();
    Context that = this;
    //private String questionText = "Kto je najlepsi?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_results);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Results");

        TextView questionTextView = (TextView) findViewById(R.id.own_results_question_text);
        questionTextView.setText(getIntent().getStringExtra("questionText"));
        OptionManager manager = new OptionManagerImpl();
        Observer<ServerResponse<List<Option>>> observer = new Observer<ServerResponse<List<Option>>>() {
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
            public void onNext(ServerResponse<List<Option>> listServerResponse) {
                    if(listServerResponse.isSuccessful()){
                        for(Option option : listServerResponse.getData()){
                            names.add(option.getText());
                            if(option.getNumOfAnswers()!=0) data.add(new Entry(option.getNumOfAnswers(),names.size()-1));
                        }
                        if(data.isEmpty()){
                            OwnResultsActivity.this.setContentView(R.layout.content_unanswered_question);
                        }else{
                            makeGraph();
                        }

                    }
            }
        };
        manager.getQuestionOptions(observer,getIntent().getLongExtra("questionId",0));
    }
    private void makeGraph(){
        relativeLayout = (RelativeLayout) findViewById(R.id.ownResultActivityLayout);
        statsChart = (PieChart) findViewById(R.id.pieStats);

        //relativeLayout.addView(statsChart);
        //statsChart.setUsePercentValues(true); ///set use percent maybe
        //statsChart.setDescription("Answers share");
        statsChart.setDrawHoleEnabled(true);
        statsChart.setHoleColor(0);
        statsChart.setHoleRadius(7);
        statsChart.setTransparentCircleRadius(10);
/*
        ViewGroup.LayoutParams params = statsChart.getLayoutParams();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;﻿
*/
        statsChart.setRotationAngle(0);
        statsChart.setRotationEnabled(true);
        statsChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                if (e == null) return;
                Toast.makeText(OwnResultsActivity.this, names.get(e.getXIndex()) + " = " + e.getVal() + " answers", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        addData();
        // bez legendy? moc sa tam nehodi ked su hodnoty aj vnutri
        //Legend l = statsChart.getLegend();
        //l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        //l.setXEntrySpace(7);
        //l.setYEntrySpace(5);
    }

    private void addData(){
        PieDataSet dataSet = new PieDataSet(data, "Answers Share");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5); // 3?

        ArrayList<Integer> colors = new ArrayList<>();
        for(int c : ColorTemplate.VORDIPLOM_COLORS) colors.add(c);
        for(int c : ColorTemplate.JOYFUL_COLORS) colors.add(c);
        for(int c : ColorTemplate.COLORFUL_COLORS) colors.add(c);
        for(int c : ColorTemplate.LIBERTY_COLORS) colors.add(c);
        for(int c : ColorTemplate.PASTEL_COLORS) colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        PieData pieData = new PieData(names, dataSet);
        pieData.setValueFormatter(new LargeValueFormatter());
        //new PercentFormatter());
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.GRAY);

        statsChart.setData(pieData);
        statsChart.highlightValues(null);
        //statsChart.highlightValue(); tu bude vybrana value
        statsChart.invalidate();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
