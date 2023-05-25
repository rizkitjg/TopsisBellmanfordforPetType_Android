package com.example.bftopsis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickInputTopsis(View view) {
        Intent i = new Intent(MainActivity.this, InputTopsisActivity.class);
        startActivity(i);
    }

    public void onClickInputNodes(View view) {
        Intent i = new Intent(MainActivity.this, InputNodesActivity.class);
        startActivity(i);
    }

    public void onClickListPetShop(View view) {
        Intent i = new Intent(MainActivity.this, PetShopListActivity.class);
        startActivity(i);
    }

    public void onClickHelp(View view) {
        Intent i = new Intent(MainActivity.this, HelpActivity.class);
        startActivity(i);
    }
}