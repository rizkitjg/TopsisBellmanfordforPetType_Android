package com.example.bftopsis;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class InputTopsisActivity extends AppCompatActivity {
    private RadioGroup radioGroup1, radioGroup2, radioGroup3, radioGroup4, radioGroup5;
    private Button btnDisplay;
    //private static List alternativeList;
    private double[][] alternativeArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputtopsis);
        addListenerOnButton();

        ImageView buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                backToMenu();
            }

        });
    }

    public void backToMenu() {

        Intent intent = new Intent(this, MainActivity.class);
        this.finish();
        startActivity(intent);

    }

    public void addListenerOnButton() {
        radioGroup1 = (RadioGroup) findViewById(R.id.radio_group_1);
        radioGroup2 = (RadioGroup) findViewById(R.id.radio_group_2);
        radioGroup3 = (RadioGroup) findViewById(R.id.radio_group_3);
        radioGroup4 = (RadioGroup) findViewById(R.id.radio_group_4);
        radioGroup5 = (RadioGroup) findViewById(R.id.radio_group_5);
        btnDisplay = (Button) findViewById(R.id.btn_hasil);

        btnDisplay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Get index chosen in radioGroup
                int index1 = radioGroup1.indexOfChild(findViewById(radioGroup1.getCheckedRadioButtonId()));
                int index2 = radioGroup2.indexOfChild(findViewById(radioGroup2.getCheckedRadioButtonId()));
                int index3 = radioGroup3.indexOfChild(findViewById(radioGroup3.getCheckedRadioButtonId()));
                int index4 = radioGroup4.indexOfChild(findViewById(radioGroup4.getCheckedRadioButtonId()));
                int index5 = radioGroup5.indexOfChild(findViewById(radioGroup5.getCheckedRadioButtonId()));

                if(index1 == -1 || index2 == -1 || index3 == -1 || index4 == -1 || index5 == -1) {
                    Toast.makeText(getApplicationContext(), "Silahkan diisi seluruh kriterianya", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent i = new Intent(InputTopsisActivity.this, HasilTopsisActivity.class);
                    i.putExtra("prefUser", index1 + ";" + index2 + ";" + index3 + ";" + index4 + ";" + index5);
                    startActivity(i);
                }
            }
        });
    }



}