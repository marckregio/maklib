package com.marckregio.maps;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.marckregio.providers.Files;
import com.marckregio.providers.Permissions;
import com.marckregio.ui.DialogView;
import com.marckregio.ui.FontAwesome;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        DialogInterface.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, LocationListener {

    private GoogleMap mMap;
    private Permissions permissions;
    private DialogView dialogView;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location location;
    private Button locationButton;
    private TextView save;

    public static String BASE_FOLDER = "MAPS/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FontAwesome.apply();
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        permissions = new Permissions(this);
        dialogView = new DialogView(this);
        locationButton = (Button) findViewById(R.id.location);
        locationButton.setOnClickListener(this);
        save = (TextView) findViewById(R.id.save);
        save.setOnClickListener(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        permissions.checkLocationPermission();
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setMyLocationEnabled(true);

        buildGoogleApiClient();


    }

    private synchronized void buildGoogleApiClient(){
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permissions[0] != android.Manifest.permission.ACCESS_FINE_LOCATION && grantResults[0] != PackageManager.PERMISSION_GRANTED){
            dialogView.showAlert("Location", "Please allow the app access your current location.", this).show();
        } else if (permissions[0] != Manifest.permission.WRITE_EXTERNAL_STORAGE &&
                grantResults[0] != PackageManager.PERMISSION_GRANTED){
        }
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        permissions.checkLocationPermission();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        permissions.checkLocationPermission();
        locationRequest = new LocationRequest();
        locationRequest.setInterval(8000);
        locationRequest.setFastestInterval(4000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        //use this for tracking;
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);

        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        goToMyLocation(false);
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void goToMyLocation(boolean animated){
        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(currentLocation).title("You are here")).showInfoWindow();
        if (animated) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 14.0f));
        } else {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 14.0f));
        }
    }

    @Override
    public void onClick(View view) {
        if (view == locationButton){
            if (location != null) {
                goToMyLocation(true);
            }
        } else if (view == save){
            dialogView.showAlert("Save Map", "This will save your current map view to your Gallery.",
                    new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mMap.snapshot(new GoogleMap.SnapshotReadyCallback() {
                        @Override
                        public void onSnapshotReady(Bitmap bitmap) {
                            Files.saveImage("map." + System.currentTimeMillis(),
                                    bitmap);
                        }
                    });

                }
            }).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.v("LOCATION", location.getProvider());
        this.location = location;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        googleApiClient.disconnect();
    }
}
