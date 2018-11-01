package com.example.apple.crime_management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.apple.crime_management.model.AnonymousTipObject;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class AuthActivity extends AppCompatActivity
{
    @BindView(R.id.activity_auth_police_login_button)
    Button policeLoginButton;
    @BindView(R.id.activity_auth_public_login_button)
    Button publicLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
        setup();
    }

    private void setup()
    {
        //setup police login button
        policeLoginButton.setOnClickListener (new View.OnClickListener ( )
        {
            @Override
            public void onClick(View view)
            {
                Intent policeLoginIntent = new Intent (AuthActivity.this, PoliceActivity.class);
                startActivity (policeLoginIntent);
            }
        });
        //setup public login button
        publicLoginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent publicIntent = new Intent(AuthActivity.this, PublicActivity.class);
                startActivity(publicIntent);
            }
        });
        
      // generateTips();
    }
    
    //method to generate tips
    public void generateTips()
    {
        for(int i = 0; i < 10000; i++)
        {
            Realm.getDefaultInstance ( ).executeTransaction (new Realm.Transaction ( )
            {
                @Override
                public void execute(Realm realm)
                {
                    AnonymousTipObject tipObject = realm.createObject (AnonymousTipObject.class);
                    tipObject.setTip (UUID.randomUUID ().toString ());
                }
            });
        }

        Toast.makeText (this, "Generated Tips", Toast.LENGTH_SHORT).show ( );
    }
}
