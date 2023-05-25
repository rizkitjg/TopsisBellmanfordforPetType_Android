package com.example.bftopsis;

import static android.view.Window.FEATURE_NO_TITLE;

import static com.example.bftopsis.InputNodesActivity.mapActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HasilTopsisActivity extends AppCompatActivity {
    private RecyclerView rvAlternatif;
    private LinearLayoutManager mManager;
    private ArrayList<Alternatif> list = new ArrayList<>();
    private ArrayList<Alternatif> alt = new ArrayList<>();
    private String[] indexData;
    private double skorAkhirPrefUser;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * get rid of the bar
         */
        requestWindowFeature(FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_hasiltopsis);

        rvAlternatif = findViewById(R.id.list_rank);
        rvAlternatif.setHasFixedSize(true);

        mManager = new LinearLayoutManager(this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        rvAlternatif.setLayoutManager(mManager);

        ImageView buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                backToMenu();
            }

        });

        Intent intent = getIntent();
        String message = intent.getStringExtra("prefUser");

        indexData = message.split(";");

        perhitunganTopsis();
        TextView tv1 = (TextView)findViewById(R.id.userRanking);
        tv1.setText("Nilai preferensi User: " + new DecimalFormat("##.##").format(skorAkhirPrefUser));
        showRecyclerList();
    }

    public void backToMenu() {

        Intent intent = new Intent(this, InputTopsisActivity.class);
        this.finish();
        startActivity(intent);
    }

    private void showRecyclerList(){
        ListAlternatifAdapter listAlternatifAdapter = new ListAlternatifAdapter(list);
        rvAlternatif.setAdapter(listAlternatifAdapter);

        listAlternatifAdapter.setOnItemClickCallback(new ListAlternatifAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Alternatif data) {
                showSelectedData(data);
            }
        });
    }

    private void showSelectedData(Alternatif alternatif) {
        Toast.makeText(this, "Kamu memilih " + alternatif.getNamaAlternatif(), Toast.LENGTH_SHORT).show();
    }

    public void perhitunganTopsis() {
        double bobot[] = {4,3,1,5,2};       //Default

        double anjingMiniPom[] = new double[5];
        double kucingNonRas[] = new double[5];
        double kucingRas[] = new double[5];
        double kelinciHias[] = new double[5];
        double burungLovebird[] = new double[5];
        double ikanMasKoki[] = new double[5];
        double hamster[] = new double[5];
        double landakMini[] = new double[5];
        double sugarGliderJoey[] = new double[5];
        double kurakuraBrasil[] = new double[5];

        double prefUser[] = {Double.valueOf(indexData[0])+1, Double.valueOf(indexData[1])+1, Double.valueOf(indexData[2])+1, Double.valueOf(indexData[3])+1, Double.valueOf(indexData[4])+1};

        InputStream inputStreamFromAlternatives = getResources().openRawResource(R.raw.alternativedata);
        new CSVParse(inputStreamFromAlternatives);
        List alternativeList = CSVParse.read();

        String[] node;
        for(int i=0; i<5; i++) {
            node = (String[]) alternativeList.get(0);
            anjingMiniPom[i] = Double.parseDouble(node[i]);
            node = (String[]) alternativeList.get(1);
            kucingNonRas[i] = Double.parseDouble(node[i]);
            node = (String[]) alternativeList.get(2);
            kucingRas[i] = Double.parseDouble(node[i]);
            node = (String[]) alternativeList.get(3);
            kelinciHias[i] = Double.parseDouble(node[i]);
            node = (String[]) alternativeList.get(4);
            burungLovebird[i] = Double.parseDouble(node[i]);
            node = (String[]) alternativeList.get(5);
            ikanMasKoki[i] = Double.parseDouble(node[i]);
            node = (String[]) alternativeList.get(6);
            hamster[i] = Double.parseDouble(node[i]);
            node = (String[]) alternativeList.get(7);
            landakMini[i] = Double.parseDouble(node[i]);
            node = (String[]) alternativeList.get(8);
            sugarGliderJoey[i] = Double.parseDouble(node[i]);
            node = (String[]) alternativeList.get(9);
            kurakuraBrasil[i] = Double.parseDouble(node[i]);
        }

        // Tahap 1 - Menentukan Skor Ternormalisasi

        double r_anjingMiniPom[] = new double[5];
        double r_kucingNonRas[] = new double[5];
        double r_kucingRas[] = new double[5];
        double r_kelinciHias[] = new double[5];
        double r_burungLovebird[] = new double[5];
        double r_ikanMasKoki[] = new double[5];
        double r_hamster[] = new double[5];
        double r_landakMini[] = new double[5];
        double r_sugarGliderJoey[] = new double[5];
        double r_kurakuraBrasil[] = new double[5];
        double r_prefUser[] = new double[5];

        for (int x = 0; x < 5; x++) {
            r_prefUser[x] =
                    prefUser[x] / Math.sqrt(
                            (anjingMiniPom[x] * anjingMiniPom[x]) +
                                    (kucingNonRas[x] * kucingNonRas[x]) +
                                    (kucingRas[x] * kucingRas[x]) +
                                    (kelinciHias[x] * kelinciHias[x]) +
                                    (burungLovebird[x] * burungLovebird[x]) +
                                    (ikanMasKoki[x] * ikanMasKoki[x]) +
                                    (hamster[x] * hamster[x]) +
                                    (landakMini[x] * landakMini[x])  +
                                    (sugarGliderJoey[x] * sugarGliderJoey[x]) +
                                    (kurakuraBrasil[x] * kurakuraBrasil[x]));

            r_anjingMiniPom[x] =
                    anjingMiniPom[x] / Math.sqrt(
                            (anjingMiniPom[x] * anjingMiniPom[x]) +
                                    (kucingNonRas[x] * kucingNonRas[x]) +
                                    (kucingRas[x] * kucingRas[x]) +
                                    (kelinciHias[x] * kelinciHias[x]) +
                                    (burungLovebird[x] * burungLovebird[x]) +
                                    (ikanMasKoki[x] * ikanMasKoki[x]) +
                                    (hamster[x] * hamster[x]) +
                                    (landakMini[x] * landakMini[x])  +
                                    (sugarGliderJoey[x] * sugarGliderJoey[x]) +
                                    (kurakuraBrasil[x] * kurakuraBrasil[x]));

            r_kucingNonRas[x] =
                    kucingNonRas[x] / Math.sqrt(
                            (anjingMiniPom[x] * anjingMiniPom[x]) +
                                    (kucingNonRas[x] * kucingNonRas[x]) +
                                    (kucingRas[x] * kucingRas[x]) +
                                    (kelinciHias[x] * kelinciHias[x]) +
                                    (burungLovebird[x] * burungLovebird[x]) +
                                    (ikanMasKoki[x] * ikanMasKoki[x]) +
                                    (hamster[x] * hamster[x]) +
                                    (landakMini[x] * landakMini[x])  +
                                    (sugarGliderJoey[x] * sugarGliderJoey[x]) +
                                    (kurakuraBrasil[x] * kurakuraBrasil[x]));

            r_kucingRas[x] =
                    kucingRas[x] / Math.sqrt(
                            (anjingMiniPom[x] * anjingMiniPom[x]) +
                                    (kucingNonRas[x] * kucingNonRas[x]) +
                                    (kucingRas[x] * kucingRas[x]) +
                                    (kelinciHias[x] * kelinciHias[x]) +
                                    (burungLovebird[x] * burungLovebird[x]) +
                                    (ikanMasKoki[x] * ikanMasKoki[x]) +
                                    (hamster[x] * hamster[x]) +
                                    (landakMini[x] * landakMini[x])  +
                                    (sugarGliderJoey[x] * sugarGliderJoey[x]) +
                                    (kurakuraBrasil[x] * kurakuraBrasil[x]));

            r_kelinciHias[x] =
                    kelinciHias[x] / Math.sqrt(
                            (anjingMiniPom[x] * anjingMiniPom[x]) +
                                    (kucingNonRas[x] * kucingNonRas[x]) +
                                    (kucingRas[x] * kucingRas[x]) +
                                    (kelinciHias[x] * kelinciHias[x]) +
                                    (burungLovebird[x] * burungLovebird[x]) +
                                    (ikanMasKoki[x] * ikanMasKoki[x]) +
                                    (hamster[x] * hamster[x]) +
                                    (landakMini[x] * landakMini[x])  +
                                    (sugarGliderJoey[x] * sugarGliderJoey[x]) +
                                    (kurakuraBrasil[x] * kurakuraBrasil[x]));

            r_burungLovebird[x] =
                    burungLovebird[x] / Math.sqrt(
                            (anjingMiniPom[x] * anjingMiniPom[x]) +
                                    (kucingNonRas[x] * kucingNonRas[x]) +
                                    (kucingRas[x] * kucingRas[x]) +
                                    (kelinciHias[x] * kelinciHias[x]) +
                                    (burungLovebird[x] * burungLovebird[x]) +
                                    (ikanMasKoki[x] * ikanMasKoki[x]) +
                                    (hamster[x] * hamster[x]) +
                                    (landakMini[x] * landakMini[x])  +
                                    (sugarGliderJoey[x] * sugarGliderJoey[x]) +
                                    (kurakuraBrasil[x] * kurakuraBrasil[x]));

            r_ikanMasKoki[x] =
                    ikanMasKoki[x] / Math.sqrt(
                            (anjingMiniPom[x] * anjingMiniPom[x]) +
                                    (kucingNonRas[x] * kucingNonRas[x]) +
                                    (kucingRas[x] * kucingRas[x]) +
                                    (kelinciHias[x] * kelinciHias[x]) +
                                    (burungLovebird[x] * burungLovebird[x]) +
                                    (ikanMasKoki[x] * ikanMasKoki[x]) +
                                    (hamster[x] * hamster[x]) +
                                    (landakMini[x] * landakMini[x])  +
                                    (sugarGliderJoey[x] * sugarGliderJoey[x]) +
                                    (kurakuraBrasil[x] * kurakuraBrasil[x]));

            r_hamster[x] =
                    hamster[x] / Math.sqrt(
                            (anjingMiniPom[x] * anjingMiniPom[x]) +
                                    (kucingNonRas[x] * kucingNonRas[x]) +
                                    (kucingRas[x] * kucingRas[x]) +
                                    (kelinciHias[x] * kelinciHias[x]) +
                                    (burungLovebird[x] * burungLovebird[x]) +
                                    (ikanMasKoki[x] * ikanMasKoki[x]) +
                                    (hamster[x] * hamster[x]) +
                                    (landakMini[x] * landakMini[x])  +
                                    (sugarGliderJoey[x] * sugarGliderJoey[x]) +
                                    (kurakuraBrasil[x] * kurakuraBrasil[x]));

            r_landakMini[x] =
                    landakMini[x] / Math.sqrt(
                            (anjingMiniPom[x] * anjingMiniPom[x]) +
                                    (kucingNonRas[x] * kucingNonRas[x]) +
                                    (kucingRas[x] * kucingRas[x]) +
                                    (kelinciHias[x] * kelinciHias[x]) +
                                    (burungLovebird[x] * burungLovebird[x]) +
                                    (ikanMasKoki[x] * ikanMasKoki[x]) +
                                    (hamster[x] * hamster[x]) +
                                    (landakMini[x] * landakMini[x])  +
                                    (sugarGliderJoey[x] * sugarGliderJoey[x]) +
                                    (kurakuraBrasil[x] * kurakuraBrasil[x]));

            r_sugarGliderJoey[x] =
                    sugarGliderJoey[x] / Math.sqrt(
                            (anjingMiniPom[x] * anjingMiniPom[x]) +
                                    (kucingNonRas[x] * kucingNonRas[x]) +
                                    (kucingRas[x] * kucingRas[x]) +
                                    (kelinciHias[x] * kelinciHias[x]) +
                                    (burungLovebird[x] * burungLovebird[x]) +
                                    (ikanMasKoki[x] * ikanMasKoki[x]) +
                                    (hamster[x] * hamster[x]) +
                                    (landakMini[x] * landakMini[x])  +
                                    (sugarGliderJoey[x] * sugarGliderJoey[x]) +
                                    (kurakuraBrasil[x] * kurakuraBrasil[x]));

            r_kurakuraBrasil[x] =
                    kurakuraBrasil[x] / Math.sqrt(
                            (anjingMiniPom[x] * anjingMiniPom[x]) +
                                    (kucingNonRas[x] * kucingNonRas[x]) +
                                    (kucingRas[x] * kucingRas[x]) +
                                    (kelinciHias[x] * kelinciHias[x]) +
                                    (burungLovebird[x] * burungLovebird[x]) +
                                    (ikanMasKoki[x] * ikanMasKoki[x]) +
                                    (hamster[x] * hamster[x]) +
                                    (landakMini[x] * landakMini[x])  +
                                    (sugarGliderJoey[x] * sugarGliderJoey[x]) +
                                    (kurakuraBrasil[x] * kurakuraBrasil[x]));
        }

        Log.d("RA", "SkorNormalisasi Anjeng" + Arrays.toString(r_anjingMiniPom));
        Log.d("RA", "SkorNormalisasi Kucing non" + Arrays.toString(r_kucingNonRas));
        Log.d("RA", "SkorNormalisasi Kucing ras" + Arrays.toString(r_kucingRas));
        Log.d("RA", "SkorNormalisasi klinci" + Arrays.toString(r_kelinciHias));
        Log.d("RA", "SkorNormalisasi burong" + Arrays.toString(r_burungLovebird));
        Log.d("RA", "SkorNormalisasi ikan" + Arrays.toString(r_ikanMasKoki));
        Log.d("RA", "SkorNormalisasi hams" + Arrays.toString(r_hamster));
        Log.d("RA", "SkorNormalisasi landak" + Arrays.toString(r_landakMini));
        Log.d("RA", "SkorNormalisasi sugar" + Arrays.toString(r_sugarGliderJoey));
        Log.d("RA", "SkorNormalisasi kura2" + Arrays.toString(r_kurakuraBrasil));
        Log.d("RA", "SkorNormalisasi user" + Arrays.toString(r_prefUser));

        // Tahap 2 - Menentukan Skor Ternormalisasi Terbobot

        double r_bobot_anjingMiniPom[] = new double[5];
        double r_bobot_kucingNonRas[] = new double[5];
        double r_bobot_kucingRas[] = new double[5];
        double r_bobot_kelinciHias[] = new double[5];
        double r_bobot_burungLovebird[] = new double[5];
        double r_bobot_ikanMasKoki[] = new double[5];
        double r_bobot_hamster[] = new double[5];
        double r_bobot_landakMini[] = new double[5];
        double r_bobot_sugarGliderJoey[] = new double[5];
        double r_bobot_kurakuraBrasil[] = new double[5];
        double r_bobot_prefUser[] = new double[5];

        for (int x = 0; x < 5; x++) {
            r_bobot_anjingMiniPom[x] = r_anjingMiniPom[x] * bobot[x];
            r_bobot_kucingNonRas[x] = r_kucingNonRas[x] * bobot[x];
            r_bobot_kucingRas[x] = r_kucingRas[x] * bobot[x];
            r_bobot_kelinciHias[x] = r_kelinciHias[x] * bobot[x];
            r_bobot_burungLovebird[x] = r_burungLovebird[x] * bobot[x];
            r_bobot_ikanMasKoki[x] = r_ikanMasKoki[x] * bobot[x];
            r_bobot_hamster[x] = r_hamster[x] * bobot[x];
            r_bobot_landakMini[x] = r_landakMini[x] * bobot[x];
            r_bobot_sugarGliderJoey[x] = r_sugarGliderJoey[x] * bobot[x];
            r_bobot_kurakuraBrasil[x] = r_kurakuraBrasil[x] * bobot[x];
            r_bobot_prefUser[x] = r_prefUser[x] * bobot[x];
        }

        Log.d("RA", "SkorNormalisasiTerbobot Anjeng" + Arrays.toString(r_bobot_anjingMiniPom));
        Log.d("RA", "SkorNormalisasiTerbobot Kucing non" + Arrays.toString(r_bobot_kucingNonRas));
        Log.d("RA", "SkorNormalisasiTerbobot Kucing ras" + Arrays.toString(r_bobot_kucingRas));
        Log.d("RA", "SkorNormalisasiTerbobot klinci" + Arrays.toString(r_bobot_kelinciHias));
        Log.d("RA", "SkorNormalisasiTerbobot burong" + Arrays.toString(r_bobot_burungLovebird));
        Log.d("RA", "SkorNormalisasiTerbobot ikan" + Arrays.toString(r_bobot_ikanMasKoki));
        Log.d("RA", "SkorNormalisasiTerbobot hams" + Arrays.toString(r_bobot_hamster));
        Log.d("RA", "SkorNormalisasiTerbobot landak" + Arrays.toString(r_bobot_landakMini));
        Log.d("RA", "SkorNormalisasiTerbobot sugar" + Arrays.toString(r_bobot_sugarGliderJoey));
        Log.d("RA", "SkorNormalisasiTerbobot kura2" + Arrays.toString(r_bobot_kurakuraBrasil));
        Log.d("RA", "SkorNormalisasiTerbobot user" + Arrays.toString(r_bobot_prefUser));


        double a_positif[] = new double[5];
        double a_negatif[] = {1, 1, 1, 1, 1};

        for (int x = 0; x < 5; x++) {
            if (a_positif[x] < r_bobot_anjingMiniPom[x])
                a_positif[x] = r_bobot_anjingMiniPom[x];

            if (a_positif[x] < r_bobot_kucingNonRas[x])
                a_positif[x] = r_bobot_kucingNonRas[x];

            if (a_positif[x] < r_bobot_kucingRas[x])
                a_positif[x] = r_bobot_kucingRas[x];

            if (a_positif[x] < r_bobot_kelinciHias[x])
                a_positif[x] = r_bobot_kelinciHias[x];

            if (a_positif[x] < r_bobot_burungLovebird[x])
                a_positif[x] = r_bobot_burungLovebird[x];

            if (a_positif[x] < r_bobot_ikanMasKoki[x])
                a_positif[x] = r_bobot_ikanMasKoki[x];

            if (a_positif[x] < r_bobot_hamster[x])
                a_positif[x] = r_bobot_hamster[x];

            if (a_positif[x] < r_bobot_landakMini[x])
                a_positif[x] = r_bobot_landakMini[x];

            if (a_positif[x] < r_bobot_sugarGliderJoey[x])
                a_positif[x] = r_bobot_sugarGliderJoey[x];

            if (a_positif[x] < r_bobot_kurakuraBrasil[x])
                a_positif[x] = r_bobot_kurakuraBrasil[x];

            // Cari yang negatif

            if (a_negatif[x] > r_bobot_anjingMiniPom[x])
                a_negatif[x] = r_bobot_anjingMiniPom[x];

            if (a_negatif[x] > r_bobot_kucingNonRas[x])
                a_negatif[x] = r_bobot_kucingNonRas[x];

            if (a_negatif[x] > r_bobot_kucingRas[x])
                a_negatif[x] = r_bobot_kucingRas[x];

            if (a_negatif[x] > r_bobot_kelinciHias[x])
                a_negatif[x] = r_bobot_kelinciHias[x];

            if (a_negatif[x] > r_bobot_burungLovebird[x])
                a_negatif[x] = r_bobot_burungLovebird[x];

            if (a_negatif[x] > r_bobot_ikanMasKoki[x])
                a_negatif[x] = r_bobot_ikanMasKoki[x];

            if (a_negatif[x] > r_bobot_hamster[x])
                a_negatif[x] = r_bobot_hamster[x];

            if (a_negatif[x] > r_bobot_landakMini[x])
                a_negatif[x] = r_bobot_landakMini[x];

            if (a_negatif[x] > r_bobot_sugarGliderJoey[x])
                a_negatif[x] = r_bobot_sugarGliderJoey[x];

            if (a_negatif[x] > r_bobot_kurakuraBrasil[x])
                a_negatif[x] = r_bobot_kurakuraBrasil[x];
        }

        Log.d("RA", "a pos = " + Arrays.toString(a_positif));
        Log.d("RA", "a neg = " + Arrays.toString(a_negatif));

        double d1_positif = Math.sqrt(
                ((a_positif[0] - r_bobot_anjingMiniPom[0]) * (a_positif[0] - r_bobot_anjingMiniPom[0]))
                        + ((a_positif[1] - r_bobot_anjingMiniPom[1]) * (a_positif[1] - r_bobot_anjingMiniPom[1]))
                        + ((a_positif[2] - r_bobot_anjingMiniPom[2]) * (a_positif[2] - r_bobot_anjingMiniPom[2]))
                        + ((a_positif[3] - r_bobot_anjingMiniPom[3]) * (a_positif[3] - r_bobot_anjingMiniPom[3]))
                        + ((a_positif[4] - r_bobot_anjingMiniPom[4]) * (a_positif[4] - r_bobot_anjingMiniPom[4])));

        double d2_positif = Math.sqrt(
                ((a_positif[0] - r_bobot_kucingNonRas[0]) * (a_positif[0] - r_bobot_kucingNonRas[0]))
                        + ((a_positif[1] - r_bobot_kucingNonRas[1]) * (a_positif[1] - r_bobot_kucingNonRas[1]))
                        + ((a_positif[2] - r_bobot_kucingNonRas[2]) * (a_positif[2] - r_bobot_kucingNonRas[2]))
                        + ((a_positif[3] - r_bobot_kucingNonRas[3]) * (a_positif[3] - r_bobot_kucingNonRas[3]))
                        + ((a_positif[4] - r_bobot_kucingNonRas[4]) * (a_positif[4] - r_bobot_kucingNonRas[4])));

        double d3_positif = Math.sqrt(
                ((a_positif[0] - r_bobot_kucingRas[0]) * (a_positif[0] - r_bobot_kucingRas[0]))
                        + ((a_positif[1] - r_bobot_kucingRas[1]) * (a_positif[1] - r_bobot_kucingRas[1]))
                        + ((a_positif[2] - r_bobot_kucingRas[2]) * (a_positif[2] - r_bobot_kucingRas[2]))
                        + ((a_positif[3] - r_bobot_kucingRas[3]) * (a_positif[3] - r_bobot_kucingRas[3]))
                        + ((a_positif[4] - r_bobot_kucingRas[4]) * (a_positif[4] - r_bobot_kucingRas[4])));

        double d4_positif = Math.sqrt(
                ((a_positif[0] - r_bobot_kelinciHias[0]) * (a_positif[0] - r_bobot_kelinciHias[0]))
                        + ((a_positif[1] - r_bobot_kelinciHias[1]) * (a_positif[1] - r_bobot_kelinciHias[1]))
                        + ((a_positif[2] - r_bobot_kelinciHias[2]) * (a_positif[2] - r_bobot_kelinciHias[2]))
                        + ((a_positif[3] - r_bobot_kelinciHias[3]) * (a_positif[3] - r_bobot_kelinciHias[3]))
                        + ((a_positif[4] - r_bobot_kelinciHias[4]) * (a_positif[4] - r_bobot_kelinciHias[4])));

        double d5_positif = Math.sqrt(
                ((a_positif[0] - r_bobot_burungLovebird[0]) * (a_positif[0] - r_bobot_burungLovebird[0]))
                        + ((a_positif[1] - r_bobot_burungLovebird[1]) * (a_positif[1] - r_bobot_burungLovebird[1]))
                        + ((a_positif[2] - r_bobot_burungLovebird[2]) * (a_positif[2] - r_bobot_burungLovebird[2]))
                        + ((a_positif[3] - r_bobot_burungLovebird[3]) * (a_positif[3] - r_bobot_burungLovebird[3]))
                        + ((a_positif[4] - r_bobot_burungLovebird[4]) * (a_positif[4] - r_bobot_burungLovebird[4])));

        double d6_positif = Math.sqrt(
                ((a_positif[0] - r_bobot_ikanMasKoki[0]) * (a_positif[0] - r_bobot_ikanMasKoki[0]))
                        + ((a_positif[1] - r_bobot_ikanMasKoki[1]) * (a_positif[1] - r_bobot_ikanMasKoki[1]))
                        + ((a_positif[2] - r_bobot_ikanMasKoki[2]) * (a_positif[2] - r_bobot_ikanMasKoki[2]))
                        + ((a_positif[3] - r_bobot_ikanMasKoki[3]) * (a_positif[3] - r_bobot_ikanMasKoki[3]))
                        + ((a_positif[4] - r_bobot_ikanMasKoki[4]) * (a_positif[4] - r_bobot_ikanMasKoki[4])));

        double d7_positif = Math.sqrt(
                ((a_positif[0] - r_bobot_hamster[0]) * (a_positif[0] - r_bobot_hamster[0]))
                        + ((a_positif[1] - r_bobot_hamster[1]) * (a_positif[1] - r_bobot_hamster[1]))
                        + ((a_positif[2] - r_bobot_hamster[2]) * (a_positif[2] - r_bobot_hamster[2]))
                        + ((a_positif[3] - r_bobot_hamster[3]) * (a_positif[3] - r_bobot_hamster[3]))
                        + ((a_positif[4] - r_bobot_hamster[4]) * (a_positif[4] - r_bobot_hamster[4])));

        double d8_positif = Math.sqrt(
                ((a_positif[0] - r_bobot_landakMini[0]) * (a_positif[0] - r_bobot_landakMini[0]))
                        + ((a_positif[1] - r_bobot_landakMini[1]) * (a_positif[1] - r_bobot_landakMini[1]))
                        + ((a_positif[2] - r_bobot_landakMini[2]) * (a_positif[2] - r_bobot_landakMini[2]))
                        + ((a_positif[3] - r_bobot_landakMini[3]) * (a_positif[3] - r_bobot_landakMini[3]))
                        + ((a_positif[4] - r_bobot_landakMini[4]) * (a_positif[4] - r_bobot_landakMini[4])));

        double d9_positif = Math.sqrt(
                ((a_positif[0] - r_bobot_sugarGliderJoey[0]) * (a_positif[0] - r_bobot_sugarGliderJoey[0]))
                        + ((a_positif[1] - r_bobot_sugarGliderJoey[1]) * (a_positif[1] - r_bobot_sugarGliderJoey[1]))
                        + ((a_positif[2] - r_bobot_sugarGliderJoey[2]) * (a_positif[2] - r_bobot_sugarGliderJoey[2]))
                        + ((a_positif[3] - r_bobot_sugarGliderJoey[3]) * (a_positif[3] - r_bobot_sugarGliderJoey[3]))
                        + ((a_positif[4] - r_bobot_sugarGliderJoey[4]) * (a_positif[4] - r_bobot_sugarGliderJoey[4])));

        double d10_positif = Math.sqrt(
                ((a_positif[0] - r_bobot_kurakuraBrasil[0]) * (a_positif[0] - r_bobot_kurakuraBrasil[0]))
                        + ((a_positif[1] - r_bobot_kurakuraBrasil[1]) * (a_positif[1] - r_bobot_kurakuraBrasil[1]))
                        + ((a_positif[2] - r_bobot_kurakuraBrasil[2]) * (a_positif[2] - r_bobot_kurakuraBrasil[2]))
                        + ((a_positif[3] - r_bobot_kurakuraBrasil[3]) * (a_positif[3] - r_bobot_kurakuraBrasil[3]))
                        + ((a_positif[4] - r_bobot_kurakuraBrasil[4]) * (a_positif[4] - r_bobot_kurakuraBrasil[4])));

        double dprefuser_positif = Math.sqrt(
                ((a_positif[0] - r_bobot_prefUser[0]) * (a_positif[0] - r_bobot_prefUser[0]))
                        + ((a_positif[1] - r_bobot_prefUser[1]) * (a_positif[1] - r_bobot_prefUser[1]))
                        + ((a_positif[2] - r_bobot_prefUser[2]) * (a_positif[2] - r_bobot_prefUser[2]))
                        + ((a_positif[3] - r_bobot_prefUser[3]) * (a_positif[3] - r_bobot_prefUser[3]))
                        + ((a_positif[4] - r_bobot_prefUser[4]) * (a_positif[4] - r_bobot_prefUser[4])));

        // solusi ideal negatif

        double d1_negatif = Math.sqrt(
                ((r_bobot_anjingMiniPom[0] - a_negatif[0]) * (r_bobot_anjingMiniPom[0] - a_negatif[0]))
                        + ((r_bobot_anjingMiniPom[1] - a_negatif[1]) * (r_bobot_anjingMiniPom[1] - a_negatif[1]))
                        + ((r_bobot_anjingMiniPom[2] - a_negatif[2]) * (r_bobot_anjingMiniPom[2] - a_negatif[2]))
                        + ((r_bobot_anjingMiniPom[3] - a_negatif[3]) * (r_bobot_anjingMiniPom[3] - a_negatif[3]))
                        + ((r_bobot_anjingMiniPom[4] - a_negatif[4]) * (r_bobot_anjingMiniPom[4] - a_negatif[4])));

        double d2_negatif = Math.sqrt(
                ((r_bobot_kucingNonRas[0] - a_negatif[0]) * (r_bobot_kucingNonRas[0] - a_negatif[0]))
                        + ((r_bobot_kucingNonRas[1] - a_negatif[1]) * (r_bobot_kucingNonRas[1] - a_negatif[1]))
                        + ((r_bobot_kucingNonRas[2] - a_negatif[2]) * (r_bobot_kucingNonRas[2] - a_negatif[2]))
                        + ((r_bobot_kucingNonRas[3] - a_negatif[3]) * (r_bobot_kucingNonRas[3] - a_negatif[3]))
                        + ((r_bobot_kucingNonRas[4] - a_negatif[4]) * (r_bobot_kucingNonRas[4] - a_negatif[4])));

        double d3_negatif = Math.sqrt(
                ((r_bobot_kucingRas[0] - a_negatif[0]) * (r_bobot_kucingRas[0] - a_negatif[0]))
                        + ((r_bobot_kucingRas[1] - a_negatif[1]) * (r_bobot_kucingRas[1] - a_negatif[1]))
                        + ((r_bobot_kucingRas[2] - a_negatif[2]) * (r_bobot_kucingRas[2] - a_negatif[2]))
                        + ((r_bobot_kucingRas[3] - a_negatif[3]) * (r_bobot_kucingRas[3] - a_negatif[3]))
                        + ((r_bobot_kucingRas[4] - a_negatif[4]) * (r_bobot_kucingRas[4] - a_negatif[4])));

        double d4_negatif = Math.sqrt(
                ((r_bobot_kelinciHias[0] - a_negatif[0]) * (r_bobot_kelinciHias[0] - a_negatif[0]))
                        + ((r_bobot_kelinciHias[1] - a_negatif[1]) * (r_bobot_kelinciHias[1] - a_negatif[1]))
                        + ((r_bobot_kelinciHias[2] - a_negatif[2]) * (r_bobot_kelinciHias[2] - a_negatif[2]))
                        + ((r_bobot_kelinciHias[3] - a_negatif[3]) * (r_bobot_kelinciHias[3] - a_negatif[3]))
                        + ((r_bobot_kelinciHias[4] - a_negatif[4]) * (r_bobot_kelinciHias[4] - a_negatif[4])));

        double d5_negatif = Math.sqrt(
                ((r_bobot_burungLovebird[0] - a_negatif[0]) * (r_bobot_burungLovebird[0] - a_negatif[0]))
                        + ((r_bobot_burungLovebird[1] - a_negatif[1]) * (r_bobot_burungLovebird[1] - a_negatif[1]))
                        + ((r_bobot_burungLovebird[2] - a_negatif[2]) * (r_bobot_burungLovebird[2] - a_negatif[2]))
                        + ((r_bobot_burungLovebird[3] - a_negatif[3]) * (r_bobot_burungLovebird[3] - a_negatif[3]))
                        + ((r_bobot_burungLovebird[4] - a_negatif[4]) * (r_bobot_burungLovebird[4] - a_negatif[4])));

        double d6_negatif = Math.sqrt(
                ((r_bobot_ikanMasKoki[0] - a_negatif[0]) * (r_bobot_ikanMasKoki[0] - a_negatif[0]))
                        + ((r_bobot_ikanMasKoki[1] - a_negatif[1]) * (r_bobot_ikanMasKoki[1] - a_negatif[1]))
                        + ((r_bobot_ikanMasKoki[2] - a_negatif[2]) * (r_bobot_ikanMasKoki[2] - a_negatif[2]))
                        + ((r_bobot_ikanMasKoki[3] - a_negatif[3]) * (r_bobot_ikanMasKoki[3] - a_negatif[3]))
                        + ((r_bobot_ikanMasKoki[4] - a_negatif[4]) * (r_bobot_ikanMasKoki[4] - a_negatif[4])));

        double d7_negatif = Math.sqrt(
                ((r_bobot_hamster[0] - a_negatif[0]) * (r_bobot_hamster[0] - a_negatif[0]))
                        + ((r_bobot_hamster[1] - a_negatif[1]) * (r_bobot_hamster[1] - a_negatif[1]))
                        + ((r_bobot_hamster[2] - a_negatif[2]) * (r_bobot_hamster[2] - a_negatif[2]))
                        + ((r_bobot_hamster[3] - a_negatif[3]) * (r_bobot_hamster[3] - a_negatif[3]))
                        + ((r_bobot_hamster[4] - a_negatif[4]) * (r_bobot_hamster[4] - a_negatif[4])));

        double d8_negatif = Math.sqrt(
                ((r_bobot_landakMini[0] - a_negatif[0]) * (r_bobot_landakMini[0] - a_negatif[0]))
                        + ((r_bobot_landakMini[1] - a_negatif[1]) * (r_bobot_landakMini[1] - a_negatif[1]))
                        + ((r_bobot_landakMini[2] - a_negatif[2]) * (r_bobot_landakMini[2] - a_negatif[2]))
                        + ((r_bobot_landakMini[3] - a_negatif[3]) * (r_bobot_landakMini[3] - a_negatif[3]))
                        + ((r_bobot_landakMini[4] - a_negatif[4]) * (r_bobot_landakMini[4] - a_negatif[4])));

        double d9_negatif = Math.sqrt(
                ((r_bobot_sugarGliderJoey[0] - a_negatif[0]) * (r_bobot_sugarGliderJoey[0] - a_negatif[0]))
                        + ((r_bobot_sugarGliderJoey[1] - a_negatif[1]) * (r_bobot_sugarGliderJoey[1] - a_negatif[1]))
                        + ((r_bobot_sugarGliderJoey[2] - a_negatif[2]) * (r_bobot_sugarGliderJoey[2] - a_negatif[2]))
                        + ((r_bobot_sugarGliderJoey[3] - a_negatif[3]) * (r_bobot_sugarGliderJoey[3] - a_negatif[3]))
                        + ((r_bobot_sugarGliderJoey[4] - a_negatif[4]) * (r_bobot_sugarGliderJoey[4] - a_negatif[4])));

        double d10_negatif = Math.sqrt(
                ((r_bobot_kurakuraBrasil[0] - a_negatif[0]) * (r_bobot_kurakuraBrasil[0] - a_negatif[0]))
                        + ((r_bobot_kurakuraBrasil[1] - a_negatif[1]) * (r_bobot_kurakuraBrasil[1] - a_negatif[1]))
                        + ((r_bobot_kurakuraBrasil[2] - a_negatif[2]) * (r_bobot_kurakuraBrasil[2] - a_negatif[2]))
                        + ((r_bobot_kurakuraBrasil[3] - a_negatif[3]) * (r_bobot_kurakuraBrasil[3] - a_negatif[3]))
                        + ((r_bobot_kurakuraBrasil[4] - a_negatif[4]) * (r_bobot_kurakuraBrasil[4] - a_negatif[4])));

        double dprefuser_negatif = Math.sqrt(
                ((r_bobot_prefUser[0] - a_negatif[0]) * (r_bobot_prefUser[0] - a_negatif[0]))
                        + ((r_bobot_prefUser[1] - a_negatif[1]) * (r_bobot_prefUser[1] - a_negatif[1]))
                        + ((r_bobot_prefUser[2] - a_negatif[2]) * (r_bobot_prefUser[2] - a_negatif[2]))
                        + ((r_bobot_prefUser[3] - a_negatif[3]) * (r_bobot_prefUser[3] - a_negatif[3]))
                        + ((r_bobot_prefUser[4] - a_negatif[4]) * (r_bobot_prefUser[4] - a_negatif[4])));

        Log.d("RA", "d1 pos Anjeng = " + d1_positif + "d1 neg = " + d1_negatif);
        Log.d("RA", "d2 Kucing non = " + d2_positif + "d2 neg = " + d2_negatif);
        Log.d("RA", "d3 Kucing ras = " + d3_positif + "d2 neg = " + d3_negatif);
        Log.d("RA", "d4 klinci = " + d4_positif + "d2 neg = " + d4_negatif);
        Log.d("RA", "d5 burong = " + d5_positif + "d2 neg = " + d5_negatif);
        Log.d("RA", "d6 ikan = " + d6_positif + "d2 neg = " + d6_negatif);
        Log.d("RA", "d7 hams = " + d7_positif + "d2 neg = " + d7_negatif);
        Log.d("RA", "d8 landak = " + d8_positif + "d2 neg = " + d8_negatif);
        Log.d("RA", "d9 sugar = " + d9_positif + "d2 neg = " + d9_negatif);
        Log.d("RA", "d10 kura2 = " + d10_positif + "d2 neg = " + d10_negatif);
        Log.d("RA", "duser user = " + dprefuser_positif + "d2 neg = " + dprefuser_negatif);

        double skorAkhirAnjingMiniPom = d1_negatif / (d1_negatif + d1_positif);
        double skorAkhirKucingNonRas = d2_negatif / (d2_negatif + d2_positif);
        double skorAkhirKucingRas = d3_negatif / (d3_negatif + d3_positif);
        double skorAkhirKelinciHias = d4_negatif / (d4_negatif + d4_positif);
        double skorAkhirBurungLovebird = d5_negatif / (d5_negatif + d5_positif);
        double skorAkhirIkanMasKoki = d6_negatif / (d6_negatif + d6_positif);
        double skorAkhirHamster = d7_negatif / (d7_negatif + d7_positif);
        double skorAkhirLandakMini = d8_negatif / (d8_negatif + d8_positif);
        double skorAkhirSugarGliderJoey = d9_negatif / (d9_negatif + d9_positif);
        double skorAkhirKurakuraBrasil = d10_negatif / (d10_negatif + d10_positif);
        skorAkhirPrefUser = dprefuser_negatif / (dprefuser_negatif + dprefuser_positif);


        alt.add(new Alternatif("Anjing Mini Pom", skorAkhirAnjingMiniPom));
        alt.add(new Alternatif("Kucing Non Ras", skorAkhirKucingNonRas));
        alt.add(new Alternatif("Kucing Ras", skorAkhirKucingRas));
        alt.add(new Alternatif("Kelinci Hias", skorAkhirKelinciHias));
        alt.add(new Alternatif("Burung Lovebird", skorAkhirBurungLovebird));
        alt.add(new Alternatif("Ikan Mas Koki", skorAkhirIkanMasKoki));
        alt.add(new Alternatif("Hamster", skorAkhirHamster));
        alt.add(new Alternatif("Landak Mini", skorAkhirLandakMini));
        alt.add(new Alternatif("Sugar Glider Joey", skorAkhirSugarGliderJoey));
        alt.add(new Alternatif("Kura Kura Brasil", skorAkhirKurakuraBrasil));

        Log.d("RA", "SkorAkhir Anjeng = " + skorAkhirAnjingMiniPom);
        Log.d("RA", "SkorAkhir Kucing non = " + skorAkhirKucingNonRas);
        Log.d("RA", "SkorAkhir Kucing ras = " + skorAkhirKucingRas);
        Log.d("RA", "SkorAkhir klinci = " + skorAkhirKelinciHias);
        Log.d("RA", "SkorAkhir burong = " + skorAkhirBurungLovebird);
        Log.d("RA", "SkorAkhir ikan = " + skorAkhirIkanMasKoki);
        Log.d("RA", "SkorAkhir hams = " + skorAkhirHamster);
        Log.d("RA", "SkorAkhir landak = " + skorAkhirLandakMini);
        Log.d("RA", "SkorAkhir sugar = " + skorAkhirSugarGliderJoey);
        Log.d("RA", "SkorAkhir kura2 = " + skorAkhirKurakuraBrasil);
        Log.d("RA", "SkorAkhir user = " + skorAkhirPrefUser);

        try {
            Collections.sort(alt, new
                    Comparator<Alternatif>() {
                        @Override
                        public int compare(Alternatif o1, Alternatif o2) {
                            return
                                    Double.compare(o1.getSkorAlternatif(),
                                            o2.getSkorAlternatif());
                        }
                    });
            for (int i = 0; i<alt.size(); i++) {
                if(alt.get(i).getSkorAlternatif() <= skorAkhirPrefUser)
                    list.add(new Alternatif(alt.get(i).getNamaAlternatif(), Double.parseDouble(new DecimalFormat("##.##").format(alt.get(i).getSkorAlternatif()))));
            }
        }
        catch (Exception e) {
            System.out.println("Thrown exception: " + e.getMessage());
        }
    }
}
