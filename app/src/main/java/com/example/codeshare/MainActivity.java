package com.example.codeshare;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    static EditText outputText;
    EditText txt;
    DatabaseReference ref, ref1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Code Share");

        txt = findViewById(R.id.txt);
        outputText = findViewById(R.id.outputTxt);

        /*outputText.setScroller(new Scroller(MainActivity.this));
        outputText.setVerticalScrollBarEnabled(true);
        outputText.setMovementMethod(new ScrollingMovementMethod());

        txt.setScroller(new Scroller(MainActivity.this));
        txt.setVerticalScrollBarEnabled(true);
        txt.setMovementMethod(new ScrollingMovementMethod());*/

        ref = FirebaseDatabase.getInstance().getReference().child("Codes");
        ref1 = FirebaseDatabase.getInstance().getReference().child("Links");
        //final String orgpushkey = ref1.push().getKey();
        final String orgpushkey = "12345";
        String duppushkey = orgpushkey + "_gunman_ml";
        Toast.makeText(this, ":" + orgpushkey, Toast.LENGTH_SHORT).show();

        ref1.child(duppushkey).setValue(orgpushkey);

        txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ref.child(orgpushkey).setValue(s.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.item2:
                Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item3: {

                new querySender(txt.getText().toString(), this).start();
                Boolean flag = false;
                while (!flag) {
                    flag = querySender.returnStatus();
                }
                MainActivity.outputText.setText(querySender.str);
                querySender.flag = false;
                //String s = querySender.str;
                //outputText.setText(s);
            }
        }


        return super.onOptionsItemSelected(item);
    }
}
