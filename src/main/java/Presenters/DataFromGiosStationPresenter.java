package Presenters;

import AnotherClasses.IDStationFinder;
import Objects.SensorData;
import ObjectsForMapper.GIOSAirIndex.AirIndexLevel;
import ObjectsForMapper.GIOSAirIndex.GIOSAirIndexRepository;
import Repositories.FromDB.GIOSSensorsRepository;
import Repositories.FromDB.GIOSStationsRepository;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONException;

import java.io.IOException;

public class DataFromGiosStationPresenter implements Runnable{

    TableColumn<AirIndexLevel,String> GIOSAirIndexValueColumn;
    TableColumn<AirIndexLevel,String>GIOSAirIndexNameColumn;
    TableView<AirIndexLevel> GIOSAirIndexTableView;
    ListView<String> GIOSStationsListView;
    TableView<SensorData> GIOSTableView;
    ListView<String> sensorsListView;
    GIOSSensorsRepository giosSensorsRepository;
    GIOSStationsRepository giosStationsRepository;
    IDStationFinder idStationFinder;

    public DataFromGiosStationPresenter(TableColumn<AirIndexLevel, String> GIOSAirIndexValueColumn, TableColumn<AirIndexLevel, String> GIOSAirIndexNameColumn, TableView<AirIndexLevel> GIOSAirIndexTableView, ListView<String> GIOSStationsListView, TableView<SensorData> GIOSTableView, ListView<String> sensorsListView, GIOSSensorsRepository giosSensorsRepository, GIOSStationsRepository giosStationsRepository, IDStationFinder idStationFinder) {
        this.GIOSAirIndexValueColumn = GIOSAirIndexValueColumn;
        this.GIOSAirIndexNameColumn = GIOSAirIndexNameColumn;
        this.GIOSAirIndexTableView = GIOSAirIndexTableView;
        this.GIOSStationsListView = GIOSStationsListView;
        this.GIOSTableView = GIOSTableView;
        this.sensorsListView = sensorsListView;
        this.giosSensorsRepository = giosSensorsRepository;
        this.giosStationsRepository = giosStationsRepository;
        this.idStationFinder = idStationFinder;
    }

    @Override
    public void run() {
        try {
            onClickGIOSStation();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    void onClickGIOSStation() throws IOException, JSONException {
        GIOSTableView.getItems().clear();
        GIOSAirIndexTableView.getItems().clear();

        GIOSAirIndexRepository giosAirIndexRepository =new GIOSAirIndexRepository();
        GIOSAirIndexNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        GIOSAirIndexValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        GIOSAirIndexTableView.getItems().addAll(giosAirIndexRepository.getAirIndexData(idStationFinder.getIDSelectedStation(GIOSStationsListView,giosStationsRepository.getStations())));
    }

}

