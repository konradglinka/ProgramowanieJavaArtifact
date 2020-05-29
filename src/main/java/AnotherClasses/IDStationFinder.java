package AnotherClasses;

import Objects.Station;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class IDStationFinder {
    public int getIDSelectedStation(ListView<String> StationsListView, ArrayList<Station>stations){
        String[]pom=StationsListView.getSelectionModel().getSelectedItem().split("\n");
        String name=pom[0];
        Double lon=Double.parseDouble(pom[1]);
        Double lat=Double.parseDouble(pom[2]);
        for(int i=0;i<stations.size();i++){
            if(stations.get(i).getName().equals(name)){
                if(stations.get(i).getLon()==lon){
                    if(stations.get(i).getLat()==lat){
                        return stations.get(i).getId();
                    }
                }
            }
        }
        return 0;
    }
}
