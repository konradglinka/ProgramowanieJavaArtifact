package WeatherInCities;

import java.util.ArrayList;

public class ListOfCitiesFactory {

    private String filepath="cities.txt"; //Scieżka do pliku, mozliwe ze ma byc /cities.txt lub jakos innaczej

    private ArrayList<String>citiesArrayList = new ArrayList<>();

    public ListOfCitiesFactory() { //Konstruktor automatycznie wypelnia liste przy tworzeniu obiektu
        fillListOfCities();
    }

    private void fillListOfCities(){ // funkcja wczytuje z pliku podanego w filepath
        // wszystkie miasta i wypelnia liste znajdujaca sie w klasie

        citiesArrayList.add("Nazwa miasta");

        //wczytujemy plik linia po lini i za kazdym obrotem petli kolejna nazwa miasta laduje do listy
    }

    public ArrayList<String> getCitiesArrayList() { //funkcja zwraca iste dostepnych miast z pomiarami
        return citiesArrayList;
    }
}
