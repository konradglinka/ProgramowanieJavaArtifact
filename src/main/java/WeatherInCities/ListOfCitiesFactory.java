package WeatherInCities;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class ListOfCitiesFactory { //Klasa odpowiada za liste miast niezbędną w wielu aspektach
    //aplikacji takich jak pomiary z OWM, dodanie lub odbiór pomiaru przez użytkownika
    //klasa otrzymuje wcześniej przygotowny plik z dostępnymi miastami i za jej pomocą mamy dostęp do miast z pliku
    ClassLoader classLoader = getClass().getClassLoader();
    //String filepath="C:\\cities.txt";
   String filepath =classLoader.getResource("cities.txt").getPath();

    private ArrayList<String>citiesArrayList = new ArrayList<>();

    public ListOfCitiesFactory() {
        fillListOfCities();
        Collections.sort(citiesArrayList);
    }

    private void fillListOfCities()  { // funkcja wczytuje z pliku podanego w filepath
        // wszystkie miasta i wypelnia liste znajdujaca sie w klasie
        BufferedReader read= null;
        FileReader fread= null;
        try {
            fread = new FileReader(filepath);
        }catch (FileNotFoundException fn){
            fn.printStackTrace();
        }
        try {
            String strCurrentLine;
            read = new BufferedReader (fread);

            while ((strCurrentLine = read.readLine()) != null) {
                citiesArrayList.add(strCurrentLine);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(read!=null)
                    read.close();
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }


        //wczytujemy plik linia po lini i za kazdym obrotem petli kolejna nazwa miasta ląduje do listy
    }

    public ArrayList<String> getCitiesArrayList() { //funkcja zwraca iste dostepnych miast z pomiarami
        return citiesArrayList;
    }
}