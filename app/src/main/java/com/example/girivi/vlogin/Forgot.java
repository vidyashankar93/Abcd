package com.example.girivi.vlogin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.example.girivi.vlogin.Appconfig.Forgot_URL;

public class Forgot extends AppCompatActivity implements View.OnClickListener{
    //public static final String Forgot_URL = "http://triphpe.16mb.com/forgotpass.php";
    public static final String KEY_EMAIL="email";
    private EditText forgot;
    private Button submit;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        forgot = (EditText) findViewById(R.id.etforgot);
        submit = (Button) findViewById(R.id.btnsubmit);
        submit.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        forget();
    }

    private void forget() {
        email = forgot.getText().toString().trim().toLowerCase();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Appconfig.Forgot_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String res = response;
                        int len = response.length();
                        if(len == 6){
                            Intent i = new Intent(getApplicationContext(),Verify.class);
                            i.putExtra("OTP", res);
                            i.putExtra("EMAIL", email);
                            startActivity(i);
                        }
                        else {

                            Toast.makeText(Forgot.this, response, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Forgot.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_EMAIL,email);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
