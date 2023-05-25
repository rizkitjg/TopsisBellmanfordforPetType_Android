package com.example.bftopsis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class PetShopDetailActivity extends AppCompatActivity {
    public static final String EXTRA_PHOTO = "extra_photo";
    public static final String EXTRA_NAME = "extra_name";
    public static final String EXTRA_ADDRESS = "extra_address";

    ImageView ivImageReceived;
    TextView tvImageNameReceived;
    TextView tvNameReceived;
    TextView tvAddressReceived;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petshopdetail);

        String photo = getIntent().getStringExtra(EXTRA_PHOTO);
        ivImageReceived = findViewById(R.id.iv_image);
        Glide.with(this)
                .load(photo)
                .into(ivImageReceived);

        //ivImageReceived.setImageResource(photo);

        tvNameReceived = findViewById(R.id.tv_petshop_name);
        tvImageNameReceived = findViewById(R.id.tv_image_name);
        String name = getIntent().getStringExtra(EXTRA_NAME);
        tvNameReceived.setText(name);
        tvImageNameReceived.setText(name);

        tvAddressReceived = findViewById(R.id.tv_petshop_address);
        String address = getIntent().getStringExtra(EXTRA_ADDRESS);
        tvAddressReceived.setText("Alamat: "+ address);

        ImageView buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                backToMenu();
            }

        });
    }

    public void backToMenu() {

        Intent intent = new Intent(this, PetShopListActivity.class);
        this.finish();
        startActivity(intent);

    }
}
