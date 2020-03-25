import DustyPlants.ActualDustyPlants;
import MeasuresFromUsers.*;
import WeatherInCities.ListOfCitiesFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class Controller {
    ActualDustyPlants actualDustyPlants=new ActualDustyPlants();
    TablesForCityFactory tablesForCityFactory = new TablesForCityFactory();
    MeasuresPLNamesFactory measuresPLNamesFactory = new MeasuresPLNamesFactory();
    ListOfCitiesFactory listOfCitiesFactory=new ListOfCitiesFactory();
    JDBC jdbc=new JDBC();
    JDBCQuery jdbcQuery;






    @FXML
    TableView plantsTableView;
    @FXML
    VBox loginVBox;
    @FXML
    VBox registerVBox;
    @FXML
    StackPane registerAndLoginStackPane;
    @FXML
    TabPane mainViewTabPane;
    @FXML
    TextField emailTextField;
    @FXML
    TextField registrationEmailTextField;
    @FXML
    TextField temperatureTextField;
    @FXML
    TextField humidityTextField;
    @FXML
    TextField windSpeedTextField;
    @FXML
    TextField claudinessTextField;
    @FXML
    TextField pressureTextField;

    @FXML
    TableColumn<TemperaturesFromUser,String> date;
    @FXML
    TableColumn<TemperaturesFromUser,String> userName;
    @FXML
    TableColumn<TablesForCityFactory,Double> temperature;
    @FXML
    TableView<TemperaturesFromUser> temperatureTableView;
@FXML
TableView<WindSpeedFromUser> windSpeedTableView;
    @FXML
    TableView<HumidityFromUser> humidityTableView;
    @FXML
    TableView<PressureFromUser> pressureTableView;
    @FXML
    TableView<ClaudinessFromUser> claudinessTableView;








    @FXML
    PasswordField passwordPasswordField;

    @FXML
    PasswordField registrationPasswordPasswordField;
    @FXML
    PasswordField registrationConfirmedPasswordPasswordField;
    @FXML
    Label badEmailOrPasswordLabel;
    @FXML
    Label registrationAlertLabel;
    @FXML
    ListView actualDustyPlantsListView;
    @FXML
    ListView<String> cityToAddMeasureListView;
    @FXML
    ListView<String> cityToTakeMaeasureFromUserListView;
@FXML
ComboBox<String> measuresFromUserComboBox;
    @FXML
    void initialize() throws SQLException {
measuresFromUserComboBox.getItems().addAll(measuresPLNamesFactory.getNamesOfMeasuresArraylist());
        actualDustyPlantsListView.getItems().addAll(actualDustyPlants.listOfActualDustyPlants());

        cityToAddMeasureListView.getItems().addAll(listOfCitiesFactory.getCitiesArrayList());
        cityToTakeMaeasureFromUserListView.getItems().addAll(listOfCitiesFactory.getCitiesArrayList());

        jdbc.getDbConnection();
         jdbcQuery= new JDBCQuery(jdbc);


    }
    @FXML
    void switchOnRegisterButton(){
        loginVBox.setVisible(false);
        registerVBox.setVisible(true);
        registrationAlertLabel.setVisible(false);
    }
    @FXML
    void switchOnLoginButton(){
        loginVBox.setVisible(true);
        registerVBox.setVisible(false);
    }
    @FXML
    void loginButton() throws SQLException {
        if (jdbcQuery.loginCheck(emailTextField,passwordPasswordField) == true) {
            registerAndLoginStackPane.setVisible(false);
            mainViewTabPane.setVisible(true);
        } else if (jdbcQuery.loginCheck(emailTextField, passwordPasswordField) == false) {
            badEmailOrPasswordLabel.setVisible(true);
        }

    }

    @FXML
    void registerButton() throws SQLException {
       if (jdbcQuery.addNewUser(registrationEmailTextField,registrationPasswordPasswordField, registrationConfirmedPasswordPasswordField,registrationAlertLabel)==true)
       {
           loginVBox.setVisible(true);
           registerVBox.setVisible(false);
       }
       else if (jdbcQuery.addNewUser(registrationEmailTextField,registrationPasswordPasswordField, registrationConfirmedPasswordPasswordField,registrationAlertLabel)==false){

       }

    }

    @FXML
    void logoutButton(){
        registerAndLoginStackPane.setVisible(true);
        mainViewTabPane.setVisible(false);
    }
@FXML
void addMeasureFromUserButton(){
        jdbcQuery.addMeasuresFromUserToDataBase(pressureTextField,temperatureTextField,windSpeedTextField,humidityTextField,claudinessTextField,cityToAddMeasureListView);
}
@FXML
void showMeasuresFromUserButton(){
        temperatureTableView.getItems().removeAll();
    try {
        jdbcQuery.getMeasureFromUserListFromDataBase();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    if(measuresFromUserComboBox.getSelectionModel().getSelectedItem().equals("Temperatura powietrza")) {

        ObservableList<TemperaturesFromUser> listOfTemperatureResults;
        listOfTemperatureResults = FXCollections.observableArrayList(tablesForCityFactory.showTemperaturesFromUserInCity(cityToTakeMaeasureFromUserListView.getSelectionModel().getSelectedItem(), jdbcQuery.getTemperaturesFromUserList()));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        userName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        temperature.setCellValueFactory(new PropertyValueFactory<>("temperature"));
        temperatureTableView.setItems(listOfTemperatureResults);
    }


}











    // sluzy do uruchomienia bez xamppa
    @FXML
    void fakeLoginButton(){
        registerAndLoginStackPane.setVisible(false);
        mainViewTabPane.setVisible(true);
    }
















    private void turnOffAllMeasuresFromUser(){
        claudinessTableView.setVisible(false);
        temperatureTableView.setVisible(false);
        pressureTableView.setVisible(false);
        windSpeedTableView.setVisible(false);
        humidityTableView.setVisible(false);
    }
}






