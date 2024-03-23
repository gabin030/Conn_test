package com.example.conn_test;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private EditText ed_qname;
    private Button b,b2,b3 ;
    private ArrayList<String> allScores = new ArrayList<>();
    private ArrayList<String> alldate = new ArrayList<>();
    public String data;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        ed_qname = findViewById(R.id.eT_queryname);
        b3 =findViewById(R.id.bt_gotopage2);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,SecondPage.class);
                intent.putExtra("ed_qname",ed_qname.getText().toString());
                intent.putExtra("scorelist",allScores);
                intent.putExtra("datelist",alldate);
                startActivity(intent);
            }
        });
        b = findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MysqpCon con = new MysqpCon();
                        con.run();
                        final ArrayList<ArrayList<String>> dataList = con.getData(ed_qname.getText().toString());
                        for (ArrayList<String> row : dataList) {
                            String score = row.get(2);
                            String date = row.get(3);
                            allScores.add(score);
                            alldate.add(date);
                        }
                        final String lastScore = dataList.get(dataList.size() - 1).get(1); // Get score from last row
                        Log.d("MainActicity ",lastScore);
                        tv.post(new Runnable() {
                            @Override
                            public void run() {
                                tv.setText(lastScore);
                            }
                        });
                    }
                }).start();
            }
        });
        b2 = findViewById(R.id.bt_alldata);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MysqpCon con = new MysqpCon();
                        con.run();
                        final String data = con.getALLData();
                        Log.d("MainActicity ",data);
                        tv.post(new Runnable() {
                            @Override
                            public void run() {
                                tv.setText(data);
                            }
                        });
                    }
                }).start();
            }
        });





    }

                /*try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Log.d("MainActicity","success");
                }catch (ClassNotFoundException e){
                    Log.d("MainActicity","fail");
                }
                final Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        /*while (!Thread.interrupted()){
                            try {
                                Thread.sleep(100);
                                Log.d("MainActicity","YES");
                            } catch (InterruptedException e) {
                                Log.e("MainActicity",e.toString());
                            }
                        }*/
                       /* Connection conn;
                        String ip ="140.118.197.223";
                        int port = 3306;
                        String db_name ="try";
                        String url = "jdbc:mysql://"+ip+":"+port+"/"+db_name;
                        String user = "test";
                        String password = "Aa12345@";

                        try{
                            conn = DriverManager.getConnection(url, user, password);
                            Log.d("MainActivity", "connect");
                            String sql = "SELECT * FROM TEST_SUBDATA";
                                try {
                                    java.sql.Statement statement = conn.createStatement();
                                    ResultSet rset = statement.executeQuery(sql);
                                    Log.d("MainActicity","受試者列表");
                                    while (rset.next()) {
                                    Log.d("MainActicity",rset.getString("id")+"\t"+rset.getString("name")+"\t"
                                    +rset.getString("score")+"\t"+rset.getString("date")+"\t");
                                    String id = rset.getString("id");
                                    String name = rset.getString("name");
                                    String score = rset.getString("score");
                                    String date = rset.getString("date");
                                    Log.d("MainActicity",id);
                                    }

                                } catch (SQLException e) {
                                    Log.e("MainActicity",e.toString());
                                }
                                try {
                                    conn.close();
                                    Log.d("MainActicity", "closed");
                                }catch (SQLException e) {
                                    Log.d("MainActicity", "關閉失敗");
                                }
                                return;
                        }catch (SQLException e) {
                            Log.d("MainActicity","資料庫連線失敗");
                            Log.e("MainActicity",e.toString());
                        }
                    }

                });th.start();*/


}