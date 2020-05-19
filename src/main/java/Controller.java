import AnotherClasses.AddUserMeasureHelper;
import ViewControll.IDStationFinder;
import Repositories.*;
import Repositories.FromDB.*;
import ViewControll.ActualDustyPlantsView;
import EmailActions.EmailToRegister;
import EmailActions.EmailToResetPassword;
import GIOS.GIOSAirIndex.AirIndexLevel;
import GIOS.GIOSAirIndex.GIOSAirIndexRepository;
import Objects.SensorData;
import Objects.*;
import Repositories.UserMeasuresPLNamesRepository;
import OWM.WeatherMeasureOWM;
import OWM.WeatherMeasuresFactory;
import AnotherClasses.RegisterHelper;
import ViewControll.UserSettingsView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import net.aksingh.owmjapis.api.APIException;
import org.json.JSONException;


import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;


public class Controller {
    UserSettingsRepository userSettingsRepository;
    DustyPlantsRepository dustyPlantsRepository;
    OWMStationsRepository OWMStationsRepository;
    UserStationsRepository userStationsRepository;
    GIOSStationsRepository giosStationsRepository;
    GIOSSensorsRepository giosSensorsRepository;
    OWMClaudinesTranslatorRepository owmClaudinesTranslatorRepository;
    UserMeasuresPLNamesRepository userMeasuresPLNamesRepository;
    IDStationFinder idStationFinder=new IDStationFinder();
    StationBrowser stationBrowser=new StationBrowser();
    String resetPasswordCode;
    String registerCode;
    RegisterHelper registerHelper = new RegisterHelper();
    WeatherMeasuresFactory weatherMeasuresFactory;
    TextFieldRestrict textFieldRestrict=new TextFieldRestrict();
    UserMeasuresRepository userMeasuresRepository = new UserMeasuresRepository(); //Do tworzenia tabel z pomiarami dla wybranych miast


    JDBC jdbc = new JDBC(); //Do połączenia z baza
    NewJDBCQuery newJDBCQuery; //Do wykonywania zapytań do bazy

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
    ListView<String> stationsToAddMeasureListView;
    @FXML
    TextField temperatureTextField;
    @FXML
    TextField humidityTextField;
    @FXML
    TextField windSpeedTextField;
    @FXML
    TextField pressureTextField;
    @FXML
    Label addMesureAlertLabel;
    //ELEMENTY POBRANIA POMIARU PRZEZ UŻYTKOWNIKA
    @FXML
    ListView<String> stationsToTakeMaeasureListView;
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
    ListView<String> OWMStationsListView;
    @FXML
    TextField OWMStationBrowserTextField;
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
    TextField TakeMeasureFromUserBrowserTextField;

    @FXML
    TextField AddMesureFromUserBrowserTextField;

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
    ListView<String> sensorsListView;

    //gios


    @FXML
    TableView<SensorData> GIOSTableView;
    @FXML
    TableColumn<SensorData, String> GIOSValueColumn;
    @FXML
    TableColumn<SensorData, String> GIOSDateColumn;
    public Controller() throws IOException {
    }
    @FXML
    TableColumn<AirIndexLevel,String>GIOSAirIndexValueColumn;
    @FXML
    TableColumn<AirIndexLevel,String>GIOSAirIndexNameColumn;
    @FXML
    TableView<AirIndexLevel>GIOSAirIndexTableView;
    @FXML
    TextField GIOSStationsBrowserTextField;
    @FXML
    ListView<String>GIOSStationsListView;
    @FXML
    void initialize() throws IOException, SQLException, APIException, ParseException {
        startConectionWithDataBase(); //Łączymy się z bazą w celu uwierzytelnienia i dalszej pracy aplikacji
        takeDataFromDBToRepositories();
        addDataFromRepositioriesToView();
        activeRestrictionsOnTextFields();
        activeStationsBrowsers();

    }

    private void startConectionWithDataBase() throws SQLException { //Połączenie aplikacji z bazą danych
        jdbc.getDbConnection();
        newJDBCQuery= new NewJDBCQuery(jdbc);
    }
    private void takeDataFromDBToRepositories() throws SQLException {
         userSettingsRepository=new UserSettingsRepository();
         dustyPlantsRepository=new DustyPlantsRepository( newJDBCQuery.getdustyplantsTable());
         OWMStationsRepository = new OWMStationsRepository(newJDBCQuery.getstationsTable("owmstations"));
         userStationsRepository =new UserStationsRepository(newJDBCQuery.getstationsTable("userstations"));
         giosStationsRepository=new GIOSStationsRepository(newJDBCQuery.getstationsTable("giosstations"));
         giosSensorsRepository=new GIOSSensorsRepository(newJDBCQuery.getGiosSensorsFromDataBase());
         owmClaudinesTranslatorRepository=new OWMClaudinesTranslatorRepository(newJDBCQuery.getCloudinessTranslatorTableENGNames(),newJDBCQuery.getCloudinessTranslatorTablePLNames());
         userMeasuresPLNamesRepository=new UserMeasuresPLNamesRepository();
    }
    private void addDataFromRepositioriesToView(){
        ActualDustyPlantsView actualDustyPlantsView = new ActualDustyPlantsView(actualDustyPlantsListView,dustyPlantsRepository);
        OWMStationsListView.getItems().addAll(OWMStationsRepository.getStationNames());
        stationsToAddMeasureListView.getItems().addAll(userStationsRepository.getStationNames());
        stationsToTakeMaeasureListView.getItems().addAll(userStationsRepository.getStationNames());
        GIOSStationsListView.getItems().addAll(giosStationsRepository.getStationNames());
        measuresFromUserComboBox.getItems().addAll(userMeasuresPLNamesRepository.getNamesOfMeasuresArraylist());
        claudinessFromIserComboBox.getItems().add(""); //Mozliwe dodanie pomiaru bez danych o zachmurzeniu
        claudinessFromIserComboBox.getSelectionModel().select(0); //Pomiar bez danych, a pusty ComboBox to
        // co innego wiec domyślnie wybrany jest bez danych
        claudinessFromIserComboBox.getItems().addAll(owmClaudinesTranslatorRepository.getPolishStringArraylist());
        measuresFromUserComboBox.getSelectionModel().select(0); //Wartość domyślna do pobrania z danych od
        // userów by combobox nie był pusty

    }
    private void activeRestrictionsOnTextFields(){
        textFieldRestrict.onlyDigitsInTextField(settingsMinTemperatureTextField);
        textFieldRestrict.onlyDigitsInTextField(settingsMaxTemperatureTextField);
        textFieldRestrict.onlyPlusDigitsInTextField(settingsMinPressureTextField);
        textFieldRestrict.onlyPlusDigitsInTextField(settingsMaxPressureTextField);
        textFieldRestrict.onlyPlusDigitsInTextField(settingsMinWindSpeedTextField);
        textFieldRestrict.onlyPlusDigitsInTextField(settingsMaxWindSpeedTextField);
    }
    private void activeStationsBrowsers(){
        StationBrowser stationBrowser=new StationBrowser();
        stationBrowser.searchByNameOnWriteInTextField(OWMStationBrowserTextField,OWMStationsRepository.getStationNames(),OWMStationsListView);
        stationBrowser.searchByNameOnWriteInTextField(AddMesureFromUserBrowserTextField,userStationsRepository.getStationNames(),stationsToAddMeasureListView);
        stationBrowser.searchByNameOnWriteInTextField(TakeMeasureFromUserBrowserTextField,userStationsRepository.getStationNames(), stationsToTakeMaeasureListView);
        stationBrowser.searchByNameOnWriteInTextField(GIOSStationsBrowserTextField,giosStationsRepository.getStationNames(),GIOSStationsListView);
    }
    //FUNKCJE DOTYCZĄCE LOGOWANIA I REJESTRACJI
    @FXML
    void loginButton() throws SQLException { //Funkcja zajmuje się uwierzytelnianiem i przełącza na główny ekran aplikacji
        if (newJDBCQuery.loginUser(userSettingsRepository,loginEmailTextField, passwordPasswordField) == true) {
            UserSettingsView userSettingsView=new UserSettingsView(userSettingsRepository,settingsMaxTemperatureTextField,settingsMinTemperatureTextField,settingsMinWindSpeedTextField,settingsMaxWindSpeedTextField,settingsMinPressureTextField,settingsMaxPressureTextField);
            registerAndLoginStackPane.setVisible(false);
            mainViewTabPane.setVisible(true);
        } else if (newJDBCQuery.loginUser(userSettingsRepository,loginEmailTextField, passwordPasswordField) == false) {
            badEmailOrPasswordLabel.setVisible(true);
        }

    }
    @FXML
    void switchOnRegisterButton() { //Funkcja przełącza na okienko do rejestracji
        loginVBox.setVisible(false);
        registerVBox.setVisible(true);
        registrationAlertLabel.setVisible(false);
    }

    @FXML
    void sendResetPasswordCode() {
        EmailToResetPassword emailToResetPassword = new EmailToResetPassword(emailToResetPasswordTextField);
        if (registerHelper.isEmail(emailToResetPasswordTextField) == true) {
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
    void goToChangePasswordAfterEnterCodeButton() {
        if (resetCodeTextField.getText().equals(resetPasswordCode)) {
            changePassword2VBox.setVisible(false);
            changePassword3VBox.setVisible(true);
        } else {
            badCodeResetPasswordLabel.setVisible(true);
        }
    }
    @FXML
    void switchOnForgotPasswordButton() {
        loginVBox.setVisible(false);
        changePassword1VBox.setVisible(true);
        badEmailResetPasswordLabel.setVisible(false);
    }
    @FXML
    void changePasswordButton() throws SQLException {
        if (registerHelper.isPasswordStrength(resetPasswordPasswordField, badPasswordLabel) == true) {
            if (resetPasswordPasswordField.getText().equals(conifrmedResetPasswordPasswordField.getText())) {
                if (newJDBCQuery.changeUserPassword(emailToResetPasswordTextField, resetPasswordPasswordField, conifrmedResetPasswordPasswordField, badPasswordLabel) == true) {
                    changePassword3VBox.setVisible(false);
                    loginVBox.setVisible(true);
                }
            } else
                badPasswordLabel.setVisible(true);
            badPasswordLabel.setText("Hasła nie są jednakowe");
        }
    }

    @FXML
    void registerButton() throws SQLException { //Funkcja dodaje nowego użytkownika do bazy i przechodzi do logowania
        if (registerCode.equals(registrationCodeTextField.getText())) {
            if(newJDBCQuery.addNewUser(registrationEmailTextField, registrationPasswordPasswordField) == true) {
                loginVBox.setVisible(true);
                register2VBox.setVisible(false);
            }
        } else {
            registrationCodeAlertLabel.setVisible(true);
            registrationCodeAlertLabel.setText("Nie prawidłowy kod");
        }
    }

    @FXML
    void sendRegistrationCode() throws SQLException {
        if (registerHelper.isEmail(registrationEmailTextField) == true
                && registerHelper.isPasswordStrength(registrationPasswordPasswordField, registrationAlertLabel) == true
                && newJDBCQuery.isAccountAlreadyEmailInDataBase(registrationEmailTextField.getText()) == false
                && registrationPasswordPasswordField.getText().equals(registrationConfirmedPasswordPasswordField.getText()) == true) {
            registerVBox.setVisible(false);
            register2VBox.setVisible(true);
            EmailToRegister emailToRegister = new EmailToRegister(registrationEmailTextField);
            Thread threadRegisterEmail = new Thread(emailToRegister);
            threadRegisterEmail.start();
            registerCode = emailToRegister.getRegistrationCode();
            registrationCodeAlertLabel.setVisible(false);
        } else if (registerHelper.isEmail(registrationEmailTextField) == false) {
            registrationAlertLabel.setText("Nie prawidłowy adres E-mail");
            registrationAlertLabel.setVisible(true);
        } else if (newJDBCQuery.isAccountAlreadyEmailInDataBase(registrationEmailTextField.getText()) == true) {
            registrationAlertLabel.setText("Konto jest już zarejstrowane");
            registrationAlertLabel.setVisible(true);
        } else if (registerHelper.isPasswordStrength(registrationPasswordPasswordField, registrationAlertLabel) == false) {
        } else if (!registrationPasswordPasswordField.equals(registrationConfirmedPasswordPasswordField)) {
            registrationAlertLabel.setText("Hasła nie są jednakowe");
            registrationAlertLabel.setVisible(true);
        }
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
    //FUNKCJE DOTYCZĄCE DODANIA POMIARU PRZEZ UŻYTKOWNIKA
    @FXML
    void addMeasureFromUserButton() throws SQLException {
        AddUserMeasureHelper addUserMeasureHelper=new AddUserMeasureHelper(userSettingsRepository);
        newJDBCQuery.addMeasuresFromUserToDataBase(addUserMeasureHelper,loginEmailTextField,pressureTextField,
                temperatureTextField, windSpeedTextField, humidityTextField, claudinessFromIserComboBox,
                idStationFinder.getIDSelectedStation(stationsToAddMeasureListView,userStationsRepository.getStations()),
                addMesureAlertLabel);
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
        if (measuresFromUserComboBox.getSelectionModel().getSelectedItem().equals("Temperatura powietrza")) {
            temperatureTableView.setVisible(true);
            ObservableList<TemperatureFromUser> listOfTemperatureResults = FXCollections.observableArrayList(userMeasuresRepository.showTemperaturesFromUsersInCity(idStationFinder.getIDSelectedStation(stationsToTakeMaeasureListView,userStationsRepository.getStations()), newJDBCQuery.getTemperaturesFromUserList()));
            dateTempUser.setCellValueFactory(new PropertyValueFactory<>("date"));
            userNameTemp.setCellValueFactory(new PropertyValueFactory<>("userName"));
            temperature.setCellValueFactory(new PropertyValueFactory<>("temperature"));
            temperatureTableView.setItems(listOfTemperatureResults);
        } else if (measuresFromUserComboBox.getSelectionModel().getSelectedItem().equals("Wilgotność powietrza")) {
            humidityTableView.setVisible(true);
            ObservableList<HumidityFromUser> listOfHumidityResults = FXCollections.observableArrayList(userMeasuresRepository.showHumidityFromUsersInCity(idStationFinder.getIDSelectedStation(stationsToTakeMaeasureListView,userStationsRepository.getStations()), newJDBCQuery.getHumidityFromUserList()));
            dateHumidityUser.setCellValueFactory(new PropertyValueFactory<>("date"));
            userNameHumidity.setCellValueFactory(new PropertyValueFactory<>("userName"));
            humidity.setCellValueFactory(new PropertyValueFactory<>("humidity"));
            humidityTableView.setItems(listOfHumidityResults);
        } else if (measuresFromUserComboBox.getSelectionModel().getSelectedItem().equals("Prędkość wiatru")) {
            windSpeedTableView.setVisible(true);
            ObservableList<WindSpeedFromUser> listOfHumidityResults = FXCollections.observableArrayList(userMeasuresRepository.showWindSpeedFromUsersInCity(idStationFinder.getIDSelectedStation(stationsToTakeMaeasureListView,userStationsRepository.getStations()), newJDBCQuery.getWindSpeedFromUserList()));
            dateWindSpeedUser.setCellValueFactory(new PropertyValueFactory<>("date"));
            userNameWindSpeed.setCellValueFactory(new PropertyValueFactory<>("userName"));
            windSpeed.setCellValueFactory(new PropertyValueFactory<>("windSpeed"));
            windSpeedTableView.setItems(listOfHumidityResults);
        } else if (measuresFromUserComboBox.getSelectionModel().getSelectedItem().equals("Ciśnienie")) {
            pressureTableView.setVisible(true);
            ObservableList<PressureFromUser> listOfPressureResults = FXCollections.observableArrayList(userMeasuresRepository.showPressureFromUsersInCity(idStationFinder.getIDSelectedStation(stationsToTakeMaeasureListView,userStationsRepository.getStations()), newJDBCQuery.getPressureFromUserList()));
            datePressureUser.setCellValueFactory(new PropertyValueFactory<>("date"));
            userNamePressure.setCellValueFactory(new PropertyValueFactory<>("userName"));
            pressure.setCellValueFactory(new PropertyValueFactory<>("pressure"));
            pressureTableView.setItems(listOfPressureResults);
        } else if (measuresFromUserComboBox.getSelectionModel().getSelectedItem().equals("Zachmurzenie")) {
            claudinessTableView.setVisible(true);
            ObservableList<ClaudinessFromUser> listOfClaudinessResults = FXCollections.observableArrayList(userMeasuresRepository.showClaudinessFromUsersInCity(idStationFinder.getIDSelectedStation(stationsToTakeMaeasureListView,userStationsRepository.getStations()), newJDBCQuery.getCloudinessFromUserList()));
            dateClaudinessUser.setCellValueFactory(new PropertyValueFactory<>("date"));
            userNameClaudiness.setCellValueFactory(new PropertyValueFactory<>("userName"));
            claudiness.setCellValueFactory(new PropertyValueFactory<>("claudiness"));
            claudinessTableView.setItems(listOfClaudinessResults);
        }
    }
    //DO POBRANIA POMIAROW Z OWM
    @FXML
    void onClickOnOWMStationsListView() throws APIException, ParseException, IOException {
            weatherMeasuresFactory = new WeatherMeasuresFactory(idStationFinder.getIDSelectedStation(OWMStationsListView,OWMStationsRepository.getStations()), 39, owmClaudinesTranslatorRepository);
            ObservableList<WeatherMeasureOWM> listOfWeatherMeasures = FXCollections.observableArrayList(weatherMeasuresFactory.getWeatherMeasuresListOWM());
            tempColumn.setCellValueFactory(new PropertyValueFactory<>("temp"));
            windColumn.setCellValueFactory(new PropertyValueFactory<>("wind"));
            humidityColumn.setCellValueFactory(new PropertyValueFactory<>("humidity"));
            pressureColumn.setCellValueFactory(new PropertyValueFactory<>("pressure"));
            claudinessColumn.setCellValueFactory(new PropertyValueFactory<>("claudiness"));
            dateOWMColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfMeasure"));
            measuresFromOWMTableView.setItems(listOfWeatherMeasures);
        }



    @FXML
    void changeUserSettingsButton() throws SQLException {
       newJDBCQuery.changeUserSettings(userSettingsRepository,settingsMaxTemperatureTextField,settingsMinTemperatureTextField,
               settingsMinWindSpeedTextField,settingsMaxWindSpeedTextField,settingsMinPressureTextField,settingsMaxPressureTextField,
               loginEmailTextField);
    }
    @FXML
    void loadDefaultSettingsButton() throws SQLException {
        newJDBCQuery.loadDefaultSettingsForUser(userSettingsRepository,loginEmailTextField);
        UserSettingsView userSettingsView=new UserSettingsView(userSettingsRepository,settingsMaxTemperatureTextField,settingsMinTemperatureTextField,settingsMinWindSpeedTextField,settingsMaxWindSpeedTextField,settingsMinPressureTextField,settingsMaxPressureTextField);
    }
    @FXML
    void onClickGIOSStation() throws IOException, JSONException {
        GIOSTableView.getItems().clear();
        GIOSAirIndexTableView.getItems().clear();
        sensorsListView.getItems().clear();
        sensorsListView.getItems().addAll(giosSensorsRepository.getSensorsForSelectedStation(idStationFinder.getIDSelectedStation(GIOSStationsListView,giosStationsRepository.getStations())));
        GIOSAirIndexRepository giosAirIndexRepository =new GIOSAirIndexRepository();
        GIOSAirIndexNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        GIOSAirIndexValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        GIOSAirIndexTableView.getItems().addAll(giosAirIndexRepository.getAirIndexData(idStationFinder.getIDSelectedStation(GIOSStationsListView,giosStationsRepository.getStations())));
    }
    @FXML
    void onClickGIOSSensor() throws IOException, JSONException, ParseException {
        GIOSTableView.getItems().clear();
        GIOSSensorDataRepository GIOSSensorDataRepository =new GIOSSensorDataRepository();
        GIOSDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        GIOSValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        GIOSTableView.getItems().addAll(GIOSSensorDataRepository.getDataFromSensor(giosSensorsRepository.getSelectedSensorID(sensorsListView)));
    }
    @FXML
    void pressEnterToTypePassword(ActionEvent ae) {
        passwordPasswordField.requestFocus();
    }

    @FXML
    void pressEnterToLogin(ActionEvent ae) throws SQLException {
            loginButton();
    }
    @FXML
    void pressEnterToSendPasswordCode(ActionEvent ae) {
        sendResetPasswordCode();
    }

    @FXML
    void pressEnterToSendRegistrationCode(ActionEvent ae) throws SQLException {
        sendRegistrationCode();
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
    void pressEnterToRegister(ActionEvent ae) throws SQLException {
        registerButton();
    }

    @FXML
    void pressEnterToResetPassword(ActionEvent ae) {
        goToChangePasswordAfterEnterCodeButton();
    }

    @FXML
    void pressEnterToTypeResetConfirmedPassword(ActionEvent ae) {
        conifrmedResetPasswordPasswordField.requestFocus();
    }

    @FXML
    void pressEnterToChangePassword(ActionEvent ae) throws SQLException {
            changePasswordButton();
    }
}





