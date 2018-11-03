package com.example.apple.crime_management;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.apple.crime_management.model.ComplaintObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class ShowComplaintActivity extends AppCompatActivity
{
    @BindView(R.id.activity_show_complaint_name_text_view)
    TextView nameTextView;
    @BindView(R.id.activity_show_complaint_text_view)
    TextView complaintTextView;
    @BindView (R.id.activity_show_aadhar_text_view)
    TextView aadharTextView;

    ComplaintObject complaintObject;
    Realm realm = Realm.getDefaultInstance ( );

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_show_complaint);
        ButterKnife.bind (this);
        setup ( );
    }

    private void setup()
    {
        //get id
        String id = getIntent ( ).getStringExtra ("cid");
        complaintObject = realm.where (ComplaintObject.class).equalTo ("id", id).findFirst ( );
        nameTextView.setText (complaintObject.getPersonName ( ));
        complaintTextView.setText (complaintObject.getComplaint ( ));
        aadharTextView.setText (complaintObject.getAadhar ());
    }
}
