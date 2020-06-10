package Repositories.FromDB;

import Objects.FromDB.Station;

import java.util.ArrayList;
import java.util.Collections;

public final class UserStationsRepository {

    private final ArrayList<Station> stations;
    private final  ArrayList<String> stationNames = new ArrayList<>();
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