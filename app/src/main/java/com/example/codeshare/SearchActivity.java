package com.example.codeshare;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

//import android.support.v7.widget.Toolbar;

public class SearchActivity extends AppCompatActivity {

    EditText etLink;
    ImageButton imgBtn;
    ListView lView;
    DatabaseReference ref, ref1;
    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Code Share");

        etLink = findViewById(R.id.etLink);
        imgBtn = findViewById(R.id.SearchLinkBtn);
        lView = findViewById(R.id.listView);
        ref = FirebaseDatabase.getInstance().getReference().child("Links");
        ref1 = FirebaseDatabase.getInstance().getReference().child("Codes");

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(TextUtils.isEmpty(etLink.getText()))) {
                    String str = etLink.getText().toString();
                    String strLink = getActualLink(str);
                    list.add(strLink);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
                    lView.setAdapter(adapter);

                }

            }
        });

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String link = list.get(position);
                Intent intent = new Intent(SearchActivity.this, ViewModeActivity.class);
                intent.putExtra("Link", link);
                startActivity(intent);
            }
        });


    }

    private String getActualLink(String str) {
        String ret = "";
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '_') {
                break;
            }
            ret = ret + c;
        }
        return ret;
    }
}