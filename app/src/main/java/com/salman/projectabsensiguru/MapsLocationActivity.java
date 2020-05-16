package com.salman.projectabsensiguru;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap gMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_location);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maplocation);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        double loc_latitude = getIntent().getDoubleExtra("loc_latitude",0);
        double loc_longitude = getIntent().getDoubleExtra("loc_longitude", 0);

        Toast.makeText(MapsLocationActivity.this,
                "Latitude : " + loc_latitude + " Longitude : " + loc_longitude,
                Toast.LENGTH_SHORT).show();

        gMap = googleMap;

        LatLng sydney = new LatLng(loc_latitude,loc_longitude);
        gMap.addMarker(new MarkerOptions().position(sydney).title("Lokasi Saat Ini"));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16));
    }
}
