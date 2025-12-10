package com.example.tp6_list_android;


import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private HashMap<String, Integer> cityIcons;
    private View cityCard;
    private View infoCard;
    private android.widget.TextView cityNameText;
    private android.widget.TextView coordinatesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Initialiser les vues
        cityCard = findViewById(R.id.cityCard);
        infoCard = findViewById(R.id.infoCard);
        cityNameText = findViewById(R.id.cityNameText);
        coordinatesText = findViewById(R.id.coordinatesText);

        // Initialiser la carte
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Initialiser les icônes
        cityIcons = new HashMap<>();
        cityIcons.put("Casablanca", R.drawable.icon_casa);
        cityIcons.put("Rabat", R.drawable.icon_rabat);
        cityIcons.put("Fes", R.drawable.icon_fes);
        cityIcons.put("Tanger", R.drawable.icon_tanger);

        // Boutons villes
        findViewById(R.id.btnCasa).setOnClickListener(v -> searchCity("Casablanca"));
        findViewById(R.id.btnRabat).setOnClickListener(v -> searchCity("Rabat"));
        findViewById(R.id.btnMarrakech).setOnClickListener(v -> searchCity("Fes"));
        findViewById(R.id.btnTanger).setOnClickListener(v -> searchCity("Tanger"));

        // Bouton retour
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        // Bouton fermer info
        findViewById(R.id.btnCloseInfo).setOnClickListener(v -> showSelectionCard());

        // Bouton retour à la sélection
        findViewById(R.id.btnBackToSelection).setOnClickListener(v -> showSelectionCard());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    // Recherche dynamique avec marker personnalisé
    private void searchCity(String cityName) {
        if (mMap == null) return;

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(cityName, 1);
            if (addresses == null || addresses.isEmpty()) {
                return;
            }

            Address address = addresses.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

            mMap.clear(); // Supprimer anciens markers

            // Ajouter marker avec icône spécifique à la ville
            int iconRes = cityIcons.containsKey(cityName) ? cityIcons.get(cityName) : 0;
            if (iconRes != 0) {
                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(cityName)
                        .icon(BitmapDescriptorFactory.fromResource(iconRes)));
            } else {
                mMap.addMarker(new MarkerOptions().position(latLng).title(cityName));
            }

            // Zoom et animation
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));

            // Afficher la carte d'information
            showInfoCard(cityName, latLng);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Afficher la carte d'information
    private void showInfoCard(String cityName, LatLng latLng) {
        cityNameText.setText(cityName);
        coordinatesText.setText(String.format(Locale.getDefault(), 
            "Coordonnées : %.4f, %.4f", latLng.latitude, latLng.longitude));

        // Masquer la carte de sélection et afficher l'info
        cityCard.setVisibility(View.GONE);
        infoCard.setVisibility(View.VISIBLE);
    }

    // Afficher la carte de sélection
    private void showSelectionCard() {
        cityCard.setVisibility(View.VISIBLE);
        infoCard.setVisibility(View.GONE);
        
        // Réinitialiser la carte
        if (mMap != null) {
            mMap.clear();
        }
    }
}
