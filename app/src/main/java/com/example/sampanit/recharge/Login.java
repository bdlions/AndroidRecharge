package com.example.sampanit.recharge;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {
    private static EditText etLoginUserName, etPassword;

    private static Button button_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etLoginUserName = (EditText) findViewById(R.id.etLoginUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        onClickButtonLoginListener();

    }
    public void onClickButtonLoginListener(){
        button_Login = (Button)findViewById(R.id.bLogin);
        button_Login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Intent intentLogin = new Intent("com.example.sampanit.recharge.RechargeMenu");
                        //startActivity(intentLogin);

                        try {


                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                            StrictMode.setThreadPolicy(policy);

                            HttpClient client = new DefaultHttpClient();
                            //HttpPost post = new HttpPost("http://50.18.235.96:3030/processqrcode");
                            //HttpPost post = new HttpPost("http://122.144.10.249/rechargeserver/welcome/app_test");
                            HttpPost post = new HttpPost("http://122.144.10.249/rechargeserver/androidapp/auth/login");



                            List<NameValuePair> nameValuePairs = new ArrayList<>();


                            nameValuePairs.add(new BasicNameValuePair("email", etLoginUserName.getText().toString()));
                            nameValuePairs.add(new BasicNameValuePair("password", etPassword.getText().toString()));
                            // nameValuePairs.add(new BasicNameValuePair("email",etLoginUserName.getText().toString()));
                            //nameValuePairs.add(new BasicNameValuePair("password", etPassword.getText().toString()));

                            System.out.println(etLoginUserName.getText().toString());
                            System.out.println(etPassword.getText().toString());



                            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                            HttpResponse response = client.execute(post);
                            /*int responseCode = response.getStatusLine().getStatusCode();
                            if (responseCode == 200){
                                System.out.println(response);
                                //Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();
                            }*/

                            //System.out.println(response);

                            //Toast.makeText(this.getApplicationContext(),"Executed", Toast.LENGTH_SHORT).show();
                            // Get the response
                            BufferedReader rd = new BufferedReader
                                    (new InputStreamReader(response.getEntity().getContent()));
                            String result = "";
                            String line = "";
                            while ((line = rd.readLine()) != null) {
                                //textView.append(line);
                                result += line;
                            }

                            if(result != null) {
                                JSONObject resultEvent = new JSONObject(result.toString());

                                int responseCode = (int)resultEvent.get("response_code");
                                if(responseCode == 2000){
                                    JSONObject resultedUserInfo = (JSONObject) resultEvent.get("result_event");

                                    //String userN =  userInfo.getFirstName() + userInfo.getLastName();
                                    //userName.setText(userInfo.getFirstName().toString());

                                    Intent intent = new Intent(getBaseContext(), RechargeMenu.class);
                                    intent.putExtra("EXTRA_OBJECT", resultedUserInfo.toString());
                                    startActivity(intent);
                                    //Intent intentLogin = new Intent("com.example.sampanit.recharge.RechargeMenu");
                                    //To pass:
                                    //intentLogin.putExtra(RechargeMenu.class, resultedUserInfo);

// To retrieve object in second Activity
                                   // getIntent().getSerializableExtra("MyClass");

                                  // startActivity(intentLogin);

                                }

                            }
                            //Toast.makeText(this.getApplicationContext(), line, Toast.LENGTH_SHORT).show();

                        }
                        catch (Exception ex){
                            //Toast.makeText(this.getApplicationContext(),ex.getMessage(), Toast.LENGTH_SHORT).show();
                            System.out.println(ex.getMessage());
                        }
                    }
                }
        );
    }
}
