package com.example.bftopsis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PetShopListActivity extends AppCompatActivity {
    private RecyclerView rvPetShops;
    private ArrayList<PetShop> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petshoplist);

        rvPetShops = findViewById(R.id.rv_petshops);
        rvPetShops.setHasFixedSize(true);

        list.addAll(PetShopsData.getListData());
        showRecyclerList();

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


    private void showRecyclerList(){
        ListPetShopAdapter listPetShopAdapter = new ListPetShopAdapter(list);
        rvPetShops.setLayoutManager(new LinearLayoutManager(this));
        rvPetShops.setAdapter(listPetShopAdapter);

        listPetShopAdapter.setOnItemClickCallback(new ListPetShopAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(PetShop data) {
                showSelectedPetShop(data);
            }
        });
    }

    private void showSelectedPetShop(PetShop petShop) {
        Intent i = new Intent(PetShopListActivity.this, PetShopDetailActivity.class);
        i.putExtra(PetShopDetailActivity.EXTRA_NAME, petShop.getName());
        i.putExtra(PetShopDetailActivity.EXTRA_PHOTO, petShop.getPhoto());
        i.putExtra(PetShopDetailActivity.EXTRA_ADDRESS, petShop.getAddress());
        startActivity(i);
        Toast.makeText(this, "Kamu memilih " + petShop.getName(), Toast.LENGTH_SHORT).show();
    }
}
