package Repositories.FromDB;

import Objects.FromDB.GIOSSensor;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class GIOSSensorsRepository {
    private final ArrayList<GIOSSensor> sensors;
    private int selectedStationID=0;
    public GIOSSensorsRepository(ArrayList<GIOSSensor> sensorsArrayList){
    this.sensors = sensorsArrayList;
    }
    public ArrayList<String> getSensorsForSelectedStation(int id){
        this.selectedStationID=id;
        ArrayList<String> sensorsNames = new ArrayList<>();
        for (int k = 0; k < sensors.size(); k++) {
            if(sensors.get(k).getIDStation()==id)
           sensorsNames.add(sensors.get(k).getShortNameOfSensor());
        }
        Collections.sort(sensorsNames);
        return sensorsNames;
    }
    public int getSelectedSensorID(ListView<String> sensorsListView){
        for(int i=0;i<sensors.size();i++){
            if(sensors.get(i).getIDStation()==selectedStationID){
                if(sensors.get(i).getShortNameOfSensor().equals(sensorsListView.getSelectionModel().getSelectedItem())){
                    return sensors.get(i).getIDSensor();
                }
            }
        }
        return 0;
    }
}
