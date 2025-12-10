package com.example.tp6_list_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ImageView imageView = findViewById(R.id.imageView2);
        TextView textView = findViewById(R.id.textView2);
        TextView textViewDescription = findViewById(R.id.textViewDescription);
        MaterialButton btnRetour = findViewById(R.id.button1);

        // Bouton retour
        btnRetour.setOnClickListener(v -> finish());

        // Récupérer les données envoyées par l'activité précédente
        Intent intent = getIntent();
        String nomDeLaVille = intent.getStringExtra("cle_ville");
        int idDeLImage = intent.getIntExtra("cle_image", 0);

        // Afficher nom et image
        textView.setText(nomDeLaVille);
        if (idDeLImage != 0) {
            imageView.setImageResource(idDeLImage);
        }

        // Ajouter une description personnalisée selon la ville
        String description;
        String villeNom = nomDeLaVille != null ? nomDeLaVille.replace("Ville : ", "").toLowerCase() : "";
        switch (villeNom) {
            case "casa":
                description = "Casablanca, la plus grande ville du Maroc, est connue pour sa mosquée Hassan II et son architecture moderne et historique.";
                break;
            case "agadir":
                description = "Agadir, station balnéaire moderne, est réputée pour ses plages de sable fin et son climat ensoleillé toute l'année.";
                break;
            case "eljadida":
                description = "El Jadida, ville côtière, abrite une cité portugaise classée au patrimoine mondial de l'UNESCO.";
                break;
            case "essaouira":
                description = "Essaouira, perle de l'Atlantique, séduit par ses remparts, son port de pêche et ses plages venteuses.";
                break;
            case "fes":
                description = "Fès, capitale spirituelle du Maroc, possède la plus ancienne université du monde et une médina fascinante.";
                break;
            case "meknes":
                description = "Meknès, ville impériale, est célèbre pour ses portes monumentales et ses vestiges historiques.";
                break;
            case "merrakech":
                description = "Marrakech, ville ocre, célèbre pour ses souks animés, le Jardin Majorelle et sa médina historique.";
                break;
            case "oujda":
                description = "Oujda, située à l'est du Maroc, est une ville frontalière dynamique avec un riche patrimoine culturel.";
                break;
            case "tanger":
                description = "Tanger, située au nord du Maroc, est célèbre pour son port international et son mélange culturel unique.";
                break;
            default:
                description = "Découvrez cette ville et ses nombreux trésors culturels et historiques.";
                break;
        }
        textViewDescription.setText(description);
    }
}
