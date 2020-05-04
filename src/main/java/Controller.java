import DustyPlants.ActualDustyPlants;
import EmailActions.EmailToRegister;
import EmailActions.EmailToResetPassword;
import MeasuresFromUsers.MeasuresPLNamesFactory;
import MeasuresFromUsers.TablesForCityFactory;
import MeasuresFromUsers.TypeOfMeasure.*;
import OWM.OWMClaudinesTranslator;
import OWM.WeatherMeasureOWM;
import OWM.WeatherMeasuresFactory;
import RegisterAndLoginActions.VerificateDataFromUser;
import WeatherInCities.ListOfCitiesFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import net.aksingh.owmjapis.api.APIException;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;


public class Controller {

    String resetPasswordCode;
    String registerCode;
    VerificateDataFromUser verificateDataFromUser = new VerificateDataFromUser();
    ActualDustyPlants actualDustyPlants = new ActualDustyPlants(); //Pobieramy listę aktualnie pylących roślin
    WeatherMeasuresFactory weatherMeasuresFactory;

    TablesForCityFactory tablesForCityFactory = new TablesForCityFactory(); //Do tworzenia tabel z pomiarami dla wybranych miast
    MeasuresPLNamesFactory measuresPLNamesFactory = new MeasuresPLNamesFactory();//Pobieramy listę dostępnych pomiarów
    ListOfCitiesFactory listOfCitiesFactory = new ListOfCitiesFactory(); //Pobieramy liste dostępnych miast
    OWMClaudinesTranslator owmClaudinesTranslator = new OWMClaudinesTranslator();
    JDBC jdbc = new JDBC(); //Do połączenia z baza
    JDBCQuery jdbcQuery; //Do wykonywania zapytań do bazy

    //ELEMENTY LOGOWANIA I REJESTRACJI
    @FXML
    StackPane registerAndLoginStackPane;
    //LOGOWANIE
    @FXML
    VBox loginVBox;
    @FXML
    TextField loginEmailTextField;
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
    TextField pressureTextField;
    @FXML
    Label addMeasureAlertLabel;

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


    //OWM
    @FXML
    ListView<String> cityToTakeMaeasureFromOWMListView;
    @FXML
    TextField nameOfCityToFindTextField;
    @FXML
    TableView<WeatherMeasureOWM> measuresFromOWMTableView;
    @FXML
    TableColumn<WeatherMeasureOWM, Double> tempColumn;
    @FXML
    TableColumn<WeatherMeasureOWM, Double> pressureColumn;
    @FXML
    TableColumn<WeatherMeasureOWM, Double> windColumn;
    @FXML
    TableColumn<WeatherMeasureOWM, Double> humidityColumn;
    @FXML
    TableColumn<WeatherMeasureOWM, String> claudinessColumn;
    @FXML
    TableColumn<WeatherMeasureOWM, String> dateOWMColumn;


    //RESETOWANIE HASŁA
    @FXML
    VBox changePassword1VBox;
    @FXML
    VBox changePassword2VBox;
    @FXML
    VBox changePassword3VBox;
    @FXML
    TextField emailToResetPasswordTextField;
    @FXML
    TextField resetCodeTextField;
    @FXML
    PasswordField resetPasswordPasswordField;
    @FXML
    PasswordField conifrmedResetPasswordPasswordField;
    @FXML
    Label badPasswordLabel;
    @FXML
    VBox register2VBox;
    @FXML
    TextField registrationCodeTextField;
    @FXML
    Label badEmailResetPasswordLabel;
    @FXML
    Label badCodeResetPasswordLabel;
    @FXML
    Label registrationCodeAlertLabel;


    @FXML
    TextField nameOfCityToFindMesureFromUserTextField;

    @FXML
    TextField nameOfCityToAddMesureFromUserTextField;

    @FXML
    ComboBox<String> claudinessFromIserComboBox;


    @FXML
    TextField settingsMaxTemperatureTextField;
    @FXML
    TextField settingsMinTemperatureTextField;
    @FXML
    TextField settingsMaxPressureTextField;
    @FXML
    TextField settingsMinPressureTextField;
    @FXML
    TextField settingsMaxWindSpeedTextField;
    @FXML
    TextField settingsMinWindSpeedTextField;
    @FXML
    Label settingsAlertLabel;

    public Controller() throws IOException {
    }

    @FXML
    void initialize() {
        startConectionWithDataBase(); //Łączymy się z bazą w celu uwierzytelnienia i dalszej pracy aplikacji
        measuresFromUserComboBox.getItems().addAll(measuresPLNamesFactory.getNamesOfMeasuresArraylist()); //Lista dostępnych pomiarów do wyboru
        claudinessFromIserComboBox.getItems().add("");
        claudinessFromIserComboBox.getSelectionModel().select(0);
        claudinessFromIserComboBox.getItems().addAll(owmClaudinesTranslator.getPolishStringArraylist());
        actualDustyPlantsListView.getItems().addAll(actualDustyPlants.listOfActualDustyPlants()); //Lista aktualnie pylących roślin
        cityToAddMeasureListView.getItems().addAll(listOfCitiesFactory.getStationsNamesArrayList()); //Lista miast do dodania pomiaru
        cityToTakeMaeasureFromUserListView.getItems().addAll(listOfCitiesFactory.getStationsNamesArrayList()); //Lista miast do pobrania pomiaru
        cityToTakeMaeasureFromOWMListView.getItems().addAll(listOfCitiesFactory.getStationsNamesArrayList());
        measuresFromUserComboBox.getSelectionModel().select(0); //Wybieramy 1 z wartości combobox by nie byl pusty
        cityToAddMeasureListView.getSelectionModel().selectFirst();
        cityToTakeMaeasureFromUserListView.getSelectionModel().selectFirst();
        cityToTakeMaeasureFromOWMListView.getSelectionModel().selectFirst();
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
    void loginButton() throws SQLException { //Funkcja zajmuje się uwierzytelnianiem i przełącza na główny ekran aplikacji
        if (jdbcQuery.loginCheck(loginEmailTextField, passwordPasswordField) == true) {
            jdbcQuery.loadUserSettingsAboutAddMesure(settingsMaxTemperatureTextField,settingsMinTemperatureTextField,settingsMinWindSpeedTextField,settingsMaxWindSpeedTextField,settingsMinPressureTextField,settingsMaxPressureTextField);
            registerAndLoginStackPane.setVisible(false);
            mainViewTabPane.setVisible(true);
        } else if (jdbcQuery.loginCheck(loginEmailTextField, passwordPasswordField) == false) {
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

        if (registerCode.equals(registrationCodeTextField.getText())) {
            if (jdbcQuery.addNewUser(registrationEmailTextField, registrationPasswordPasswordField) == true) {
                loginVBox.setVisible(true);
                register2VBox.setVisible(false);
            }
        } else {
            registrationCodeAlertLabel.setVisible(true);
            registrationCodeAlertLabel.setText("Nie prawidłowy kod");
        }
    }

    @FXML
    void logoutButton() { //Funkcja wylogowywuje z aplikacji i przełącza do logowania
        registerAndLoginStackPane.setVisible(true);
        mainViewTabPane.setVisible(false);
    }

    //FUNKCJE DOTYCZĄCE DODANIA POMIARU PRZEZ UŻYTKOWNIKA
    @FXML
    void addMeasureFromUserButton() {
        String[]namelonlat=cityToAddMeasureListView.getSelectionModel().getSelectedItem().split("\n");
        String name=namelonlat[0];
        Double lon=Double.parseDouble(namelonlat[1]);
        Double lat=Double.parseDouble(namelonlat[2]);
        int id=listOfCitiesFactory.findIDForCity(name,lon,lat);
        jdbcQuery.addMeasuresFromUserToDataBase(pressureTextField, temperatureTextField, windSpeedTextField, humidityTextField, claudinessFromIserComboBox,id,addMeasureAlertLabel);
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
    void showMeasuresFromUserButton() throws SQLException { //Funkcja odpowiadająca za wypełnienie tabeli pomiarami po kliknieciu przycisku
        turnOffAllMeasuresFromUser(); //Wyłączamy poprzedni widok
        clearAllTablesWithMeasuresFromUser(); //Czyścimy poprzednie tabele
        String[]namelonlat=cityToTakeMaeasureFromUserListView.getSelectionModel().getSelectedItem().split("\n");
        String name=namelonlat[0];
        double lon =Double.parseDouble(namelonlat[1]);
        double lat =Double.parseDouble(namelonlat[2]);
        int id=listOfCitiesFactory.findIDForCity(name,lon,lat);

        if (measuresFromUserComboBox.getSelectionModel().getSelectedItem().equals("Temperatura powietrza")) {
            temperatureTableView.setVisible(true);
            ObservableList<TemperatureFromUser> listOfTemperatureResults = FXCollections.observableArrayList(tablesForCityFactory.showTemperaturesFromUserInCity(id, jdbcQuery.getTemperaturesFromUserList()));
            dateTempUser.setCellValueFactory(new PropertyValueFactory<>("date"));
            userNameTemp.setCellValueFactory(new PropertyValueFactory<>("userName"));
            temperature.setCellValueFactory(new PropertyValueFactory<>("temperature"));
            temperatureTableView.setItems(listOfTemperatureResults);
        } else if (measuresFromUserComboBox.getSelectionModel().getSelectedItem().equals("Wilgotność powietrza")) {
            humidityTableView.setVisible(true);
            ObservableList<HumidityFromUser> listOfHumidityResults = FXCollections.observableArrayList(tablesForCityFactory.showHumidityFromUserInCity(id, jdbcQuery.getHumidityFromUserList()));
            dateHumidityUser.setCellValueFactory(new PropertyValueFactory<>("date"));
            userNameHumidity.setCellValueFactory(new PropertyValueFactory<>("userName"));
            humidity.setCellValueFactory(new PropertyValueFactory<>("humidity"));
            humidityTableView.setItems(listOfHumidityResults);
        } else if (measuresFromUserComboBox.getSelectionModel().getSelectedItem().equals("Prędkość wiatru")) {
            windSpeedTableView.setVisible(true);
            ObservableList<WindSpeedFromUser> listOfHumidityResults = FXCollections.observableArrayList(tablesForCityFactory.showWindSpeedFromUserInCity(id, jdbcQuery.getWindSpeedFromUserList()));
            dateWindSpeedUser.setCellValueFactory(new PropertyValueFactory<>("date"));
            userNameWindSpeed.setCellValueFactory(new PropertyValueFactory<>("userName"));
            windSpeed.setCellValueFactory(new PropertyValueFactory<>("windSpeed"));
            windSpeedTableView.setItems(listOfHumidityResults);
        } else if (measuresFromUserComboBox.getSelectionModel().getSelectedItem().equals("Ciśnienie")) {
            pressureTableView.setVisible(true);
            ObservableList<PressureFromUser> listOfPressureResults = FXCollections.observableArrayList(tablesForCityFactory.showPressureFromUserInCity(id, jdbcQuery.getPressureFromUserList()));
            datePressureUser.setCellValueFactory(new PropertyValueFactory<>("date"));
            userNamePressure.setCellValueFactory(new PropertyValueFactory<>("userName"));
            pressure.setCellValueFactory(new PropertyValueFactory<>("pressure"));
            pressureTableView.setItems(listOfPressureResults);
        } else if (measuresFromUserComboBox.getSelectionModel().getSelectedItem().equals("Zachmurzenie")) {
            claudinessTableView.setVisible(true);
            ObservableList<ClaudinessFromUser> listOfClaudinessResults = FXCollections.observableArrayList(tablesForCityFactory.showClaudinessFromUserInCity(id, jdbcQuery.getClaudinessFromUserList()));
            dateClaudinessUser.setCellValueFactory(new PropertyValueFactory<>("date"));
            userNameClaudiness.setCellValueFactory(new PropertyValueFactory<>("userName"));
            claudiness.setCellValueFactory(new PropertyValueFactory<>("claudiness"));
            claudinessTableView.setItems(listOfClaudinessResults);
        }
    }

    @FXML
    void findOWMStationButton() throws APIException, ParseException, IOException {
        boolean finded=false;
        ArrayList<String>findedCities=new ArrayList<>();
        String input = nameOfCityToFindTextField.getText().toLowerCase();
        String finalInput = "";
        for (int i = 0; i < input.length(); i++) {
            if (i == 0) {
                finalInput += input.charAt(i);
                finalInput = finalInput.toUpperCase();
            }
            if (i > 0) {
                if (input.charAt(i) == 32) {
                    String bigletter = " " + input.substring(i + 1, i + 2).toUpperCase();
                    finalInput += bigletter;
                    i++;
                } else {
                    finalInput += input.charAt(i);
                }

            }
        }
        if(finalInput.length()>0) {
            for (int i = 0; i < listOfCitiesFactory.getStationsNamesArrayList().size(); i++) {
                if (listOfCitiesFactory.getStationsNamesArrayList().get(i).startsWith(finalInput)) {

                    finded = true;
                    findedCities.add(listOfCitiesFactory.getStationsNamesArrayList().get(i));
                }
            }
            if (finded == true) {
                nameOfCityToFindTextField.setStyle("-fx-control-inner-background: green;");
                cityToTakeMaeasureFromOWMListView.getItems().clear();
                cityToTakeMaeasureFromOWMListView.getItems().addAll(findedCities);
                cityToTakeMaeasureFromOWMListView.getSelectionModel().selectFirst();

            } else if (finded == false) {
                nameOfCityToFindTextField.setStyle("-fx-control-inner-background: red;");
                cityToTakeMaeasureFromOWMListView.getItems().removeAll();
                cityToTakeMaeasureFromOWMListView.getItems().clear();
                cityToTakeMaeasureFromOWMListView.getItems().addAll(listOfCitiesFactory.getStationsNamesArrayList());
            }
        }
        else
        {
            nameOfCityToFindTextField.setStyle("");
            cityToTakeMaeasureFromOWMListView.getItems().removeAll();
            cityToTakeMaeasureFromOWMListView.getItems().clear();
            cityToTakeMaeasureFromOWMListView.getItems().addAll(listOfCitiesFactory.getStationsNamesArrayList());
        }
    }
    String[] temp={"a","b","c"};
    @FXML
    void onClickOnOWMStationsListView() throws APIException, ParseException, IOException {

      String[]namelonlat=cityToTakeMaeasureFromOWMListView.getSelectionModel().getSelectedItem().split("\n");

        if(!temp.equals(namelonlat[0])) {
            String name = namelonlat[0];
            Double lon = Double.parseDouble(namelonlat[1]);
            Double lat = Double.parseDouble(namelonlat[2]);
            weatherMeasuresFactory = new WeatherMeasuresFactory(listOfCitiesFactory.findIDForCity(name, lon, lat), 39);
            ObservableList<WeatherMeasureOWM> listOfWeatherMeasures = FXCollections.observableArrayList(weatherMeasuresFactory.getWeatherMeasuresListOWM());
            tempColumn.setCellValueFactory(new PropertyValueFactory<>("temp"));
            windColumn.setCellValueFactory(new PropertyValueFactory<>("wind"));
            humidityColumn.setCellValueFactory(new PropertyValueFactory<>("humidity"));
            pressureColumn.setCellValueFactory(new PropertyValueFactory<>("pressure"));
            claudinessColumn.setCellValueFactory(new PropertyValueFactory<>("claudiness"));
            dateOWMColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfMeasure"));
            measuresFromOWMTableView.setItems(listOfWeatherMeasures);
            temp=namelonlat;

        }
    }
    @FXML
    void findCityToTakeMeasureFromUsersButton() {
        boolean finded=false;
        ArrayList<String>findedCities=new ArrayList<>();
        String input = nameOfCityToFindMesureFromUserTextField.getText().toLowerCase();
        String finalInput = "";
        for (int i = 0; i < input.length(); i++) {
            if (i == 0) {
                finalInput += input.charAt(i);
                finalInput = finalInput.toUpperCase();
            }
            if (i > 0) {
                if (input.charAt(i) == 32) {
                    String bigletter = " " + input.substring(i + 1, i + 2).toUpperCase();
                    finalInput += bigletter;
                    i++;
                } else {
                    finalInput += input.charAt(i);
                }

            }
        }
        if(finalInput.length()>0) {
            for (int i = 0; i < listOfCitiesFactory.getStationsNamesArrayList().size(); i++) {
                if (listOfCitiesFactory.getStationsNamesArrayList().get(i).startsWith(finalInput)) {

                    finded = true;
                    findedCities.add(listOfCitiesFactory.getStationsNamesArrayList().get(i));
                }
            }
            if (finded == true) {
                nameOfCityToFindMesureFromUserTextField.setStyle("-fx-control-inner-background: green;");

                cityToTakeMaeasureFromUserListView.getItems().clear();
                cityToTakeMaeasureFromUserListView.getItems().addAll(findedCities);
                cityToTakeMaeasureFromUserListView.getSelectionModel().selectFirst();
            } else if (finded == false) {
                nameOfCityToFindMesureFromUserTextField.setStyle("-fx-control-inner-background: red;");
                cityToTakeMaeasureFromUserListView.getItems().removeAll();
                cityToTakeMaeasureFromUserListView.getItems().clear();
                cityToTakeMaeasureFromUserListView.getItems().addAll(listOfCitiesFactory.getStationsNamesArrayList());
            }
        }
        else
        {
            nameOfCityToFindMesureFromUserTextField.setStyle("");
            cityToTakeMaeasureFromUserListView.getItems().removeAll();
            cityToTakeMaeasureFromUserListView.getItems().clear();
            cityToTakeMaeasureFromUserListView.getItems().addAll(listOfCitiesFactory.getStationsNamesArrayList());
        }
    }

    @FXML
    void findCityToAddMeasureFromUsersButton() {
        boolean finded=false;

        ArrayList<String>findedCities=new ArrayList<>();
        String input = nameOfCityToAddMesureFromUserTextField.getText().toLowerCase();
        String finalInput = "";
        for (int i = 0; i < input.length(); i++) {
            if (i == 0) {
                finalInput += input.charAt(i);
                finalInput = finalInput.toUpperCase();
            }
            if (i > 0) {
                if (input.charAt(i) == 32) {
                    String bigletter = " " + input.substring(i + 1, i + 2).toUpperCase();
                    finalInput += bigletter;
                    i++;
                } else {
                    finalInput += input.charAt(i);
                }

            }
        }
        if(finalInput.length()>0) {
            for (int i = 0; i < listOfCitiesFactory.getStationsNamesArrayList().size(); i++) {
                if (listOfCitiesFactory.getStationsNamesArrayList().get(i).startsWith(finalInput)) {

                    finded = true;
                    findedCities.add(listOfCitiesFactory.getStationsNamesArrayList().get(i));
                }
            }
            if (finded == true) {
                nameOfCityToAddMesureFromUserTextField.setStyle("-fx-control-inner-background: green;");
                cityToAddMeasureListView.getItems().clear();
                cityToAddMeasureListView.getItems().addAll(findedCities);
                cityToAddMeasureListView.getSelectionModel().selectFirst();
            } else if (finded == false) {
                nameOfCityToAddMesureFromUserTextField.setStyle("-fx-control-inner-background: red;");
                cityToAddMeasureListView.getItems().removeAll();
                cityToAddMeasureListView.getItems().clear();
                cityToAddMeasureListView.getItems().addAll(listOfCitiesFactory.getStationsNamesArrayList());
            }
        }
        else
        {
            nameOfCityToAddMesureFromUserTextField.setStyle("");
            cityToAddMeasureListView.getItems().removeAll();
            cityToAddMeasureListView.getItems().clear();
            cityToAddMeasureListView.getItems().addAll(listOfCitiesFactory.getStationsNamesArrayList());
        }
    }


    @FXML
    void sendRegistrationCode() throws SQLException {

        if (verificateDataFromUser.isEmail(registrationEmailTextField) == true
                && verificateDataFromUser.isPasswordStrength(registrationPasswordPasswordField, registrationAlertLabel) == true
                && jdbcQuery.isAccountAlreadyEmailInDataBase(registrationEmailTextField.getText()) == false
                && registrationPasswordPasswordField.getText().equals(registrationConfirmedPasswordPasswordField.getText()) == true) {
            registerVBox.setVisible(false);
            register2VBox.setVisible(true);
            EmailToRegister emailToRegister = new EmailToRegister(registrationEmailTextField);
            Thread threadRegisterEmail = new Thread(emailToRegister);
            threadRegisterEmail.start();
            registerCode = emailToRegister.getRegistrationCode();
            registrationCodeAlertLabel.setVisible(false);
        } else if (verificateDataFromUser.isEmail(registrationEmailTextField) == false) {
            registrationAlertLabel.setText("Nie prawidłowy adres E-mail");
            registrationAlertLabel.setVisible(true);
        } else if (jdbcQuery.isAccountAlreadyEmailInDataBase(registrationEmailTextField.getText()) == true) {
            registrationAlertLabel.setText("Konto jest już zarejstrowane");
            registrationAlertLabel.setVisible(true);
        } else if (verificateDataFromUser.isPasswordStrength(registrationPasswordPasswordField, registrationAlertLabel) == false) {
        } else if (!registrationPasswordPasswordField.equals(registrationConfirmedPasswordPasswordField)) {
            registrationAlertLabel.setText("Hasła nie są jednakowe");
            registrationAlertLabel.setVisible(true);
        }


    }

    @FXML
    void sendResetPasswordCode() {
        EmailToResetPassword emailToResetPassword = new EmailToResetPassword(emailToResetPasswordTextField);
        if (verificateDataFromUser.isEmail(emailToResetPasswordTextField) == true) {
            Thread threadResetPasswordEmail = new Thread(emailToResetPassword);
            threadResetPasswordEmail.start();
            changePassword1VBox.setVisible(false);
            changePassword2VBox.setVisible(true);
            resetPasswordCode = emailToResetPassword.getResetPasswordCode();
            badCodeResetPasswordLabel.setVisible(false);
        } else {
            badEmailResetPasswordLabel.setVisible(true);
        }
    }

    @FXML
    void goToChangePasswordButton() {
        if (resetCodeTextField.getText().equals(resetPasswordCode)) {
            changePassword2VBox.setVisible(false);
            changePassword3VBox.setVisible(true);
        } else {
            badCodeResetPasswordLabel.setVisible(true);
        }
    }

    @FXML
    void changePasswordButton() throws SQLException {
        if (verificateDataFromUser.isPasswordStrength(resetPasswordPasswordField, badPasswordLabel) == true) {
            if (resetPasswordPasswordField.getText().equals(conifrmedResetPasswordPasswordField.getText())) {
                if (jdbcQuery.changeUserPassword(emailToResetPasswordTextField, resetPasswordPasswordField, conifrmedResetPasswordPasswordField, badPasswordLabel) == true) {
                    changePassword3VBox.setVisible(false);
                    loginVBox.setVisible(true);
                }
            } else
                badPasswordLabel.setVisible(true);
            badPasswordLabel.setText("Hasła nie są jednakowe");
        }
    }

    @FXML
    void switchOnForgotPasswordButton() {
        loginVBox.setVisible(false);
        changePassword1VBox.setVisible(true);
        badEmailResetPasswordLabel.setVisible(false);
    }


    @FXML
    void switchOnLoginButton() {
        changePassword1VBox.setVisible(false);
        changePassword2VBox.setVisible(false);
        changePassword3VBox.setVisible(false);
        registerVBox.setVisible(false);
        register2VBox.setVisible(false);
        badEmailOrPasswordLabel.setVisible(false);
        loginVBox.setVisible(true);
    }

    @FXML
    void pressEnterToTypePassword(ActionEvent ae) {
        passwordPasswordField.requestFocus();
    }

    @FXML
    void pressEnterToLogin(ActionEvent ae) {
        try {
            loginButton();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void pressEnterToSendPasswordCode(ActionEvent ae) {
        sendResetPasswordCode();
    }

    @FXML
    void pressEnterToSendRegistrationCode(ActionEvent ae) {
        try {
            sendRegistrationCode();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void pressEnterToTypeRegistrationPassword(ActionEvent ae) {
        registrationPasswordPasswordField.requestFocus();
    }

    @FXML
    void pressEnterToTypeConfirmedRegistrationPassword(ActionEvent ae) {
        registrationConfirmedPasswordPasswordField.requestFocus();
    }

    @FXML
    void pressEnterToRegister(ActionEvent ae) {
        try {
            registerButton();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void pressEnterToResetPassword(ActionEvent ae) {
        goToChangePasswordButton();
    }

    @FXML
    void pressEnterToTypeResetConfirmedPassword(ActionEvent ae) {
        conifrmedResetPasswordPasswordField.requestFocus();
    }

    @FXML
    void pressEnterToChangePassword(ActionEvent ae) {
        try {
            changePasswordButton();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void pressEnterToTakeOWMMeasure(ActionEvent ae) {
        try {
            findOWMStationButton();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (APIException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void changeUserSettingsButton() throws SQLException {
        jdbcQuery.changeUserSettings(settingsMaxTemperatureTextField,settingsMinTemperatureTextField,settingsMinWindSpeedTextField,settingsMaxWindSpeedTextField,settingsMinPressureTextField,settingsMaxPressureTextField,loginEmailTextField);
    }
    @FXML
    void loadDefaultSettingsButton() throws SQLException {
        jdbcQuery.loadDefaultSettingsForUser(loginEmailTextField);

        jdbcQuery.loadUserSettingsAboutAddMesure(settingsMaxTemperatureTextField,settingsMinTemperatureTextField,settingsMinWindSpeedTextField,settingsMaxWindSpeedTextField,settingsMinPressureTextField,settingsMaxPressureTextField);
    }
}





