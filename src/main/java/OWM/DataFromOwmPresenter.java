package OWM;

import AnotherClasses.IDStationFinder;
import Repositories.FromDB.OWMClaudinesTranslatorRepository;
import Repositories.FromDB.OWMStationsRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.aksingh.owmjapis.api.APIException;

import java.io.IOException;
import java.text.ParseException;

public class DataFromOwmPresenter implements Runnable {
    private TableView<WeatherMeasureOWM> measuresFromOWMTableView;
    private TableColumn<WeatherMeasureOWM, Double> tempColumn;
    private TableColumn<WeatherMeasureOWM, Double> pressureColumn;
    private TableColumn<WeatherMeasureOWM, Double> windColumn;
    private TableColumn<WeatherMeasureOWM, Double> humidityColumn;
    private TableColumn<WeatherMeasureOWM, String> claudinessColumn;
    private TableColumn<WeatherMeasureOWM, String> dateOWMColumn;
    private WeatherMeasuresFactory weatherMeasuresFactory;
    private IDStationFinder idStationFinder;
    private ListView<String> OWMStationsListView;
    private OWMStationsRepository owmStationsRepository;
    private OWMClaudinesTranslatorRepository owmClaudinesTranslatorRepository;

    public DataFromOwmPresenter(TableView<WeatherMeasureOWM> measuresFromOWMTableView, TableColumn<WeatherMeasureOWM, Double> tempColumn, TableColumn<WeatherMeasureOWM, Double> pressureColumn, TableColumn<WeatherMeasureOWM, Double> windColumn, TableColumn<WeatherMeasureOWM, Double> humidityColumn, TableColumn<WeatherMeasureOWM, String> claudinessColumn, TableColumn<WeatherMeasureOWM, String> dateOWMColumn, WeatherMeasuresFactory weatherMeasuresFactory, IDStationFinder idStationFinder, ListView<String> OWMStationsListView, OWMStationsRepository owmStationsRepository, OWMClaudinesTranslatorRepository owmClaudinesTranslatorRepository) {
        this.measuresFromOWMTableView = measuresFromOWMTableView;
        this.tempColumn = tempColumn;
        this.pressureColumn = pressureColumn;
        this.windColumn = windColumn;
        this.humidityColumn = humidityColumn;
        this.claudinessColumn = claudinessColumn;
        this.dateOWMColumn = dateOWMColumn;
        this.weatherMeasuresFactory = weatherMeasuresFactory;
        this.idStationFinder = idStationFinder;
        this.OWMStationsListView = OWMStationsListView;
        this.owmStationsRepository = owmStationsRepository;
        this.owmClaudinesTranslatorRepository = owmClaudinesTranslatorRepository;
    }

    @Override
    public void run() {
        try {
            owmDataShow();
        } catch (APIException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void owmDataShow() throws APIException, ParseException, IOException {
        measuresFromOWMTableView.getItems().clear();
        weatherMeasuresFactory = new WeatherMeasuresFactory(idStationFinder.getIDSelectedStation(OWMStationsListView, owmStationsRepository.getStations()), 39, owmClaudinesTranslatorRepository);
        ObservableList<WeatherMeasureOWM> listOfWeatherMeasures = FXCollections.observableArrayList(weatherMeasuresFactory.getWeatherMeasuresListOWM());
        tempColumn.setCellValueFactory(new PropertyValueFactory<>("temp"));
        windColumn.setCellValueFactory(new PropertyValueFactory<>("wind"));
        humidityColumn.setCellValueFactory(new PropertyValueFactory<>("humidity"));
        pressureColumn.setCellValueFactory(new PropertyValueFactory<>("pressure"));
        claudinessColumn.setCellValueFactory(new PropertyValueFactory<>("claudiness"));
        dateOWMColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfMeasure"));
        measuresFromOWMTableView.setItems(listOfWeatherMeasures);
    }
}
