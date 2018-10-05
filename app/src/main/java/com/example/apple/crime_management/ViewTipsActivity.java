package com.example.apple.crime_management;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.apple.crime_management.model.AnonymousTipObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;

public class ViewTipsActivity extends AppCompatActivity
{
    @BindView(R.id.activity_view_tips_search_edit_text)
    EditText searchEditText;
    @BindView(R.id.activity_view_tips_main_recycler_view)
    RecyclerView mainRecyclerView;

    Realm realm = Realm.getDefaultInstance ( );
    RealmResults<AnonymousTipObject> tips;
    TipsAdapter tipsAdapter = new TipsAdapter ( );

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_view_tips);
        ButterKnife.bind (this);
        setup ( );
    }

    private void setup()
    {
        //get anonymous tips
        tips = realm.where (AnonymousTipObject.class).findAll ( );

        //setup recyclerview
        mainRecyclerView.setLayoutManager (new LinearLayoutManager (this));
        mainRecyclerView.setAdapter (tipsAdapter);

        //setup search
        searchEditText.addTextChangedListener (new TextWatcher ( )
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                String searchString = charSequence.toString ();

                if(!searchString.isEmpty ())
                {
                    //find tips
                    tips = realm.where (AnonymousTipObject.class).contains ("tip", searchString, Case.INSENSITIVE).findAll ();
                    tipsAdapter.notifyDataSetChanged ();
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });
    }

    public class TipsAdapter extends RecyclerView.Adapter<TipViewHolder>
    {
        @NonNull
        @Override
        public TipViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
        {
            View view = LayoutInflater.from (ViewTipsActivity.this).inflate (R.layout.view_holder_tip, viewGroup, false);
            return new TipViewHolder (view);
        }

        @Override
        public void onBindViewHolder(@NonNull TipViewHolder tipViewHolder, int i)
        {
            AnonymousTipObject tipObject = tips.get (i);

            //setup tip data
            tipViewHolder.tipTextView.setText (tipObject.getTip ( ));
        }

        @Override
        public int getItemCount()
        {
            if (tips != null)
            {
                return tips.size ( );
            } else
            {
                return 0;
            }
        }
    }

    public class TipViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.view_holder_tip_text_view)
        TextView tipTextView;

        TipViewHolder(View itemView)
        {
            super (itemView);
            ButterKnife.bind (this, itemView);
        }
    }
}
