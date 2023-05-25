package com.example.bftopsis;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.view.Window.FEATURE_NO_TITLE;
import static java.lang.Integer.parseInt;
import static com.example.bftopsis.InputNodesActivity.mapActivity;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    /**
     * Debugging. TAG for your Log calls
     */
    private static final String TAG = "MapsActivity";

    private static Context context; //yeah I know

    /**
     * Path, nodes and edges
     * These will contain everything needed for
     * path calculation
     */
    private static List nodeList;
    private static Edge[] edges;
    private ArrayList<Integer> path;

    /**
     * Google Maps stuff
     */
    private static GoogleMap mMap;
    private static final LatLng startLatLng = new LatLng(3.5614083, 98.654454); // could be anything, this will be the reference
                                                                                       // for everything. Doesn't matter if using blank
                                                                                       // map
        /**
         * MAP OVERLAY
         */
        //private static GroundOverlay mapOverlay;
        //private static GroundOverlayOptions groundOverlayOptions;


        private static final int overlayImageWidth = 558; //on newer devices both width and height should be powers of 2
        private static final int overlayImageHeight = 556;//set these for the size of the overlay image in pixels.

        //private static LatLngBounds bounds;

    /**
     * APP behaviour.
     *
     * You need this for device rotation, because
     * when you do that it have to draw the path
     * again.
     */
    private static Boolean savedInstance = false;

    /**
     * This is where you save data you want to keep
     * in case of reload
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntegerArrayList("pathNow", path);
    }

    /**
     * Start the activity
     * @param savedInstanceState
     */
    @SuppressLint({"" +
            "lity", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * get rid of the bar
         */
        requestWindowFeature(FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_maps);

        MapsActivity.context = getApplicationContext();

        /**
         * if this is not the first load (device rotated for example) we
         * load the saved instance state. For example we save the
         * calculated path so no need calculating it again.
         */
        if (savedInstanceState != null) {
            path = savedInstanceState.getIntegerArrayList("pathNow");
            savedInstance = true;
        }


        /**
         * We load the nodes and edges, they are
         * csv files in the raw folder
         *
         * nodes.csv
         *
         * Example: 1,1412,1673,Node Name,1,1
         *
         * (ID,posX,posY,Node Name,level,importance)
         *
         */
        InputStream inputStreamNodes = getResources().openRawResource(R.raw.nodes);
        new CSVParse(inputStreamNodes);
        nodeList = CSVParse.read();

        /**
         * edges.csv
         *
         * Example: 1,2,121,3,Edge name
         *
         * (id,from node,to node,length,Edge name)
         *
         */
        InputStream inputStreamEdges = getResources().openRawResource(R.raw.edges);
        new CSVParse(inputStreamEdges);
        edges = CSVParse.readEdge();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);


        /**
         * Back button
         */
        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                    backToMenu();
                }
                return false;
            }

        });
    }


    /**
     * Memory check
     */
    private ActivityManager.MemoryInfo getAvailableMemory(){
        ActivityManager activityManager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo;
    }


/**
 * Custom methods
 */
    /**
     * Back to main activity
     */
    public void backToMenu() {
        Intent intent = new Intent(this, InputNodesActivity.class);
        this.finish();
        startActivity(intent);
    }



    /**
     * draw overlay, points (nodes) and lines (edges)
     */
    private void drawOnLevel() {

        int nodeID;

        /**
         * Set up lines and points
         */
        PolylineOptions pathLineOptions = new PolylineOptions();
        CircleOptions pathCircleOptions = new CircleOptions();

        //new latLngMapper(overlayImageWidth, overlayImageHeight, bounds);

        for (int pathNumber = 0; pathNumber < path.size(); pathNumber++) {

            nodeID = path.get(pathNumber);
            String[] nodeNow = (String[]) nodeList.get(nodeID-1);


            int col = getResources().getColor(R.color.blue);

            /**
             *  all the nodes have a name that you can show by touching them
             */
            String tag = nodeNow[3];

            /**
             * The starting point is red. The path is backwards in the list
             * so we color tha last one
             */
            if (pathNumber == path.size() - 1) {
                col = Color.RED;
                tag = getText(R.string.startPoint) + tag;
            }
            if (pathNumber == 0) {
                col = Color.GREEN;
                tag = "End Point - " + tag;
            }

                /*pathCircleOptions.center(latLngMapper.calculateLatLng(parseInt(nodeNow[1]), parseInt(nodeNow[2])))
                        .radius(15).strokeWidth(0)
                        .fillColor(col)
                        .zIndex(1000)
                        .clickable(true);*/
            pathCircleOptions.center(new LatLng(Double.valueOf(nodeNow[1]),Double.valueOf(nodeNow[2])))
                    .radius(15).strokeWidth(0)
                    .fillColor(col)
                    .zIndex(1000)
                    .clickable(true);
            Circle circle = mMap.addCircle(pathCircleOptions);
            circle.setTag(tag);

            mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {

                @Override
                public void onCircleClick(Circle circle) {

                    DisplayMetrics dM = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(dM);

                    Toast placeToast = Toast.makeText(getApplicationContext(), (String) circle.getTag(), Toast.LENGTH_LONG);

                    placeToast.setGravity(Gravity.CENTER_HORIZONTAL, 0, -(dM.heightPixels / 2 / 2)); //so it is a little higher than middle of the screen
                    placeToast.show();

                }
            });

                /*pathLineOptions.add(
                        latLngMapper.calculateLatLng(parseInt(nodeNow[1]), parseInt(nodeNow[2]))
                )
                        .color(getResources().getColor(R.color.blue_dark))
                        .zIndex(500);//you need z-index so it isn't behind the overlay*/
            pathLineOptions.add(
                    new LatLng(Double.valueOf(nodeNow[1]),Double.valueOf(nodeNow[2]))
            )
                    .color(getResources().getColor(R.color.blue_dark))
                    .zIndex(500);//you need z-index so it isn't behind the overlay



        }

        mMap.addPolyline(pathLineOptions);

    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     *
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        /**
         * Map ini
         */
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setTiltGesturesEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.setMinZoomPreference(5);
        mMap.setMaxZoomPreference(16);


        /*groundOverlayOptions = new GroundOverlayOptions()
                .position(startLatLng, overlayImageWidth, overlayImageHeight);*/


        /**
         * we get the data from main activity
         */
        Intent intent = getIntent();
        String message = intent.getStringExtra(mapActivity);

        String[] mapData = message.split(";");

        /**
         * If we have no saved instance this is the first load
         * so we have to calculate everything.
         */

        if (!savedInstance) {

            Graph g = new Graph(edges);

            try {
                g.calculateShortestDistances(parseInt(mapData[0]), parseInt(mapData[1]));
                g.calculatePath();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), getString(R.string.error_path), Toast.LENGTH_SHORT).show();
                Log.v("BF, gagal karena: ", e.toString());
                backToMenu();
            }

            path = g.getPath();

            int pathStartNodeID = path.get(path.size() - 1) - 1;
            String[] pathStartNode = (String[]) nodeList.get(pathStartNodeID);

            /*int pathEndNodeID = path.get(0);
            String[] pathEndNode = (String[]) nodeList.get(pathEndNodeID-1);*/

            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.valueOf(pathStartNode[1]),Double.valueOf(pathStartNode[2]))));
        }

        /**
         * Map ini continued. We needed data for these
         */
        ActivityManager.MemoryInfo memoryInfo = getAvailableMemory();
        if(!memoryInfo.lowMemory) {

            //bounds = mapOverlay.getBounds();
            //mMap.setLatLngBoundsForCameraTarget(bounds); //this makes it so you can't drag the map far from the overlay

            drawOnLevel();
        } else {
            Toast.makeText(getApplicationContext(), getText(R.string.error_memory),Toast.LENGTH_LONG);
        }

    }//end of onMapReady
}