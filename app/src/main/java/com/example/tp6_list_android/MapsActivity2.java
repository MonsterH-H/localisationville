package com.example.tp6_list_android;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * Activité principale affichant Google Maps et permettant
 * la recherche d'une ville via un champ texte.
 */
public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText editTextCity;
    private ProgressBar progressBar;
    private Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);

        // Champ de saisie et ProgressBar
        editTextCity = findViewById(R.id.ville);
        progressBar = findViewById(R.id.progressBar);

        // Initialisation du Geocoder
        geocoder = new Geocoder(this);

        // Initialisation de la carte
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Bouton pour rechercher une ville
        findViewById(R.id.btn1).setOnClickListener(v -> searchCity());

        // Bouton retour vers MainActivity
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Position par défaut (Sydney)
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10));
    }

    /**
     * Recherche la ville saisie et place un marqueur sur la carte.
     */
    private void searchCity() {
        String city = editTextCity.getText().toString().trim();

        // Masquer le clavier
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(editTextCity.getWindowToken(), 0);
        }

        // Vérification de la saisie utilisateur
        if (city.isEmpty()) {
            Toast.makeText(this, "Veuillez entrer une ville", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Geocoder.isPresent()) {
            Toast.makeText(this, "Le service de géocodage n'est pas disponible", Toast.LENGTH_LONG).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        try {
            // On récupère 1 résultat maximum
            List<Address> results = geocoder.getFromLocationName(city, 1);

            if (results != null && !results.isEmpty()) {
                Address address = results.get(0);
                LatLng position = new LatLng(address.getLatitude(), address.getLongitude());

                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(position).title(city));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 12));

            } else {
                Toast.makeText(this, "Ville introuvable", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            Toast.makeText(this, "Erreur lors de la recherche: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            progressBar.setVisibility(View.GONE);
        }
    }
}
