package com.example.conn_test;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class MysqpCon {
    //資料庫定義
    String mysql_ip ="140.118.197.223";
    int mysql_port = 3306;
    String db_name = "try";
    String url = "jdbc:mysql://"+mysql_ip+":"+mysql_port+"/"+db_name;
    String db_user = "test";
    String db_password = "Aa12345@";
    public void run() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Log.v("DB","加載驅動成功");
        }catch( ClassNotFoundException e) {
            Log.e("DB","加載驅動失敗");
            return;
        }
        // 連接資料庫
        try {
            Connection con = DriverManager.getConnection(url,db_user,db_password);
            Log.v("DB","遠端連接成功");
        }catch(SQLException e) {
            Log.e("DB","遠端連接失敗");
            Log.e("DB", e.toString());
        }
    }
   /* public String getData(String input_name) {
        String data = "";
        try {
            Connection con = DriverManager.getConnection(url, db_user, db_password);
            String sql = "SELECT * FROM TEST_SUBDATA WHERE name = '"+ input_name+"'" ;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next())
            {
                String id = String.valueOf(rs.getInt("id"));
                String name = rs.getString("name");
                String score = String.valueOf(rs.getFloat("score"));
                String date = String.valueOf(rs.getDate("date"));
                data += id + ", " + name +", "+score+", "+date+ "\n";
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }*/
    //把對應到名字的資料放到List裡
   public ArrayList<ArrayList<String>> getData(String input_name) {
       ArrayList<ArrayList<String>> dataList = new ArrayList<>();
       ArrayList<String> header = new ArrayList<>();
       header.add("id");
       header.add("name");
       header.add("score");
       header.add("date");
       dataList.add(header);

       try {
           Connection con = DriverManager.getConnection(url, db_user, db_password);
           String sql = "SELECT * FROM TEST_SUBDATA WHERE name = ?";
           PreparedStatement ps = con.prepareStatement(sql);
           ps.setString(1, input_name);
           ResultSet rs = ps.executeQuery();

           while (rs.next()) {
               ArrayList<String> row = new ArrayList<>();
               row.add(String.valueOf(rs.getInt("id")));
               row.add(rs.getString("name"));
               row.add(String.valueOf(rs.getFloat("score")));
               row.add(String.valueOf(rs.getDate("date")));
               dataList.add(row);
           }
           ps.close();
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return dataList;
   }

    public String getALLData() {
        String data = "";
        try {
            Connection con = DriverManager.getConnection(url, db_user, db_password);
            String sql = "SELECT * FROM TEST_SUBDATA " ;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next())
            {
                String id = String.valueOf(rs.getInt("id"));
                String name = rs.getString("name");
                String score = String.valueOf(rs.getFloat("score"));
                String date = String.valueOf(rs.getDate("date"));
                data += id + ", " + name +", "+score+", "+date+ "\n";
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
}



