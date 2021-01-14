package com.example.dogcare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DogActivityNew extends AppCompatActivity {

    DatabaseReference reference;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    ImageView plus, threeDogs;
    TextView anyPets, noPets;
    Button  addNewDogBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_new);
        reference = FirebaseDatabase.getInstance().getReference("Dogs");
        listView = (ListView) findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
        plus = findViewById(R.id.plus);
        threeDogs = findViewById(R.id.threedogs);
        anyPets = findViewById(R.id.anyPets);
        noPets = findViewById(R.id.noPets);
        addNewDogBtn = findViewById(R.id.addNewDogBtn);

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                if (snapshot.exists()) {
                    String value = snapshot.getValue(DogHelper.class).toString();
                    arrayList.add(value);
                    arrayAdapter.notifyDataSetChanged();
                    plus.setVisibility(View.GONE);
                    threeDogs.setVisibility(View.GONE);
                    anyPets.setVisibility(View.GONE);
                    noPets.setVisibility(View.GONE);
                    addNewDogBtn.setVisibility(View.VISIBLE);
                    //detailsBtn.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.VISIBLE);
                } else {
                    listView.setVisibility(View.GONE);
                    plus.setVisibility(View.VISIBLE);
                    threeDogs.setVisibility(View.VISIBLE);
                    anyPets.setVisibility(View.VISIBLE);
                    noPets.setVisibility(View.VISIBLE);
                    addNewDogBtn.setVisibility(View.GONE);
                    //detailsBtn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addDogIntent = new Intent(DogActivityNew.this, AddDog.class);
                startActivity(addDogIntent);
            }
        });
        addNewDogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addNewDogIntent = new Intent(DogActivityNew.this, AddDog.class);
                startActivity(addNewDogIntent);
            }
        });
/*        detailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addNewDogIntent = new Intent(DogActivityNew.this, AddDog.class);
                startActivity(addNewDogIntent);
            }
        });*/
    }

}