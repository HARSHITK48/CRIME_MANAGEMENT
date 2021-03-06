package com.example.apple.crime_management;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apple.crime_management.model.MostWantedObject;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class MostWantedActivity extends AppCompatActivity
{
    @BindView(R.id.activity_most_wanted_recycler_view)
    RecyclerView recyclerView;

    Realm realm = Realm.getDefaultInstance ( );
    RealmResults<MostWantedObject> mostWantedObjects;
    MostWantedAdapter mostWantedAdapter = new MostWantedAdapter ();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_most_wanted);
        ButterKnife.bind (this);
        setup ( );
    }

    private void setup()
    {
        generate ( );

        //setup recycler view
        recyclerView.setLayoutManager (new LinearLayoutManager (this));
        recyclerView.setAdapter (mostWantedAdapter);
    }

    private void generate()
    {
        mostWantedObjects = realm.where (MostWantedObject.class).findAll ( );

        if (mostWantedObjects.isEmpty ( ))
        {
            //generate list
            for (int i = 0; i < 100; i++)
            {
                realm.beginTransaction ( );
                MostWantedObject newObject = realm.createObject (MostWantedObject.class);
                newObject.setName (UUID.randomUUID ( ).toString ( ));
                newObject.setPosition (i + 1);
                realm.commitTransaction ( );
            }
        }
    }

    public class MostWantedAdapter extends RecyclerView.Adapter<MostWantedViewHolder>
    {
        @NonNull
        @Override
        public MostWantedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
        {
            View view = LayoutInflater.from (MostWantedActivity.this).inflate (R.layout.view_holder_most_wanted, viewGroup, false);
            return new MostWantedViewHolder (view);
        }

        @Override
        public void onBindViewHolder(@NonNull MostWantedViewHolder mostWantedViewHolder, int i)
        {
            MostWantedObject mostWantedObject = mostWantedObjects.get (i);

            //bind views
            mostWantedViewHolder.nameTextView.setText (mostWantedObject.getName ());
            mostWantedViewHolder.positionTextView.setText ("" + mostWantedObject.getPosition ());
        }

        @Override
        public int getItemCount()
        {
            return mostWantedObjects.size ();
        }
    }

    public class MostWantedViewHolder extends RecyclerView.ViewHolder
    {
        @BindView (R.id.view_holder_most_wanted_name_text_view)
        TextView nameTextView;
        @BindView (R.id.view_holder_most_wanted_danger_level_text_view)
        TextView positionTextView;

        public MostWantedViewHolder(View itemView)
        {
            super (itemView);
            ButterKnife.bind (this, itemView);
        }
    }
}
