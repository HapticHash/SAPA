package com.codebrain.harshit.sapa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private Button btnPass, btnEmp;
    private EditText userName, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = (EditText)findViewById(R.id.input_username);
        password = (EditText)findViewById(R.id.input_pass);
        btnPass = (Button)findViewById(R.id.btn);
        btnEmp = (Button)findViewById(R.id.btn2);

    }

    public void onClickBtnEmp(View view)
    {
        String inpUser = userName.getText().toString();
        String inpPass = password.getText().toString();

        if(inpUser.equals("Safety") && inpPass.equals("Railway"))
        {
            Intent i = new Intent(Login.this, MainActivity.class);
            startActivity(i);
            /*Toast.makeText(this, "Successfully Logged In as Employee.", Toast.LENGTH_LONG).show();*/
        }
        else
        {
            Toast.makeText(this, "Username or Password is INCORRECT!", Toast.LENGTH_LONG).show();
        }

    }

    public void onClickBtnPass(View v)
    {
        Intent i = new Intent(Login.this, MainActivity.class);
        startActivity(i);
       /* Toast.makeText(this, "Continuing as Public", Toast.LENGTH_LONG).show(); */
    }

}
