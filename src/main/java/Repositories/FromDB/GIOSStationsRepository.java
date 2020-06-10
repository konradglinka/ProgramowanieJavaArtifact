package Repositories.FromDB;

import Objects.FromDB.Station;

import java.util.ArrayList;
import java.util.Collections;


public class GIOSStationsRepository {
    private final ArrayList<Station> stations;
    private final ArrayList<String> stationsNamesWithCoordinates = new ArrayList<>();

    public GIOSStationsRepository(ArrayList<Station> stationsArrayList) {
        this.stations = stationsArrayList;
        for (int k = 0; k < stations.size(); k++) {
            this.stationsNamesWithCoordinates.add(stations.get(k).getName() + "\n" + stations.get(k).getLon() + "\n" + stations.get(k).getLat());
        }
        Collections.sort(this.stationsNamesWithCoordinates);
    }
    public ArrayList<String> getStationNames() { //funkcja zwraca iste dostepnych miast z pomiarami
        return stationsNamesWithCoordinates;
    }

    public ArrayList<Station> getStations() {
        return stations;
    }
}
