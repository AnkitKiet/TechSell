package com.techsell.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.techsell.R;

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

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ankit on 26/06/16.
 */
public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    @Bind(R.id.name)
    EditText etName;
    @Bind(R.id.email)
    EditText etEmail;
    @Bind(R.id.password)
    EditText etPassword;
    @Bind(R.id.gender)
    TextView etGender;
    @Bind(R.id.contact)
    EditText etContact;
    @Bind(R.id.age)
    EditText etAge;
    @Bind(R.id.collage)
    TextView etCollage;
    @Bind(R.id.btnRegister)
    Button btnRegister;
    @Bind(R.id.btnLinkToLoginScreen)
    Button btnLinkToLogin;

    String user = new String();
    Integer exist;
    String pass = new String();
    String email = new String();
    String age = new String();
    String collage = new String();
    String gender = new String();
    String contact = new String();
Spinner GenderList;
    Spinner CollageList;
    JSONObject jsonObj = null;
    HttpEntity entity;
    String resultnew = null;
    String response;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        GenderList = (Spinner) findViewById(R.id.genderlist);
        CollageList = (Spinner) findViewById(R.id.collagelist);
        GenderList.setOnItemSelectedListener(this);
        CollageList.setOnItemSelectedListener(this);
        List<String> Collage = new ArrayList<String>();
        Collage.add("KIET");
        Collage.add(" JAYPEE INSTITUTE");
        Collage.add("RKGIT");
        Collage.add("NIET");
        Collage.add("AKG");
        Collage.add("JSS");
        List<String> GENDER = new ArrayList<String>();
        GENDER.add("MALE");
        GENDER.add(" FEMALE");
        GENDER.add("Others");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Collage);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CollageList.setAdapter(dataAdapter);
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, GENDER);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        GenderList.setAdapter(dataAdapter1);



        populate();
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    private void populate() {
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btnRegister.setBackgroundResource(R.drawable.ripple);
        }
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etAge.getText().toString().trim().equals("") || !etContact.getText().toString().trim().equals("") || etName.getText().toString().trim().equals("") || etPassword.getText().toString().equals("") || etEmail.getText().toString().equals("")) {
                    if (etContact.getText().length() == 10) {
                        user = etName.getText().toString();
                        pass = etPassword.getText().toString();
                        email = etEmail.getText().toString();
                        gender  = GenderList.getSelectedItem().toString();
                        age = etAge.getText().toString();
                        collage  = CollageList.getSelectedItem().toString();
                        contact = etContact.getText().toString();


                        Network net = new Network();

                        net.execute();

                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "Mobile Number Is Invalid", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Fields can't be left empty", Toast.LENGTH_SHORT).show();
                }
            }
         });
    }




    public  class Network extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);

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
            HttpEntity entity = null;
            HttpResponse httpResponse = null;
            httpClient = new DefaultHttpClient();
            List<NameValuePair> pairs = new ArrayList<NameValuePair>(1);
            String url = "http://192.168.16.1:1234/snb/client_register.php";
            pairs.add(new BasicNameValuePair("email", email));
            pairs.add(new BasicNameValuePair("user", user));
            pairs.add(new BasicNameValuePair("pass", pass));
            pairs.add(new BasicNameValuePair("gender", gender));
            pairs.add(new BasicNameValuePair("collage", collage));
            pairs.add(new BasicNameValuePair("age", age));
            pairs.add(new BasicNameValuePair("contact", contact));

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
            JSONObject jsonObj = new JSONObject(resultnew);
            exist = jsonObj.getInt("exists");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (exist == 1) {
            Toast.makeText(RegisterActivity.this, "user already exist..try another", Toast.LENGTH_LONG).show();
            Intent i = new Intent(RegisterActivity.this, RegisterActivity.class);
            startActivity(i);


        } else {
            Toast.makeText(RegisterActivity.this, "Successfully Registered :)", Toast.LENGTH_LONG).show();
            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(i);

        }


    }

}






