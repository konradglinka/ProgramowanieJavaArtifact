import DustyPlants.ActualDustyPlantsFactory;
import MeasuresFromUsers.MeasuresPLNamesFactory;
import MeasuresFromUsers.TablesForCityFactory;
import MeasuresFromUsers.TypeOfMeasure.*;
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
    ActualDustyPlantsFactory actualDustyPlantsFactory = new ActualDustyPlantsFactory(); //Pobieramy listę aktualnie pylących roślin
    TablesForCityFactory tablesForCityFactory = new TablesForCityFactory(); //Do tworzenia tabel z pomiarami dla wybranych miast
    MeasuresPLNamesFactory measuresPLNamesFactory = new MeasuresPLNamesFactory();//Pobieramy listę dostępnych pomiarów
    ListOfCitiesFactory listOfCitiesFactory = new ListOfCitiesFactory(); //Pobieramy liste dostępnych miast
    JDBC jdbc = new JDBC(); //Do połączenia z baza
    JDBCQuery jdbcQuery; //Do wykonywania zapytań do bazy

    //ELEMENTY LOGOWANIA I REJESTRACJI
    @FXML
    StackPane registerAndLoginStackPane;
    //LOGOWANIE
    @FXML
    VBox loginVBox;
    @FXML
    TextField emailTextField;
    @FXML
    PasswordField passwordPasswordField;
    //REJESTRACJA
    @FXML
    VBox registerVBox;
    @FXML
    TextField registrationEmailTextField;
    @FXML
    PasswordField registrationPasswordPasswordField;
    @FXML
    PasswordField registrationConfirmedPasswordPasswordField;
    @FXML
    Label badEmailOrPasswordLabel;
    @FXML
    Label registrationAlertLabel;
    //ELEMENTY GŁÓWNEGO OKNA APLIKACJI
    @FXML
    TabPane mainViewTabPane;
    //ELEMENTY OKNA Z ROŚLINAMI PYLĄCYMI
    @FXML
    ListView actualDustyPlantsListView;
    //ELEMENTY DODANIA POMIARU PRZEZ UŻYTKOWNIKA
    @FXML
    ListView<String> cityToAddMeasureListView;
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
    //ELEMENTY POBRANIA POMIARU PRZEZ UŻYTKOWNIKA
    @FXML
    ListView<String> cityToTakeMaeasureFromUserListView;
    @FXML
    ComboBox<String> measuresFromUserComboBox;  //wybór rodzaju pomiaru
    //TEMPERATURA POWIETRZA
    @FXML
    TableView<TemperatureFromUser> temperatureTableView;
    @FXML
    TableColumn<TemperatureFromUser, String> dateTempUser;
    @FXML
    TableColumn<TemperatureFromUser, String> userNameTemp;
    @FXML
    TableColumn<TemperatureFromUser, Double> temperature;
    //PREDKOŚĆ WIATRU
    @FXML
    TableView<WindSpeedFromUser> windSpeedTableView;
    @FXML
    TableColumn<WindSpeedFromUser, String> dateWindSpeedUser;
    @FXML
    TableColumn<WindSpeedFromUser, String> userNameWindSpeed;
    @FXML
    TableColumn<WindSpeedFromUser, Double> windSpeed;
    //WILGOTNOŚĆ POWIETRZA
    @FXML
    TableView<HumidityFromUser> humidityTableView;
    @FXML
    TableColumn<HumidityFromUser, String> dateHumidityUser;
    @FXML
    TableColumn<HumidityFromUser, String> userNameHumidity;
    @FXML
    TableColumn<HumidityFromUser, Double> humidity;
    //CIŚNIENIE POWIETRZA
    @FXML
    TableView<PressureFromUser> pressureTableView;
    @FXML
    TableColumn<PressureFromUser, String> datePressureUser;
    @FXML
    TableColumn<PressureFromUser, String> userNamePressure;
    @FXML
    TableColumn<PressureFromUser, Double> pressure;
    //ZACHMURZENIE
    @FXML
    TableView<ClaudinessFromUser> claudinessTableView;
    @FXML
    TableColumn<ClaudinessFromUser, String> dateClaudinessUser;
    @FXML
    TableColumn<ClaudinessFromUser, String> userNameClaudiness;
    @FXML
    TableColumn<ClaudinessFromUser, String> claudiness;


    @FXML
    void initialize() {
        startConectionWithDataBase(); //Łączymy się z bazą w celu uwierzytelnienia i dalszej pracy aplikacji
        measuresFromUserComboBox.getItems().addAll(measuresPLNamesFactory.getNamesOfMeasuresArraylist()); //Lista dostępnych pomiarów do wyboru
        actualDustyPlantsListView.getItems().addAll(actualDustyPlantsFactory.listOfActualDustyPlants()); //Lista aktualnie pylących roślin
        cityToAddMeasureListView.getItems().addAll(listOfCitiesFactory.getCitiesArrayList()); //Lista miast do dodania pomiaru
        cityToTakeMaeasureFromUserListView.getItems().addAll(listOfCitiesFactory.getCitiesArrayList()); //Lista miast do pobrania pomiaru
    }
    private void startConectionWithDataBase() { //Połączenie aplikacji z bazą danych
        jdbc.getDbConnection();
        try {
            jdbcQuery = new JDBCQuery(jdbc);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //FUNKCJE DOTYCZĄCE LOGOWANIA I REJESTRACJI
    @FXML
    void switchOnRegisterButton() { //Funkcja przełącza na okienko do rejestracji
        loginVBox.setVisible(false);
        registerVBox.setVisible(true);
        registrationAlertLabel.setVisible(false);
    }

    @FXML
    void switchOnLoginButton() { //Funkcja przełącza na okienko do logowania
        loginVBox.setVisible(true);
        registerVBox.setVisible(false);
    }

    @FXML
    void loginButton() throws SQLException { //Funkcja zajmuje się uwierzytelnianiem i przełącza na główny ekran aplikacji
        if (jdbcQuery.loginCheck(emailTextField, passwordPasswordField) == true) {
            registerAndLoginStackPane.setVisible(false);
            mainViewTabPane.setVisible(true);
        } else if (jdbcQuery.loginCheck(emailTextField, passwordPasswordField) == false) {
            badEmailOrPasswordLabel.setVisible(true);
        }
    }
    @FXML
    void fakeLoginButton() { //Funkcja przełącza naekran główny bez uwierzytelniania (TYLKO DO TESTOWANIA APLIKACJI, ZASTĘPUJE loginButton)
        registerAndLoginStackPane.setVisible(false);
        mainViewTabPane.setVisible(true);
    }
    @FXML
    void registerButton() throws SQLException { //Funkcja dodaje nowego użytkownika do bazy i przechodzi do logowania
        if (jdbcQuery.addNewUser(registrationEmailTextField, registrationPasswordPasswordField, registrationConfirmedPasswordPasswordField, registrationAlertLabel) == true) {
            loginVBox.setVisible(true);
            registerVBox.setVisible(false);
        } else if (jdbcQuery.addNewUser(registrationEmailTextField, registrationPasswordPasswordField, registrationConfirmedPasswordPasswordField, registrationAlertLabel) == false) { }
    }

    @FXML
    void logoutButton() { //Funkcja wylogowywuje z aplikacji i przełącza do logowania
        registerAndLoginStackPane.setVisible(true);
        mainViewTabPane.setVisible(false);
    }
    //FUNKCJE DOTYCZĄCE DODANIA POMIARU PRZEZ UŻYTKOWNIKA
    @FXML
    void addMeasureFromUserButton() {
        jdbcQuery.addMeasuresFromUserToDataBase(pressureTextField, temperatureTextField, windSpeedTextField, humidityTextField, claudinessTextField, cityToAddMeasureListView);
    }
    //FUNKCJE DOTYCZĄCE POBRANIA POMIARU PRZEZ UŻYTKOWNIKA

    private void turnOffAllMeasuresFromUser() { //Funkcja pomocnicza wyłącza wszystkie widoczne tabele z pomiarami
        claudinessTableView.setVisible(false);
        temperatureTableView.setVisible(false);
        pressureTableView.setVisible(false);
        windSpeedTableView.setVisible(false);
        humidityTableView.setVisible(false);
    }

    private void clearAllTablesWithMeasuresFromUser() { //Funkcja pomocnicza czyści wszystkie tabele z pomiarami
        temperatureTableView.getItems().removeAll();
        pressureTableView.getItems().removeAll();
        windSpeedTableView.getItems().removeAll();
        humidityTableView.getItems().removeAll();
        claudinessTableView.getItems().removeAll();

    }

    @FXML
    void showMeasuresFromUserButton() { //Funkcja odpowiadająca za wypełnienie tabeli pomiarami po kliknieciu przycisku
        turnOffAllMeasuresFromUser(); //Wyłączamy poprzedni widok
        clearAllTablesWithMeasuresFromUser(); //Czyścimy poprzednie tabele
        try {
            jdbcQuery.getMeasureFromUserListFromDataBase(); //Pobieramy pomiary z bazy za każdym razem na nowo by dane były aktualne
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (measuresFromUserComboBox.getSelectionModel().getSelectedItem().equals("Temperatura powietrza")) {
            temperatureTableView.setVisible(true);
            ObservableList<TemperatureFromUser> listOfTemperatureResults = FXCollections.observableArrayList(tablesForCityFactory.showTemperaturesFromUserInCity(cityToTakeMaeasureFromUserListView.getSelectionModel().getSelectedItem(), jdbcQuery.getTemperaturesFromUserList()));
            dateTempUser.setCellValueFactory(new PropertyValueFactory<>("date"));
            userNameTemp.setCellValueFactory(new PropertyValueFactory<>("userName"));
            temperature.setCellValueFactory(new PropertyValueFactory<>("temperature"));
            temperatureTableView.setItems(listOfTemperatureResults);
        } else if (measuresFromUserComboBox.getSelectionModel().getSelectedItem().equals("Wilgotność powietrza")) {
            humidityTableView.setVisible(true);
            ObservableList<HumidityFromUser> listOfHumidityResults = FXCollections.observableArrayList(tablesForCityFactory.showHumidityFromUserInCity(cityToTakeMaeasureFromUserListView.getSelectionModel().getSelectedItem(), jdbcQuery.getHumidityFromUserList()));
            dateHumidityUser.setCellValueFactory(new PropertyValueFactory<>("date"));
            userNameHumidity.setCellValueFactory(new PropertyValueFactory<>("userName"));
            humidity.setCellValueFactory(new PropertyValueFactory<>("humidity"));
            humidityTableView.setItems(listOfHumidityResults);
        } else if (measuresFromUserComboBox.getSelectionModel().getSelectedItem().equals("Prędkość wiatru")) {
            windSpeedTableView.setVisible(true);
            ObservableList<WindSpeedFromUser> listOfHumidityResults = FXCollections.observableArrayList(tablesForCityFactory.showWindSpeedFromUserInCity(cityToTakeMaeasureFromUserListView.getSelectionModel().getSelectedItem(), jdbcQuery.getWindSpeedFromUserList()));
            dateWindSpeedUser.setCellValueFactory(new PropertyValueFactory<>("date"));
            userNameWindSpeed.setCellValueFactory(new PropertyValueFactory<>("userName"));
            windSpeed.setCellValueFactory(new PropertyValueFactory<>("windSpeed"));
            windSpeedTableView.setItems(listOfHumidityResults);
        } else if (measuresFromUserComboBox.getSelectionModel().getSelectedItem().equals("Ciśnienie")) {
            pressureTableView.setVisible(true);
            ObservableList<PressureFromUser> listOfPressureResults = FXCollections.observableArrayList(tablesForCityFactory.showPressureFromUserInCity(cityToTakeMaeasureFromUserListView.getSelectionModel().getSelectedItem(), jdbcQuery.getPressureFromUserList()));
            datePressureUser.setCellValueFactory(new PropertyValueFactory<>("date"));
            userNamePressure.setCellValueFactory(new PropertyValueFactory<>("userName"));
            pressure.setCellValueFactory(new PropertyValueFactory<>("pressure"));
            pressureTableView.setItems(listOfPressureResults);
        } else if (measuresFromUserComboBox.getSelectionModel().getSelectedItem().equals("Zachmurzenie")) {
            claudinessTableView.setVisible(true);
            ObservableList<ClaudinessFromUser> listOfClaudinessResults = FXCollections.observableArrayList(tablesForCityFactory.showClaudinessFromUserInCity(cityToTakeMaeasureFromUserListView.getSelectionModel().getSelectedItem(), jdbcQuery.getClaudinessFromUserList()));
            dateClaudinessUser.setCellValueFactory(new PropertyValueFactory<>("date"));
            userNameClaudiness.setCellValueFactory(new PropertyValueFactory<>("userName"));
            claudiness.setCellValueFactory(new PropertyValueFactory<>("claudiness"));
            claudinessTableView.setItems(listOfClaudinessResults);
        }


    }

}






