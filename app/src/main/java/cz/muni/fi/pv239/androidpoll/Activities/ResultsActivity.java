package cz.muni.fi.pv239.androidpoll.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import cz.muni.fi.pv239.androidpoll.R;

/**
 * Created by Filip on 28.5.2016.
 */
public class ResultsActivity extends AppCompatActivity {

    private RelativeLayout relativeLayout;
    private PieChart statsChart;
    private float[] data = {10, 10, 20};
    private String[] names = {"Marek", "Lauro", "Filip"};
    private String questionText = "Kto je najlepsi?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Results");

        makeGraph();
    }
    private void makeGraph(){
        relativeLayout = (RelativeLayout) findViewById(R.id.resultActivityLayout);
        statsChart = (PieChart) findViewById(R.id.pieStats);//new PieChart(thi


        // s);
//        relativeLayout.addView(statsChart);
        //statsChart.setUsePercentValues(true); ///set use percent maybe
        statsChart.setDescription("Answers share");
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
                Toast.makeText(ResultsActivity.this, names[e.getXIndex()] + " = " + e.getVal() + " answers", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        addData();

        Legend l = statsChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);
    }

    private void addData(){
        ArrayList<Entry> listY = new ArrayList<>();

        for(int i = 0; i < data.length; i++){
            listY.add(new Entry(data[i], i));
        }
        ArrayList<String> listX = new ArrayList<>();

        for(int i = 0; i < names.length; i++){
            listX.add(names[i]);
        }

        PieDataSet dataSet = new PieDataSet(listY, "Answers Share");
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
        PieData pieData = new PieData(listX, dataSet);
        pieData.setValueFormatter(new LargeValueFormatter());
                //new PercentFormatter());
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.GRAY);

        statsChart.setData(pieData);
        statsChart.highlightValues(null);
        //statsChart.highlightValue(); tu bude vybrana value
        statsChart.invalidate();
    }

    public void onMenuClick(View v){
        Intent intent = new Intent(ResultsActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    public void onNextClick(View v){
        Intent intent = new Intent(ResultsActivity.this, AnswerActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(ResultsActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}