package Repositories.FromDB;

import Objects.Station;

import java.util.ArrayList;
import java.util.Collections;

public class OWMStationsRepository {

    private ArrayList<Station> stations;
    private ArrayList<String> stationsNamesWithCoordinates = new ArrayList<>();
    public OWMStationsRepository(ArrayList<Station> stations) {
        this.stations = stations;
        for (int k = 0; k < stations.size(); k++) {
            this.stationsNamesWithCoordinates.add(stations.get(k).getName() + "\n" + stations.get(k).getLon() + "\n" + stations.get(k).getLat());
        }
        Collections.sort(this.stationsNamesWithCoordinates);
    }
    public ArrayList<String> getStationNames() { //funkcja zwraca iste dostepnych miast z pomiarami
        Collections.sort(stationsNamesWithCoordinates);
        return stationsNamesWithCoordinates;
    }

    public ArrayList<Station> getStations() {
        return stations;
    }
}