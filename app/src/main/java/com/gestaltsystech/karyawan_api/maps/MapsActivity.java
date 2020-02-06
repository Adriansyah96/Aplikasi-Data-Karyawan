package com.gestaltsystech.karyawan_api.maps;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.gestaltsystech.karyawan_api.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker mCurrLocationMarker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        final  String Skey = getIntent().getStringExtra("key");
        mMap = googleMap;



            LatLng bandung = new LatLng(-6.902618, 107.618795);
            mMap.addMarker(new MarkerOptions().position(bandung).title("Bandung"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(bandung,18),5000,null);
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(bandung));


            // Add a marker in Sydney and move the camera
            LatLng jakarta = new LatLng(-6.175510, 106.827174);
            mMap.addMarker(new MarkerOptions().position(jakarta).title("Jakarta"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(jakarta,18),5000,null);
//         mMap.moveCamera(CameraUpdateFactory.newLatLng(jakarta));

//        }else if (Skey.equals(btn)){
            // Add a marker in Sydney and move the camera
            LatLng banten = new LatLng(-6.309847, 106.106635);
            mMap.addMarker(new MarkerOptions().position(banten).title("Banten"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(banten,18),5000,null);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(banten));

//        }




    }

}
