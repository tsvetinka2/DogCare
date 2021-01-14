package com.example.dogcare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


public class AddDog extends AppCompatActivity {
    EditText addPetName, addPetBirthday, addPrimaryBreed, addPetWeight;
    Button addDogBtn;
    DatePickerDialog.OnDateSetListener setListener;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    private Boolean validateName() {
        String value = addPetName.getText().toString();
        if (value.isEmpty()) {
            addPetName.setError("Field can not be empty");
            return false;
        } else {
            addPetName.setError(null);
            return true;
        }
    }

    private Boolean validWeight() {
        String value = addPetWeight.getText().toString();
        String weightPattern = "^[0-9]*$";
        if (value.isEmpty()) {
            addPetWeight.setError("Field can not be empty");
            return false;
        } else if (!value.matches(weightPattern)) {
            addPetWeight.setError("You have to enter an integer number");
            return false;
        } else {
            addPetWeight.setError(null);
            return true;
        }
    }

    private Boolean validatePrimaryBreed() {
        String value = addPrimaryBreed.getText().toString();
        if (value.isEmpty()) {
            addPrimaryBreed.setError("Field can not be empty");
            return false;
        } else {
            addPrimaryBreed.setError(null);
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dog);

        //Hooks to all elements in activity_add_dog.xml

        addPetName = findViewById(R.id.petName);
        addPetBirthday = findViewById(R.id.petBirthday);
        addPrimaryBreed = findViewById(R.id.primaryBreed);
        addPetWeight = findViewById(R.id.petWeight);

        firebaseAuth = FirebaseAuth.getInstance();
        addDogBtn = findViewById(R.id.addDogBtn);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        addPetBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddDog.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                        setListener, year, month, day
                );

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                addPetBirthday.setText(date);
            }
        };

        //Save data in FireBase on button Click
        addDogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Dogs");

                if (!validateName() || !validWeight() || !validatePrimaryBreed()) {
                    return;
                }

                //Get all the values

                String petBirthday = addPetBirthday.getText().toString();
                String petName = addPetName.getText().toString();
                String primaryBreed = addPrimaryBreed.getText().toString();
                String petWeight = addPetWeight.getText().toString();

                DogHelper dogHelper = new DogHelper(petName, petBirthday, primaryBreed, petWeight);

                //reference.child(petName).setValue(dogHelper);
                reference.child(petName).setValue(dogHelper);
                Toast.makeText(AddDog.this, "A new dog is added!", Toast.LENGTH_SHORT).show();
                Intent addNewDogIntent = new Intent(AddDog.this, DogActivityNew.class);
                startActivity(addNewDogIntent);
            }
        });
    }

}