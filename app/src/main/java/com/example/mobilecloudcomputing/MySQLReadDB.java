package com.example.mobilecloudcomputing;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySQLReadDB {
    static String respuestaSql= "";
    public static String command = "";
    static String username ="";
    static String password="";

    public MySQLReadDB(){
    }
    public MySQLReadDB(String usernameApp,String passwordApp){
        username =usernameApp;
        password =passwordApp;
    }

    public String verfUserSQL() throws InterruptedException {
        command = "SELECT * FROM users";
        new Task().execute();
        Thread.sleep(2000);
        return respuestaSql;
    }


    static class Task extends AsyncTask<Void, Void, Void> {

        @Override
        public Void doInBackground(Void... params) {
            Connection connection = null;
            Statement statement = null;
            ResultSet result = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String url="jdbc:mysql://mysqlmobilecomputing.mysql.database.azure.com:3306/androidproject?useSSL=true";
                connection = DriverManager.getConnection(url,"admin1", "Password1!");
                statement = connection.createStatement();
                result = statement.executeQuery(command);
                respuestaSql = "";
                while (result.next()) {
                    String userInDB = result.getString("username");
                    String passInDB = result.getString("password");
                    if(userInDB.equals(username) && passInDB.equals(password)) {
                        respuestaSql = "True";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(e.getMessage());
                System.err.println("Error: Cant connect!");
                connection = null;
            } finally {
                if (statement != null) {
                    try {
                        result.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.err.println(e.getMessage());
                    }
                }
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.err.println(e.getMessage());
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.err.println(e.getMessage());
                    }
                }
            }
            return null;
        }
    }

}
