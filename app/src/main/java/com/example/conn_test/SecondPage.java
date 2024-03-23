package com.example.conn_test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SecondPage extends AppCompatActivity {
    LineChart lineChart_ma,lineChart_rest;
    Button b1,b2;
    private int i = 0 ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondpage);
        lineChart_rest = findViewById(R.id.chart_REST);
        lineChart_ma = findViewById(R.id.chart_MA);
        xtext();//x軸設定
        ytext();//y軸設定

        //顯示分數及說明
        b1 = findViewById(R.id.bt_score);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //顯示BP腦波圖
        b2 = findViewById(R.id.bt_chart);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                ArrayList<String> allScores = intent.getStringArrayListExtra("scorelist");
                ArrayList<Float> floatScores = new ArrayList<>();
                for (String score : allScores) {
                    try {
                        float floatScore = Float.parseFloat(score);
                        floatScores.add(floatScore);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }}
                    ArrayList<String> alldate = intent.getStringArrayListExtra("datelist");
                    ArrayList<Entry> values1 = new ArrayList<>();
                    for (float row : floatScores) {
                        i++;
                        values1.add(new Entry(i, row));
                    }
                    text_all(values1);
            }
        });

        }


    //x軸設定
    private void xtext() {
        XAxis x = lineChart_ma.getXAxis();
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setGranularity(1);
        XAxis x1 = lineChart_rest.getXAxis();
        x1.setPosition(XAxis.XAxisPosition.BOTTOM);
    }
    //y軸設定
    private void ytext() {
        YAxis Yright = lineChart_ma.getAxisRight();
        Yright.setEnabled(false);
        YAxis Yright1 = lineChart_rest.getAxisRight();
        Yright1.setEnabled(false);
    }
    // Method to parse date string
    private Date parseDate(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
            return new Date(); // Return current date if parsing fails
        }
    }

    private void text_all(ArrayList<Entry> values1) {
        LineDataSet set1;
        set1 = new LineDataSet(values1,"DATA");
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setColor(Color.GRAY);
        set1.setLineWidth(2);
        set1.setCircleRadius(4);
        set1.enableDashedLine(5,5,0);
        set1.setHighlightLineWidth(2);
        set1.setHighlightEnabled(true);
        set1.setHighLightColor(Color.BLUE);
        set1.setValueTextSize(20);
        set1.setDrawFilled(false);

        LineData data = new LineData(set1); //設定圖表基本資料
        lineChart_ma.setData(data); //將圖表基本資料放入圖中
        lineChart_ma.invalidate();//繪製圖表


    }
}
