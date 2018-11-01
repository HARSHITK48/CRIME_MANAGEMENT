package com.example.apple.crime_management;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apple.crime_management.model.AnonymousTipObject;

import javax.annotation.Nonnegative;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class PublicActivity extends AppCompatActivity
{
    @BindView(R.id.activity_public_most_wanted_button)
    Button mostWantedButton;
    @BindView(R.id.activity_public_file_complaint_button)
    Button fileComplaintButton;
    @BindView(R.id.activity_public_tip_button)
    Button tipButton;
    @BindView(R.id.activity_public_scan_face_button)
    Button scanFaceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_public);
        ButterKnife.bind (this);
        setup ( );
    }

    private void setup()
    {
        //setup complaint button
        fileComplaintButton.setOnClickListener (new View.OnClickListener ( )
        {
            @Override
            public void onClick(View view)
            {
                Intent fileComplaintIntent = new Intent (PublicActivity.this, NewComplaintActivity.class);
                startActivity (fileComplaintIntent);
            }
        });

        //setup tip button
        tipButton.setOnClickListener (new View.OnClickListener ( )
        {
            @Override
            public void onClick(View view)
            {
                AnonymousTipDialogFragment anonymousTipDialogFragment = new AnonymousTipDialogFragment ( );
                anonymousTipDialogFragment.show (getSupportFragmentManager ( ), "AnonymousTipDialogFragment");
            }
        });
    }

    public static class AnonymousTipDialogFragment extends DialogFragment
    {
        Realm realm = Realm.getDefaultInstance ( );

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder (getContext ( ));
            builder.setTitle ("Anonymous Tip");

            //inflate and set dialog view
            final View view = LayoutInflater.from (getContext ( )).inflate (R.layout.layout_anonymous_tip_dialog_fragment, null);
            builder.setView (view);
            builder.setPositiveButton ("Submit", new DialogInterface.OnClickListener ( )
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    realm.executeTransaction (new Realm.Transaction ( )
                    {
                        @Override
                        public void execute(Realm realm)
                        {
                            EditText editText = view.findViewById (R.id.layout_anonymous_tip_dialog_fragment_edit_text);
                            AnonymousTipObject object = realm.createObject (AnonymousTipObject.class);
                            object.setTip (editText.getText ( ).toString ( ));
                            Toast.makeText (getContext ( ), "Tip Submitted", Toast.LENGTH_SHORT).show ( );
                            dismiss ( );
                        }
                    });
                }
            });
            builder.setNegativeButton ("Cancel", null);
            return builder.create ( );
        }
    }
}
