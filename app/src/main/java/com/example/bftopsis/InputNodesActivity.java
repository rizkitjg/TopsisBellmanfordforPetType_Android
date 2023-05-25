package com.example.bftopsis;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.view.Window.FEATURE_NO_TITLE;

public class InputNodesActivity extends AppCompatActivity {
    private static final String TAG = "InputNodesActivity";

    public static final String mapActivity = "Data yang mau dibawa ke map";

    private Spinner fromWhere;
    private Spinner toWhere;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /**
         * get rid of the bar
         */
        requestWindowFeature(FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_inputnodes);

        /**
         *   GUI ini
         */
        fromWhere = findViewById(R.id.fromWhere);
        toWhere = findViewById(R.id.toWhere);

        Button startButton = findViewById(R.id.startButton);

        /**
         *  spinners
         */
        List<StringWithTag> listFrom = new ArrayList<StringWithTag>();
        List<StringWithTag> listTo = new ArrayList<StringWithTag>();


        /**
         * We load the nodes and edges, they are
         * csv files in the raw folder
         *
         * this is a list of nodes that we want as starting points
         * preferably we should list many of them with names.
         *
         * menu_from_nodes.csv
         *
         * Example: 1,1412,1673,Node Name,1,1
         *
         * (ID,posX,posY,Node Name,level,importance)
         *
         */
        InputStream inputStreamFromNodes = getResources().openRawResource(R.raw.menu_from_nodes);
        new CSVParse(inputStreamFromNodes);
        List fromNodeList = CSVParse.read();

        listFrom.add(new StringWithTag(getString(R.string.dropdownFromWhere), "0"));

        for (Integer i = 0; i < fromNodeList.size(); i++) {
            String[] node = (String[]) fromNodeList.get(i);
            listFrom.add(new StringWithTag(node[3], node[0]));
        }

        InputStream inputStreamToNodes = getResources().openRawResource(R.raw.menu_to_nodes);
        new CSVParse(inputStreamToNodes);
        List toNodeList = CSVParse.read();

        listTo.add(new StringWithTag(getString(R.string.dropdownToWhere), "0"));

        for (Integer i = 0; i < toNodeList.size(); i++) {
            String[] node = (String[]) toNodeList.get(i);
            listTo.add(new StringWithTag(node[3], node[0]));
        }

        ArrayAdapter<StringWithTag> adapterFrom = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listFrom);

        listTo.set(0, new StringWithTag(getString(R.string.dropdownToWhere), "0"));

        ArrayAdapter<StringWithTag> adapterTo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listTo);


        fromWhere.setAdapter(adapterFrom);
        toWhere.setAdapter(adapterTo);

        startButton.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                    goToMap();
                }
                return false;
            }

        });

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

    public void goToMap() {
        StringWithTag fromSelected = (StringWithTag) fromWhere.getSelectedItem();
        StringWithTag toSelected = (StringWithTag) toWhere.getSelectedItem();

        if (fromSelected.tag == "0" || toSelected.tag == "0") {
            Toast.makeText(getApplicationContext(), getString(R.string.error_nothing_selected), Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, MapsActivity.class);

            intent.putExtra(mapActivity, fromSelected.tag + ";" + toSelected.tag);
            this.finish();
            startActivity(intent);
        }
    }
}