package com.techsell.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.techsell.R;
import com.techsell.global.AppConfig;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ankit on 26/06/16.
 */
public class LoginActivity extends AppCompatActivity{
    String user = new String();
    Integer exist;
    String pass = new String();
    JSONObject jsonObj = null;
    HttpEntity entity;
    String resultnew = null;
    Button login;
    Button loginToRegister;
    EditText contact, password;
    public static final String Contact = "Contact";
    public static final String Password = "Password";
    public String us = "1234";
    public String ps = "admin";
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        contact = (EditText) findViewById(R.id.contact);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.btnLogin);
        loginToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        loginToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);

                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user = contact.getText().toString();
                pass = password.getText().toString();
                if(user.equals(us) && pass.equals(ps))
                {


                    AppConfig.login(LoginActivity.this);


                    SharedPreferences pref = getApplicationContext().getSharedPreferences("Options", MODE_PRIVATE);
                    editor=pref.edit();
                    editor.putString("username", user);
                    editor.commit();

                    Intent i = new Intent(LoginActivity.this, DashboardActivity.class);

                    startActivity(i);

                }
                else
                {
                Network net = new Network();

                net.execute();
            }
            }
        });

    }

    public  class Network extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);

        protected void onPreExecute() {
            progressDialog.setTitle("Loading.....");
            progressDialog.setMessage("Loading.....");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface arg0) {
                    Network.this.cancel(true);
                }
            });
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {

            DefaultHttpClient httpClient = new DefaultHttpClient();
            entity = null;
            HttpResponse httpResponse = null;
            httpClient = new DefaultHttpClient();
            List<NameValuePair> pairs = new ArrayList<NameValuePair>(1);
            String url = "http://192.168.16.1:1234/snb/client_login.php";
            pairs.add(new BasicNameValuePair("user", user));
            pairs.add(new BasicNameValuePair("pass", pass));
            InputStream inputStream = null;
            String result = null;

            try {
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(pairs));
                httpResponse = httpClient.execute(httpPost);

                entity = httpResponse.getEntity();

                inputStream = entity.getContent();
                // json is UTF-8 by default
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                result = sb.toString();
            } catch (Exception e) {
                // Oops
            }
            finally {
                try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
            }
            return result;
        }

        protected void onPostExecute(String result) {
            resultnew = result;
            check();

        }
    }

    private void check() {
        try {
            jsonObj = new JSONObject(resultnew);
            exist = jsonObj.getInt("exists");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (exist == 1) {
            Toast.makeText(LoginActivity.this, "Login Success :)", Toast.LENGTH_LONG).show();
            AppConfig.login(LoginActivity.this);
            Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(i);


        } else {
            Toast.makeText(LoginActivity.this, "Failed :(", Toast.LENGTH_LONG).show();
            Intent i = new Intent(LoginActivity.this, LoginActivity.class);
            startActivity(i);

        }


    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
}

