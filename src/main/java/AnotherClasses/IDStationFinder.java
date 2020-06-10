package AnotherClasses;

import Objects.FromDB.Station;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class IDStationFinder { //Klasa wyszukuje ID zaznacoznej stacji
    public int getIDSelectedStation(ListView<String> StationsListView, ArrayList<Station>stations){
        String[]pom=StationsListView.getSelectionModel().getSelectedItem().split("\n"); //Zamieniamy zaznaczoną wartość na tablice ze stringami dzieląc po nowej lini
        String name=pom[0];
        Double lon=Double.parseDouble(pom[1]);
        Double lat=Double.parseDouble(pom[2]);
        for(int i=0;i<stations.size();i++){
            if(stations.get(i).getName().equals(name)&& stations.get(i).getLon()==lon && stations.get(i).getLat()==lat){
                        return stations.get(i).getId();
            }
        }
        return 0;
    }
}
