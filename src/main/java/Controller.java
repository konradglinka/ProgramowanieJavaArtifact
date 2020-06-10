import AnotherClasses.*;
import Presenters.DataFromGiosSensorPresenter;
import Presenters.DataFromGiosStationPresenter;
import Presenters.DataFromOwmPresenter;
import Objects.FromDB.*;
import Repositories.FromDB.*;
import ViewControll.ActualDustyPlantsView;
import EmailActions.EmailToRegister;
import EmailActions.EmailToResetPassword;
import ObjectsForMapper.GIOSAirIndex.AirIndexLevel;
import ObjectsForMapper.GIOSAirIndex.GIOSAirIndexRepository;
import Objects.SensorData;
import Repositories.UserMeasuresPLNamesRepository;
import OWM.WeatherMeasureOWM;
import OWM.WeatherMeasuresFactory;
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
    AppSettingsRepository appSettingsRepository;
    DustyPlantsRepository dustyPlantsRepository;
    OWMStationsRepository owmStationsRepository;
    UserStationsRepository usersStationsRepository;
    GIOSStationsRepository giosStationsRepository;
    GIOSSensorsRepository giosSensorsRepository;
    OWMClaudinesTranslatorRepository owmClaudinesTranslatorRepository;
    UserMeasuresPLNamesRepository userMeasuresPLNamesRepository;
    IDStationFinder idStationFinder=new IDStationFinder();
    String resetPasswordCode;
    String registerCode;
    RegisterHelper registerHelper = new RegisterHelper();
    WeatherMeasuresFactory weatherMeasuresFactory;
    TextFieldRestrict textFieldRestrict=new TextFieldRestrict();
    UserMeasuresRepository userMeasuresRepository = new UserMeasuresRepository(); //Do tworzenia tabel z pomiarami dla wybranych miast
    StationBrowser stationBrowser=new StationBrowser();

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
    ListView<String> usersStationsToAddMeasureListView;
    @FXML
    TextField addTemperatureFromUserTextField;
    @FXML
    TextField addHumidityFromUserTextField;
    @FXML
    TextField addWindSpeedFromUserTextField;
    @FXML
    TextField addPressureFromUserTextField;
    @FXML
    Label addMesureAlertLabel;
    //ELEMENTY POBRANIA POMIARU PRZEZ UŻYTKOWNIKA
    @FXML
    ListView<String> usersStationsToTakeMaeasureListView;
    @FXML
    ComboBox<String> takeMeasuresFromUserComboBox;  //wybór rodzaju pomiaru
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
    TextField OWMStationsBrowserTextField;
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
    TextField takeMeasureFromUserBrowserTextField;
    @FXML
    TextField addMesureFromUserBrowserTextField;
    @FXML
    ComboBox<String> claudinessFromIserComboBox;


    //gios
    @FXML
    ListView<String> sensorsListView;
    @FXML
    TableView<SensorData> GIOSTableView;
    @FXML
    TableColumn<SensorData, String> GIOSValueColumn;
    @FXML
    TableColumn<SensorData, String> GIOSDateColumn;

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

    //RESET CODE
    @FXML
    Button sendResetPasswordCodeButton;
    @FXML
    Button sendRegistrationCodeButton;
    @FXML
    void initialize() throws IOException, SQLException, APIException, ParseException {
        startConectionWithDataBase(); //Łączymy się z bazą w celu uwierzytelnienia i dalszej pracy aplikacji
        takeDataFromDBToRepositories();
        addDataFromRepositioriesToView();
        activeRestrictionsOnTextFields();
        activeStationsBrowsers();
        deletePlaceholders();
    }
    private void deletePlaceholders(){
        claudinessTableView.setPlaceholder(new Label(""));
        temperatureTableView.setPlaceholder(new Label(""));
        pressureTableView.setPlaceholder(new Label(""));
        windSpeedTableView.setPlaceholder(new Label(""));
        humidityTableView.setPlaceholder(new Label(""));
        GIOSAirIndexTableView.setPlaceholder(new Label(""));
        GIOSTableView.setPlaceholder(new Label(""));
        measuresFromOWMTableView.setPlaceholder(new Label(""));

    }
    private void startConectionWithDataBase() throws SQLException { //Połączenie aplikacji z bazą danych
        jdbc.getDbConnection();
        jdbcQuery = new JDBCQuery(jdbc);
    }
    private void takeDataFromDBToRepositories() throws SQLException {
         appSettingsRepository =new AppSettingsRepository(jdbcQuery.getAppSettings());
         dustyPlantsRepository=new DustyPlantsRepository( jdbcQuery.getdustyplantsTable());
         owmStationsRepository = new OWMStationsRepository(jdbcQuery.getstationsTable("owmstations"));
         usersStationsRepository =new UserStationsRepository(jdbcQuery.getstationsTable("usersstations"));
         giosStationsRepository=new GIOSStationsRepository(jdbcQuery.getstationsTable("giosstations"));
         giosSensorsRepository=new GIOSSensorsRepository(jdbcQuery.getGiosSensorsFromDataBase());
         owmClaudinesTranslatorRepository=new OWMClaudinesTranslatorRepository(jdbcQuery.getCloudinessTranslatorTableENGNames(), jdbcQuery.getCloudinessTranslatorTablePLNames());
         userMeasuresPLNamesRepository=new UserMeasuresPLNamesRepository();
    }
    private void addDataFromRepositioriesToView(){
        ActualDustyPlantsView actualDustyPlantsView = new ActualDustyPlantsView(actualDustyPlantsListView,dustyPlantsRepository);
        OWMStationsListView.getItems().addAll(owmStationsRepository.getStationNames());
        usersStationsToAddMeasureListView.getItems().addAll(usersStationsRepository.getStationNames());
        usersStationsToTakeMaeasureListView.getItems().addAll(usersStationsRepository.getStationNames());
        GIOSStationsListView.getItems().addAll(giosStationsRepository.getStationNames());
        takeMeasuresFromUserComboBox.getItems().addAll(userMeasuresPLNamesRepository.getNamesOfMeasuresArraylist());
        claudinessFromIserComboBox.getItems().add(""); //Mozliwe dodanie pomiaru bez danych o zachmurzeniu
        claudinessFromIserComboBox.getSelectionModel().select(0); //Pomiar bez danych, a pusty ComboBox to
        // co innego wiec domyślnie wybrany jest bez danych
        claudinessFromIserComboBox.getItems().addAll(owmClaudinesTranslatorRepository.getPolishStringArraylist());
        takeMeasuresFromUserComboBox.getSelectionModel().select(0); //Wartość domyślna do pobrania z danych od
        // userów by combobox nie był pusty

    }
    private void activeRestrictionsOnTextFields(){
        textFieldRestrict.limitCharsForTextField(addMesureFromUserBrowserTextField,40);
        textFieldRestrict.limitCharsForTextField(OWMStationsBrowserTextField,40);
        textFieldRestrict.limitCharsForTextField(takeMeasureFromUserBrowserTextField,40);
        textFieldRestrict.limitCharsForTextField(GIOSStationsBrowserTextField,40);
        textFieldRestrict.onlyTextInTextField(addMesureFromUserBrowserTextField);
        textFieldRestrict.onlyTextInTextField(takeMeasureFromUserBrowserTextField);
        textFieldRestrict.onlyTextInTextField(OWMStationsBrowserTextField);
        textFieldRestrict.onlyTextInTextField(GIOSStationsBrowserTextField);
        textFieldRestrict.onlyDigitsInTextField(addTemperatureFromUserTextField);
        textFieldRestrict.onlyPlusDigitsInTextField(addHumidityFromUserTextField);
        textFieldRestrict.onlyPlusDigitsInTextField(addWindSpeedFromUserTextField);
        textFieldRestrict.onlyPlusDigitsInTextField(addPressureFromUserTextField);
        textFieldRestrict.limitCharsForTextField(addPressureFromUserTextField,8);
        textFieldRestrict.limitCharsForTextField(addWindSpeedFromUserTextField,8);
        textFieldRestrict.limitCharsForTextField(addHumidityFromUserTextField,8);
        textFieldRestrict.limitCharsForTextField(addTemperatureFromUserTextField,8);
    }

    private void activeStationsBrowsers(){

        stationBrowser.searchByNameOnWriteInTextField(OWMStationsBrowserTextField, owmStationsRepository.getStationNames(),OWMStationsListView);
        stationBrowser.searchByNameOnWriteInTextField(addMesureFromUserBrowserTextField, usersStationsRepository.getStationNames(), usersStationsToAddMeasureListView);
        stationBrowser.searchByNameOnWriteInTextField(takeMeasureFromUserBrowserTextField, usersStationsRepository.getStationNames(), usersStationsToTakeMaeasureListView);
        stationBrowser.searchByNameOnWriteInTextField(GIOSStationsBrowserTextField,giosStationsRepository.getStationNames(),GIOSStationsListView);
    }
    //FUNKCJE DOTYCZĄCE LOGOWANIA I REJESTRACJI
    @FXML
    void loginButton() throws SQLException { //Funkcja zajmuje się uwierzytelnianiem i przełącza na główny ekran aplikacji
        if (jdbcQuery.loginUser(appSettingsRepository,loginEmailTextField, passwordPasswordField) == true) {
            registerAndLoginStackPane.setVisible(false);
            mainViewTabPane.setVisible(true);
        } else if (jdbcQuery.loginUser(appSettingsRepository,loginEmailTextField, passwordPasswordField) == false) {
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

        EmailToResetPassword emailToResetPassword = new EmailToResetPassword(emailToResetPasswordTextField,sendResetPasswordCodeButton);
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
    void sendResetPasswordCodeAgain(){
        EmailToResetPassword emailToResetPassword = new EmailToResetPassword(emailToResetPasswordTextField,sendResetPasswordCodeButton);
        Thread threadResetPasswordEmail = new Thread(emailToResetPassword);
        threadResetPasswordEmail.start();
        changePassword1VBox.setVisible(false);
        changePassword2VBox.setVisible(true);
        resetPasswordCode = emailToResetPassword.getResetPasswordCode();
        badCodeResetPasswordLabel.setVisible(false);
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
                if (jdbcQuery.changeUserPassword(emailToResetPasswordTextField, resetPasswordPasswordField) == true) {
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
            if(jdbcQuery.addNewUser(registrationEmailTextField, registrationPasswordPasswordField) == true) {
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
                && jdbcQuery.isAccountAlreadyEmailInDataBase(registrationEmailTextField.getText()) == false
                && registrationPasswordPasswordField.getText().equals(registrationConfirmedPasswordPasswordField.getText()) == true) {
            registerVBox.setVisible(false);
            register2VBox.setVisible(true);
            EmailToRegister emailToRegister = new EmailToRegister(registrationEmailTextField,sendRegistrationCodeButton);
            Thread threadRegisterEmail = new Thread(emailToRegister);
            threadRegisterEmail.start();
            registerCode = emailToRegister.getRegistrationCode();
            registrationCodeAlertLabel.setVisible(false);
        } else if (registerHelper.isEmail(registrationEmailTextField) == false) {
            registrationAlertLabel.setText("Nie prawidłowy adres E-mail");
            registrationAlertLabel.setVisible(true);
        } else if (jdbcQuery.isAccountAlreadyEmailInDataBase(registrationEmailTextField.getText()) == true) {
            registrationAlertLabel.setText("Konto jest już zarejstrowane");
            registrationAlertLabel.setVisible(true);
        } else if (registerHelper.isPasswordStrength(registrationPasswordPasswordField, registrationAlertLabel) == false) {
        } else if (!registrationPasswordPasswordField.equals(registrationConfirmedPasswordPasswordField)) {
            registrationAlertLabel.setText("Hasła nie są jednakowe");
            registrationAlertLabel.setVisible(true);
        }
    }
    @FXML
    void sendResetRegistrationCodeAgain(){
        registerVBox.setVisible(false);
        register2VBox.setVisible(true);
        EmailToRegister emailToRegister = new EmailToRegister(registrationEmailTextField,sendRegistrationCodeButton);
        Thread threadRegisterEmail = new Thread(emailToRegister);
        threadRegisterEmail.start();
        registerCode = emailToRegister.getRegistrationCode();
        registrationCodeAlertLabel.setVisible(false);
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
        try {
            appSettingsRepository = new AppSettingsRepository(jdbcQuery.getAppSettings());
            AddUserMeasureHelper addUserMeasureHelper = new AddUserMeasureHelper(appSettingsRepository);
            jdbcQuery.addMeasuresFromUserToDataBase(addUserMeasureHelper, loginEmailTextField, addPressureFromUserTextField,
                    addTemperatureFromUserTextField, addWindSpeedFromUserTextField, addHumidityFromUserTextField, claudinessFromIserComboBox,
                    idStationFinder.getIDSelectedStation(usersStationsToAddMeasureListView, usersStationsRepository.getStations()),
                    addMesureAlertLabel);
        }
        catch (NullPointerException e) //jeżeli użytkownik nie zaznaczył stacji
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nie wybrano stacji pomiarowej");
            alert.setHeaderText("");
            alert.setContentText("Wybierz stacje pomiarową z menu po lewej stronie");
            alert.showAndWait();
        }

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
        try {
            turnOffAllMeasuresFromUser(); //Wyłączamy poprzedni widok
            clearAllTablesWithMeasuresFromUser(); //Czyścimy poprzednie tabele

            if (takeMeasuresFromUserComboBox.getSelectionModel().getSelectedItem().equals("Temperatura powietrza")) {
                temperatureTableView.setVisible(true);
                ObservableList<TemperatureFromUser> listOfTemperatureResults = FXCollections.observableArrayList(userMeasuresRepository.showTemperaturesFromUsersInCity(idStationFinder.getIDSelectedStation(usersStationsToTakeMaeasureListView, usersStationsRepository.getStations()), jdbcQuery.getTemperaturesFromUserList()));
                dateTempUser.setCellValueFactory(new PropertyValueFactory<>("date"));
                userNameTemp.setCellValueFactory(new PropertyValueFactory<>("userName"));
                temperature.setCellValueFactory(new PropertyValueFactory<>("temperature"));
                temperatureTableView.setItems(listOfTemperatureResults);
            } else if (takeMeasuresFromUserComboBox.getSelectionModel().getSelectedItem().equals("Wilgotność powietrza")) {
                humidityTableView.setVisible(true);
                ObservableList<HumidityFromUser> listOfHumidityResults = FXCollections.observableArrayList(userMeasuresRepository.showHumidityFromUsersInCity(idStationFinder.getIDSelectedStation(usersStationsToTakeMaeasureListView, usersStationsRepository.getStations()), jdbcQuery.getHumidityFromUserList()));
                dateHumidityUser.setCellValueFactory(new PropertyValueFactory<>("date"));
                userNameHumidity.setCellValueFactory(new PropertyValueFactory<>("userName"));
                humidity.setCellValueFactory(new PropertyValueFactory<>("humidity"));
                humidityTableView.setItems(listOfHumidityResults);
            } else if (takeMeasuresFromUserComboBox.getSelectionModel().getSelectedItem().equals("Prędkość wiatru")) {
                windSpeedTableView.setVisible(true);
                ObservableList<WindSpeedFromUser> listOfHumidityResults = FXCollections.observableArrayList(userMeasuresRepository.showWindSpeedFromUsersInCity(idStationFinder.getIDSelectedStation(usersStationsToTakeMaeasureListView, usersStationsRepository.getStations()), jdbcQuery.getWindSpeedFromUserList()));
                dateWindSpeedUser.setCellValueFactory(new PropertyValueFactory<>("date"));
                userNameWindSpeed.setCellValueFactory(new PropertyValueFactory<>("userName"));
                windSpeed.setCellValueFactory(new PropertyValueFactory<>("windSpeed"));
                windSpeedTableView.setItems(listOfHumidityResults);
            } else if (takeMeasuresFromUserComboBox.getSelectionModel().getSelectedItem().equals("Ciśnienie")) {
                pressureTableView.setVisible(true);
                ObservableList<PressureFromUser> listOfPressureResults = FXCollections.observableArrayList(userMeasuresRepository.showPressureFromUsersInCity(idStationFinder.getIDSelectedStation(usersStationsToTakeMaeasureListView, usersStationsRepository.getStations()), jdbcQuery.getPressureFromUserList()));
                datePressureUser.setCellValueFactory(new PropertyValueFactory<>("date"));
                userNamePressure.setCellValueFactory(new PropertyValueFactory<>("userName"));
                pressure.setCellValueFactory(new PropertyValueFactory<>("pressure"));
                pressureTableView.setItems(listOfPressureResults);
            } else if (takeMeasuresFromUserComboBox.getSelectionModel().getSelectedItem().equals("Zachmurzenie")) {
                claudinessTableView.setVisible(true);
                ObservableList<ClaudinessFromUser> listOfClaudinessResults = FXCollections.observableArrayList(userMeasuresRepository.showClaudinessFromUsersInCity(idStationFinder.getIDSelectedStation(usersStationsToTakeMaeasureListView, usersStationsRepository.getStations()), jdbcQuery.getCloudinessFromUserList()));
                dateClaudinessUser.setCellValueFactory(new PropertyValueFactory<>("date"));
                userNameClaudiness.setCellValueFactory(new PropertyValueFactory<>("userName"));
                claudiness.setCellValueFactory(new PropertyValueFactory<>("claudiness"));
                claudinessTableView.setItems(listOfClaudinessResults);
            }
        }
          catch (NullPointerException e) //jeżeli użytkownik nie zaznaczył stacji
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nie wybrano stacji pomiarowej");
            alert.setHeaderText("");
            alert.setContentText("Wybierz stacje pomiarową z menu po lewej stronie");
            alert.showAndWait();
        }
    }
    //DO POBRANIA POMIAROW Z OWM
    @FXML
    void onClickOwmStationsListView()  {
        DataFromOwmPresenter dataFromOwmPresenter=new DataFromOwmPresenter(measuresFromOWMTableView,tempColumn,pressureColumn,
                windColumn,humidityColumn,claudinessColumn,dateOWMColumn,weatherMeasuresFactory,idStationFinder,OWMStationsListView,
                owmStationsRepository,owmClaudinesTranslatorRepository);
        Thread showOwmDataThread =new Thread(dataFromOwmPresenter);
        showOwmDataThread.start();

        }

    @FXML
    void onClickGIOSStation() throws IOException, JSONException, InterruptedException {
        sensorsListView.getItems().clear();
        sensorsListView.getItems().addAll(giosSensorsRepository.getSensorsForSelectedStation(idStationFinder.getIDSelectedStation(GIOSStationsListView,giosStationsRepository.getStations())));
        DataFromGiosStationPresenter dataFromGiosStationPresenter=new DataFromGiosStationPresenter(GIOSAirIndexValueColumn,GIOSAirIndexNameColumn,GIOSAirIndexTableView,GIOSStationsListView,GIOSTableView,sensorsListView,giosSensorsRepository,giosStationsRepository,idStationFinder);
        Thread dataFromGiosStationThread=new Thread(dataFromGiosStationPresenter);
        dataFromGiosStationThread.start();
    }
    @FXML
    void onClickGIOSSensor() throws IOException, JSONException, ParseException {
        DataFromGiosSensorPresenter dataFromGiosSensorPresenter =new DataFromGiosSensorPresenter(giosSensorsRepository,GIOSTableView,GIOSValueColumn,GIOSDateColumn,sensorsListView);
        Thread dataFromGiosSensorThread=new Thread(dataFromGiosSensorPresenter);
        dataFromGiosSensorThread.start();
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
    @FXML
    void onClickRefreshGiosStationsAndSensorsButton() throws SQLException {
        GIOSStationsListView.getItems().clear();
        giosStationsRepository=new GIOSStationsRepository(jdbcQuery.getstationsTable("giosstations"));
        GIOSStationsListView.getItems().addAll(giosStationsRepository.getStationNames());
        stationBrowser.searchByNameOnWriteInTextField(GIOSStationsBrowserTextField,giosStationsRepository.getStationNames(),GIOSStationsListView);
        GIOSStationsBrowserTextField.setText("");

    }
    @FXML
    void onClickRefreshOwmStationsButton() throws SQLException {
        OWMStationsListView.getItems().clear();
        owmStationsRepository = new OWMStationsRepository(jdbcQuery.getstationsTable("owmstations"));
       OWMStationsListView.getItems().addAll(owmStationsRepository.getStationNames());
        stationBrowser.searchByNameOnWriteInTextField(OWMStationsBrowserTextField, owmStationsRepository.getStationNames(),OWMStationsListView);
        OWMStationsBrowserTextField.setText("");
    }
    @FXML
    void onClickRefreshUsersStations() throws SQLException {
        usersStationsToAddMeasureListView.getItems().clear();
        usersStationsToTakeMaeasureListView.getItems().clear();
        usersStationsRepository = new UserStationsRepository(jdbcQuery.getstationsTable("usersstations"));
        usersStationsToTakeMaeasureListView.getItems().addAll(usersStationsRepository.getStationNames());
        usersStationsToAddMeasureListView.getItems().addAll(usersStationsRepository.getStationNames());
        stationBrowser.searchByNameOnWriteInTextField(addMesureFromUserBrowserTextField, usersStationsRepository.getStationNames(), usersStationsToAddMeasureListView);
        stationBrowser.searchByNameOnWriteInTextField(takeMeasureFromUserBrowserTextField, usersStationsRepository.getStationNames(), usersStationsToTakeMaeasureListView);
        addHumidityFromUserTextField.setText("");
        takeMeasureFromUserBrowserTextField.setText("");
    }
    @FXML
    void onClickOpenDocumentationButton() throws IOException {
        OpenFileHelper openFileHelper =new OpenFileHelper();
        openFileHelper.openPdfFile(getClass().getResource("Dokumentacja_uzytkownika.pdf").getPath());
    }

}





