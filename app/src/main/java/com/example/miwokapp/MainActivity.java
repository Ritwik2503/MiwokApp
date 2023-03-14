package com.example.miwokapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView numbers= findViewById(R.id.numbers);
        TextView familyMembers= findViewById(R.id.family);
        TextView phrases= findViewById(R.id.phrases);
        TextView colors = findViewById(R.id.colors);
        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, NumbersActivity.class);
                startActivity(intent);
            }
        });
        colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, ColoursActivity.class);
                startActivity(intent);
            }
        });
        familyMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, FamilyMembersActivity.class);
                startActivity(intent);
            }
        });
        phrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, PhrasesActivity.class);
                startActivity(intent);
            }
        });
    }
}