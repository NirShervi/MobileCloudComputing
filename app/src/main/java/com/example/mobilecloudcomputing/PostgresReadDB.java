package com.example.mobilecloudcomputing;

import android.os.AsyncTask;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PostgresReadDB {
    static String cadenaConexion = "jdbc:postgresql://postgrescloudproject.postgres.database.azure.com:5432/androidpostgres?user=admin1&password=Password1!&sslmode=require";
    static String respuestaSql= "";
    public static String command = "";
    static String username ="";
    static String password="";


    public PostgresReadDB()
    {
    }

    public PostgresReadDB(String usernameApp,String passwordApp)
    {
        username =usernameApp;
        password =passwordApp;
    }

    public String verfUser() throws InterruptedException {
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
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(cadenaConexion);
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
