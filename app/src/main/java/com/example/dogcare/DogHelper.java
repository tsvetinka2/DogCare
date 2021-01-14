package com.example.dogcare;

public class DogHelper {

    String petName, petBirthday, petPrimaryBreed, petWeight;

    public DogHelper() {
    }

    public DogHelper(String petName, String petBirthday, String petPrimaryBreed, String petWeight) {
        this.petName = petName;
        this.petBirthday = petBirthday;
        this.petPrimaryBreed = petPrimaryBreed;
        this.petWeight = petWeight;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetBirthday() {
        return petBirthday;
    }

    public void setPetBirthday(String petBirthday) {
        this.petBirthday = petBirthday;
    }

    public String getPetPrimaryBreed() {
        return petPrimaryBreed;
    }

    public void setPetPrimaryBreed(String petPrimaryBreed) {
        this.petPrimaryBreed = petPrimaryBreed;
    }

    public String getPetWeight() {
        return petWeight;
    }

    public void setPetWeight(String petWeight) {
        this.petWeight = petWeight;
    }

    @Override
    public String toString() {

        return String.format("Dog Name: %s \t  Dog Primary Breed: %s \n", petName,petPrimaryBreed);
    }

}
