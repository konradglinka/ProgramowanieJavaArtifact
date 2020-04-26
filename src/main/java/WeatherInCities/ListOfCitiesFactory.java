package WeatherInCities;

import javafx.collections.FXCollections;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class ListOfCitiesFactory { //Klasa odpowiada za liste miast niezbędną w wielu aspektach
    //aplikacji takich jak pomiary z OWM, dodanie lub odbiór pomiaru przez użytkownika
    //klasa otrzymuje wcześniej przygotowny plik z dostępnymi miastami i za jej pomocą mamy dostęp do miast z pliku
    //String filepath="C:\\cities.txt";
    ClassLoader classLoader = getClass().getClassLoader();
    String filepath =classLoader.getResource("cities.txt").getPath();

    private ArrayList<OWMStation> stationsArrayList = new ArrayList<>();
    private ArrayList<String> stationsNamesArrayList = new ArrayList<>();
    public ListOfCitiesFactory() {
        fillListOfCities();
    }

    private void fillListOfCities()  { // funkcja wczytuje z pliku podanego w filepath
        // wszystkie miasta i wypelnia liste znajdujaca sie w klasie
        int id=0;
        String name=null;
        double lon=0;
        double lat=0;
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
            int i=0;
            while ((strCurrentLine = read.readLine()) != null) {
            i++;
               if(i%4==1) {
                   id = Integer.parseInt(strCurrentLine.trim());
               }
                if(i%4==2) {
                    name = strCurrentLine.trim();
                }
                if(i%4==3) {
                    lon = Double.parseDouble(strCurrentLine.trim());
                }
                if(i%4==0) {
                    lat = Double.parseDouble(strCurrentLine.trim());
                    stationsArrayList.add(new OWMStation(id,name,lon,lat));
                }
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
        for(int k=0; k<stationsArrayList.size();k++){
            stationsNamesArrayList.add(stationsArrayList.get(k).getName()+"\n"+stationsArrayList.get(k).getLon()+"\n"+stationsArrayList.get(k).getLat());
        }

        //wczytujemy plik linia po lini i za kazdym obrotem petli kolejna nazwa miasta ląduje do listy
    }

    public ArrayList<String> getStationsNamesArrayList() { //funkcja zwraca iste dostepnych miast z pomiarami
        Collections.sort(stationsNamesArrayList);
        return stationsNamesArrayList;
    }
    public int findIDForCity(String name,double lon, double lat){
        for(int i=0;i<stationsArrayList.size();i++){
            if(stationsArrayList.get(i).getName().equals(name)){
                if(stationsArrayList.get(i).getLon()==lon){
                    if(stationsArrayList.get(i).getLat()==lat){
                        return stationsArrayList.get(i).getId();
                    }
                }
            }
        }
        return 0;
    }
}