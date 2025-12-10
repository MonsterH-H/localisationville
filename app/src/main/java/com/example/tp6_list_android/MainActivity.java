package com.example.tp6_list_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Tableau des noms des villes
    String[] ville = new String[] {
            "agadir", "casa","eljadida",
            "essaouira","fes","meknes",
            "merrakech","oujda","tanger"
    };
    //    Tableau des images des villes
    int[] img= new int[]{
            R.drawable.agadir,
            R.drawable.casa,
            R.drawable.eljadida,
            R.drawable.essaouira,
            R.drawable.fes,
            R.drawable.meknes,
            R.drawable.merrakech,
            R.drawable.oujda,
            R.drawable.tanger
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        // Configuration de la toolbar
        setSupportActionBar(findViewById(R.id.toolbar));


        // Each row in the list stores country name, currency and flag
        List<HashMap<String,String>> aList = new ArrayList<>();
        for(int i=0;i<9;i++){
            HashMap<String, String> hm = new HashMap<>();
            hm.put("txt", "Ville : " + ville[i]);
            //  hm.put("cur","Position : " + position[i]);
            hm.put("flag", Integer.toString(img[i]) );
            aList.add(hm);
        }
        // Keys used in Hashmap
        String[] from = { "flag","txt"};
        // Ids of views in listview_layout
        int[] to = { R.id.flag,R.id.txt};

        // Instantiating an adapter to store each items
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.layout, from, to);
        // Getting a reference to listview of main.xml layout file
        ListView listView = findViewById(R.id.l1);
        // Setting the adapter to the listView
        listView.setAdapter(adapter);


        listView.setOnItemClickListener((parent, view, position, id) -> {
            // On récupère le nom de la ville et l'ID de l'image cliquées grâce à la "position"
            String nomDeLaVille = ville[position];
            int idDeLImage = img[position];

            // On prépare l'Intent pour démarrer MainActivity2
            Intent echange = new Intent(getApplicationContext(), MainActivity2.class);

            // On attache les données à l'Intent
            // "cle_ville" et "cle_image" sont des clés pour retrouver les données dans l'autre activité
            echange.putExtra("cle_ville", "Ville : " + nomDeLaVille);
            echange.putExtra("cle_image", idDeLImage);

            // On démarre la nouvelle activité
            startActivity(echange);
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notremenu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.a2) {
            startActivity(new Intent(this, Contact.class));
            return true;
        } else if (id == R.id.a3) {
            startActivity(new Intent(this, About.class));
            return true;
        } else if (id == R.id.a4) {
            startActivity(new Intent(this, MapsActivity.class));
            return true;
        } else if (id == R.id.a5) {
            startActivity(new Intent(this, MapsActivity2.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
