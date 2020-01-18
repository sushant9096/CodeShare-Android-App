package com.example.codeshare;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewModeActivity extends AppCompatActivity {

    DatabaseReference ref;
    String link;
    private EditText txt, output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mode);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Code Share");

        txt = findViewById(R.id.txt);
        output = findViewById(R.id.outputTxt2);
        ref = FirebaseDatabase.getInstance().getReference().child("Codes");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            link = extras.getString("Link");

            ref.child(link).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null)
                        txt.setText(dataSnapshot.getValue().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.omenu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item3: {

                new querySender(txt.getText().toString(), this).start();
                Boolean flag = false;
                while (!flag) {
                    flag = querySender.returnStatus();
                }
                output.setText(querySender.str);
                querySender.flag = false;
                //String s = querySender.str;
                //outputText.setText(s);
            }
        }


        return super.onOptionsItemSelected(item);
    }

}
