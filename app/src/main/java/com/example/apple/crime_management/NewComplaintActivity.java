package com.example.apple.crime_management;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.apple.crime_management.model.ComplaintObject;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.wonderkiln.camerakit.CameraKitEventCallback;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraView;

import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class NewComplaintActivity extends AppCompatActivity
{
    @BindView(R.id.activity_new_complaint_camera_view)
    CameraView cameraView;
    @BindView(R.id.activity_new_complaint_capture_button)
    CardView captureButton;
    @BindView(R.id.activity_new_complaint_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.activity_new_complaint_name_edit_text)
    EditText nameEditText;
    @BindView(R.id.activity_new_complaint_complaint_edit_text)
    EditText complaintEditText;

    Realm realm = Realm.getDefaultInstance ( );

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_new_complaint);
        ButterKnife.bind (this);
        setup ( );
    }

    @Override
    protected void onResume()
    {
        super.onResume ( );
        cameraView.start ( );
    }

    @Override
    protected void onPause()
    {
        cameraView.stop ( );
        super.onPause ( );
    }

    private void setup()
    {
        //setup capture button
        captureButton.setOnClickListener (new View.OnClickListener ( )
        {
            @Override
            public void onClick(View view)
            {
                Log.d ("DebugK", "Capturing");
                progressBar.setVisibility (View.VISIBLE);
                cameraView.captureImage (new CameraKitEventCallback<CameraKitImage> ( )
                {
                    @Override
                    public void callback(CameraKitImage cameraKitImage)
                    {
                        //detect face
                        Log.d ("DebugK", "Camera Kit Callback");
                        FirebaseVisionFaceDetectorOptions options = new FirebaseVisionFaceDetectorOptions.Builder ( )
                                .setPerformanceMode (FirebaseVisionFaceDetectorOptions.FAST)
                                .enableTracking ( )
                                .build ( );
                        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap (cameraKitImage.getBitmap ( ));
                        FirebaseVisionFaceDetector detector = FirebaseVision.getInstance ( ).getVisionFaceDetector (options);
                        detector.detectInImage (image).addOnSuccessListener (new OnSuccessListener<List<FirebaseVisionFace>> ( )
                        {
                            @Override
                            public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces)
                            {
                                if (firebaseVisionFaces.isEmpty ( ))
                                    return;

                                FirebaseVisionFace face = firebaseVisionFaces.get (0);
                                progressBar.setVisibility (View.GONE);
                                Log.d ("DebugK", "Face " + face.getTrackingId ( ));

                                //register the complaint if details provided
                                if (!nameEditText.getText ( ).toString ( ).isEmpty ( ) && !complaintEditText.getText ( ).toString ( ).isEmpty ( ))
                                {
                                    realm.beginTransaction ( );
                                    ComplaintObject newComplaint = realm.createObject (ComplaintObject.class);
                                    newComplaint.setId (UUID.randomUUID ( ).toString ( ));
                                    newComplaint.setPersonName (nameEditText.getText ( ).toString ( ));
                                    newComplaint.setPersonId (face.getTrackingId ( ));
                                    newComplaint.setComplaint (complaintEditText.getText ( ).toString ( ));
                                    realm.commitTransaction ( );
                                }

                                //search db for existing complaints
                                else
                                {
                                    ComplaintObject complaintObject = realm.where (ComplaintObject.class).equalTo ("personId", face.getTrackingId ( )).findFirst ( );
                                    Intent intent = new Intent (NewComplaintActivity.this, ShowComplaintActivity.class);
                                    intent.putExtra ("cid", complaintObject.getId ( ));
                                    startActivity (intent);
                                }
                            }
                        }).addOnFailureListener (new OnFailureListener ( )
                        {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                progressBar.setVisibility (View.GONE);
                                Log.d ("DebugK", "Error " + e.getMessage ( ));
                            }
                        });
                    }
                });
            }
        });
    }
}
