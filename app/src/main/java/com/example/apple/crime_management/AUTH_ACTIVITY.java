package com.example.apple.crime_management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AUTH_ACTIVITY extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth__activity);


        Button PUBLIC_BUTTON=findViewById(R.id.public_login_button);
        Button POLICE_BUTTON=findViewById(R.id.police_login_button);

        PUBLIC_BUTTON.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent public_intent = new Intent(AUTH_ACTIVITY.this,PUBLIC_LOGIN.class);
                startActivity(public_intent);

            }
        });

        POLICE_BUTTON.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent police_intent = new Intent(AUTH_ACTIVITY.this,POLICE_LOGIN.class);
                startActivity(police_intent);

            }
        });





    }
}
