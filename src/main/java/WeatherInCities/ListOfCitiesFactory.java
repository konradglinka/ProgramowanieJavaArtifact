package WeatherInCities;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class ListOfCitiesFactory {
    ClassLoader classLoader = getClass().getClassLoader();

   // private String filepath= "cities.txt";//Scieżka do pliku, mozliwe ze ma byc /cities.txt lub jakos innaczej
    String filepath =classLoader.getResource("cities.txt").getPath();
    private ArrayList<String>citiesArrayList = new ArrayList<>();

    public ListOfCitiesFactory() { //Konstruktor automatycznie wypelnia liste przy tworzeniu obiektu
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


        //wczytujemy plik linia po lini i za kazdym obrotem petli kolejna nazwa miasta laduje do listy
    }

    public ArrayList<String> getCitiesArrayList() { //funkcja zwraca iste dostepnych miast z pomiarami
        return citiesArrayList;
    }
}