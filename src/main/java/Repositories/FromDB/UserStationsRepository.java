package Repositories.FromDB;

import Objects.Station;

import java.util.ArrayList;
import java.util.Collections;

public class UserStationsRepository {

    private ArrayList<Station> stations;
    private ArrayList<String> stationNames = new ArrayList<>();
    public UserStationsRepository(ArrayList<Station> stations) {
        this.stations = stations;
        for (int k = 0; k < stations.size(); k++) {
            this.stationNames.add(stations.get(k).getName() + "\n" + stations.get(k).getLon() + "\n" + stations.get(k).getLat());
        }
        Collections.sort(this.stationNames);
    }
    public ArrayList<String> getStationNames() { //funkcja zwraca iste dostepnych miast z pomiarami
        Collections.sort(stationNames);
        return stationNames;
    }

    public ArrayList<Station> getStations() {
        return stations;
    }
}