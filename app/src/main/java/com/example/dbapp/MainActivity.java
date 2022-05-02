package com.example.dbapp;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Database db;
    private EditText editName;
    private EditText editSurname;
    private EditText editAge;
    private Button saveButton;
    private Button deleteButton;
    private TextView resText;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new Database(this);
        editName = (EditText) findViewById(R.id.editTextTextPersonName2);
        editSurname = (EditText) findViewById(R.id.editTextTextPersonName3);
        editAge = (EditText) findViewById(R.id.editTextTextPersonName4);
        saveButton = (Button) findViewById(R.id.button1);
        deleteButton = (Button) findViewById(R.id.button2);
        resText = (TextView) findViewById(R.id.res);
        if(db.length() > 0){
            for(int i = 0; i < db.length(); i++){
                String name = db.select(i).getName();
                String surname = db.select(i).getSurname();
                int age = db.select(i).getAge();
                resText.append(String.format("%s %s, %d лет \n", surname, name, age));
            }
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString();
                String surname = editSurname.getText().toString();
                int age = Integer.valueOf(String.valueOf(editAge.getText()));
                db.insert(name, surname, age);
                Log.d("Sas", String.valueOf(db.length()));
                resText.append(String.format("%s %s, %d лет\n", surname, name, age));
            }

        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.deleteAll();
                resText.setText("");
            }
        });

    }
}