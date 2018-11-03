package com.example.apple.crime_management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PoliceActivity extends AppCompatActivity
{
    @BindView(R.id.activity_police_read_tips_button)
    Button readTipsButton;
    @BindView(R.id.activity_police_read_complaints_button)
    Button readComplaintButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_police);
        ButterKnife.bind (this);
        setup ( );
    }

    private void setup()
    {
        //setup read tips button
        readTipsButton.setOnClickListener (new View.OnClickListener ( )
        {
            @Override
            public void onClick(View view)
            {
                Intent readTipsIntent = new Intent (PoliceActivity.this, ViewTipsActivity.class);
                startActivity (readTipsIntent);

            }
        });

        readComplaintButton.setOnClickListener (new View.OnClickListener ( )
        {
            @Override
            public void onClick(View view)
            {
                Intent readTipsIntent = new Intent (PoliceActivity.this, ViewTipsActivity.class);
                startActivity (readTipsIntent);
            }
        });
    }
}


