package Presenters;

import Objects.SensorData;
import Repositories.FromDB.GIOSSensorsRepository;
import Repositories.GIOSSensorDataRepository;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;

public class DataFromGiosSensorPresenter implements Runnable{

    GIOSSensorsRepository giosSensorsRepository;
    TableView<SensorData> GIOSTableView;
    TableColumn<SensorData, String> GIOSValueColumn;
    TableColumn<SensorData, String> GIOSDateColumn;
    ListView<String> sensorsListView;

    public DataFromGiosSensorPresenter(GIOSSensorsRepository giosSensorsRepository, TableView<SensorData> GIOSTableView, TableColumn<SensorData, String> GIOSValueColumn, TableColumn<SensorData, String> GIOSDateColumn, ListView<String> sensorsListView) {
        this.giosSensorsRepository = giosSensorsRepository;
        this.GIOSTableView = GIOSTableView;
        this.GIOSValueColumn = GIOSValueColumn;
        this.GIOSDateColumn = GIOSDateColumn;
        this.sensorsListView = sensorsListView;
    }

    @Override
    public void run() {
        try {
            onClickGIOSSensor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    void onClickGIOSSensor() throws IOException, JSONException, ParseException {
        GIOSTableView.getItems().clear();
        GIOSSensorDataRepository GIOSSensorDataRepository =new GIOSSensorDataRepository();
        GIOSDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        GIOSValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        GIOSTableView.getItems().addAll(GIOSSensorDataRepository.getDataFromSensor(giosSensorsRepository.getSelectedSensorID(sensorsListView)));
    }
}
